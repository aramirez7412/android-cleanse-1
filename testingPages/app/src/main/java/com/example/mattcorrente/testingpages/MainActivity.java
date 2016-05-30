package com.example.mattcorrente.testingpages;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ListView testList;
    ViewGroup addMe;


    String temp = "{\n" +
            "   \"meal\": [\t\n" +
            "      {\n" +
            "         \"type\":\"recipe\",\n" +
            "         \"cellHeader\": \"Breakfast\",\n" +
            "         \"mealTitle\": \"Beef Stew\",\n" +
            "         \"images\": \"@mipmap/sample_food\",\n" +
            "         \"servings\": 4,\n" +
            "         \"ingredients\": [\"cheese\", \"eggs\"],\n" +
            "         \"directions\": [\"milk cow\", \"bake potatoes\", \"code\"]\n" +
            "      },\t \n" +
            "      {\n" +
            "        \"type\":\"recipe\",\n" +
            "         \"cellHeader\": \"Lunch\",\n" +
            "         \"mealTitle\": \"Chicken Nuggets\",\n" +
            "         \"images\": \"@mipmap/sample_food\",\n" +
            "         \"servings\": 3,\n" +
            "         \"ingredients\": [\"chicken\", \"nuggets\"],\n" +
            "         \"directions\": [\"go to mcDons\", \"give them money\"]\n" +
            "      },\t \n" +
            "      {\n" +
            "        \"type\":\"recipe\",\n" +
            "         \"cellHeader\": \"Dinner\",\n" +
            "         \"mealTitle\": \"Hot Dogs\",\n" +
            "         \"images\": \"@mipmap/sample_food\",\n" +
            "         \"servings\": 900,\n" +
            "         \"ingredients\": [\"1000 packages of hot dogs\"],\n" +
            "         \"directions\": [\"grill those puppies up\", \"eat til you puke\"]\n" +
            "      }\n" +
            "   ]\n" +
            "}";



    //variables used for dailyMealList
    ExpandableListView expandableListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //will need to dynamically change with day
        ((TextView) findViewById(R.id.mealListDayHeader)).setText("Day 1");

        ArrayList<MealItem> list = new ArrayList<>();


        try {
            JSONObject jsonObject = new JSONObject(temp);
            MealPlan mealPlan = new MealPlan(jsonObject.getJSONArray("meal"));

            //set up one day, when adding more days will need to add another for loop
            //to iterate through all days
            for(int i = 0; i < mealPlan.getMealCountForDay(0); i++){
                list.add(mealPlan.getMeal(0,i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }




        testList = (ListView) findViewById(R.id.testListView);
        addMe = (ViewGroup) findViewById(R.id.recipeItem);



         MealItemAdapter adapter = new MealItemAdapter(this, R.layout.meal_tracker_recipe_item, list);
        testList.setAdapter(adapter);


    }


}


 class MealItemAdapter extends ArrayAdapter<MealItem> {

    private ArrayList<MealItem> items;

    public MealItemAdapter(Context context, int textViewResourceId, ArrayList<MealItem> items) {
        super(context, textViewResourceId, items);
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.meal_tracker_recipe_item, null);
            final TextView recipeHeader = (TextView) v.findViewById(R.id.recipeHeader);


             final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            mealComplete(currentSelection);
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };



            final AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
            builder.setMessage("Have You Completed This Meal?\n(option cannot be undone)").setPositiveButton("YES", dialogClickListener)
                    .setNegativeButton("NO", dialogClickListener);

            recipeHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideMealItem(v);
                }
            });
            recipeHeader.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    currentSelection = v;

                    builder.show();

                    return true;
                }
            });

        }
        MealItem o = items.get(position);
        if (o != null) {

            TextView recipeTitle = (TextView) v.findViewById(R.id.recipeTitle);
            TextView recipeServingsHeader = (TextView) v.findViewById(R.id.recipeServingsHeader);
            TextView ingredients = (TextView) v.findViewById(R.id.ingredients);
            TextView directionsContent = (TextView) v.findViewById(R.id.directionsContent);
            TextView recipeHeader = (TextView) v.findViewById(R.id.recipeHeader);



            if (recipeTitle != null) {
                recipeTitle.setText(o.getTitle());
                recipeServingsHeader.setText("Ingredients (Serves "+ o.getServings() + ")");
                ingredients.setText(o.getIngredients());
                directionsContent.setText(o.getDirections());
                recipeHeader.setText(o.getHeader());
                System.out.println(o.getTitle());
            }

        }
        return v;
    }

     void hideMealItem(View v){

         View parentView = (View) v.getParent();

         View mealItemView = parentView.findViewById(R.id.verticalList);

         if(mealItemView.getVisibility() == View.VISIBLE)
             mealItemView.setVisibility(View.GONE);
         else
             mealItemView.setVisibility(View.VISIBLE);

         System.out.println("position" + v.getTop());



     }

     void mealComplete(View v){
            v.setBackgroundColor(Color.GRAY);
            v.setOnLongClickListener(null);

     }

private View currentSelection;

}