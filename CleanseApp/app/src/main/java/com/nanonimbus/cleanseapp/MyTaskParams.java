package com.nanonimbus.cleanseapp;

import android.app.ProgressDialog;
import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


/**
 * Created by mattcorrente on 9/10/16.
 */
class MyTaskParams {
    Set<String> set;
    ArrayList<String> recipeList;
    Context context;
    String  jsonURL;
    ProgressDialog progRef;
    String id;
    ArrayList<PurchaseHelperClass> purchases;
    HashMap<String,String> keyMap;


    MyTaskParams(ArrayList<PurchaseHelperClass> h, Context c, boolean t) {
        purchases = h;
        context = c;

    }

    MyTaskParams(Set<String> s, Context c, ProgressDialog prog) {
        set = s;
        context = c;
        progRef = prog;
    }

    MyTaskParams(Set<String> s, Context c) {
        set = s;
        context = c;
    }


    MyTaskParams(ArrayList<String> s, Context c) {
        recipeList = s;
        context = c;
    }

    MyTaskParams(String s, String i, Context c) {
        jsonURL = s;
        context = c;
        id = i;
    }

    MyTaskParams(String url, Context c) {
        jsonURL = url;
        context = c;
    }



    void instantiateTree(){
        keyMap = new HashMap<String, String>();
    }

    void addPair(String key, String id){
        keyMap.put(key, id);
    }

    String getURL(){
        return jsonURL;
    }

    String getId(){
        return id;
    }
}
