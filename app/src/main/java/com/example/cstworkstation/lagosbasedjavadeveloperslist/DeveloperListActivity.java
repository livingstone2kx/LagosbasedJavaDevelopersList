package com.example.cstworkstation.lagosbasedjavadeveloperslist;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import java.util.ArrayList;

public class DeveloperListActivity extends AppCompatActivity {

    public static final String DEVELOPER_DETAIL_KEY = "developer";
    private ListView lvdevs;
    private DeveloperAdapter developerAdapter;
    private  DeveloperClient client;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_list);
        lvdevs = (ListView) findViewById(R.id.lvdevs);
        ArrayList<Developer> aDevelopers = new ArrayList<Developer>();
        developerAdapter = new DeveloperAdapter(this.aDevelopers);
        lvdevs.setAdapter(developerAdapter);
        progress = (ProgressBar) findViewById(R.id.progress);
        setupBookSelectedListener();

        // Fetch the data remotely
        // fetchDevelopers();
    }

    public void setupBookSelectedListener() {
        lvdevs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Launch the detail view passing book as an extra
                Intent intent = new Intent(DeveloperListActivity.this, DeveloperDetailActivity.class);
                intent.putExtra(DEVELOPER_DETAIL_KEY, developerAdapter.getItem(position));
                startActivity(intent);
            }
        });

    // Executes an API call to the Github search endpoint, parses the results
    // Converts them into an array of developer objects and adds them to the adapter
    private void fetchDevelopers(String query) {
        // Show progress bar before making network request
        progress.setVisibility(ProgressBar.VISIBLE);
    ...

        client.getDevelopers(query, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    // hide progress bar
                    progress.setVisibility(ProgressBar.GONE);
          ...
                } catch (JSONException e) {
                    // Invalid JSON format, show appropriate error.
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progress.setVisibility(ProgressBar.GONE);
            }
        });
    }

    // Executes an API call to the GitHub search endpoint, parses the results
    // Converts them into an array of developer objects and adds them to the adapter
    private void fetchDevelopers() {
        client = new DeveloperClient();
        client.getDevelopers("Lagos", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray dev = null;
                    if(response != null) {
                        // Get the dev json array
                        dev = response.getJSONArray("dev");
                        // Parse json array into array of model objects
                        final ArrayList<Developer> developers = Developer.fromJson(dev);
                        // Remove all developers from the adapter
                        developerAdapter.clear();
                        // Load model objects into the adapter
                        for (Developer developer : developers) {
                            developerAdapter.add(developer); // add developer through the adapter
                        }
                        developerAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    // Invalid JSON format, show appropriate error.
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_developer_list, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Fetch the data remotely
                fetchDevelopers(query);
                // Reset SearchView
                searchView.clearFocus();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                searchItem.collapseActionView();
                // Set activity title to search query
                DeveloperListActivity.this.setTitle(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

}



