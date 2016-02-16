package com.myluco.nytimessearch.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.myluco.nytimessearch.listener.EndlessScrollListener;
import com.myluco.nytimessearch.model.Article;
import com.myluco.nytimessearch.adapters.ArticleArrayAdapter;
import com.myluco.nytimessearch.R;
import com.myluco.nytimessearch.model.Settings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {
    private static final int PAGE_LIMIT = 5 ;
    EditText etQuery;
    Button btSearch;
    GridView gvResults;
    ArrayList<Article> articles;
    ArticleArrayAdapter aaArticle;
    Settings settings;
    private static String API_KEY = "baf4f7c5b58a6076860b84fe12416362:11:74404265";
    private static String NYTimes_URL = "http://api.nytimes.com/svc/search/v2/articlesearch.json";
    private static int SETTINGS_ACTIVITY = 1;

    private String query;
    private RequestParams params;
    private AsyncHttpClient client;
    private int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupViews();

    }

    private void setupViews() {
        articles = new ArrayList<Article>();
        settings = new Settings();
        aaArticle = new ArticleArrayAdapter(this,articles);
        etQuery = (EditText)findViewById(R.id.etQuery);
        btSearch = (Button)findViewById(R.id.btSearch);
        gvResults = (GridView)findViewById(R.id.gvResults);
        gvResults.setAdapter(aaArticle);

        gvResults.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //create an Intent to display article
                        Intent i = new Intent(getApplicationContext(), ArticleActivity.class);
                        //get the article to display
                        Article article = articles.get(position);
                        //pass article to intent
//                        i.putExtra("url", article.getUrl());
                        i.putExtra("article", article);
                        //launch activity
                        startActivity(i);
                    }
                }
        );
        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                customLoadMoreDataFromApi(page);
                // or customLoadMoreDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });

    }
    // Append more data into the adapter
    private void customLoadMoreDataFromApi(int offset) {
        // This method probably sends out a network request and appends new data items to your adapter.
        // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
        // Deserialize API response and then construct new objects to append to the adapter
        count++;
        if (count < PAGE_LIMIT) {
            loadMorePages(offset);
        }


    }

    private void loadMorePages(int offset) {
        params.put("page", offset);

        Log.v("DEBUG-PARAMS", params.toString());
        client.get(NYTimes_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.v("DEBUG", response.toString());
                JSONArray articlesJsonResult;

                try {
                    articlesJsonResult = response.getJSONObject("response").getJSONArray("docs");
//                    Log.v("DEBUG", articlesJsonResult.toString());
                    aaArticle.addAll(Article.fromJSONArray(articlesJsonResult));
//                    aaArticle.notifyDataSetChanged();
                    Log.v("DEBUG", articles.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if (activeNetworkInfo != null) {
            Log.v("CONNECTION", activeNetworkInfo.toString());
            if (activeNetworkInfo.isConnectedOrConnecting()) {
                Log.v("CONNECTION-2", "Connected");
            }
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    private String formDate() {

        String s = String.format("%04d%02d%02d", settings.getYear(), settings.getMonth(), settings.getDay());
        Log.v("DEBUG",s);
        return  s ;
    }

    private void doTheSearch(View view) {
        //deal with this count so I do not have problems with the api key - (Limit the number of calls)
        count = 0;

//        Toast.makeText(this,query,Toast.LENGTH_LONG).show();
        //http://api.nytimes.com/svc/search/v2/articlesearch.json?api-key=baf4f7c5b58a6076860b84fe12416362:11:74404265
        client = new AsyncHttpClient();

        params = new RequestParams();
        params.put("api-key",API_KEY);
        params.put("page", 0);
        params.put("q", query);
        if (settings.getYear() > 0) {
            params.put("begin_date",formDate());
        }
        if (settings.isOldest()) {
            params.put("sort","oldest");

        }else {
            params.put("sort","newest");
        }

        params.put("fq=news_desk", settings.getNewsDeskList(getResources()));
        Log.v("DEBUG-PARAMS", params.toString());
        aaArticle.clear();
        client.get(NYTimes_URL,params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.v("DEBUG", response.toString());
                JSONArray articlesJsonResult;

                try {
                    articlesJsonResult = response.getJSONObject("response").getJSONArray("docs");
//                    Log.v("DEBUG", articlesJsonResult.toString());
                    aaArticle.addAll(Article.fromJSONArray(articlesJsonResult));
//                    aaArticle.notifyDataSetChanged();
                    Log.v("DEBUG", articles.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

             });

    }


    public void onArticleSearch(View view) {

        if (!isNetworkAvailable().booleanValue()) {
            Toast.makeText(this,"Cannot Search: Network not Available",Toast.LENGTH_LONG).show();
        }else {
            Log.v("Debug", settings.toString());
            query = etQuery.getText().toString();
            if (query.trim().length()  > 0) {
                doTheSearch(view);
            }else {
               Toast.makeText(this,"Please enter a search query",Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onSettingsSelected(MenuItem item) {
        Intent i = new Intent(getApplicationContext(),SettingsActivity.class);
        i.putExtra("settings", settings);
        startActivityForResult(i, SETTINGS_ACTIVITY);

        Log.v("DEBUG", articles.toString());
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == SETTINGS_ACTIVITY) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                settings = (Settings)data.getSerializableExtra("settings");
            }
        }
    }
}
