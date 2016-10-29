package com.nanonimbus.cleanseapp;

/**
 * Created by mattcorrente on 10/25/16.
 */
public class PurchaseHelperClass {

    PurchaseHelperClass(String i, String s){
        id = i;
        link = s;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    String id;
    String link;




}
