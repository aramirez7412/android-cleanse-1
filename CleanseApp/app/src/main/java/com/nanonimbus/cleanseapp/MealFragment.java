package com.nanonimbus.cleanseapp;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
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
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.view.View.INVISIBLE;
import static android.view.View.OnClickListener;
import static android.view.View.OnTouchListener;
import static android.view.View.VISIBLE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MealFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MealFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MealFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";




    View view;


    //used for both displaying one day of plan, all elements marked 2
    //are used to animate swiping through days of plan
    ListView testList;
    ListView testList2;
    ArrayList<MealItem> list;
    ArrayList<MealItem> list2;
    TextView menuArrow;
    TextView menuArrow2;
    ListView dayListView;
    ListView dayListView2;
//    MealItemAdapter adapter;
//    MealItemAdapter adapter2;

    ViewGroup addMe;
    MealPlan mealPlan;
    TextView tv;
    TextView tv2;

    int day;
    int daysInPlan;

    int listViewNum;


    View recipeBox;

    ViewGroup mealCell;
    TextView currenttv;
   // MealItemAdapter currentAdapter;
    ListView previousList;

    ViewAnimator viewAnimator;


    TextView recipeTitle;
    TextView ingredientHeader;
    TextView recipeIngredientsContent;
    TextView recipeDirectionsContent;
    ImageView recipeBoxImageView;


    TextView nextDayButton;
    TextView prevDayButton;
    TextView nextDayButton2;
    TextView prevDayButton2;

    LinearLayout dailyMealList;
    LinearLayout dailyMealList2;
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

    Button addOunceButton2;
    Button subtractOunceButton2;
    ViewGroup add8Ounces2;
    ViewGroup add16Ounces2;
    ViewGroup add24Ounces2;
    ViewGroup add32Ounces2;


    int currentProgress;

    TextView ouncesTextView1;
    TextView ouncesTextView2;
    TextView currentOuncesTextView;

    TextView inspirationalQuote1;
    TextView inspirationalQuote2;
    TextView currentInspirationalQuote;

    ViewGroup layout1;
    ViewGroup layout2;



    private View currentSelection; //this is used to instantaneously change a meal to completed when when "YES" is selected



    //detects swipes while using this fragment
    //final SwipeDetector swipeDetector = new SwipeDetector();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

   // private OnFragmentInteractionListener mListener;

    public MealFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MealFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MealFragment newInstance(String param1, String param2) {
        MealFragment fragment = new MealFragment();
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

        day = ((MainActivity) getActivity()).getDayOfPlan();
        if(day == 10)
            day = 9;

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
       //  layout2 = (ViewGroup) view.findViewById(R.id.layout2);



        //will need to dynamically change with day
        tv = ((TextView) layout1.findViewById(R.id.mealListDayHeader));
        tv2 = ((TextView) layout2.findViewById(R.id.mealListDayHeader));


        inspirationalQuote1 = ((TextView) layout1.findViewById(R.id.inspirationalQuoteTV));
        inspirationalQuote2 = ((TextView) layout2.findViewById(R.id.inspirationalQuoteTV));
        currentInspirationalQuote = inspirationalQuote1;

        // dayListView = (ListView) layout1.findViewById(R.id.testListView);
        //dayListView2 = (ListView) layout2.findViewById(R.id.testListView);

        dailyMealList = (LinearLayout) layout1.findViewById(R.id.mealList);
        dailyMealList2 = (LinearLayout) layout2.findViewById(R.id.mealList);
        currentDailyMealList = dailyMealList;


       // viewAnimator = (ViewAnimator) view.findViewById(R.id.myViewAnimator);

        mealCell = (ViewGroup) layout1.findViewById(R.id.meal_cell_view);

        recipeBox = view.findViewById(R.id.recipeBoxView);
        recipeBox.setX(1500);
        recipeBox.setAlpha(0);


        recipeTitle = (TextView) recipeBox.findViewById(R.id.recipeTitle);
        ingredientHeader = (TextView) recipeBox.findViewById(R.id.ingredientHeader);
        recipeIngredientsContent = (TextView) recipeBox.findViewById(R.id.recipeIngredientsContent);
        recipeDirectionsContent = (TextView) recipeBox.findViewById(R.id.recipeDirectionsContent);
        recipeBoxImageView = (ImageView) recipeBox.findViewById(R.id.recipeBoxImageView);


        //setup water tracker

        ouncesTextView1 = (TextView) layout1.findViewById(R.id.ouncesToGoTextView);
        ouncesTextView2 = (TextView) layout2.findViewById(R.id.ouncesToGoTextView);
        //set up water tracker functionality
        addOunceButton = (Button) layout1.findViewById(R.id.addOunceButton);
        subtractOunceButton = (Button) layout1.findViewById(R.id.subtractOunceButton);
        add8Ounces = (ViewGroup) layout1.findViewById(R.id.ounce8);
        add16Ounces = (ViewGroup) layout1.findViewById(R.id.ounce16);
        add24Ounces = (ViewGroup) layout1.findViewById(R.id.ounce24);
        add32Ounces = (ViewGroup) layout1.findViewById(R.id.ounce32);


        addOunceButton2 = (Button) layout2.findViewById(R.id.addOunceButton);
        subtractOunceButton2 = (Button) layout2.findViewById(R.id.subtractOunceButton);
        add8Ounces2 = (ViewGroup) layout2.findViewById(R.id.ounce8);
        add16Ounces2 = (ViewGroup) layout2.findViewById(R.id.ounce16);
        add24Ounces2 = (ViewGroup) layout2.findViewById(R.id.ounce24);
        add32Ounces2 = (ViewGroup) layout2.findViewById(R.id.ounce32);

        addOunceButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addOunces(1);
                updateWaterProgressTextView();
            }
        });

        addOunceButton2.setOnClickListener(new OnClickListener() {
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

        subtractOunceButton2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentProgress != 0)
                    currentProgress -= 1;
                updateWaterProgressTextView();

            }
        });

        subtractOunceButton2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                currentProgress = 0;
                updateWaterProgressTextView();
                return true;
            }
        });

        add8Ounces.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addOunces(8);
                updateWaterProgressTextView();

            }
        });

        add8Ounces2.setOnClickListener(new OnClickListener() {
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

        add16Ounces2.setOnClickListener(new OnClickListener() {
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

        add24Ounces2.setOnClickListener(new OnClickListener() {
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

        add32Ounces2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addOunces(32);
                updateWaterProgressTextView();

            }
        });








        //end setup water tracker

//        FileOutputStream fos = null;
//        Boolean test = false;
        try {

            FileInputStream fis = new FileInputStream(new File(getContext().getFilesDir() + "/" + ((MainActivity) getActivity()).getUserId(), "currentPlan.ser"));

            ObjectInputStream is = new ObjectInputStream(fis);
            mealPlan = (MealPlan) is.readObject();


        //System.out.println(((MealPlan) is.readObject()).getListForDay(0).get(0).isCompleted() + " better be right");
        is.close();
        fis.close();
    } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (OptionalDataException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
//
//            System.out.println("successfully loaded mealPlan");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            test = true;
//        } catch (IOException e) {
//            test = true;
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            test = true;
//            e.printStackTrace();
//        }
//
//        if(test) {
//            try {
//                String jsonPlan = sendGET("http://ec2-52-52-65-150.us-west-1.compute.amazonaws.com:3000/meal-plans");
//                if (!jsonPlan.isEmpty()) {
//                    ((MainActivity) getActivity()).setPlan(jsonPlan);
//                   // ((MainActivity) getActivity()).setPlanInt(1);
//                }
//
//            } catch (IOException e) {
//                System.out.println("error downloading plan");
//                e.printStackTrace();
//            }
//
//            setCurrentPlan(((MainActivity) getActivity()).getJSONPlan());
//
//            fos = null;
//            try {
//
//                //File file = new File();
//
////                File parent = file.getParentFile();
////                if(!parent.exists() && !parent.mkdirs()){
////                    throw new IllegalStateException("Couldn't create dir: " + parent);
////                }
//
//                fos = new FileOutputStream(new File(getContext().getFilesDir() + "/" + ((MainActivity) getActivity()).getUserId(), "currentPlan.ser"));
//                // fos = new FileOutputStream(file, Context.MODE_PRIVATE);
//                ObjectOutputStream os = new ObjectOutputStream(fos);
//                os.reset();
//                os.writeObject(mealPlan);
//                os.close();
//                fos.close();
//                System.out.println("successfully saved mealPlan to: somewhere"); //+ file.getAbsolutePath());
//
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//
//        }






            currentProgress = mealPlan.getWaterForDay(day);
            currentOuncesTextView = ouncesTextView1;
            daysInPlan = mealPlan.getDays();

            updateWaterProgress();

             System.out.println("post the parse");


            getMealViews(dailyMealList);

            list = new ArrayList<MealItem>();


            //setup the first layout to be displayed
            //---------
          //  testList = (ListView) layout1.findViewById(R.id.testListView);
            addMe = (ViewGroup) view.findViewById(R.id.recipeItem);
//            adapter = new MealItemAdapter(getContext(), R.layout.meal_tracker_recipe_item, list);
//            testList.setAdapter(adapter);
//            adapter.addAll(mealPlan.getListForDay(day));
            //----------


            //setup secondary layout, used when animating between daily meal plans
            //----------
            list = mealPlan.getListForDay(day);
            list2 = new ArrayList<MealItem>();
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
            previousList = dayListView2;
           // currentAdapter = adapter;
            currenttv = tv;
            listViewNum = 1;
            currenttv.setText("Day " + (day + 1));
            currentInspirationalQuote.setText(mealPlan.getDailyFacts(day).dailyInspiration);


        recipeSets = new ArrayList<ArrayList<MealItem>>();
        headerTitles = new ArrayList<>();
        childTitles = new ArrayList<>();


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

                                                    //recipeSets.get(groupPosition).get(childPosition).setHeader(mealItemToSwap.getHeader());
                                                    //items.set(helperPosition, recipeSets.get(groupPosition).get(childPosition));

                                                    String tempHeader =  mealPlan.getListForDay(day).get(helperPosition).getHeader();

                                                    if(mealPlan.getListForDay(day).get(helperPosition).getTitle().toUpperCase().equals("FAST METABOLISM CLEANSE")){
                                                        mealPlan.setShakesSelectedPerDay(day, (mealPlan.getShakesSelectedPerDay(day) - 1));
                                                    }



                                                    mealPlan.swapMeal(day, helperPosition, (MealItem) swapRecipeAdapter.getChild(groupPosition, childPosition));
                                                   // mealPlan.getListForDay(day).get(helperPosition).setHeader(items.get(helperPosition).getHeader());


                                                    mealPlan.getListForDay(day).get(helperPosition).setHeader(tempHeader);

                                                    if(mealPlan.getListForDay(day).get(helperPosition).getTitle().toUpperCase().equals("FAST METABOLISM CLEANSE")){
                                                        mealPlan.setShakesSelectedPerDay(day, (mealPlan.getShakesSelectedPerDay(day) + 1));
                                                    }

                                                    //currentAdapter.notifyDataSetChanged();

                                                    currentDailyMealList.removeAllViews();
                                                    getMealViews(currentDailyMealList);

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

                        builderSingle.setTitle("Select Recipe to Swap:");

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


            cantSwapBuilder.setMessage("You Cannot Swap A Shake");


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




            ViewGroup rl = (ViewGroup) view.findViewById(R.id.recipeBoxLinear);


        rl.setOnTouchListener(new OnTouchListener() {
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


                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:


                        long pressDuration = System.currentTimeMillis() - pressStartTime;
                        if (pressDuration < MAX_CLICK_DURATION) {
                            //LR
                            System.out.println("difference " + (pressedY - e.getRawY()));

                            if (pressedX < e.getRawX() && ((pressedY - e.getRawY()) > -20) && ((pressedY - e.getRawY()) < 20)) {
                                recipeBox.animate().translationX(1500).alpha(0).setDuration(600).start();
                            }

                        }
                }
                        return true;
                }

        });


           // nextDayButton = (TextView) layout1.findViewById(R.id.nextDayButton);
           // prevDayButton = (TextView) layout1.findViewById(R.id.prevDayButton);
           // nextDayButton2 = (TextView) layout2.findViewById(R.id.nextDayButton);
            //prevDayButton2 = (TextView) layout2.findViewById(R.id.prevDayButton);

            OnClickListener nextDay = new OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (day < daysInPlan - 1) {

                        viewAnimator.setInAnimation(inRight);
                        viewAnimator.setOutAnimation(outLeft);

                        switchListView();

                        day++;
                        currenttv.setText("Day " + (day + 1));
//                        currentAdapter.clear();
//                        currentAdapter.addAll(mealPlan.getListForDay(day));
//                        currentAdapter.notifyDataSetChanged();


                        currentInspirationalQuote.setText(mealPlan.getDailyFacts(day).dailyInspiration);

                        currentDailyMealList.removeAllViews();
                        getMealViews(currentDailyMealList);

                        updateWaterProgress();

                        viewAnimator.showNext();



                    }

                }
            };


            OnClickListener prevDay = new OnClickListener() {
                @Override
                public void onClick(View v) {

                    //check if previous day exists, if so proceed displaying previous day
                    if (day > 0) {

                        viewAnimator.setInAnimation(inLeft);
                        viewAnimator.setOutAnimation(outRight);

                        switchListView();

                        day--;

                        currenttv.setText("Day " + (day + 1));
//                        currentAdapter.clear();
//                        currentAdapter.addAll(mealPlan.getListForDay(day));
//                        currentAdapter.notifyDataSetChanged();

                        currentDailyMealList.removeAllViews();
                        getMealViews(currentDailyMealList);



                        updateWaterProgress();

                        currentInspirationalQuote.setText(mealPlan.getDailyFacts(day).dailyInspiration);



                        viewAnimator.showPrevious();

                    }
                }
            };


            nextDayButton.setOnClickListener(nextDay);
            nextDayButton2.setOnClickListener(nextDay);


            prevDayButton.setOnClickListener(prevDay);
            prevDayButton2.setOnClickListener(prevDay);





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
    void switchListView(){



        if(listViewNum == 1){

            layout2.scrollTo(0, 0);

            listViewNum =2;
           // currentAdapter = adapter2;
            currenttv = tv2;
            currentDailyMealList = dailyMealList2;
            currentOuncesTextView = ouncesTextView2;
            currentInspirationalQuote = inspirationalQuote2;

            previousList = dayListView;


        }
        else{

            layout1.scrollTo(0,0);

            listViewNum = 1;
          //  currentAdapter = adapter;
            currenttv = tv;

            currentDailyMealList = dailyMealList;
            currentOuncesTextView = ouncesTextView1;
            currentInspirationalQuote = inspirationalQuote1;
            previousList = dayListView2;
        }


    }

    void updateWaterProgress(){
        //need to add method to return progress for that day from meal plan
        currentProgress = (mealPlan.getWaterForDay(day));
        updateWaterProgressTextView();
    }

    void updateWaterProgressTextView(){
        mealPlan.saveWaterForDay(day, currentProgress);
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

    void saveFile(){
        FileOutputStream fos = null;
        try {



            fos = new FileOutputStream(new File(getContext().getFilesDir() + "/" + ((MainActivity) getActivity()).getUserId() + "/currentPlan.ser"));
            // fos = new FileOutputStream(file, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.reset();
            os.writeObject(mealPlan);
            os.close();
            fos.close();


            FileInputStream fis = new FileInputStream(new File(getContext().getFilesDir() + "/" + ((MainActivity) getActivity()).getUserId() + "/currentPlan.ser"));

            ObjectInputStream is = new ObjectInputStream(fis);


            is.close();
            fis.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

//    void setCurrentPlan(String JSONPlan) {
//
//
//        try {
//
//            //initialize the jsonObject
//            JSONArray jsonArray;
//            System.out.println("please work" + JSONPlan);
//            jsonArray = new JSONArray(JSONPlan);
//            JSONObject jsonObject = jsonArray.getJSONObject(0);
//
//            System.out.println("before the parse");
//            //parse the object and create a meal plan
//            mealPlan = new MealPlan(jsonObject, getContext());
//
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//    }



    private static String sendGET(String GET_URL) throws IOException {


        String USER_AGENT = "Mozilla/5.0";
       // String GET_URL = "http://ec2-52-52-65-150.us-west-1.compute.amazonaws.com:3000/meal-plans";

        URL obj = new URL(GET_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            // print result
            System.out.println(response.toString());
            return response.toString();
        } else {
            System.out.println("GET request not worked");
            return "";
        }

    }



    public void getMealViews(LinearLayout dailyLayout) {

        //initialize all animation variables
        final Animation outRight = AnimationUtils.loadAnimation(
                getContext(), R.anim.slide_out_right);
        final Animation inLeft = AnimationUtils.loadAnimation(
                getContext(), R.anim.slide_in_left);
        final Animation inRight = AnimationUtils.loadAnimation(
                getContext(), R.anim.slide_in_right_mt);
        final Animation outLeft = AnimationUtils.loadAnimation(
                getContext(), R.anim.slide_out_left);

        View v;

        LayoutInflater vi = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for (int i = 0; i < mealPlan.getMealCountForDay(day); i++) {

            v = vi.inflate(R.layout.meal_cell, null);


            final MealItem o = mealPlan.getMeal(day, i);


            if (o != null) {

                TextView recipeTitle = (TextView) v.findViewById(R.id.mealName);
                final TextView recipeHeader = (TextView) v.findViewById(R.id.mealHeader);
                final ImageView recipeImageView = (ImageView) v.findViewById(R.id.mealCellImageView);


                loadPicassoPicFromFileAsync(recipeImageView, o);


                if (recipeTitle != null) {
                    recipeTitle.setText(o.getTitle());

                    recipeHeader.setText(o.getHeader());


                    v.findViewById(R.id.completeCheckBox).setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            o.toggleComplete();
                        }
                    });

                    final int counter = i;
                    final View finalView = v;

                    final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {

                                case DialogInterface.BUTTON_POSITIVE:
                                    mealPlan.toggleCompletion(day, counter);

                                    if (mealPlan.isCompleted(day, counter)) {
                                        System.out.println("complete");
                                        mealComplete(finalView);
                                    } else {
                                        mealUncomplete(finalView);
                                    }

                                    saveFile();

                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:


                                    if (o.getTitle().toUpperCase().equals("FAST METABOLISM CLEANSE")) {

                                        if (mealPlan.getShakesSelectedPerDay(day) == mealPlan.getShakesNeededPerDay(day)){

                                            if(mealPlan.getShakesSelectedPerDay(day) == 5){
                                                alert2.setMessage("Day " + (day + 1) + " of the cleanse consists of only shakes.");
                                            }
                                            else {
                                                alert2.setMessage(mealPlan.getShakesNeededPerDay(day) + " shakes are needed for this day!\n" +
                                                        "Swap another meal to a shake first.");
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

                    ((CheckBox) v.findViewById(R.id.completeCheckBox)).setChecked(false);

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

                                    //LR
                                    if (pressedX < e.getRawX() && ((pressedY - e.getRawY()) > -20) && ((pressedY - e.getRawY()) < 20)) {
                                        //check if previous day exists, if so proceed displaying previous day
                                        if (day > 0) {

                                            viewAnimator.setInAnimation(inLeft);
                                            viewAnimator.setOutAnimation(outRight);

                                            switchListView();

                                            day--;

                                            currenttv.setText("Day " + (day + 1));
//                                            currentAdapter.clear();
//                                            currentAdapter.addAll(mealPlan.getListForDay(day));
//                                            currentAdapter.notifyDataSetChanged();

                                            currentInspirationalQuote.setText(mealPlan.getDailyFacts(day).dailyInspiration);

                                            currentDailyMealList.removeAllViews();
                                            getMealViews(currentDailyMealList);


                                            updateWaterProgress();

                                            viewAnimator.showPrevious();
                                        }
                                    }
                                    //RL
                                    else if(((pressedY - e.getRawY()) > -20) && ((pressedY - e.getRawY()) < 20)) {
                                        System.out.println("on swappin ");

                                        //check if next day exists, if so proceed displaying next day
                                        if (day < daysInPlan - 1) {

                                            viewAnimator.setInAnimation(inRight);
                                            viewAnimator.setOutAnimation(outLeft);

                                            switchListView();

                                            day++;
                                            currenttv.setText("Day " + (day + 1));
//                                            currentAdapter.clear();
//                                            currentAdapter.addAll(mealPlan.getListForDay(day));
//                                            currentAdapter.notifyDataSetChanged();


                                            currentInspirationalQuote.setText(mealPlan.getDailyFacts(day).dailyInspiration);

                                            currentDailyMealList.removeAllViews();
                                            getMealViews(currentDailyMealList);

                                            updateWaterProgress();

                                            viewAnimator.showNext();

                                        }//end if
                                    }//end else


                                    break;
                                case MotionEvent.ACTION_UP:

                                    long pressDuration = System.currentTimeMillis() - pressStartTime;
                                    if (pressDuration < MAX_CLICK_DURATION && stayedWithinClickDistance) {
                                        // Click event has occurred
                                        // open recipe
                                        hideAndShowMealItem(o);
                                    } else if (pressDuration < MAX_CLICK_DURATION) {
                                        //LR
                                        System.out.println("difference " + (pressedY - e.getRawY()));

                                        if (pressedX < e.getRawX() && ((pressedY - e.getRawY()) > -20) && ((pressedY - e.getRawY()) < 20)) {


                                                //check if previous day exists, if so proceed displaying previous day
                                            if (day > 0) {

                                                viewAnimator.setInAnimation(inLeft);
                                                viewAnimator.setOutAnimation(outRight);

                                                switchListView();

                                                day--;

                                                currenttv.setText("Day " + (day + 1));
//                                                currentAdapter.clear();
//                                                currentAdapter.addAll(mealPlan.getListForDay(day));
//                                                currentAdapter.notifyDataSetChanged();

                                                currentDailyMealList.removeAllViews();
                                                getMealViews(currentDailyMealList);

                                                currentInspirationalQuote.setText(mealPlan.getDailyFacts(day).dailyInspiration);


                                                updateWaterProgress();

                                                viewAnimator.showPrevious();
                                            }
                                        }
                                        //RL
                                        else if(((pressedY - e.getRawY()) > -20) && ((pressedY - e.getRawY()) < 20)) {
                                            //check if next day exists, if so proceed displaying next day
                                            if (day < daysInPlan - 1) {

                                                viewAnimator.setInAnimation(inRight);
                                                viewAnimator.setOutAnimation(outLeft);

                                                switchListView();

                                                day++;
                                                currenttv.setText("Day " + (day + 1));
//                                                currentAdapter.clear();
//                                                currentAdapter.addAll(mealPlan.getListForDay(day));
//                                                currentAdapter.notifyDataSetChanged();

                                                currentInspirationalQuote.setText(mealPlan.getDailyFacts(day).dailyInspiration);


                                                currentDailyMealList.removeAllViews();
                                                getMealViews(currentDailyMealList);
                                                updateWaterProgress();

                                                viewAnimator.showNext();

                                            }//end if
                                        }//end else
                                    }


                            }
                            return false;
                        }

                    });

                }//end else


            }//if (recipeTitle != null) {

            //end if (o != null) {

            if (o.isCompleted())
                mealComplete(v);


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
        recipeTitle.setText(o.getTitle());
        ingredientHeader.setText("Ingredients (Serves " + o.getServings() + ")");
        recipeIngredientsContent.setText(o.getIngredients());
        recipeDirectionsContent.setText(o.getDirections());

        if(!o.getImageUrl().isEmpty()) {
            // Picasso.with(getContext()).load(o.getImageUrl()).into(recipeBoxImageView);
            loadPicassoPicFromFileAsync(recipeBoxImageView, o);
        }

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









