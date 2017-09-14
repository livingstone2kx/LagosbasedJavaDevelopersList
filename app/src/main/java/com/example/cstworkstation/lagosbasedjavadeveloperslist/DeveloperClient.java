package com.example.cstworkstation.lagosbasedjavadeveloperslist;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by CST Workstation on 9/11/2017.
 */

public class DeveloperClient {
    private static final String API_BASE_URL = "https://api.github.com";
    private AsyncHttpClient client;

    public DeveloperClient (){
        this.client = new AsyncHttpClient();
    }

    private String getApiUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }

    // Method for accessing the search API
    public void getDevelopers(final String query, JsonHttpResponseHandler handler) {
        try {
            String url = getApiUrl("/search/users?q=language:java+location:lagos");
            client.get(url + URLEncoder.encode(query, "utf-8"), handler);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


}
