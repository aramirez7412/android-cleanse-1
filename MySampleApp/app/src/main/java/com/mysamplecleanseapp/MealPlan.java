package com.mysamplecleanseapp;

import android.app.ProgressDialog;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.sql.Struct;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by mattcorrente on 5/30/16.
 */



public class MealPlan implements Serializable {

    String urlString;

    // copy constructor
    MealPlan(MealPlan m) {
        this.mealPlan = new ArrayList<ArrayList<MealItem>>();

        for (int i = 0; i < m.getDays(); i++) {
            for (int j = 0; j < m.getListForDay(i).size(); j++) {
                this.mealPlan.get(i).add(j, m.getListForDay(i).get(j));
            }
        }

    }



    MealPlan(JSONObject jsonObject, Context context, ProgressDialog progRef) throws JSONException {



        Set<String> imageURLSet = new TreeSet<>();

        boolean snackBool = false; //used to place snacks into right position

        mealPlan = new ArrayList<ArrayList<MealItem>>();

        dailyFacts = new ArrayList<>();


        DailyFacts tempFacts = new DailyFacts();

        //add temporary items so we can add objects in the right order
        for(int z = 0; z < 10; z++) {
            ArrayList<MealItem> tempList = new ArrayList<>();
            dailyFacts.add(z, tempFacts);
            mealPlan.add(z, tempList);
        }


        JSONArray planAr = jsonObject.getJSONArray("dailyPlans");


        //initialize water progress for each day to 0
        waterArray = new ArrayList<>();
        for (int z = 0; z < planAr.length(); z++) {
            waterArray.add(z, 0);
        }

        //loop through each day
        for(int k = 0; k < planAr.length(); k++) {

            ArrayList<MealItem> dayPlan = new ArrayList<>();

            //initialize list so we can set the elements so that they will be sorted by time of day
            for(int z = 0; z < 5; z++) {
                MealItem tempItem = new MealItem();
                dayPlan.add(tempItem);
            }

            tempFacts = new DailyFacts();
            tempFacts.atAGlance = planAr.getJSONObject(k).getString("atAGlanceInstruction");
            tempFacts.tipOfDay = planAr.getJSONObject(k).getString("tipOfTheDay");
            tempFacts.detoxFact = planAr.getJSONObject(k).getString("detoxFacts");
            tempFacts.dailyInspiration = planAr.getJSONObject(k).getString("dailyInspiration");


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

                JSONObject recipeObject = mealObject.getJSONObject("recipe");
                mealItem.setTitle(recipeObject.getString("name"));
                //set images
                //------------
                    //urlString = mealObject.getString("imgurl");
                    //Picasso.with(context).load(urlString).into(picassoImageTarget(c.getApplicationContext(), "imageDir", mealObject.getString("imgurl")));
                     mealItem.setImageUrl(recipeObject.getString("imgUrl"));
                imageURLSet.add(recipeObject.getString("imgUrl"));

                //------------

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

                switch(mealItem.getHeader().toUpperCase()){



                    case "BREAKFAST":
                        dayPlan.set(0, mealItem);

                        break;
                    case "LUNCH":
                        dayPlan.set(2, mealItem);

                        break;
                    case "DINNER":
                        dayPlan.set(4, mealItem);

                        break;
                    case "SNACK":

                        if(!snackBool)
                            dayPlan.set(1, mealItem);
                        else
                            dayPlan.set(3, mealItem);

                        snackBool = true;

                        break;
                }

            }

            snackBool = false;

            mealPlan.set(planAr.getJSONObject(k).getInt("day")-1, dayPlan);

            dailyFacts.set(planAr.getJSONObject(k).getInt("day")-1, tempFacts);

        }


        startDate = Calendar.getInstance();



        MyTaskParams params = new MyTaskParams(imageURLSet, context, progRef);
        MyAsyncTask myTask = new MyAsyncTask();
        myTask.execute(params);

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

    int getWaterForDay(int day){return waterArray.get(day);}

    Calendar getStartDate( ){return startDate;}
    void setStartDate(Calendar date){startDate = date;}


    void saveWaterForDay(int day, int progress){ waterArray.set(day, progress);}

    DailyFacts getDailyFacts(int day){ return



            dailyFacts.get(day);

    }



    ArrayList<MealItem> getListForDay(int day){
      //  ArrayList<MealItem> ar//= mealPlan.elementAt(day)
        return mealPlan.get(day);
    }

    ArrayList<ArrayList<MealItem>> getPlan(){
        return mealPlan;
    }

    void swapMeal(int day, int mealNum, MealItem newMeal){
        mealPlan.get(day).set(mealNum, newMeal);
        System.out.println("setting day " + day + "'s meal " + mealNum + "   to " + mealPlan.get(day).get(mealNum).getTitle());


    }


    ArrayList<ArrayList<MealItem>> mealPlan;

    ArrayList<Integer> waterArray;

    ArrayList<DailyFacts> dailyFacts;

    Calendar startDate;








}




