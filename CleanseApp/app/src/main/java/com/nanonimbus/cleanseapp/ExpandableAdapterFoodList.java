package com.nanonimbus.cleanseapp;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mattcorrente on 4/7/16.
 */
 class ExpandableListAdapterFoodList extends BaseExpandableListAdapter{

    private List<String> headerTitles;
    private List<String> secondaryHeaderTitles;

    private List<Object> childTitles;
    private Context context;

    ExpandableListAdapterFoodList(Context context, List<String> headerTitles, List<Object> childTitles, List<String> secondaryHeaderTitles){
        this.context = context;
        this.childTitles = childTitles;
        this.headerTitles  = headerTitles;
        this.secondaryHeaderTitles  = secondaryHeaderTitles;

    }

    @Override
    public int getGroupCount() {
        return headerTitles.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // return childTitles.size();
        return ((ArrayList)childTitles.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return headerTitles.get(groupPosition);
    }


    public Object getSecondaryGroup(int groupPosition) {
        return secondaryHeaderTitles.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return ((ArrayList) childTitles.get(groupPosition)).get(childPosition);
        // return childTitles.get(headerTitles.get(groupPosition)).get(0);
        //return childTitles.get(headerTitles.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String title = (String) getGroup(groupPosition);
        String title2 = (String) getSecondaryGroup(groupPosition);

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.meal_expandable_list_child_header_single, null);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.mealListHeaderItem);
        textView.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        textView.setText(title);

        textView = (TextView) convertView.findViewById(R.id.secondaryHeader);
        textView.setText(title2);

        return convertView;
    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        String title =  (String)this.getChild(groupPosition, childPosition);
        //int id = (int) this.getChild(groupPosition, childPosition).getClass();


        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = layoutInflater.inflate(R.layout.meal_expandable_list_child_single, null);
//        if(title == "meal")
//            convertView = layoutInflater.inflate(R.layout.recipe_box, null);
//        else
//            convertView = layoutInflater.inflate(R.layout.meal_cell_sample, null);

        //  }



        TextView textView = (TextView) convertView.findViewById(R.id.mealListChildItem);

            textView.setTypeface(Typeface.SERIF);


        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView.setText(title);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }



}



