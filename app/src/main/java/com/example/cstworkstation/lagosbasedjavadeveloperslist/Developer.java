package com.example.cstworkstation.lagosbasedjavadeveloperslist;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by CST Workstation on 9/11/2017.
 */

public class Developer {
    private String developerName;
    private String developerUsername;
    private String developerProfileUrl;
    private String followers;
    private String followings;
    private String repositories;

    //Get Developer Name
    public String getDaveloperName(){
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
            if (jsonObject.has("username")){
                developer.developerUsername = jsonObject.getString(developer.developerUsername);
            }
        }
        catch (JSONException e){
            e.printStackTrace();
            return null;
        }
        //Return new object
        return developer;
    }
}
