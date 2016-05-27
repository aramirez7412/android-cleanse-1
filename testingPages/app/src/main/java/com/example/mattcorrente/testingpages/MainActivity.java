package com.example.mattcorrente.testingpages;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;
import android.widget.ViewFlipper;
import android.widget.ViewSwitcher;

public class MainActivity extends AppCompatActivity {


    //declare variables to store all layouts
    ViewGroup dailyCalorieListLayout;


    ViewGroup childMealElement;

    TextView mealListHeader;
    ViewGroup mealCell;




    //--------------------------------------------
    DailyCalorieList sundayList;








    //variables used for dailyMealList
    ExpandableListView expandableListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_meal);



        dailyCalorieListLayout = (ViewGroup) findViewById(R.id.mealListLayout);
        expandableListView = (ExpandableListView) findViewById(R.id.dailyMealListView);
        mealListHeader = (TextView) dailyCalorieListLayout.findViewById(R.id.mealListHeader);

       mealCell = (ViewGroup) findViewById(R.id.recipeBox);

        //--------------------------------------------
        sundayList = new DailyCalorieList(getApplicationContext());

        mealListHeader.setText("Day 1");


        sundayList.add("Breakfast", "meal" );
        sundayList.add("Dinner", "meal" );





        mealCell = (ViewGroup) findViewById(R.id.shake_cell);
        sundayList.add("Snack", "shake" );
        sundayList.add("Lunch", "shake" );






//        //inintialize expandable list headings
//        mealListHeadings.add("BREAKFAST");
//        mealListHeadings.add("LUNCH");
//        mealListHeadings.add("DINNER");
//        mealListHeadings.add("SNACKS");
//
//        childList.put(mealListHeadings.get(0), breakfastList);
//        childList.put(mealListHeadings.get(1), lunchList);
//        childList.put(mealListHeadings.get(2), dinnerList);
//        childList.put(mealListHeadings.get(3), snackList);
//        ExpandableListAdapter adapter = new ExpandableListAdapter(view.getContext(), mealListHeadings, childList );
        expandableListView.setAdapter(sundayList.getAdapter());



        for(int i = 0; i < 4; i++)
            expandableListView.expandGroup(i);




    }
}
