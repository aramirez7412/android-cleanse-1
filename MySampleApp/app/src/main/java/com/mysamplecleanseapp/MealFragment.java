package com.mysamplecleanseapp;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewAnimator;


import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
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

    ViewGroup noPlanView;

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
    MealItemAdapter adapter;
    MealItemAdapter adapter2;

    ViewGroup addMe;
    MealPlan mealPlan;
    TextView tv;
    TextView tv2;

    int day;
    int daysInPlan;

    int listViewNum;

    TextView upArrow;

    View recipeBox;

    ViewGroup mealCell;
    ViewGroup topMenu;
    TextView currenttv;
    MealItemAdapter currentAdapter;
    ListView previousList;

    ViewAnimator viewAnimator;


    Button takeQuizButton;


    TextView recipeTitle;
    TextView ingredientHeader;
    TextView recipeIngredientsContent;
    TextView recipeDirectionsContent;
    ImageView recipeBoxImageView;



    TextView purchasePlanTextView1;


    TextView nextDayButton;
    TextView prevDayButton;
    TextView nextDayButton2;
    TextView prevDayButton2;

    String heavyMetalInformation = "The Fast Metabolism Heavy Metal Cleanse is for individuals looking for a program designed to help reduce: \n• Alcohol intolerance • Allergies (environmental / food sensitivities) • Anxiety and irritability • Brain fog • Inability to lose weight • Chronic unexplained pain • Coated tongue • Cold hands and feet • Dark circles under the eyes • Depression • Digestive problems • Extreme fatigue • Frequent colds and flus • Headaches • High levels of toxic metals in your blood, urine or tissues • Insomnia • Intolerance to medications & vitamins • Loss of memory and forgetfulness • Low body temperature • Metallic taste in mouth • Muscle and joint pain • Muscle tics or twitches • Muscle tremors • Night sweats • Prone to mood swings • Prone to rashes • Sensitive teeth • Sensitive to smells like tobacco smoke, perfumes, paint fumes and chemical odors • Skin problems • Small black spots on your gums • Sore or receding gums • Tingling in the extremities";

    String candidaInformation = "The Fast Metabolism Candida Cleanse is for individuals looking for a program designed to help reduce:\n• Skin and nail fungal infections, such as athlete's foot or toenail fungus • Feeling tired and worn down, or suffering from chronic fatigue or fibromyalgia • Digestive issues such as bloating, constipation, diarrhea or chronic flatulence • Abdominal cramps alleviated by bowel movements • Irritable Bowel Syndrome ( Note: some have had amazing results with IBS after dealing with Candida / Yeast Issues ) • Heart burn / Indigestion • Dry mouth, bad breath • Autoimmune diseases such as Hashimoto's thyroiditis, rheumatoid arthritis, ulcerative colitis, lupus, psoriasis, scleroderma, or multiple sclerosis • Difficulty concentrating, poor memory, lack of focus, ADD, ADHD, and brain fog • Skin issues (eczema, psoriasis, hives, and rashes) • Irritability, mood swings, anxiety, or depression • Vaginal infections, urinary tract infections, rectal itching, or vaginal itching • Severe seasonal allergies or itchy ears • Strong sugar and refined carbohydrate cravings • White coated tongue / Oral thrush • Food and chemical sensitivities • Eye fatigue, spots in front of eyes, burning or tearing eyes • Frequent ear infections, pressure, swelling or tingling of ears, itchy ears • Headaches • Dandruff, dry, itchy skin • Acne or other skin problems • Frequent urination • Frequent vaginal yeast infections, persistent vaginal  itching • Irregular menstruation, endometriosis, PMS • Poor libido";

    String parasiteInformation = "The Fast Metabolism Parasite Cleanse is for individuals looking for a program designed to help reduce:\n• Parasites • Symptoms of IBS • Traveler's diarrhea • Skin irritations or unexplained rashes, hives, rosacea or eczema • Teeth grinding throughout the night • Pain or aching in your muscles or joints • Fatigue, exhaustion, depression, or frequent feelings of apathy • Iron-deficiency anemia \nIt also can help enhance performance, mental clarity, and stimulate detoxiﬁcation.";


    ViewGroup topMenuPage1;
    ViewGroup topMenuPage2;
    Button viewPlansButton;



    //detects swipes while using this fragment
    final SwipeDetector swipeDetector = new SwipeDetector();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

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

        noPlanView = (ViewGroup) view.findViewById(R.id.noPlanLayoutId);


        ViewGroup layout1 = (ViewGroup) view.findViewById(R.id.layout1);
        ViewGroup layout2 = (ViewGroup) view.findViewById(R.id.layout2);

        //will need to dynamically change with day
        tv = ((TextView) layout1.findViewById(R.id.mealListDayHeader));
        tv2 = ((TextView) layout2.findViewById(R.id.mealListDayHeader));

        dayListView = (ListView) layout1.findViewById(R.id.testListView);
        dayListView2 = (ListView) layout2.findViewById(R.id.testListView);

        viewAnimator = (ViewAnimator) view.findViewById(R.id.myViewAnimator);

        mealCell = (ViewGroup) layout1.findViewById(R.id.meal_cell_view);

        recipeBox = view.findViewById(R.id.recipeBoxView);
        recipeBox.setX(1500);
        recipeBox.setAlpha(0);


        recipeTitle = (TextView) recipeBox.findViewById(R.id.recipeTitle);
        ingredientHeader = (TextView) recipeBox.findViewById(R.id.ingredientHeader);
        recipeIngredientsContent = (TextView) recipeBox.findViewById(R.id.recipeIngredientsContent);
        recipeDirectionsContent = (TextView) recipeBox.findViewById(R.id.recipeDirectionsContent);
        recipeBoxImageView = (ImageView) recipeBox.findViewById(R.id.recipeBoxImageView);

        //setup top menu
        topMenu = (ViewGroup) view.findViewById(R.id.mealTrackerTopMenu);
        topMenuPage1 = (ViewGroup) view.findViewById(R.id.mealTrackerTopMenuPage1);
        topMenuPage2 = (ViewGroup) view.findViewById(R.id.mealTrackerTopMenuPage2);
        viewPlansButton = (Button) view.findViewById(R.id.viewOtherPlansButton);
        takeQuizButton = (Button) view.findViewById(R.id.takeQuizButton);

        topMenu = (ViewGroup) view.findViewById(R.id.mealTrackerTopMenu);
        menuArrow = (TextView) layout1.findViewById(R.id.menuArrow);
        menuArrow2 = (TextView) layout2.findViewById(R.id.menuArrow);
        upArrow = (TextView) view.findViewById(R.id.upMenuButton);

        final Animation mQuickFadeOut = AnimationUtils.loadAnimation(this.getContext(), R.anim.quick_fade_out);

        final Animation mQuickFadeIn = AnimationUtils.loadAnimation(this.getContext(), R.anim.quick_fade_in);

        mQuickFadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                topMenuPage1.setVisibility(view.GONE);
                topMenuPage2.startAnimation(mQuickFadeIn);
                topMenuPage2.setVisibility(view.VISIBLE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });





        viewPlansButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                topMenuPage1.startAnimation(mQuickFadeOut);

            }
        });


        topMenu.setY(-1000);
        topMenu.setVisibility(INVISIBLE);
        topMenu.bringToFront();


        final View.OnClickListener showTopMenu = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                topMenu.setVisibility(View.VISIBLE);
                topMenuPage2.setVisibility(View.INVISIBLE);
                topMenuPage1.setVisibility(View.VISIBLE);
                topMenu.animate().translationY(0).setDuration(600).start();

            }
        };

        menuArrow.setOnClickListener(showTopMenu);

        menuArrow2.setOnClickListener(showTopMenu);

        upArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topMenu.animate().translationY(-1000).setDuration(600).start();
                topMenu.setVisibility(View.INVISIBLE);
            }
        });

        takeQuizButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });

        purchasePlanTextView1 = (TextView) view.findViewById(R.id.purchasePlanTextView1);


        FileOutputStream fos = null;
        Boolean test = false;
        try {

            FileInputStream fis = new FileInputStream(new File(getContext().getFilesDir() + "/" + ((MainActivity) getActivity()).getUserId(), "currentPlan.ser"));

            ObjectInputStream is = new ObjectInputStream(fis);
            mealPlan = (MealPlan) is.readObject();


            //System.out.println(((MealPlan) is.readObject()).getListForDay(0).get(0).isCompleted() + " better be right");
            is.close();
            fis.close();

            System.out.println("successfully loaded mealPlan");
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

        if(test) {
            try {
                String jsonPlan = sendGET("http://ec2-52-52-65-150.us-west-1.compute.amazonaws.com:3000/meal-plans");
                if (!jsonPlan.isEmpty()) {
                    ((MainActivity) getActivity()).setPlan(jsonPlan);
                   // ((MainActivity) getActivity()).setPlanInt(1);
                }

            } catch (IOException e) {
                System.out.println("error downloading plan");
                e.printStackTrace();
            }

            setCurrentPlan(((MainActivity) getActivity()).getJSONPlan());

            fos = null;
            try {

                //File file = new File();

//                File parent = file.getParentFile();
//                if(!parent.exists() && !parent.mkdirs()){
//                    throw new IllegalStateException("Couldn't create dir: " + parent);
//                }

                fos = new FileOutputStream(new File(getContext().getFilesDir() + "/" + ((MainActivity) getActivity()).getUserId(), "currentPlan.ser"));
                // fos = new FileOutputStream(file, Context.MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                os.reset();
                os.writeObject(mealPlan);
                os.close();
                fos.close();
                System.out.println("successfully saved mealPlan to: somewhere"); //+ file.getAbsolutePath());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }





            noPlanView.setVisibility(View.GONE);



            daysInPlan = mealPlan.getDays();
            day = 0;
            System.out.println("post the parse");

            list = new ArrayList<MealItem>();


            //setup the first layout to be displayed
            //---------
            testList = (ListView) layout1.findViewById(R.id.testListView);
            addMe = (ViewGroup) view.findViewById(R.id.recipeItem);
            adapter = new MealItemAdapter(getContext(), R.layout.meal_tracker_recipe_item, list);
            testList.setAdapter(adapter);
            adapter.addAll(mealPlan.getListForDay(day));
            //----------


            //setup secondary layout, used when animating between daily meal plans
            //----------
            list = mealPlan.getListForDay(day);
            list2 = new ArrayList<MealItem>();
            testList2 = (ListView) layout2.findViewById(R.id.testListView);
            adapter2 = new MealItemAdapter(getContext(), R.layout.meal_tracker_recipe_item, list2);
            testList2.setAdapter(adapter2);
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
            currentAdapter = adapter;
            currenttv = tv;
            listViewNum = 1;
            currenttv.setText("Day " + (day + 1));


            ViewGroup rl = (ViewGroup) view.findViewById(R.id.recipeBoxLinear);

            rl.setOnTouchListener(swipeDetector);

            rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (swipeDetector.swipeDetected()) {
                        if (swipeDetector.getAction() == SwipeDetector.Action.LR) {
                            recipeBox.animate().translationX(1500).alpha(0).setDuration(600).start();
                        }
                    }//end if swipe detected
                }
            });



            nextDayButton = (TextView) layout1.findViewById(R.id.nextDayButton);
            prevDayButton = (TextView) layout1.findViewById(R.id.prevDayButton);
            nextDayButton2 = (TextView) layout2.findViewById(R.id.nextDayButton);
            prevDayButton2 = (TextView) layout2.findViewById(R.id.prevDayButton);

            OnClickListener nextDay = new OnClickListener() {
                @Override
                public void onClick(View v) {


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
                        currentAdapter.clear();
                        currentAdapter.addAll(mealPlan.getListForDay(day));
                        currentAdapter.notifyDataSetChanged();

                        viewAnimator.showPrevious();

                    }
                }
            };


            nextDayButton.setOnClickListener(nextDay);
            nextDayButton2.setOnClickListener(nextDay);


            prevDayButton.setOnClickListener(prevDay);
            prevDayButton2.setOnClickListener(prevDay);



      //  }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    //this method is used to switch all current- variables to the other layout (used for animation purposes)
    void switchListView(){



        if(listViewNum == 1){
            listViewNum =2;
            currentAdapter = adapter2;
            currenttv = tv2;
            previousList = dayListView;
        }
        else{
            listViewNum = 1;
            currentAdapter = adapter;
            currenttv = tv;
            previousList = dayListView2;
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



        saveFile();

        mListener = null;


    }

    RecipeSet getSetFromFile(String fileName) {

        RecipeSet retSet = null;

        FileOutputStream fos = null;
        Boolean test = false;
        try {

            FileInputStream fis = new FileInputStream(new File(getContext().getFilesDir() + "/recipeSet/", "genericSet.ser"));

            ObjectInputStream is = new ObjectInputStream(fis);
            retSet = (RecipeSet) is.readObject();


            //System.out.println(((MealPlan) is.readObject()).getListForDay(0).get(0).isCompleted() + " better be right");
            is.close();
            fis.close();

            System.out.println("successfully loaded recipeSet");
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
            System.out.println(mealPlan.getListForDay(0).get(0).isCompleted() + "da fuq is this shit");

            System.out.println("successfully saved meal plan to: somethin");

            FileInputStream fis = new FileInputStream(new File(getContext().getFilesDir() + "/" + ((MainActivity) getActivity()).getUserId() + "/currentPlan.ser"));

            ObjectInputStream is = new ObjectInputStream(fis);

            System.out.println(((MealPlan) is.readObject()).getListForDay(0).get(0).isCompleted() + " better be right");

            is.close();
            fis.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    //used to override back button functionality
    @Override
    public void onResume() {
        super.onResume();

        final Animation mQuickFadeOut = AnimationUtils.loadAnimation(this.getContext(), R.anim.quick_fade_out);

        final Animation mQuickFadeIn = AnimationUtils.loadAnimation(this.getContext(), R.anim.quick_fade_in);

        mQuickFadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                    topMenuPage2.setVisibility(view.INVISIBLE);
                    topMenuPage1.startAnimation(mQuickFadeIn);
                    topMenuPage1.setVisibility(view.VISIBLE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });




        getView().setFocusableInTouchMode(true);
        getView().requestFocus();

        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {


                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    System.out.println(recipeBox.getX());
                    if(topMenuPage2.getVisibility() == View.VISIBLE) {
                        topMenuPage2.startAnimation(mQuickFadeOut);
                    }
                    else if(topMenu.getVisibility() == View.VISIBLE){
                        topMenu.animate().translationY(-1000).setDuration(600).start();
                        topMenu.setVisibility(View.INVISIBLE);
                    }
                    else if(recipeBox.getX() == 0) {
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

        ((MainActivity)getActivity()).switchToPurchaseFragment(planInfo, planDesc);
    }

    void switchLayout(ViewGroup v1, ViewGroup v2) {
        v1.setVisibility(INVISIBLE);
        v2.setX(1000f);
        v2.setVisibility(VISIBLE);
        v2.animate().translationXBy(-1000f);

    }

    void setCurrentPlan(String JSONPlan) {


        try {

            //initialize the jsonObject
            JSONArray jsonArray;
            System.out.println("please work" + JSONPlan);
            jsonArray = new JSONArray(JSONPlan);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            System.out.println("before the parse");
            //parse the object and create a meal plan
            mealPlan = new MealPlan(jsonObject);



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



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


    class MealItemAdapter extends ArrayAdapter<MealItem> {

        private ArrayList<MealItem> items;
//        ArrayAdapter<String> swapRecipeAdapter = new ArrayAdapter<String>(
//                getContext(),
//                android.R.layout.select_dialog_singlechoice);


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



        public MealItemAdapter(Context context, int textViewResourceId, ArrayList<MealItem> items2) {
            super(context, textViewResourceId, items2);



            File file = new File(getActivity().getFilesDir() + "/recipeSet/");
            File inputFile = new File(file, "genericSet.ser");

            RecipeSet rs = getSetFromFile(inputFile.getAbsolutePath());
            //items = rs.getRecipeSet();
            set1 = rs.getRecipeSet();
            recipeSets = new ArrayList<ArrayList<MealItem>>();


            recipeSets.add(set1);



            headerTitles = new ArrayList<>();
            headerTitles.add(rs.getRecipeSetTitle());


            childTitles = new ArrayList<>();
            childTitles.add(set1);




            swapRecipeAdapter = new ExpandableListAdapter(getContext(),headerTitles, childTitles);

                        myList = new ExpandableListView(getContext());

                        myList.setAdapter(swapRecipeAdapter);


            myList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                            @Override
                            public boolean onChildClick(ExpandableListView parent, View v, final int groupPosition, final int childPosition, long id) {



                                String strName = ((MealItem) swapRecipeAdapter.getChild(groupPosition,childPosition)).getHeader();


                                    AlertDialog.Builder builderInner = new AlertDialog.Builder(
                                            getContext());

                                    //builderInner.setMessage(strName);

                                    builderInner.setTitle("You Have Selected");

                                    View myV;
                                    LayoutInflater myVi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    myV = myVi.inflate(R.layout.meal_cell_image_only_layout, null);
                                    TextView text = (TextView) myV.findViewById(R.id.nameToSwap);
                                    text.setText(((MealItem) swapRecipeAdapter.getChild(groupPosition, childPosition)).getTitle());
                                    ImageView image = (ImageView) myV.findViewById(R.id.imageViewtoSwap);

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
                                                    mealPlan.swapMeal(day, helperPosition, (MealItem) swapRecipeAdapter.getChild(groupPosition, childPosition));
                                                    mealPlan.getListForDay(day).get(helperPosition).setHeader(items.get(helperPosition).getHeader());
                                                    //currentAdapter.notifyDataSetChanged();
                                                    currentAdapter.clear();
                                                    currentAdapter.addAll(mealPlan.getListForDay(day));
                                                    currentAdapter.notifyDataSetChanged();

                                                    dialog.dismiss();
                                                    alert.dismiss();
                                                    //System.out.println("should have swapped " + recipeSets.get(groupPosition).get(childPosition).getTitle() + " with " + items.get(helperPosition).getTitle());


                                                    // saveFile();
                                                }
                                            });

                                    builderInner.show();
                                    loadPicassoPicFromFileAsync(image, ((MealItem) swapRecipeAdapter.getChild(groupPosition, childPosition)));


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


            cantSwapBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener(

            ) {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    //builderSingle.show();
                }
            });



            alert = builderSingle.create();
            alert2 = cantSwapBuilder.create();



            this.items = items2;

        }




        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View v = convertView;
            //initialize all animation variables
            final Animation outRight = AnimationUtils.loadAnimation(
                    getContext(), R.anim.slide_out_right);
            final Animation inLeft = AnimationUtils.loadAnimation(
                    getContext(), R.anim.slide_in_left);
            final Animation inRight = AnimationUtils.loadAnimation(
                    getContext(), R.anim.slide_in_right_mt);
            final Animation outLeft = AnimationUtils.loadAnimation(
                    getContext(), R.anim.slide_out_left);

            if (v == null) {
                LayoutInflater vi = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.meal_cell, null);
            }

            final MealItem o = items.get(position);


            if (o != null) {

                TextView recipeTitle = (TextView) v.findViewById(R.id.mealName);
                final TextView recipeHeader = (TextView) v.findViewById(R.id.mealHeader);
                final ImageView recipeImageView = (ImageView) v.findViewById(R.id.mealCellImageView);


                loadPicassoPicFromFileAsync(recipeImageView, o);



                if (recipeTitle != null) {
                    recipeTitle.setText(o.getTitle());

                    recipeHeader.setText(o.getHeader());


                        final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {

                                    case DialogInterface.BUTTON_POSITIVE:
                                        mealPlan.toggleCompletion(day, position);

                                        if(mealPlan.isCompleted(day, position)){
                                            System.out.println("complete");
                                            mealComplete(currentSelection);
                                        }
                                        else{
                                            mealUncomplete(currentSelection);
                                            System.out.println("uncomp");
                                        }

                                        saveFile();

                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:

                                        System.out.println(o.getTitle().toUpperCase().equals("FAST METABOLISM CLEANSE"));

                                        if(o.getTitle().toUpperCase().equals("FAST METABOLISM CLEANSE")) {
                                           alert2.show();
                                        }
                                        else {
                                            for (int i = 0; i < swapRecipeAdapter.getGroupCount(); i++) {
                                                myList.collapseGroup(i);

                                                alert.show();
                                            }
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
                                .setNegativeButton("Change Meal", dialogClickListener).setNeutralButton("Cancel", dialogClickListener);


                        v.setBackgroundColor(Color.parseColor("#AFC6C9"));
                        recipeImageView.setBackgroundColor(Color.parseColor("#AFC6C9"));



                        //this listener allows users to complete meal
                        v.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                currentSelection = v;
                                builder.show();
                                helperPosition = position;

                                return true;
                            }
                        });

                        v.setOnTouchListener(new OnTouchListener() {

                            @Override
                            public boolean onTouch(View v, MotionEvent e) {


                                switch (e.getAction()) {
                                    case MotionEvent.ACTION_DOWN: {
                                        pressStartTime = System.currentTimeMillis();
                                        pressedX = e.getX();
                                        pressedY = e.getY();
                                        stayedWithinClickDistance = true;
                                        break;
                                    }
                                    case MotionEvent.ACTION_MOVE: {
                                        if (stayedWithinClickDistance && distance(pressedX, pressedY, e.getX(), e.getY()) > MAX_CLICK_DISTANCE) {
                                            stayedWithinClickDistance = false;
                                        }
                                        break;
                                    }
                                    case MotionEvent.ACTION_UP: {
                                        long pressDuration = System.currentTimeMillis() - pressStartTime;
                                        if (pressDuration < MAX_CLICK_DURATION && stayedWithinClickDistance) {
                                            // Click event has occurred
                                            // open recipe
                                            hideAndShowMealItem(o);
                                        }
                                        else if(pressDuration < MAX_CLICK_DURATION){
                                            //LR
                                            if(pressedX < e.getX()){
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
                                            }
                                            //RL
                                            else{

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

                                                }//end if
                                            }//end else
                                        }
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

            return v;
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

        void loadPicassoPicFromFile(final ImageView v, final MealItem o){


                File file = new File(getContext().getFilesDir(), o.getImageUrl());

                if (!file.exists()) {
                    System.out.println("file did not exist");
                    //o.setLoaded(false);

                    Picasso.with(getContext()).load(o.getImageUrl()).into(v, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {


                            File file = new File(getContext().getFilesDir(), o.getImageUrl());

                            File parent = file.getParentFile();
                            if(!parent.exists() && !parent.mkdirs()){
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

                                Bitmap bitmap = ((BitmapDrawable)v.getDrawable()).getBitmap();

                                String s = o.getImageUrl();
                                //if(s.substring(s.length() - 3).equalsIgnoreCase("JPG")){
                                //  bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                                //}
                                //else
                                //{
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
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

                        }

                        @Override
                        public void onError() {
                            System.out.println("failure");
                        }
                    });




                } else {

                    System.out.println("loaded from memory");
                    Picasso.with(getContext()).load("file://" + file.getAbsolutePath()).fit().centerCrop().into(v);
                    //Glide.with(getContext()).load("file://" + file.getAbsolutePath()).centerCrop().crossFade().into(v);

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
                                if(!parent.exists() && !parent.mkdirs()){
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


                        new MyAsyncTask().execute(((BitmapDrawable)v.getDrawable()).getBitmap());

                    }

                    @Override
                    public void onError() {
                        System.out.println("failure");
                    }
                });




            } else {

                System.out.println("loaded from memory");
                Picasso.with(getContext()).load("file://" + file.getAbsolutePath()).fit().centerCrop().into(v);
                //Glide.with(getContext()).load("file://" + file.getAbsolutePath()).centerCrop().crossFade().into(v);

            }
        }


        void downloadAndSaveImage(){

        }

        void mealComplete(View v) {
            v.setBackgroundColor(Color.GRAY);
            v.findViewById(R.id.mealCellImageView).setBackgroundColor(Color.GRAY);
            //v.setOnLongClickListener(null);
        }

        void mealUncomplete(View v){
            v.setBackgroundColor(getResources().getColor(R.color.main_background));
            v.findViewById(R.id.mealCellImageView).setBackgroundColor(getResources().getColor(R.color.main_background));
            //v.setOnLongClickListener(null);
        }

        void swapMeal(){

            currentAdapter.notifyDataSetChanged();
        }

        private View currentSelection; //this is used to instantaneously change a meal to completed when when "YES" is selected
        private int tempDay; //used to check if all items need to be reset to closed
    }











    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}






class SwipeDetector implements OnTouchListener {

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









