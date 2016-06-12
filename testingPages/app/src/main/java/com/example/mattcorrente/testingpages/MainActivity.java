package com.example.mattcorrente.testingpages;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewAnimator;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    ListView testList;
    ListView testList2;
    ViewGroup addMe;

    ArrayList<MealItem> list;
    ArrayList<MealItem> list2;
    MealPlan mealPlan;
    TextView tv;
    TextView tv2;
    int day;
    int daysInPlan;
    ViewGroup dayListView;
    ViewGroup dayListView2;
    MealItemAdapter adapter;
    MealItemAdapter adapter2;
    int listViewNum;




    ViewGroup currentListView;
    TextView currenttv;
    MealItemAdapter currentAdapter;

    ViewAnimator viewAnimator;

    String temp = "{\n" +
            "    \"mealPlan\": { \n" +
            "     \"0\": [\t\n" +
            "        {\n" +
            "           \"type\":\"recipe\",\n" +
            "           \"cellHeader\": \"Breakfast\",\n" +
            "           \"mealTitle\": \"Beef Stew\",\n" +
            "           \"images\": \"@mipmap/sample_food\",\n" +
            "           \"servings\": 4,\n" +
            "           \"ingredients\": [\"cheese\", \"eggs\"],\n" +
            "           \"directions\": [\"milk cow\", \"bake potatoes\", \"code\"]\n" +
            "        },\t \n" +
            "        {\n" +
            "          \"type\":\"recipe\",\n" +
            "           \"cellHeader\": \"Lunch\",\n" +
            "           \"mealTitle\": \"Tamales\",\n" +
            "           \"images\": \"@mipmap/sample_food\",\n" +
            "           \"servings\": 3,\n" +
            "           \"ingredients\": [\"chicken\", \"nuggets\"],\n" +
            "           \"directions\": [\"go to mcDons\", \"give them money\"]\n" +
            "        },\t \n" +
            "        {\n" +
            "          \"type\":\"recipe\",\n" +
            "           \"cellHeader\": \"Dinner\",\n" +
            "           \"mealTitle\": \"Burgers\",\n" +
            "           \"images\": \"@mipmap/sample_food\",\n" +
            "           \"servings\": 900,\n" +
            "           \"ingredients\": [\"1000 packages of hot dogs\"],\n" +
            "           \"directions\": [\"grill those bad boys\", \"eat til you puke\"]\n" +
            "        }\n" +
            "   ],\n" +
            "   \"1\": [\t\n" +
            "        {\n" +
            "           \"type\":\"recipe\",\n" +
            "           \"cellHeader\": \"Breakfast\",\n" +
            "           \"mealTitle\": \"Tacos\",\n" +
            "           \"images\": \"@mipmap/sample_food\",\n" +
            "           \"servings\": 4,\n" +
            "           \"ingredients\": [\"cheese\", \"eggs\"],\n" +
            "           \"directions\": [\"milk cow\", \"bake potatoes\", \"code\"]\n" +
            "        },\t \n" +
            "        {\n" +
            "          \"type\":\"recipe\",\n" +
            "           \"cellHeader\": \"Lunch\",\n" +
            "           \"mealTitle\": \"Garbage\",\n" +
            "           \"images\": \"@mipmap/sample_food\",\n" +
            "           \"servings\": 3,\n" +
            "           \"ingredients\": [\"chicken\", \"nuggets\"],\n" +
            "           \"directions\": [\"go to mcDons\", \"give them money\"]\n" +
            "        },\t \n" +
            "        {\n" +
            "          \"type\":\"recipe\",\n" +
            "           \"cellHeader\": \"Dinner\",\n" +
            "           \"mealTitle\": \"Salad\",\n" +
            "           \"images\": \"@mipmap/sample_food\",\n" +
            "           \"servings\": 900,\n" +
            "           \"ingredients\": [\"1000 packages of hot dogs\"],\n" +
            "           \"directions\": [\"grill those bad boys\", \"eat til you puke\"]\n" +
            "        }\n" +
            "      ],\n" +
            "   \"2\": [\t\n" +
            "        {\n" +
            "           \"type\":\"recipe\",\n" +
            "           \"cellHeader\": \"nothing\",\n" +
            "           \"mealTitle\": \"Candy\",\n" +
            "           \"images\": \"@mipmap/sample_food\",\n" +
            "           \"servings\": 4,\n" +
            "           \"ingredients\": [\"cheese\", \"eggs\"],\n" +
            "           \"directions\": [\"milk cow\", \"bake potatoes\", \"code\"]\n" +
            "        },\t \n" +
            "        {\n" +
            "          \"type\":\"recipe\",\n" +
            "           \"cellHeader\": \"iForgot\",\n" +
            "           \"mealTitle\": \"Crab Cakes\",\n" +
            "           \"images\": \"@mipmap/sample_food\",\n" +
            "           \"servings\": 3,\n" +
            "           \"ingredients\": [\"chicken\", \"nuggets\"],\n" +
            "           \"directions\": [\"go to mcDons\", \"give them money\"]\n" +
            "        }\n" +
            "   ]\n" +
            "}\n" +
            "  \n" +
            "}";


    //detects swipes while using this fragment
    final SwipeDetector swipeDetector = new SwipeDetector();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //will need to dynamically change with day
        tv = ((TextView) findViewById(R.id.mealListDayHeader));
        tv2 = ((TextView) findViewById(R.id.mealListDayHeader2));

        dayListView = (ViewGroup) findViewById(R.id.testId);
        dayListView2 = (ViewGroup) findViewById(R.id.testId);


        viewAnimator = (ViewAnimator) findViewById(R.id.myViewAnimator);


        try {

            //initialize the jsonObject
            JSONObject jsonObject = new JSONObject(temp);


            //parse the object and create a meal plan
            mealPlan = new MealPlan(jsonObject.getJSONObject("mealPlan"));
            daysInPlan = mealPlan.getDays();
            day = 0;

            //list = mealPlan.getListForDay(day);
            list = new ArrayList<MealItem>();


            //setup the first layout to be displayed
            //---------
            testList = (ListView) findViewById(R.id.testListView);
            addMe = (ViewGroup) findViewById(R.id.recipeItem);
            adapter = new MealItemAdapter(this, R.layout.meal_tracker_recipe_item, list);
            testList.setAdapter(adapter);
            adapter.addAll(mealPlan.getListForDay(day));
            //----------


            //setup secondary layout, used when animating between daily meal plans
            //----------
            list = mealPlan.getListForDay(day);
            list2 = new ArrayList<MealItem>();
            testList2 = (ListView) findViewById(R.id.testListView2);
            adapter2 = new MealItemAdapter(this, R.layout.meal_tracker_recipe_item, list2);
            testList2.setAdapter(adapter2);
            //----------


            //initialize all animation variables
            final Animation outRight = AnimationUtils.loadAnimation(
                    getApplicationContext(), R.anim.slide_out_right);
            final Animation inLeft = AnimationUtils.loadAnimation(
                    getApplicationContext(), R.anim.slide_in_left);
            final  Animation inRight = AnimationUtils.loadAnimation(
                    getApplicationContext(), R.anim.slide_in_right);
            final  Animation outLeft = AnimationUtils.loadAnimation(
                    getApplicationContext(), R.anim.slide_out_left);

            //initialize all current- variables to currently displayed views
            currentAdapter = adapter;
            currenttv = tv;
            listViewNum = 1;
            currenttv.setText("Day " + (day + 1));


            dayListView.setOnTouchListener(swipeDetector);

            dayListView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (swipeDetector.swipeDetected()) {

                        //figure out how to use correct libraries to support all animations


                        switch (swipeDetector.getAction()) {

                            //if right to left swipe then swipe then switch displayed plan to the next day
                            case RL:

                                //check if next day exists, if so proceed displaying next day
                                if (day < daysInPlan - 1) {

                                    viewAnimator.setInAnimation(inRight);
                                    viewAnimator.setOutAnimation(outLeft);

                                    switchListView();

                                    day++;
                                    currenttv.setText("Day " + (day + 1));
                                    currentAdapter.clear();
                                    currentAdapter.addAll(mealPlan.getListForDay(day));
                                    currentAdapter.notifyDataSetChanged();

                                    viewAnimator.showNext();

                                }
                                break;

                            //if left to right swipe then switch displayed plan to previous day
                            case LR:

                                //check if previous day exists, if so proceed displaying previous day
                                if (day > 0) {

                                    viewAnimator.setInAnimation(inLeft);
                                    viewAnimator.setOutAnimation(outRight);

                                    switchListView();

                                    day--;

                                    currenttv.setText("Day " + (day + 1));
                                    currentAdapter.clear();
                                    currentAdapter.addAll(mealPlan.getListForDay(day));
                                    currentAdapter.notifyDataSetChanged();

                                    viewAnimator.showPrevious();

                                }
                                break;
                            default:
                                //do nothing
                                break;
                        }//end switch
                    }//end if swipe detected
                }//end on click

            });


        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    //this method is used to switch all current- variables to the other layout (used for animation purposes)
    void switchListView(){
        if(listViewNum == 1){
            listViewNum =2;
            currentAdapter = adapter2;
            currentListView =  dayListView2;
            currenttv = tv2;
        }
        else{
            listViewNum = 1;
            currentAdapter = adapter;
            currentListView = dayListView;
            currenttv = tv;
        }
    }


    class MealItemAdapter extends ArrayAdapter<MealItem> {


        private ArrayList<MealItem> items;

        public MealItemAdapter(Context context, int textViewResourceId, ArrayList<MealItem> items) {
            super(context, textViewResourceId, items);
            this.items = items;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.meal_tracker_recipe_item, null);
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
                    recipeServingsHeader.setText("Ingredients (Serves " + o.getServings() + ")");
                    ingredients.setText(o.getIngredients());
                    directionsContent.setText(o.getDirections());
                    recipeHeader.setText(o.getHeader());

                    //check if meal to be added is completed, if so then it will not need a long click listener and will
                    //need to set the header to grey
                    if (o.isCompleted()) {
                        mealComplete(recipeHeader);
                    }
                    //if not then set up listener to handle the completion of a meal and set color to blue
                    else {

                        //sets up the actions that are connected to meal completion confirmation dialog
                        final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE:
                                        mealComplete(currentSelection);

                                        mealPlan.setCompleted(day, position);
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        break;
                                }//end switch
                            }
                        };//end listener

                        //this sets up a yes/no selection box to make sure users want to complete a meal
                        final AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
                        builder.setMessage("Have You Completed This Meal?\n(option cannot be undone)").setPositiveButton("YES", dialogClickListener)
                                .setNegativeButton("NO", dialogClickListener);


                        recipeHeader.setBackgroundColor(Color.parseColor("#628799"));


                        //this listen allows users to expand/collapse a meal
                        recipeHeader.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                tempDay = day; //used to see if new day when displaying list, so it will close all open items
                                hideAndShowMealItem(v);
                            }
                        });

                        //this listener allows users to complete meal
                        recipeHeader.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                currentSelection = v;
                                builder.show();
                                return true;
                            }
                        });
                    }

                    ///this must be fixed so all items close when switching days
                    if(day != tempDay && recipeHeader.getVisibility() == View.VISIBLE){
                        hideMealItem(recipeHeader);
                    }

                }


            }

            return v;
        }


        void hideMealItem(View v) {

            View parentView = (View) v.getParent();
            View mealItemView = parentView.findViewById(R.id.verticalList);

            if (mealItemView.getVisibility() == View.VISIBLE)
                mealItemView.setVisibility(View.GONE);

        }



        void hideAndShowMealItem(View v) {

            View parentView = (View) v.getParent();

            View mealItemView = parentView.findViewById(R.id.verticalList);

            if (mealItemView.getVisibility() == View.VISIBLE)
                mealItemView.setVisibility(View.GONE);
            else
                mealItemView.setVisibility(View.VISIBLE);

        }

        void mealComplete(View v) {
            v.setBackgroundColor(Color.GRAY);
            v.setOnLongClickListener(null);
        }




        private View currentSelection; //this is used to instantaneously change a meal to completed when when "YES" is selected
        private int tempDay; //used to check if all items need to be reset to closed
    }

}


