package com.myluco.nytimessearch.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.myluco.nytimessearch.Article;
import com.myluco.nytimessearch.ArticleArrayAdapter;
import com.myluco.nytimessearch.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {
    EditText etQuery;
    Button btSearch;
    GridView gvResults;
    ArrayList<Article> articles;
    ArticleArrayAdapter aaArticle;

    private static String API_KEY = "baf4f7c5b58a6076860b84fe12416362:11:74404265";
    private static String NYTimes_URL = "http://api.nytimes.com/svc/search/v2/articlesearch.json";

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
                        i.putExtra("article",article);
                        //launch activity
                        startActivity(i);
                    }
                }
        );

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

    public void onArticleSearch(View view) {
        String query = etQuery.getText().toString();
//        Toast.makeText(this,query,Toast.LENGTH_LONG).show();
        //http://api.nytimes.com/svc/search/v2/articlesearch.json?api-key=baf4f7c5b58a6076860b84fe12416362:11:74404265
        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();
        params.put("api-key",API_KEY);
        params.put("page", 0);
        params.put("q", query);

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
}
