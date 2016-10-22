package com.nanonimbus.cleanseapp;

import android.content.Context;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mattcorrente on 8/6/16.
 */
public class User implements Serializable{

    String userName;
    String sessionAuthToken;
    String apiKey;
    boolean hasPlan;
    String id;
    ArrayList<String> recipeSetPaths;


    User(Context ctx){
        userName = "";
        sessionAuthToken = "";
        apiKey = "";
        id = "";
        hasPlan = false;
        recipeSetPaths = new ArrayList<>();
        File file = new File(ctx.getFilesDir() + "/recipeSet/", "genericSet");
        addRecipeSetPath(file.getAbsolutePath());

    }

    void setPlan(boolean b){ hasPlan = b;};
    Boolean getPlan(){ return hasPlan;};
    void setUserName(String n){userName = n;};
    String getUserName(){return userName;};
    void setUserId(String i){id = i;};
    String getUserId(){return id;};
    void addRecipeSetPath(String temp){recipeSetPaths.add(temp);};

    String getRecipeSetPath(int i){return recipeSetPaths.get(i);};
    int getRecipeCount(){return recipeSetPaths.size();};


}
