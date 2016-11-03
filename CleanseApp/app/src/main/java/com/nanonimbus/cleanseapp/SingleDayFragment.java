package com.nanonimbus.cleanseapp;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.support.v4.view.ViewPager;


import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.INVISIBLE;
import static android.view.View.OnClickListener;
import static android.view.View.OnTouchListener;
import static android.view.View.VISIBLE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SingleDayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SingleDayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingleDayFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";




    View view;


    //used for both displaying one day of plan, all elements marked 2
    //are used to animate swiping through days of plan

    ArrayList<MealItem> list;




    ViewGroup addMe;
    MealPlan mealPlan;
    TextView tv;

    int day;
    int daysInPlan;

    int listViewNum;
    ViewPager vp;

    ScrollView recipeBox;

    ViewGroup mealCell;
    TextView currenttv;
    // MealItemAdapter currentAdapter;



    ArrayList<MealItem> dayList;



    TextView recipeTitle;
    TextView ingredientHeader;
    TextView recipeIngredientsContent;
    TextView recipeDirectionsContent;
    ImageView recipeBoxImageView;





    LinearLayout dailyMealList;
    LinearLayout currentDailyMealList;



    //used for detecting swipe vs click
    final int MAX_CLICK_DURATION = 500;
    final int MAX_CLICK_DISTANCE = 15;
    long pressStartTime;
    float pressedX;
    float pressedY;
    boolean stayedWithinClickDistance;
    AlertDialog.Builder builderSingle;

    ExpandableListView myList;
    ExpandableListAdapter swapRecipeAdapter;
    AlertDialog alert;
    AlertDialog alert2;
    ArrayList<MealItem> set1;
    List<String> headerTitles;
    List<Object> childTitles;
    int helperPosition;
    ArrayList<ArrayList<MealItem>> recipeSets;


    Button addOunceButton;
    Button subtractOunceButton;
    ViewGroup add8Ounces;
    ViewGroup add16Ounces;
    ViewGroup add24Ounces;
    ViewGroup add32Ounces;




    int currentProgress;

    TextView ouncesTextView1;
    TextView currentOuncesTextView;

    TextView inspirationalQuote1;
    TextView currentInspirationalQuote;

    ViewGroup layout1;



    private View currentSelection; //this is used to instantaneously change a meal to completed when when "YES" is selected



    //detects swipes while using this fragment
    //final SwipeDetector swipeDetector = new SwipeDetector();

    // TODO: Rename and change types of parameters
    private int mParam1;
    private String mParam2;

    // private OnFragmentInteractionListener mListener;

    public SingleDayFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SingleDayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SingleDayFragment newInstance(int param1, String param2) {
        SingleDayFragment fragment = new SingleDayFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            day = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            //day = getArguments().getInt("day_of_plan");
            System.out.println("setting day to " + day);
        }

//        day = ((MainActivity) getActivity()).getDayOfPlan();
//        if(day == 10)
//            day = 9;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_meal, container, false);


        layout1 = (ViewGroup) view.findViewById(R.id.layout1);

        //will need to dynamically change with day
        tv = ((TextView) layout1.findViewById(R.id.mealListDayHeader));

        inspirationalQuote1 = ((TextView) layout1.findViewById(R.id.inspirationalQuoteTV));
        currentInspirationalQuote = inspirationalQuote1;

        dailyMealList = (LinearLayout) layout1.findViewById(R.id.mealList);
        currentDailyMealList = dailyMealList;


        mealCell = (ViewGroup) layout1.findViewById(R.id.meal_cell_view);

        vp =(ViewPager) getActivity().findViewById(R.id.pager);


        recipeBox = (ScrollView) view.findViewById(R.id.recipeBoxView);
         
        recipeBox.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent e) {

                vp.requestDisallowInterceptTouchEvent(true);


                switch (e.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        pressedX = e.getRawX();
                        pressedY = e.getRawY();
                        break;
                    }


                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:


                        if (pressedX < e.getRawX() && ((pressedY - e.getRawY()) > -40) && ((pressedY - e.getRawY()) < 40)) {
                            recipeBox.animate().translationX(1500).alpha(0).setDuration(600).start();
                        }


                }
                return false;
            }

        });
