package com.myluco.nytimessearch.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by lcc on 2/13/16.
 */
public class Article implements Serializable{
    private String url;
    public final static String DEFAULT_IMAGE_URL="http://i.imgur.com/GIfRrEd.png";

    public String getUrl() {
        return url;
    }

    public String getHeadline() {
        return headline;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    private String headline;
    private String thumbnailUrl;
    private String NYTimes_URL_PREFIX = "http://www.nytimes.com/";
    public Article (JSONObject jsonObject) {

        try {
            url = jsonObject.getString("web_url");
            headline = jsonObject.getJSONObject("headline").getString("main");
            JSONArray multimedia = jsonObject.getJSONArray("multimedia");
            if (multimedia.length() > 0) {
                JSONObject multimediaElement = multimedia.getJSONObject(0);
                thumbnailUrl = NYTimes_URL_PREFIX + multimediaElement.getString("url");
            } else {
                thumbnailUrl = "";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<Article> fromJSONArray (JSONArray articlesJson) {
        ArrayList<Article> result = new ArrayList<Article>();
        for (int i = 0; i < articlesJson.length(); i++) {
            try {
                result.add(new Article(articlesJson.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
