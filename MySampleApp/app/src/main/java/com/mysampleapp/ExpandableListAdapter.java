package com.mysampleapp;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by mattcorrente on 4/7/16.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter{

    private List<String> headerTitles;
    private HashMap<String, List<String>> childTitles;
    private Context context;

    ExpandableListAdapter(Context context, List<String> headerTitles, HashMap<String, List<String>> childTitles){
        this.context = context;
        this.childTitles = childTitles;
        this.headerTitles  = headerTitles;
    }

    @Override
    public int getGroupCount() {
        return headerTitles.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childTitles.get(headerTitles.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return headerTitles.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        return childTitles.get(headerTitles.get(groupPosition)).get(0);
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
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.meal_expandable_list_header, null);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.mealListHeadingItem);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(title);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        String title = (String) this.getChild(groupPosition, childPosition);
        //int id = (int) this.getChild(groupPosition, childPosition).getClass();


        //if(convertView == null){
        System.out.println(title);

        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(title == "meal")
            convertView = layoutInflater.inflate(R.layout.recipe_box, null);
        else
            convertView = layoutInflater.inflate(R.layout.meal_cell_sample, null);

        //}


        //TextView textView = (TextView) convertView.findViewById(R.id.recipeTitle);
        //textView.setTypeface(null, Typeface.NORMAL);
        //textView.setText(title);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }



}



