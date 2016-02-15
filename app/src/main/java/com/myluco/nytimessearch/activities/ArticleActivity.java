package com.myluco.nytimessearch.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.myluco.nytimessearch.model.Article;
import com.myluco.nytimessearch.R;

public class ArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get stuff form intent
//        String url = getIntent().getStringExtra("url");
        Article article = (Article)getIntent().getSerializableExtra("article");
        WebView wvArticle = (WebView)findViewById(R.id.wvArticle);
        wvArticle.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //article will be embedded in activity
//                view.loadUrl(url);
//                return true;
                return false;
            }
        });
        wvArticle.loadUrl(article.getUrl());

    }

}
