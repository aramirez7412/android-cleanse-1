package com.nanonimbus.cleanseapp;

import java.util.ArrayList;

/**
 * Created by mattcorrente on 10/15/16.
 */
public class StoreListing {

    public StoreListing() {
        this.recipeNames = new ArrayList<>();
    }

    public String getRecipeName(int i) {
        return recipeNames.get(i);
    }

    public void AddRecipeName(String recipeName) {
        this.recipeNames.add(recipeName);
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public String getTitle() {
        return title;
    }

    public int getRecipeCount() {
        return recipeNames.size();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    ArrayList<String> recipeNames;
    String imageLocation;
    String title;
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
