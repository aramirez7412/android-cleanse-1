package com.mysamplecleanseapp;

import java.io.Serializable;

/**
 * Created by mattcorrente on 5/28/16.
 */
public class MealItem implements Serializable, Comparable<MealItem> {

    String type;
    String header;
    String title;
    String imageUrl;
    String servings;
    String ingredients;
    String directions;
    boolean loaded;
    boolean completed;

    MealItem() {
        imageUrl = "@mipmap/sample_food";
        completed = false;
        loaded = false;
    }

    String getType(){
        return type;
    }

    boolean isLoaded() { return loaded; };

    void setLoaded(boolean b) { loaded = b; };

    String getHeader(){
        return header;
    }

    String getTitle(){
        return title;
    }

    String getImageUrl(){
        return imageUrl;
    }

    String getServings(){
        return servings;
    }

    String getIngredients(){
        return ingredients;
    }

    String getDirections(){
        return directions;
    }

    void setHeader(String temp){
        header = temp;
    }

    void setTitle(String temp){
        title = temp;
    }
    void setImageUrl(String temp){
        imageUrl = temp;
    }
    void setServings(String temp){
        servings = temp;
    }
    void setIngredients(String temp){
        ingredients = temp;
    }

    void setDirections(String temp){
        directions = temp;
    }

    void setType(String temp){
        type = temp;
    }

    boolean isCompleted(){
        return completed;
    }

    void toggleComplete(){
        completed = !completed;
    }


    @Override
    public boolean equals(Object o)
    {
       return this.getTitle().equals(((MealItem) o).getTitle());
    }

    @Override
    public int compareTo(MealItem another) {
        return this.getTitle().compareTo(another.getTitle());
    }
}

