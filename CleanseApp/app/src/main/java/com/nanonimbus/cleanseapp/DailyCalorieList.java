package com.nanonimbus.cleanseapp;//package com.mysampleapp;
//
//import android.content.Context;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Scanner;
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
//        adapter = new ExpandableListAdapter(context, mealListHeadings, childList);
//
//    }
//
//    public void add(String mealType, String mealName) {
//        switch (mealType) {
//            case "Breakfast":
//                breakfastList.add(mealName);
//                break;
//
//            case "Lunch":
//                lunchList.add(mealName);
//                break;
//            case "Dinner":
//                dinnerList.add(mealName);
//                break;
//            case "Snack":
//                snackList.add(mealName);
//                break;
//        }
//    }
//
//    public void delete(int group, int child){
//
//        String listItemString;
//
//        switch(group){
//            case 0:
//
//                listItemString = breakfastList.get(child).toString();
//                breakfastList.remove(child);
//                break;
//            case 1:
//                listItemString = lunchList.get(child).toString();
//                lunchList.remove(child);
//                break;
//            case 2:
//                listItemString = dinnerList.get(child).toString();
//                dinnerList.remove(child);
//                break;
//            case 3:
//                listItemString = snackList.get(child).toString();
//                snackList.remove(child);
//                break;
//            default:
//                listItemString = "1 2 0";
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
//    private List<String> breakfastList = new ArrayList<String>();
//    private List<String> lunchList = new ArrayList<String>();
//    private List<String> dinnerList = new ArrayList<String>();
//    private List<String> snackList = new ArrayList<String>();
//    private HashMap<String, List<String>> childList = new HashMap<String, List<String>>();
//    ExpandableListAdapter adapter;
//
//
//
//}
//
//
