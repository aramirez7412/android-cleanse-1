package com.mysampleapp;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by mattcorrente on 8/6/16.
 */
public class User implements Serializable{

    String userName;
    String sessionAuthToken;
    String apiKey;
    boolean hasPlan;
    String id;


    User(){
        userName = "";
        sessionAuthToken = "";
        apiKey = "";
        id = "";
        hasPlan = false;
    }

    void setPlan(boolean b){ hasPlan = b;};
    Boolean getPlan(){ return hasPlan;};
    void setUserName(String n){userName = n;};
    String getUserName(){return userName;};
    void setUserId(String i){id = i;};
    String getUserId(){return id;};


}
