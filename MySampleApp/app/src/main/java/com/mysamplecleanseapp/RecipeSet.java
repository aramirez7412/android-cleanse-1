package com.mysamplecleanseapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mattcorrente on 8/20/16.
 */
public class RecipeSet  implements Serializable  {

    RecipeSet(JSONObject jsonObject)  throws JSONException{


        recipeSet = new ArrayList<MealItem>();

        recipeSetTitle =  jsonObject.getString("recipeSet");

        JSONArray setAr = jsonObject.getJSONArray("meals");


        //loop through each meal
        for(int i = 0; i < setAr.length(); i++) {

            MealItem mealItem = new MealItem();
            JSONArray tempAr;
            String tempString = "";
            JSONObject mealObject = setAr.getJSONObject(i);

            // mealItem.setType(mealObject.getString("type"));

            mealItem.setHeader(mealObject.getString("time"));
            mealItem.setTitle(mealObject.getString("meal"));
            //set images
            //------------
            //urlString = mealObject.getString("imgurl");
            //Picasso.with(context).load(urlString).into(picassoImageTarget(c.getApplicationContext(), "imageDir", mealObject.getString("imgurl")));
            mealItem.setImageUrl(mealObject.getString("imgurl"));
            //------------
            JSONObject recipeObject = mealObject.getJSONObject("recipe");

            mealItem.setServings(recipeObject.getString("serves"));
            //set ingredients
            //---------------------------------------------------
            JSONArray ingredientsArray = recipeObject.getJSONArray("ingredients");
            for (int j = 0; j < ingredientsArray.length(); j++) {
                tempString += "•" + ingredientsArray.getString(j) + "\n";
            }
            mealItem.setIngredients(tempString);
            //---------------------------------------------------
            mealItem.setDirections(recipeObject.getString("instructions"));



            recipeSet.add(mealItem);


        }

    }

    ArrayList<MealItem> getRecipeSet(){
        return recipeSet;
    }

    String getRecipeSetTitle(){
        //return recipeSetTitle
                return recipeSetTitle;
    }

    String recipeSetTitle;
    ArrayList<MealItem> recipeSet;
}