//        recipeBox.setOnTouchListener(new OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                vp.requestDisallowInterceptTouchEvent(true);
//
//
//                recipeBox.animate().translationX(1500).alpha(0).setDuration(600).start();
//
//                return false;
//            }
//        });
        recipeBox.setX(1500);
        recipeBox.setAlpha(0);


        recipeTitle = (TextView) recipeBox.findViewById(R.id.recipeTitle);
        ingredientHeader = (TextView) recipeBox.findViewById(R.id.ingredientHeader);
        recipeIngredientsContent = (TextView) recipeBox.findViewById(R.id.recipeIngredientsContent);
        recipeDirectionsContent = (TextView) recipeBox.findViewById(R.id.recipeDirectionsContent);
        recipeBoxImageView = (ImageView) recipeBox.findViewById(R.id.recipeBoxImageView);


        //setup water tracker

        ouncesTextView1 = (TextView) layout1.findViewById(R.id.ouncesToGoTextView);
        //set up water tracker functionality
        addOunceButton = (Button) layout1.findViewById(R.id.addOunceButton);
        subtractOunceButton = (Button) layout1.findViewById(R.id.subtractOunceButton);
        add8Ounces = (ViewGroup) layout1.findViewById(R.id.ounce8);
        add16Ounces = (ViewGroup) layout1.findViewById(R.id.ounce16);
        add24Ounces = (ViewGroup) layout1.findViewById(R.id.ounce24);
        add32Ounces = (ViewGroup) layout1.findViewById(R.id.ounce32);



        addOunceButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addOunces(1);
                updateWaterProgressTextView();
            }
        });





        subtractOunceButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                currentProgress = 0;
                updateWaterProgressTextView();
                return true;
            }
        });

        subtractOunceButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentProgress != 0)
                    currentProgress -= 1;
                updateWaterProgressTextView();

            }
        });



        add8Ounces.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addOunces(8);
                updateWaterProgressTextView();

            }
        });



        add16Ounces.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addOunces(16);
                updateWaterProgressTextView();

            }
        });



        add24Ounces.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addOunces(24);
                updateWaterProgressTextView();

            }
        });


        add32Ounces.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addOunces(32);
                updateWaterProgressTextView();

            }
        });


        //end setup water tracker

//        FileOutputStream fos = null;
//        Boolean test = false;




        //maybe keep this idk
//        try {
//
//            FileInputStream fis = new FileInputStream(new File(getContext().getFilesDir() + "/" + ((MainActivity) getActivity()).getUserId(), "currentPlan.ser"));
//
//            ObjectInputStream is = new ObjectInputStream(fis);
//            mealPlan = (MealPlan) is.readObject();
//
//
//            //System.out.println(((MealPlan) is.readObject()).getListForDay(0).get(0).isCompleted() + " better be right");
//            is.close();
//            fis.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (OptionalDataException e) {
//            e.printStackTrace();
//        } catch (StreamCorruptedException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }


        currentProgress = ((MainActivity) getActivity()).getWaterForDayMain(day);
        currentOuncesTextView = ouncesTextView1;

        updateWaterProgress();



        getMealViews(dailyMealList);
        System.out.println("finished initialized fuck");

        list = new ArrayList<MealItem>();



        //setup the first layout to be displayed
        //---------
        //  testList = (ListView) layout1.findViewById(R.id.testListView);
        addMe = (ViewGroup) view.findViewById(R.id.recipeItem);



        //setup secondary layout, used when animating between daily meal plans
        //----------
       // dayList = ((MainActivity) getActivity()).getListForDayMain(day);

        //list = mealPlan.getListForDay(day);
        //  testList2 = (ListView) layout2.findViewById(R.id.testListView);
        // adapter2 = new MealItemAdapter(getContext(), R.layout.meal_tracker_recipe_item, list2);
        //  testList2.setAdapter(adapter2);
        //----------


        //initialize all animation variables
        final Animation outRight = AnimationUtils.loadAnimation(
                getContext(), R.anim.slide_out_right);
        final Animation inLeft = AnimationUtils.loadAnimation(
                getContext(), R.anim.slide_in_left);
        final Animation inRight = AnimationUtils.loadAnimation(
                getContext(), R.anim.slide_in_right_mt);
        final Animation outLeft = AnimationUtils.loadAnimation(
                getContext(), R.anim.slide_out_left);

        //initialize all current- variables to currently displayed views
        // currentAdapter = adapter;
        currenttv = tv;
        listViewNum = 1;
        currenttv.setText("Day " + (day + 1));
        currentInspirationalQuote.setText(((MainActivity) getActivity()).getDailyInspirationMain(day));


        recipeSets = new ArrayList<ArrayList<MealItem>>();
        headerTitles = new ArrayList<>();
        childTitles = new ArrayList<>();


