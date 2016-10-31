package com.nanonimbus.cleanseapp;

import android.content.Context;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

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
    int day;
    Calendar calInstance;


    User(Context ctx){
        userName = "";
        sessionAuthToken = "";
        apiKey = "";
        id = "";
        hasPlan = false;
        recipeSetPaths = new ArrayList<>();
        File file = new File(ctx.getFilesDir() + "/recipeSet/", "genericSet");
        addRecipeSetPath(file.getAbsolutePath());
        day = 0;

        calInstance = Calendar.getInstance();
        calInstance.set(Calendar.HOUR_OF_DAY, 0);
        calInstance.set(Calendar.MINUTE, 0);
        calInstance.set(Calendar.SECOND, 0);
        calInstance.set(Calendar.MILLISECOND, 0);
        System.out.println("initialized user to time " + calInstance.getTime());
    }

    void setPlan(boolean b){ hasPlan = b;};
    Boolean getPlan(){ return hasPlan;};
    void setUserName(String n){userName = n;};
    String getUserName(){return userName;};
    void setUserId(String i){id = i;};
    String getUserId(){return id;};
    void addRecipeSetPath(String temp){recipeSetPaths.add(temp);};

    void setCurrentCalInstance(Calendar i){calInstance = i;};
    Calendar getCurrentCalendarInstance(){return calInstance;};
    int getCurrentDayOfPlan(){return day;};
    void setCurrentDayOfPlay(int i){day = i;};


    String getRecipeSetPath(int i){return recipeSetPaths.get(i);};
    int getRecipeCount(){return recipeSetPaths.size();};

    Boolean checkIfSetIsAdded(String path){

        for (int i = 0; i < recipeSetPaths.size(); i++) {
            System.out.println("testing " + recipeSetPaths.get(i) + " with " + path + " is " + recipeSetPaths.get(i).equals(path));
            if(recipeSetPaths.get(i).equals(path))
                return true;
        }

        return false;

    }

    void resetPurchases(Context ctx){
        System.out.println("resetting purchases");
        recipeSetPaths = new ArrayList<>();
        File file = new File(ctx.getFilesDir() + "/recipeSet/", "genericSet");
        addRecipeSetPath(file.getAbsolutePath());
    }



}
