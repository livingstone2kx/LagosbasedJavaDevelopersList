package com.example.cstworkstation.lagosbasedjavadeveloperslist;

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
        return developerProfileUrl;
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
}