class SwipeDetector implements View.OnTouchListener {

    int HORIZONTAL_MIN_DISTANCE = 1;
    int VERTICAL_MIN_DISTANCE = 1;

    public static enum Action {
        LR, // Left to Right
        RL, // Right to Left
        TB, // Top to bottom
        BT, // Bottom to Top
        None // when no action was detected
    }

    private static final String logTag = "SwipeDetector";
    private static final int MIN_DISTANCE = 100;
    private float downX, downY, upX, upY;
    private Action mSwipeDetected = Action.None;

    public boolean swipeDetected() {
        return mSwipeDetected != Action.None;
    }

    public Action getAction() {
        return mSwipeDetected;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                downX = event.getX();
                downY = event.getY();
                mSwipeDetected = Action.None;
                return false; // allow other events like Click to be processed
            }
            case MotionEvent.ACTION_MOVE: {
                upX = event.getX();
                upY = event.getY();

                float deltaX = downX - upX;
                float deltaY = downY - upY;

                // horizontal swipe detection
                if (Math.abs(deltaX) > HORIZONTAL_MIN_DISTANCE) {
                    // left or right
                    if (deltaX < 0) {
                        Log.i(logTag, "Swipe Left to Right");
                        mSwipeDetected = Action.LR;
                        return true;
                    }
                    if (deltaX > 0) {
                        Log.i(logTag, "Swipe Right to Left");
                        mSwipeDetected = Action.RL;
                        return true;
                    }
                } else

                    // vertical swipe detection
                    if (Math.abs(deltaY) > VERTICAL_MIN_DISTANCE) {
                        // top or down
                        if (deltaY < 0) {
                            Log.i(logTag, "Swipe Top to Bottom");
                            mSwipeDetected = Action.TB;
                            return false;
                        }
                        if (deltaY > 0) {
                            Log.i(logTag, "Swipe Bottom to Top");
                            mSwipeDetected = Action.BT;
                            return false;
                        }
                    }
                return true;
            }
        }
        return false;
    }
}