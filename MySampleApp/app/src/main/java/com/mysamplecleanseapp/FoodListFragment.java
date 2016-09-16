package com.mysamplecleanseapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FoodListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FoodListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoodListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ExpandableListAdapterFoodList myAdapter;
    ExpandableListView myList;

    String jsonString = "{\n" +
            "  \"name\": \"The Fast Metabolism Cleanse Food List\",\n" +
            "  \"lists\": [\n" +
            "    {\n" +
            "      \"section\": \"Vegetables and Salad Greens (fresh, canned or frozen)\",\n" +
            "      \"serving-size\": \"Unlimited\",\n" +
            "      \"foods\": [\n" +
            "        \"Artichokes\",\n" +
            "        \"Arugula\",\n" +
            "        \"Asparagus\",\n" +
            "        \"Bean sprouts\",\n" +
            "        \"Beans: green, yellow\",\n" +
            "        \"(wax), French (string)\",\n" +
            "        \"Beets: greens, roots\",\n" +
            "        \"Bok choy\",\n" +
            "        \"Broccoli\",\n" +
            "        \"Brussels sprouts\",\n" +
            "        \"Cabbage, all types\",\n" +
            "        \"Carrots\",\n" +
            "        \"Cauliflower florets\",\n" +
            "        \"Celery\",\n" +
            "        \"Chicory (curly endive)\",\n" +
            "        \"Collard greens\",\n" +
            "        \"Cucumbers\",\n" +
            "        \"Eggplant\",\n" +
            "        \"Endive\",\n" +
            "        \"Fennel\",\n" +
            "        \"Green chiles\",\n" +
            "        \"Green onions\",\n" +
            "        \"Hearts of palm\",\n" +
            "        \"Jicama\",\n" +
            "        \"Kale\",\n" +
            "        \"Kohlrabi\",\n" +
            "        \"Leeks\",\n" +
            "        \"Lettuce (any except iceberg)\",\n" +
            "        \"Mixed greens\",\n" +
            "        \"Mushrooms\",\n" +
            "        \"Okra\",\n" +
            "        \"Olives, any type\",\n" +
            "        \"Onions\",\n" +
            "        \"Peppers: bell\",\n" +
            "        \"pepperoncini\",\n" +
            "        \"Radishes\",\n" +
            "        \"Rhubarb\",\n" +
            "        \"Seaweed\",\n" +
            "        \"Spinach\",\n" +
            "        \"Spirulina\",\n" +
            "        \"Sprouts\",\n" +
            "        \"Sweet potatoes / yams\",\n" +
            "        \"Tomatoes, fresh and canned: round, plum, cherry\",\n" +
            "        \"Watercress\",\n" +
            "        \"Zucchini and winter\",\n" +
            "        \"yellow summer squash\"\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"section\": \"Fruits (Fresh or frozen)\",\n" +
            "      \"serving-size\": \"1/2 cup of fruit\",\n" +
            "      \"foods\": [\n" +
            "        \"Peaches\",\n" +
            "        \"Plums\",\n" +
            "        \"Prickly pears\",\n" +
            "        \"Raspberries\",\n" +
            "        \"Apples\",\n" +
            "        \"Blackberries\",\n" +
            "        \"Blueberries\",\n" +
            "        \"Cherries\",\n" +
            "        \"Cranberries\",\n" +
            "        \"Grapefruit\",\n" +
            "        \"Lemons\",\n" +
            "        \"Limes\"\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"section\": \"Grains & Starche\",\n" +
            "      \"serving-size\": \"1/2 cup cooked or 1 slice\",\n" +
            "      \"foods\": [\n" +
            "        \"Barley: black or white\",\n" +
            "        \"Brown rice\",\n" +
            "        \"Oats: steel-cut, oldfashioned\",\n" +
            "        \"Quinoa\",\n" +
            "        \"Sprouted-grain: bread, bagels, english muffins\",\n" +
            "        \"tortillas\",\n" +
            "        \"Tapioca\",\n" +
            "        \"Wild rice\"\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"section\": \"Vegetable Protein\",\n" +
            "      \"serving-size\": \"1/2 cup cooked\",\n" +
            "      \"foods\": [\n" +
            "        \"Almond flour Dried (or canned)\",\n" +
            "        \"beans: azuki, chickpeas, garbanzo beans, black, butter, cannellini, kidney,lima, navy, pinto or white\",\n" +
            "        \"Lentils\"\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"section\": \"Animal Protein\",\n" +
            "      \"serving-size\": \"4 ounces uncooked (meat); 6 ounces uncooked (fish)\",\n" +
            "      \"foods\": [\n" +
            "        \"Buffalo meat\",\n" +
            "        \"Calamari\",\n" +
            "        \"Chicken: boneless, skinless dark or white meat, ground\",\n" +
            "        \"Clams\",\n" +
            "        \"Cornish game hens\",\n" +
            "        \"Crab, lump meat\",\n" +
            "        \"Deli meats, nitrate-free: turkey, chicken\",\n" +
            "        \"Eggs, whole\",\n" +
            "        \"Game: pheasant\",\n" +
            "        \"Halibut fillet\",\n" +
            "        \"Herring\",\n" +
            "        \"Lamb\",\n" +
            "        \"Liver\",\n" +
            "        \"Lobster meat\",\n" +
            "        \"Oysters\",\n" +
            "        \"Pork: chops, loin roast\",\n" +
            "        \"Rabbit\",\n" +
            "        \"Salmon: fresh, frozen, or nitrate-free smoked\",\n" +
            "        \"Sardines, packed in olive oil\",\n" +
            "        \"Sausage, nitrate-free: chicken, turkey\",\n" +
            "        \"Jerky, nitrate-free: buffalo, turkey, elk, ostrich\",\n" +
            "        \"Scallops\",\n" +
            "        \"Sea bass fillet\",\n" +
            "        \"Shrimp\",\n" +
            "        \"Skate\",\n" +
            "        \"Trout\",\n" +
            "        \"Tuna, fresh or packed in water or oil\",\n" +
            "        \"Turkey\",\n" +
            "        \"Turkey bacon, nitrate-free\"\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"section\": \"Broths, Herbs, Spices, Condiments, and Supplements\",\n" +
            "      \"serving-size\": \"as needed\",\n" +
            "      \"foods\": [\n" +
            "        \"Almond milk, unsweetened\",\n" +
            "        \"Arrowroot\",\n" +
            "        \"Brewer’s yeast\",\n" +
            "        \"Broths: chicken, vegetable*\",\n" +
            "        \"Carob chips\",\n" +
            "        \"Cashew milk\",\n" +
            "        \"Dried herbs: all types\",\n" +
            "        \"Fresh herbs: all types\",\n" +
            "        \"Garlic, fresh\",\n" +
            "        \"Ginger, fresh\",\n" +
            "        \"Hemp milk, unsweetened\",\n" +
            "        \"Horseradish\",\n" +
            "        \"Ketchup: no sugar added, no corn syrup\",\n" +
            "        \"Mustard: prepared, dry\",\n" +
            "        \"Natural seasonings:\",\n" +
            "        \"Bragg Liquid Aminos, coconut amino acids\",\n" +
            "        \"Non-caffeinated herbal teas or Pero\",\n" +
            "        \"Nutritional yeast\",\n" +
            "        \"Pickles, no sugar added\",\n" +
            "        \"Salsa\",\n" +
            "        \"Seasonings: black and white peppers, celery seed, chophouse seasoning, cinnamon, chili powder, crushed red pepper flakes, cumin, curry powder, onion salt, paprika, raw cacao powder, turmeric, sea salt, Simply Organic seasoning\",\n" +
            "        \"Sweeteners: stevia, xylitol (birch only)\",\n" +
            "        \"Tamari\",\n" +
            "        \"Tomato paste, tomato sauce; no sugar added\",\n" +
            "        \"Vanilla or peppermint extract\",\n" +
            "        \"Vinegar: any type (except rice)\",\n" +
            "        \"All broths should be free of additives and preservatives.\"\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"section\": \"Healthy Fats\",\n" +
            "      \"serving-size\": \"2 to 4 tablespoons\",\n" +
            "      \"foods\": [\n" +
            "        \"Coconut: coconut butter\",\n" +
            "        \"coconut milk\",\n" +
            "        \"coconut cream\",\n" +
            "        \"coconut water\",\n" +
            "        \"Hummus\",\n" +
            "        \"Mayonnaise\",\n" +
            "        \"safflower\",\n" +
            "        \"Nuts nut/seed butters and pastes, raw: almonds, cashews, hazelnuts, pecans, pine nuts, pistachios, walnuts\",\n" +
            "        \"Nut flours\",\n" +
            "        \"Oils: coconut\",\n" +
            "        \"grapeseed, olive, sesame, toasted sesame (Asian) seeds\",\n" +
            "        \"raw: flax\",\n" +
            "        \"hemp\",\n" +
            "        \"pumpkin\",\n" +
            "        \"sesame\",\n" +
            "        \"sunflower\",\n" +
            "        \"tahini\"\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"section\": \"Beverages\",\n" +
            "      \"serving-size\": \"1/2 your body weight in ounces\",\n" +
            "      \"foods\": [\n" +
            "        \"Spring Water\",\n" +
            "        \"Whole Food Powder Shakes\"\n" +
            "      ]\n" +
            "    }\n" +
            "  ]\n" +
            "}";


    List<String> headerTitles;
    List<String> secondaryHeaderTitles;
    List<Object> childTitles;


    public FoodListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FoodListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FoodListFragment newInstance(String param1, String param2) {
        FoodListFragment fragment = new FoodListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        headerTitles = new ArrayList();
        secondaryHeaderTitles = new ArrayList();
        childTitles = new ArrayList<>();


        JSONObject jsonObject = null;
        try {

            jsonObject = new JSONObject(jsonString);

             JSONArray listAr = jsonObject.getJSONArray("lists");



            //loop through each day
            for(int k = 0; k < listAr.length(); k++) {

                headerTitles.add(listAr.getJSONObject(k).getString("section"));

                ArrayList<String> foodSection = new ArrayList<>();
                JSONArray foodAr = null;
                try {
                    foodAr = listAr.getJSONObject(k).getJSONArray("foods");
                } catch (JSONException e) {
                    System.out.println("Failed");
                }

                secondaryHeaderTitles.add("Daily Serving Size: " + listAr.getJSONObject(k).getString("serving-size"));


                //loop through every meal per day
                for (int i = 0; i < foodAr.length(); i++) {


                    foodSection.add(foodAr.getString(i));


                }

                childTitles.add(foodSection);
        }
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v;
        v = inflater.inflate(R.layout.fragment_foodlist, container, false);







        myAdapter = new ExpandableListAdapterFoodList(getContext(),headerTitles, childTitles, secondaryHeaderTitles);

        myList = (ExpandableListView) v.findViewById(R.id.expandableListViewFoodList);

        myList.setAdapter(myAdapter);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }








//    class FoodListAdapter extends ArrayAdapter<String> {
//
//        private ArrayList<MealItem> items;
////        ArrayAdapter<String> foodListAdapter = new ArrayAdapter<String>(
////                getContext(),
////                android.R.layout.select_dialog_singlechoice);
//
//
//
//
//
//        ExpandableListView myList;
//        ExpandableListAdapter foodListAdapter;
//
//        ArrayList<MealItem> set1;
//        List<String> headerTitles;
//        List<Object> childTitles;
//        ArrayList<ArrayList<MealItem>> foodListSets
//                ;
//
//
//
//        public FoodListAdapter(Context context, int textViewResourceId, ArrayList<String> items2) {
//            super(context, textViewResourceId, items2);
//
//
//
//            File file = new File(getActivity().getFilesDir() + "/recipeSet/");
//            File inputFile = new File(file, "genericSet.ser");
//
//            RecipeSet rs = getSetFromFile(inputFile.getAbsolutePath());
//            items = rs.getRecipeSet();
//            set1 = rs.getRecipeSet();
//
//            foodListSets
//                    = new ArrayList<ArrayList<MealItem>>();
//
//
//            foodListSets
//                    .add(set1);
//
//
//
//            headerTitles = new ArrayList<>();
//            headerTitles.add(rs.getRecipeSetTitle());
//
//
//            childTitles = new ArrayList<>();
//            childTitles.add(set1);
//
//
//
//
//            foodListAdapter = new ExpandableListAdapter(getContext(),headerTitles, childTitles);
//
//            myList = new ExpandableListView(getContext());
//
//            myList.setAdapter(foodListAdapter);
//
//
//
//
//            this.items = items2;
//
//        }
//
//
//
//
//        @Override
//        public View getView(final int position, View convertView, ViewGroup parent) {
//            View v = convertView;
//
//
//            if (v == null) {
//                LayoutInflater vi = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                v = vi.inflate(R.layout.meal_cell, null);
//            }
//
//            final MealItem o = items.get(position);
//
//
//            if (o != null) {
//
//                TextView recipeTitle = (TextView) v.findViewById(R.id.mealName);
//                final TextView recipeHeader = (TextView) v.findViewById(R.id.mealHeader);
//                final ImageView recipeImageView = (ImageView) v.findViewById(R.id.mealCellImageView);
//
//
//                loadPicassoPicFromFileAsync(recipeImageView, o);
//
//
//
//                if (recipeTitle != null) {
//                    recipeTitle.setText(o.getTitle());
//
//                    recipeHeader.setText(o.getHeader());
//
//
//                    v.findViewById(R.id.completeCheckBox).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            o.toggleComplete();
//                        }
//                    });
//
//                    final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            switch (which) {
//
//                                case DialogInterface.BUTTON_POSITIVE:
//                                    mealPlan.toggleCompletion(day, position);
//
//                                    if(mealPlan.isCompleted(day, position)){
//                                        System.out.println("complete");
//                                        mealComplete(currentSelection);
//                                    }
//                                    else{
//                                        mealUncomplete(currentSelection);
//                                        System.out.println("uncomp");
//                                    }
//
//                                    saveFile();
//
//                                    break;
//
//                                case DialogInterface.BUTTON_NEGATIVE:
//
//                                    System.out.println(o.getTitle().toUpperCase().equals("FAST METABOLISM CLEANSE"));
//
//                                    if(o.getTitle().toUpperCase().equals("FAST METABOLISM CLEANSE")) {
//                                        alert2.show();
//                                    }
//                                    else {
//                                        for (int i = 0; i < foodListAdapter.getGroupCount(); i++) {
//                                            myList.collapseGroup(i);
//
//                                            alert.show();
//                                        }
//                                    }
//
//                                    break;
//
//                                case DialogInterface.BUTTON_NEUTRAL:
//                                    break;
//                            }//end switch
//                        }
//                    };//end listener
//
//                    //this sets up a yes/no selection box to make sure users want to complete a meal
//                    final AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
//                    builder.setMessage("Please Select an Option").setPositiveButton("Complete", dialogClickListener)
//                            .setNegativeButton("Change Meal", dialogClickListener).setNeutralButton("Cancel", dialogClickListener);
//
//                    ((CheckBox) v.findViewById(R.id.completeCheckBox)).setChecked(false);
//
//                    //v.setBackgroundColor(Color.parseColor("#FFFFFF"));
//                    //recipeImageView.setBackgroundColor(Color.parseColor("#FFFFFF"));
//
//
//
//                    //this listener allows users to complete meal
//                    v.setOnLongClickListener(new View.OnLongClickListener() {
//                        @Override
//                        public boolean onLongClick(View v) {
//                            currentSelection = v;
//                            builder.show();
//                            helperPosition = position;
//
//                            return true;
//                        }
//                    });
//
//                    v.setOnTouchListener(new View.OnTouchListener() {
//
//                        @Override
//                        public boolean onTouch(View v, MotionEvent e) {
//
//
//                            switch (e.getAction()) {
//                                case MotionEvent.ACTION_DOWN: {
//                                    pressStartTime = System.currentTimeMillis();
//                                    pressedX = e.getX();
//                                    pressedY = e.getY();
//                                    stayedWithinClickDistance = true;
//                                    break;
//                                }
//                                case MotionEvent.ACTION_MOVE: {
//                                    if (stayedWithinClickDistance && distance(pressedX, pressedY, e.getX(), e.getY()) > MAX_CLICK_DISTANCE) {
//                                        stayedWithinClickDistance = false;
//                                    }
//                                    break;
//                                }
//                                case MotionEvent.ACTION_UP: {
//                                    long pressDuration = System.currentTimeMillis() - pressStartTime;
//                                    if (pressDuration < MAX_CLICK_DURATION && stayedWithinClickDistance) {
//                                        // Click event has occurred
//                                        // open recipe
//                                        hideAndShowMealItem(o);
//                                    }
//                                    else if(pressDuration < MAX_CLICK_DURATION){
//                                        //LR
//                                        if(pressedX < e.getX()){
//                                            //check if previous day exists, if so proceed displaying previous day
//                                            if (day > 0) {
//
//                                                viewAnimator.setInAnimation(inLeft);
//                                                viewAnimator.setOutAnimation(outRight);
//
//                                                switchListView();
//
//                                                day--;
//
//                                                currenttv.setText("Day " + (day + 1));
//                                                currentAdapter.clear();
//                                                currentAdapter.addAll(mealPlan.getListForDay(day));
//                                                currentAdapter.notifyDataSetChanged();
//
//                                                viewAnimator.showPrevious();
//                                            }
//                                        }
//                                        //RL
//                                        else{
//
//                                            //check if next day exists, if so proceed displaying next day
//                                            if (day < daysInPlan - 1) {
//
//                                                viewAnimator.setInAnimation(inRight);
//                                                viewAnimator.setOutAnimation(outLeft);
//
//                                                switchListView();
//
//                                                day++;
//                                                currenttv.setText("Day " + (day + 1));
//                                                currentAdapter.clear();
//                                                currentAdapter.addAll(mealPlan.getListForDay(day));
//                                                currentAdapter.notifyDataSetChanged();
//
//                                                viewAnimator.showNext();
//
//                                            }//end if
//                                        }//end else
//                                    }
//                                }
//                            }
//                            return false;
//                        }
//
//                    });
//
//                }//end else
//
//
//            }//if (recipeTitle != null) {
//
//            //end if (o != null) {
//
//            if (o.isCompleted())
//                mealComplete(v);
//
//            return v;
//        }
//
//
//
//
//
//
//        private View currentSelection; //this is used to instantaneously change a meal to completed when when "YES" is selected
//        private int tempDay; //used to check if all items need to be reset to closed
//    }











}
