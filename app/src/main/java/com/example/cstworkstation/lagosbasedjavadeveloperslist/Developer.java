package com.example.cstworkstation.lagosbasedjavadeveloperslist;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.jar.Attributes;

/**
 * Created by CST Workstation on 9/11/2017.
 */

public class Developer implements Serializable {
    private String developerName;
    private String developerUsername;
    private String developerProfileUrl;
    private String developerProfilePictureUrl;
    private String followers;
    private String followings;
    private String repositories;

    //Get Developer Name
    public String getDeveloperName(){
        return developerName;
    }

    // Get Developer Username
    public String getDeveloperUsername(){
        return developerUsername;
    }

    //Get Developer Profile URL
    public String getDeveloperProfileUrl(){
        return developerProfileUrl = "https://api.github.com/users/" + developerUsername;
    }

    //Get Developer Profile Picture URL
    public String getDeveloperProfilePictureUrl(){
        return developerProfilePictureUrl = "https://api.github.com/users/" + developerUsername + ".png";
    }

    //Get Numer od Developer's followers
    public String getFollowers() {
        return followers;
    }

    //Get number of people Developer is following
    public String getFollowings() {
        return followings;
    }

    //Get number of repositories owned by developer
    public String getRepositories() {
        return repositories;
    }

    //Returns a Developer detail given the expected JSON
    public static Developer fromJson(JSONObject jsonObject){
        Developer developer = new Developer();
        try {
            //Deserialize json into object fields
            //Check if a username is specified
            if (jsonObject.has("username")) {
                developer.developerUsername = jsonObject.getString(developer.developerUsername);
            } else if (jsonObject.has("Name")) {
                final JSONArray devs = jsonObject.getJSONArray("Name");
                developer.developerUsername = devs.getString(0);
            }
            developer.developerName = jsonObject.has("Name") ? jsonObject.getString("Name"): "";
        }
        catch (JSONException e){
            e.printStackTrace();
            return null;
        }
        //Return new object
        return developer;

        // Decodes array of Developer json results into business model objects
        public static ArrayList<Developer> fromJson(JSONArray jsonArray) {
            ArrayList<Developer> developers = new ArrayList<Developer>(jsonArray.length());
            // Process each result in json array, decode and convert to business
            // object
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject developerJson = null;
                try {
                    developerJson = jsonArray.getJSONObject(i);
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
                Developer developer = Developer.fromJson(developerJson);
                if (developer != null) {
                    developers.add(developers);
                }
            }
            return developers;
        }
    }
    }
}
