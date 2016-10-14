package com.nanonimbus.cleanseapp;

import android.app.ProgressDialog;
import android.content.Context;

import java.util.Set;

/**
 * Created by mattcorrente on 9/10/16.
 */
class MyTaskParams {
    Set<String> set;
    Context context;
    String  jsonURL;
    ProgressDialog progRef;

    MyTaskParams(Set<String> s, Context c, ProgressDialog prog) {
        set = s;
        context = c;
        progRef = prog;
    }

    MyTaskParams(Set<String> s, Context c) {
        set = s;
        context = c;
    }

    MyTaskParams(String url, Context c) {
        jsonURL = url;
        context = c;
    }
}