try{

        for (int i = 0; i < ((MainActivity) getActivity()).getRecipeSetCount(); i++) {

            File file = new File(getActivity().getFilesDir() + "/recipeSet/");
            File inputFile = new File(((MainActivity) getActivity()).getSetPath(i));

            RecipeSet rs = getSetFromFile(inputFile.getAbsolutePath());
            //items = rs.getRecipeSet();
            set1 = rs.getRecipeSet();

            recipeSets.add(set1);

            headerTitles.add(rs.getRecipeSetTitle());


            childTitles.add(set1);

        }









        swapRecipeAdapter = new ExpandableListAdapter(getContext(),headerTitles, childTitles);

        myList = new ExpandableListView(getContext());

        myList.setAdapter(swapRecipeAdapter);


        myList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, final int groupPosition, final int childPosition, long id) {


                AlertDialog.Builder builderInner = new AlertDialog.Builder(
                        getContext());

                //builderInner.setMessage(strName);

                builderInner.setTitle("");


                View myV;
                LayoutInflater myVi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                myV = myVi.inflate(R.layout.meal_cell_image_only_layout, null);
                TextView text = (TextView) myV.findViewById(R.id.nameToSwap);
                text.setText(((MealItem) swapRecipeAdapter.getChild(groupPosition, childPosition)).getTitle());
                ImageView image = (ImageView) myV.findViewById(R.id.imageViewtoSwap);
                //loadPicassoPicFromFileAsync(recipeImageView, o);
                Picasso.with(getContext()).load(((MealItem) swapRecipeAdapter.getChild(groupPosition, childPosition)).getImageUrl()).into(image);


                builderInner.setView(myV);


                builderInner.setNegativeButton("Back", new DialogInterface.OnClickListener(

                ) {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //builderSingle.show();
                    }
                });
                builderInner.setPositiveButton(
                        "Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(
                                    DialogInterface dialog,
                                    int which) {


                                String tempHeader =  ((MainActivity) getActivity()).getListForDayMain(day).get(helperPosition).getHeader();

                                if(((MainActivity) getActivity()).getListForDayMain(day).get(helperPosition).getTitle().toUpperCase().equals("FAST METABOLISM CLEANSE")){
                                    ((MainActivity) getActivity()).setShakesSelectedPerDayMain(day, (((MainActivity) getActivity()).getShakesSelectedPerDayMain(day) - 1));
                                }



                                ((MainActivity) getActivity()).swapMealMain(day, helperPosition, (MealItem) swapRecipeAdapter.getChild(groupPosition, childPosition));


                                ((MainActivity) getActivity()).getListForDayMain(day).get(helperPosition).setHeader(tempHeader);

                                if(((MainActivity) getActivity()).getListForDayMain(day).get(helperPosition).getTitle().toUpperCase().equals("FAST METABOLISM CLEANSE")){
                                    ((MainActivity) getActivity()).setShakesSelectedPerDayMain(day, (((MainActivity) getActivity()).getShakesSelectedPerDayMain(day) + 1));
                                }

                                //currentAdapter.notifyDataSetChanged();

                                dailyMealList.removeAllViews();
                                getMealViews(dailyMealList);

                                dialog.dismiss();
                                alert.dismiss();



                                saveFile();
                            }
                        });

                builderInner.show();
                // Picasso.with(getContext()).load(((MealItem) swapRecipeAdapter.getChild(groupPosition, childPosition)).getImageUrl()).into(image);


                return false;
            }

        });

        builderSingle = new AlertDialog.Builder(getContext());
        // builderSingle.setIcon(R.drawable.ic_launcher);

        builderSingle.setTitle("Select Recipe to Swap In:");

        builderSingle.setView(myList);


        builderSingle.setNeutralButton(
                "cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alert.dismiss();
                    }
                });

        AlertDialog.Builder cantSwapBuilder = new AlertDialog.Builder(
                getContext());


        cantSwapBuilder.setMessage("You Cannot Swap A Fast Metabolism Cleanse Shake");


        cantSwapBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener(

        ) {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //builderSingle.show();
            }
        });



        alert = builderSingle.create();
        alert2 = cantSwapBuilder.create();




