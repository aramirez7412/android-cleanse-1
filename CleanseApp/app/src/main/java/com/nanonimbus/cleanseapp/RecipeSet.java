package com.nanonimbus.cleanseapp;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by mattcorrente on 8/20/16.
 */
public class RecipeSet  implements Serializable  {

    RecipeSet(){
        recipeSet = new ArrayList<MealItem>();
    }

    void addRecipe(MealItem m){
        if(!recipeSet.contains(m))
            recipeSet.add(m);
    }


    RecipeSet(JSONObject jsonObject, Context context)  throws JSONException{

        Set<String> imageURLSet = new TreeSet<>();



        recipeSet = new ArrayList<MealItem>();

        recipeSetSaveTitle = "";
        recipeSetTitle =  "temporary";


        JSONArray setAr = jsonObject.getJSONArray("recipes");

        //for testing
        //******************************************************************************************
        int setId;
        setId = setAr.getJSONObject(0).getInt("recipeSetId");
        if(setId == 1){
            recipeSetSaveTitle = "genericSet";
            recipeSetTitle = "Basic Set";
        }
        else{
            recipeSetSaveTitle = "something";
            recipeSetTitle = jsonObject.getString("name");
        }
        //******************************************************************************************


        //loop through each meal
        for(int i = 0; i < setAr.length(); i++) {

            MealItem mealItem = new MealItem();
            JSONArray tempAr;
            String tempString = "";
            JSONObject mealObject = setAr.getJSONObject(i);

            // mealItem.setType(mealObject.getString("type"));

            //mealItem.setHeader(mealObject.getString("time"));
            mealItem.setTitle(mealObject.getString("name"));
            //set images
            //------------
            //urlString = mealObject.getString("imgurl");
            //Picasso.with(context).load(urlString).into(picassoImageTarget(c.getApplicationContext(), "imageDir", mealObject.getString("imgurl")));
            mealItem.setImageUrl(mealObject.getString("imgUrl"));

            imageURLSet.add(mealObject.getString("imgUrl"));
            //------------
           // JSONObject recipeObject = mealObject.getJSONObject("recipe");

            mealItem.setServings(mealObject.getString("serves"));
            //set ingredients
            //---------------------------------------------------
            JSONArray ingredientsArray = mealObject.getJSONArray("ingredients");
            for (int j = 0; j < ingredientsArray.length(); j++) {
                if(j == 0)
                   tempString += "•" + ingredientsArray.getString(j);
                else
                    tempString += "\n•" + ingredientsArray.getString(j);
            }
            mealItem.setIngredients(tempString);
            //---------------------------------------------------
            mealItem.setDirections(mealObject.getString("instructions"));



            recipeSet.add(mealItem);


        }

        Collections.sort(recipeSet);

        MyTaskParams params = new MyTaskParams(imageURLSet, context);
        MyAsyncTask myTask = new MyAsyncTask();
        myTask.execute(params);
        //saveImages(imageURLSet, context);

    }

    void sort(){
        Collections.sort(recipeSet);

    }

    ArrayList<MealItem> getRecipeSet(){
        return recipeSet;
    }

    String getRecipeSetTitle(){
        //return recipeSetTitle
                return recipeSetTitle;
    }






    public static void saveImages(Set<String> mySet, Context context) {

        URL url;
        InputStream is;
        File file;


        for(String theURL : mySet) {

            try {

                url = new URL(theURL);
                is = url.openStream();

                file = new File(context.getFilesDir(), theURL);

                if (!file.exists()) {
                    System.out.println("file did not exist");
                    //o.setLoaded(false);


                    OutputStream os = new FileOutputStream(file);

                    byte[] b = new byte[2048];
                    int length;

                    while ((length = is.read(b)) != -1) {
                        os.write(b, 0, length);
                    }

                    is.close();
                    os.close();

                    System.out.println("Successfully saved image " + theURL);

                }
            }catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



//    void loadPicassoPicFromFileAsync(final ImageView v, final MealItem o) {
//
//
//        File file = new File(getContext().getFilesDir(), o.getImageUrl());
//
//        if (!file.exists()) {
//            System.out.println("file did not exist");
//            //o.setLoaded(false);
//
//            Picasso.with(getContext()).load(o.getImageUrl()).into(v, new com.squareup.picasso.Callback() {
//                @Override
//                public void onSuccess() {
//
//
//                    // The types specified here are the input data type, the progress type, and the result type
//                    class MyAsyncTask extends AsyncTask<Bitmap, Void, Bitmap> {
//                        @Override
//                        protected Bitmap doInBackground(Bitmap... params) {
//                            // Some long-running task like downloading an image.
//
//                            File file = new File(getContext().getFilesDir(), o.getImageUrl());
//
//                            File parent = file.getParentFile();
//                            if (!parent.exists() && !parent.mkdirs()) {
//                                throw new IllegalStateException("Couldn't create dir: " + parent);
//                            }
//
//                            try {
//                                file.createNewFile();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//
//                            System.out.println(file.exists() + " does it exists");
//
//                            FileOutputStream fos = null;
//                            try {
//
//                                // fos = getContext().openFileOutput(file.getName(), Context.MODE_PRIVATE);
//                                fos = new FileOutputStream(file);
//
//                                //Bitmap bitmap = ((BitmapDrawable)v.getDrawable()).getBitmap();
//
//
//                                params[0].compress(Bitmap.CompressFormat.PNG, 100, fos);
//                                //}
//
//                                o.setLoaded(true);
//                                Log.i("image", "image saved to >>>" + file.getAbsolutePath());
//
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            } finally {
//                                try {
//                                    fos.close();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//
//                            return null;
//                        }
//
//                        protected void onPreExecute() {
//                            // Runs on the UI thread before doInBackground
//                        }
//
//
////                            protected void onProgressUpdate(Progress... values) {
////                                // Executes whenever publishProgress is called from doInBackground
////                            }
//
//                        protected void onPostExecute(Bitmap result) {
//                            // This method is executed in the UIThread
//                            // with access to the result of the long running task
//                            //v.setImageBitmap(result);
//                        }
//                    }
//
//
//                    new MyAsyncTask().execute(((BitmapDrawable) v.getDrawable()).getBitmap());
//
//                }
//
//                @Override
//                public void onError() {
//                    System.out.println("failure");
//                }
//            });
//        }
//    }

    String getRecipeSetSaveTitle(){return recipeSetSaveTitle;};

    String recipeSetSaveTitle;
    String recipeSetTitle;
    ArrayList<MealItem> recipeSet;
}
