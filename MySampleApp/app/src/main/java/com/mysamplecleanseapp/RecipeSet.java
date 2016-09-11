package com.mysamplecleanseapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

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
import java.sql.Struct;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by mattcorrente on 8/20/16.
 */
public class RecipeSet  implements Serializable  {

    RecipeSet(JSONObject jsonObject, Context context)  throws JSONException{

        Set<String> imageURLSet = new TreeSet<>();

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

            imageURLSet.add(mealObject.getString("imgurl"));
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

        MyTaskParams params = new MyTaskParams(imageURLSet, context);
        MyAsyncTask myTask = new MyAsyncTask();
        myTask.execute(params);
        //saveImages(imageURLSet, context);

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


    String recipeSetTitle;
    ArrayList<MealItem> recipeSet;
}
