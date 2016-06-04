package com.example.mattcorrente.testingpages;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by mattcorrente on 5/30/16.
 */
public class MealPlan {


    MealPlan(JSONObject planObject) throws JSONException {

        mealPlan = new ArrayList<ArrayList<MealItem>>();

        System.out.println(planObject.length() + "---asdfljdslfjdslfjlasdjflkasdjfldsjflsd");

        //loop through each day
        for(int k = 0; k < planObject.length(); k++) {

            System.out.println("we gotta k of " + k);
            ArrayList<MealItem> dayPlan = new ArrayList<>();
            JSONArray mealAr = null;
            try {
                mealAr = planObject.getJSONArray(Integer.toString(k));
            } catch (JSONException e) {
                System.out.println("fuckkkkkkk");            }

            //loop through every meal per day
            for (int i = 0; i < mealAr.length(); i++) {
                MealItem mealItem = new MealItem();
                JSONArray tempAr;
                String tempString = "";
                JSONObject mealObject = mealAr.getJSONObject(i);

                mealItem.setType(mealObject.getString("type"));

                mealItem.setHeader(mealObject.getString("cellHeader"));
                mealItem.setTitle(mealObject.getString("mealTitle"));
                //set images
                //------------
                //------------
                mealItem.setServings(mealObject.getInt("servings"));
                //set ingredients
                //---------------------------------------------------
                tempAr = mealObject.getJSONArray("ingredients");
                for (int j = 0; j < tempAr.length(); j++) {
                    tempString += "•" + tempAr.getString(j) + "\n";
                }
                mealItem.setIngredients(tempString);
                //---------------------------------------------------
                //set directions
                //---------------------------------------------------
                tempString = "";
                tempAr = mealObject.getJSONArray("directions");
                for (int j = 0; j < tempAr.length(); j++) {
                    tempString += (j + 1) + ": " + tempAr.getString(j) + "\n";
                }
                mealItem.setDirections(tempString);
                //---------------------------------------------------

                dayPlan.add(mealItem);
            }

            mealPlan.add(k, dayPlan);
        }

    }

    void setCompleted(int day, int meal){
        mealPlan.get(day).get(meal).complete();
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
