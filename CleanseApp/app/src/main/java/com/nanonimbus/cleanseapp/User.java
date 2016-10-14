package com.nanonimbus.cleanseapp;

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
