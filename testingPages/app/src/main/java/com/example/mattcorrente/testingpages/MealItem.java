package com.example.mattcorrente.testingpages;

/**
 * Created by mattcorrente on 5/28/16.
 */
public class MealItem {

    String type;
    String header;
    String title;
    String imageUrl;
    int servings;
    String ingredients;
    String directions;
    boolean completed;

    MealItem() {
        imageUrl = "@mipmap/sample_food";
        completed = false;
    }

    String getType(){
        return type;
    }

    String getHeader(){
        return header;
    }

    String getTitle(){
        return title;
    }

    String getImageUrl(){
        return imageUrl;
    }

    int getServings(){
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
    void setServings(int temp){
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

    void complete() {
        completed = true;
    }


}

