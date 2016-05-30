//package com.example.mattcorrente.testingpages;
//
//import android.content.Context;
//import android.text.Layout;
//import android.view.ViewGroup;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
///**
// * Created by mattcorrente on 4/17/16.
// */
//public class DailyCalorieList {
//
//    DailyCalorieList(Context context){
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
//
//
//
//        adapter = new ExpandableListAdapter(context, mealListHeadings, childList );
//
//    }
//
//    public void add(String mealType, ViewGroup mealCell) {
//
//        switch (mealType) {
//
//            case "Breakfast":
//                breakfastList.add(mealCell);
//                break;
//
//            case "Lunch":
//                lunchList.add(mealCell);
//                break;
//            case "Dinner":
//                dinnerList.add(mealCell);
//                break;
//            case "Snack":
//                snackList.add(mealCell);
//                break;
//        }
//    }
//
//    public void delete(int group, int child){
//
//        ViewGroup listItemString;
//
//        switch(group){
//            case 0:
//
//                listItemString = breakfastList.get(child);
//                breakfastList.remove(child);
//                break;
//            case 1:
//                listItemString = lunchList.get(child);
//                lunchList.remove(child);
//                break;
//            case 2:
//                listItemString = dinnerList.get(child);
//                dinnerList.remove(child);
//                break;
//            case 3:
//                listItemString = snackList.get(child);
//                snackList.remove(child);
//                break;
//            default:
//                listItemString = null;
//        }
//
//
//    }
//
//    ExpandableListAdapter getAdapter(){
//        return adapter;
//    }
//
//
//    //variables used for dailyMealList
//    private List<String> mealListHeadings = new ArrayList<String>();
//    private List<ViewGroup> breakfastList = new ArrayList<ViewGroup>();
//    private List<ViewGroup> lunchList = new ArrayList<ViewGroup>();
//    private List<ViewGroup> dinnerList = new ArrayList<ViewGroup>();
//    private List<ViewGroup> snackList = new ArrayList<ViewGroup>();
//    private HashMap<String, List<ViewGroup>> childList = new HashMap<String, List<ViewGroup>>();
//    ExpandableListAdapter adapter;
//
//
//
//}

package com.example.mattcorrente.testingpages;

import android.content.Context;
import android.text.Layout;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mattcorrente on 4/17/16.
 */
public class DailyCalorieList {

    DailyCalorieList(Context context){
        //inintialize expandable list headings
//        mealListHeadings.add("BREAKFAST");
//        mealListHeadings.add("LUNCH");
//        mealListHeadings.add("DINNER");
//        mealListHeadings.add("SNACKS");
//
//        childList.put(mealListHeadings.get(0), breakfastList);
//        childList.put(mealListHeadings.get(1), lunchList);
//        childList.put(mealListHeadings.get(2), dinnerList);
//        childList.put(mealListHeadings.get(3), snackList);



        adapter = new ExpandableListAdapter(context, mealListHeadings, childList );

    }

//    public void add(String mealType, String mealCell) {
//
//        switch (mealType) {
//
//            case "Breakfast":
//                breakfastList.add(mealCell);
//                break;
//
//            case "Lunch":
//                lunchList.add(mealCell);
//                break;
//            case "Dinner":
//                dinnerList.add(mealCell);
//                break;
//            case "Snack":
//                snackList.add(mealCell);
//                break;
//        }
//    }

    public void add(){
        mealListHeadings.add("BREAKFAST");

        childList.put(mealListHeadings.get(0), breakfastList);
    }

    public void delete(int group, int child){

        String listItemString;

        switch(group){
            case 0:

                listItemString = breakfastList.get(child);
                breakfastList.remove(child);
                break;
            case 1:
                listItemString = lunchList.get(child);
                lunchList.remove(child);
                break;
            case 2:
                listItemString = dinnerList.get(child);
                dinnerList.remove(child);
                break;
            case 3:
                listItemString = snackList.get(child);
                snackList.remove(child);
                break;
            default:
                listItemString = null;
        }


    }

    ExpandableListAdapter getAdapter(){
        return adapter;
    }


    //variables used for dailyMealList
    private List<String> mealListHeadings = new ArrayList<String>();
    private List<String> breakfastList = new ArrayList<String>();
    private List<String> lunchList = new ArrayList<String>();
    private List<String> dinnerList = new ArrayList<String>();
    private List<String> snackList = new ArrayList<String>();
    private HashMap<String, List<String>> childList = new HashMap<>();
    ExpandableListAdapter adapter;



}
