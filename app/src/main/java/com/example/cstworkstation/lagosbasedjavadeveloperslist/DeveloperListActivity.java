package com.example.cstworkstation.lagosbasedjavadeveloperslist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class DeveloperListActivity extends AppCompatActivity {

    private ListView lvdevs;
    private DeveloperAdapter developerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_list);
        lvdevs = (ListView) findViewById(R.id.lvdevs);
        ArrayList<Developer> aDevelopers = new ArrayList<Developer>();
        developerAdapter = new DeveloperAdapter(this. aDevelopers);
        lvdevs.setAdapter(developerAdapter);
    }
}
