package com.nanonimbus.cleanseapp;

import android.app.ProgressDialog;
import android.content.Context;

import java.util.Set;

/**
 * Created by mattcorrente on 9/10/16.
 */
class MyTaskParamsRecipeSets {
    Set<String> set;
    Context context;
    String  jsonURL;
    ProgressDialog progRef;

    MyTaskParamsRecipeSets(Set<String> s, Context c, ProgressDialog prog) {
        set = s;
        context = c;
        progRef = prog;
    }

    MyTaskParamsRecipeSets(Set<String> s, Context c) {
        set = s;
        context = c;
    }

    MyTaskParamsRecipeSets(String url, Context c) {
        jsonURL = url;
        context = c;
    }
}