//        ViewGroup rl = (ViewGroup) view.findViewById(R.id.recipeBoxLinear);
//
//
//        rl.setOnTouchListener(new OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent e) {
//
//
//                switch (e.getAction()) {
//                    case MotionEvent.ACTION_DOWN: {
//                        pressStartTime = System.currentTimeMillis();
//                        pressedX = e.getRawX();
//                        pressedY = e.getRawY();
//                        stayedWithinClickDistance = true;
//                        break;
//                    }
//
//
//                    case MotionEvent.ACTION_CANCEL:
//                    case MotionEvent.ACTION_UP:
//
//
//                        long pressDuration = System.currentTimeMillis() - pressStartTime;
//                        if (pressDuration < MAX_CLICK_DURATION) {
//                            //LR
//                            System.out.println("difference " + (pressedY - e.getRawY()));
//
//                            if (pressedX < e.getRawX() && ((pressedY - e.getRawY()) > -20) && ((pressedY - e.getRawY()) < 20)) {
//                                recipeBox.animate().translationX(1500).alpha(0).setDuration(600).start();
//                            }
//
//                        }
//                }
//                return true;
//            }
//
//        });

}
catch(NullPointerException ex){


    ((TestMe) getParentFragment()).redownload();


    ex.printStackTrace();
}

        return view;
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            //i have no idea why this is here
//           // mListener.onFragmentInteraction(uri);
//        }
//    }

    //this method is used to switch all current- variables to the other layout (used for animation purposes)


    void updateWaterProgress(){
        //need to add method to return progress for that day from meal plan
        currentProgress = (((MainActivity) getActivity()).getWaterForDayMain(day));
        updateWaterProgressTextView();
    }

    void updateWaterProgressTextView(){
        ((MainActivity) getActivity()).saveWaterForDayMain(day, currentProgress);
        currentOuncesTextView.setText("Consumed: " + (currentProgress) + " oz");
    }

    void addOunces(int addMe){
        if(currentProgress + addMe >= 300)
            currentProgress = 300;
        else
            currentProgress += addMe;
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



        saveFile();

        //mListener = null;


    }

    RecipeSet getSetFromFile(String fileName) {

        RecipeSet retSet = null;

        FileOutputStream fos = null;
        Boolean test = false;
        try {

            FileInputStream fis = new FileInputStream(new File(fileName));

            ObjectInputStream is = new ObjectInputStream(fis);
            retSet = (RecipeSet) is.readObject();


            //System.out.println(((MealPlan) is.readObject()).getListForDay(0).get(0).isCompleted() + " better be right");
            is.close();
            fis.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            test = true;
        } catch (IOException e) {
            test = true;
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            test = true;
            e.printStackTrace();
        }



        return retSet;
    }

    //maybe switch this to main....
    void saveFile(){

        ((MainActivity) getActivity()).saveMealPlan();
//        FileOutputStream fos = null;
//        try {
//
//
//
//            fos = new FileOutputStream(new File(getContext().getFilesDir() + "/" + ((MainActivity) getActivity()).getUserId() + "/currentPlan.ser"));
//            // fos = new FileOutputStream(file, Context.MODE_PRIVATE);
//            ObjectOutputStream os = new ObjectOutputStream(fos);
//            os.reset();
//            os.writeObject(mealPlan);
//            os.close();
//            fos.close();
//
//
//            FileInputStream fis = new FileInputStream(new File(getContext().getFilesDir() + "/" + ((MainActivity) getActivity()).getUserId() + "/currentPlan.ser"));
//
//            ObjectInputStream is = new ObjectInputStream(fis);
//
//
//            is.close();
//            fis.close();
//
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    //used to override back button functionality
    @Override
    public void onResume() {
        super.onResume();


        getView().setFocusableInTouchMode(true);
        getView().requestFocus();

        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {


                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    if(recipeBox.getX() == 0) {
                        recipeBox.animate().translationX(1500).alpha(0).setDuration(600).start();
                    }
                    else{
                        //Toast.makeText(getActivity(), "Back press", Toast.LENGTH_SHORT).show();
                        getActivity().onBackPressed();
                    }

                    return true;
                }

                return false;
            }
        });





    }

    void startQuiz() {

        ((MainActivity)getActivity()).switchToQuizFragment();
    }

    void switchToPurchaseFragment(String planInfo, String planDesc){

        ((MainActivity)getActivity()).switchToPurchaseFragment();
    }

    void switchLayout(ViewGroup v1, ViewGroup v2) {
        v1.setVisibility(INVISIBLE);
        v2.setX(1000f);
        v2.setVisibility(VISIBLE);
        v2.animate().translationXBy(-1000f);

    }





    public void getMealViews(LinearLayout dailyLayout) {

        dailyLayout.removeAllViews();

        //initialize all animation variables
//        final Animation outRight = AnimationUtils.loadAnimation(
//                getContext(), R.anim.slide_out_right);
//        final Animation inLeft = AnimationUtils.loadAnimation(
//                getContext(), R.anim.slide_in_left);
//        final Animation inRight = AnimationUtils.loadAnimation(
//                getContext(), R.anim.slide_in_right_mt);
//        final Animation outLeft = AnimationUtils.loadAnimation(
//                getContext(), R.anim.slide_out_left);

        View v;

        LayoutInflater vi = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for (int i = 0; i < ((MainActivity) getActivity()).getListForDayMain(day).size(); i++) {

            v = vi.inflate(R.layout.meal_cell, null);


            final MealItem o = ((MainActivity) getActivity()).getListForDayMain(day).get(i);

         //   System.out.println(((MainActivity) getActivity()).isCompletedMain(day, i) + " for this day: " + day);


            if (o != null) {

                TextView recipeTitle = (TextView) v.findViewById(R.id.mealName);
                final TextView recipeHeader = (TextView) v.findViewById(R.id.mealHeader);
                final ImageView recipeImageView = (ImageView) v.findViewById(R.id.mealCellImageView);


                loadPicassoPicFromFileAsync(recipeImageView, o);




                if (recipeTitle != null) {
                    recipeTitle.setText(o.getTitle());

                    recipeHeader.setText(o.getHeader());



                    final int counter = i;
                    final View finalView = v;


                    final CheckBox cb = (CheckBox) v.findViewById(R.id.completeCheckBox);


                    cb.findViewById(R.id.completeCheckBox).setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((MainActivity) getActivity()).toggleCompletionMain(day, counter);
                            //o.toggleComplete();
                        }
                    });

                    cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if(((MainActivity) getActivity()).checkCompletedMain(day, counter))
                                cb.setChecked(true);
                        }
                    });





                    final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {

                                case DialogInterface.BUTTON_POSITIVE:
                                    ((MainActivity) getActivity()).toggleCompletionMain(day, counter);
                                    //o.toggleComplete();

                                    //if (o.isCompleted()) {
                                    if(((MainActivity) getActivity()).isCompletedMain(day, counter)){
                                        System.out.println("complete");
                                        mealComplete(finalView);
                                    } else {
                                        mealUncomplete(finalView);
                                    }

                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:


                                    if (o.getTitle().toUpperCase().equals("FAST METABOLISM CLEANSE")) {

                                        if (((MainActivity) getActivity()).getShakesSelectedPerDayMain(day) == ((MainActivity) getActivity()).getShakesNeededPerDayMain(day)){

                                            if(((MainActivity) getActivity()).getShakesSelectedPerDayMain(day) == 5){
                                                alert2.setMessage("Day " + (day + 1) + " of the cleanse consists of only Fast Metabolism Cleanse shakes.");
                                            }
                                            else {
                                                alert2.setMessage(((MainActivity) getActivity()).getShakesNeededPerDayMain(day) + " Fast Metabolism Cleanse shakes are needed for this day!\n" +
                                                        "Swap in a Fast Metabolism Cleanse shake first (located under Basic Set section).");
                                            }
                                            alert2.show();
                                        }
                                        else{
                                            alert.show();
                                        }


                                    } else {
                                        // for (int i = 0; i < swapRecipeAdapter.getGroupCount(); i++) {
                                        //    myList.collapseGroup(i);

                                        alert.show();
                                        //}
                                    }

                                    break;

                                case DialogInterface.BUTTON_NEUTRAL:
                                    break;
                            }//end switch
                        }
                    };//end listener

                    //this sets up a yes/no selection box to make sure users want to complete a meal
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
                    builder.setMessage("Please Select an Option").setPositiveButton("Complete", dialogClickListener)
                            .setNegativeButton("Swap Meal", dialogClickListener).setNeutralButton("Cancel", dialogClickListener);




                    //v.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    //recipeImageView.setBackgroundColor(Color.parseColor("#FFFFFF"));


                    //this listener allows users to complete meal
                    v.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            currentSelection = v;
                            builder.show();
                            helperPosition = counter;

                            return true;
                        }
                    });

                    v.setOnTouchListener(new OnTouchListener() {

                        @Override
                        public boolean onTouch(View v, MotionEvent e) {

                            switch (e.getAction()) {
                                case MotionEvent.ACTION_DOWN: {
                                    pressStartTime = System.currentTimeMillis();
                                    pressedX = e.getRawX();
                                    pressedY = e.getRawY();
                                    stayedWithinClickDistance = true;
                                    break;
                                }
                                case MotionEvent.ACTION_MOVE: {
                                    if (stayedWithinClickDistance && distance(pressedX, pressedY, e.getRawX(), e.getRawY()) > MAX_CLICK_DISTANCE) {
                                        stayedWithinClickDistance = false;
                                    }
                                    break;
                                }
                                case MotionEvent.ACTION_CANCEL:


                                    if (stayedWithinClickDistance) {
                                        // Click event has occurred
                                        // open recipe
                                        // hideAndShowMealItem(o);
                                    }
                                    System.out.println("difference " + (pressedY - e.getRawY()));


                                    break;
                                case MotionEvent.ACTION_UP:

                                    long pressDuration = System.currentTimeMillis() - pressStartTime;
                                    if (pressDuration < MAX_CLICK_DURATION && stayedWithinClickDistance) {
                                        // Click event has occurred
                                        // open recipe
                                        hideAndShowMealItem(o);
                                    }

                            }
                            return false;
                        }

                    });

                }//end else




            }//if (recipeTitle != null)

            //end if (o != null) {

           // if (o.isCompleted())



            if (((MainActivity) getActivity()).isCompletedMain(day, i)){
                ((CheckBox)v.findViewById(R.id.completeCheckBox)).setChecked(true);
                System.out.println("its set to fucking true and it is " + ((CheckBox)v.findViewById(R.id.completeCheckBox)).isChecked() + " for num " + i +"  and day " + day);
            }
            else {
                ((CheckBox) v.findViewById(R.id.completeCheckBox)).setChecked(false);
            }

            dailyLayout.addView(v);


        }

    }


    void loadPicassoPicFromFileAsync(final ImageView v, final MealItem o){


        File file = new File(getContext().getFilesDir(), o.getImageUrl());

        if (!file.exists()) {
            System.out.println("file did not exist");
            //o.setLoaded(false);

            Picasso.with(getContext()).load(o.getImageUrl()).into(v, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {


                    // The types specified here are the input data type, the progress type, and the result type
                    class MyAsyncTask extends AsyncTask<Bitmap, Void, Bitmap> {
                        @Override
                        protected Bitmap doInBackground(Bitmap... params) {
                            // Some long-running task like downloading an image.

                            File file = new File(getContext().getFilesDir(), o.getImageUrl());

                            File parent = file.getParentFile();
                            if (!parent.exists() && !parent.mkdirs()) {
                                throw new IllegalStateException("Couldn't create dir: " + parent);
                            }

                            try {
                                file.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            System.out.println(file.exists() + " does it exists");

                            FileOutputStream fos = null;
                            try {

                                // fos = getContext().openFileOutput(file.getName(), Context.MODE_PRIVATE);
                                fos = new FileOutputStream(file);

                                //Bitmap bitmap = ((BitmapDrawable)v.getDrawable()).getBitmap();


                                params[0].compress(Bitmap.CompressFormat.PNG, 100, fos);
                                //}

                                o.setLoaded(true);
                                Log.i("image", "image saved to >>>" + file.getAbsolutePath());

                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                try {
                                    fos.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            return null;
                        }

                        protected void onPreExecute() {
                            // Runs on the UI thread before doInBackground
                        }


//                            protected void onProgressUpdate(Progress... values) {
//                                // Executes whenever publishProgress is called from doInBackground
//                            }

                        protected void onPostExecute(Bitmap result) {
                            // This method is executed in the UIThread
                            // with access to the result of the long running task
                            //v.setImageBitmap(result);
                        }
                    }


                    new MyAsyncTask().execute(((BitmapDrawable) v.getDrawable()).getBitmap());

                }

                @Override
                public void onError() {
                    System.out.println("failure");
                }
            });
        } else {

            //System.out.println("loaded from memory");
            // Picasso.with(getContext()).load("file://" + file.getAbsolutePath()).fit().centerCrop().into(v);
            Picasso.with(getContext()).load("file://" + file.getAbsolutePath()).fit().into(v);



        }
    }


    private float distance(float x1, float y1, float x2, float y2) {
        float dx = x1 - x2;
        float dy = y1 - y2;
        float distanceInPx = (float) Math.sqrt(dx * dx + dy * dy);
        return pxToDp(distanceInPx);
    }

    private float pxToDp(float px) {
        return px / getResources().getDisplayMetrics().density;
    }

    void hideAndShowMealItem(MealItem o) {

        vp.requestDisallowInterceptTouchEvent(false);

        recipeTitle.setText(o.getTitle());
        ingredientHeader.setText("Ingredients (Serves " + o.getServings() + ")");
        recipeIngredientsContent.setText(o.getIngredients());
        recipeDirectionsContent.setText(o.getDirections());

        if(!o.getImageUrl().isEmpty()) {
            // Picasso.with(getContext()).load(o.getImageUrl()).into(recipeBoxImageView);
            loadPicassoPicFromFileAsync(recipeBoxImageView, o);
        }

        System.out.println("hiding show");

        recipeBox.animate().translationX(0).alpha(1).setDuration(600).start();


    }

    void mealComplete(View v) {
        //v.setBackgroundColor(Color.GRAY);
        ((CheckBox) v.findViewById(R.id.completeCheckBox)).setChecked(true);
        //v.findViewById(R.id.mealCellImageView).setBackgroundColor(Color.GRAY);
        //v.setOnLongClickListener(null);
        saveFile();
    }
    void mealUncomplete(View v){
        ((CheckBox) v.findViewById(R.id.completeCheckBox)).setChecked(false);
        saveFile();
        // v.setBackgroundColor(getResources().getColor(R.color.main_background));
        //v.findViewById(R.id.mealCellImageView).setBackgroundColor(getResources().getColor(R.color.main_background));
        //v.setOnLongClickListener(null);
    }





//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p/>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
//




}









