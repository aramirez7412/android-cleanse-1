package com.mysampleapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mattcorrente on 5/30/16.
 */
public class MealPlan {


    MealPlan(JSONObject jsonObject) throws JSONException {

        mealPlan = new ArrayList<ArrayList<MealItem>>();


        JSONArray planAr = jsonObject.getJSONArray("days");

        System.out.println(planAr.length() + "---asdfljdslfjdslfjlasdjflkasdjfldsjflsd");

        //loop through each day
        for(int k = 0; k < planAr.length(); k++) {

            ArrayList<MealItem> dayPlan = new ArrayList<>();
            JSONArray mealAr = null;
            try {
                mealAr = planAr.getJSONObject(k).getJSONArray("meals");
            } catch (JSONException e) {
                System.out.println("Failed");
                          }

            //loop through every meal per day
            for (int i = 0; i < mealAr.length(); i++) {
                MealItem mealItem = new MealItem();
                JSONArray tempAr;
                String tempString = "";
                JSONObject mealObject = mealAr.getJSONObject(i);

               // mealItem.setType(mealObject.getString("type"));

                mealItem.setHeader(mealObject.getString("time"));
                mealItem.setTitle(mealObject.getString("meal"));
                //set images
                //------------
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

                System.out.println(mealItem.getDirections());




//                mealItem.setServings(mealObject.getInt("servings"));
//                //set ingredients
//                //---------------------------------------------------
//                tempAr = mealObject.getJSONArray("ingredients");
//                for (int j = 0; j < tempAr.length(); j++) {
//                    tempString += "•" + tempAr.getString(j) + "\n";
//                }
//                mealItem.setIngredients(tempString);
//                //---------------------------------------------------
//                //set directions
//                //---------------------------------------------------
//                tempString = "";
//                tempAr = mealObject.getJSONArray("directions");
//                for (int j = 0; j < tempAr.length(); j++) {
//                    tempString += (j + 1) + ": " + tempAr.getString(j) + "\n";
//                }
//                mealItem.setDirections(tempString);
//                //---------------------------------------------------

                dayPlan.add(mealItem);
            }

            mealPlan.add(k, dayPlan);
        }

    }

    void toggleCompletion(int day, int meal){
        mealPlan.get(day).get(meal).toggleComplete();
    }

    Boolean isCompleted(int day, int meal){
       return mealPlan.get(day).get(meal).isCompleted();
    }

    int getDays(){
        return mealPlan.size();
    }

    int getMealCountForDay(int day){
        return mealPlan.get(day).size();
    }

    MealItem getMeal(int day, int meal){
        return mealPlan.get(day).get(meal);
    }

    ArrayList<MealItem> getListForDay(int day){
      //  ArrayList<MealItem> ar//= mealPlan.elementAt(day)
        return mealPlan.get(day);
    }


    ArrayList<ArrayList<MealItem>> mealPlan;
}
