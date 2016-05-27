package com.mysampleapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.mysampleapp.demo.DemoFragmentBase;

import static android.view.View.INVISIBLE;
import static android.view.View.OnClickListener;
import static android.view.View.OnKeyListener;
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


    //declare variables to store all layouts

    ViewGroup dailyCalorieListLayout;
    boolean onIntroLayout = true;



    String mealName;




    TextView childMealElement;

    TextView mealListHeader;


    //used to override back button
    ViewGroup previousLayout;
    ViewGroup currentLayout;
    ViewGroup mealCell;


    //--------------------------------------------
    DailyCalorieList sundayList;




    //to detect swipes
    GestureDetectorCompat mDetector;


    //--------------------------------------------





    //variables used for dailyMealList
    ExpandableListView expandableListView;



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

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_meal, container, false);

        dailyCalorieListLayout = (ViewGroup) view.findViewById(R.id.mealListLayout);
        expandableListView = (ExpandableListView) view.findViewById(R.id.dailyMealListView);
        mealListHeader = (TextView) view.findViewById(R.id.mealListHeader);


        final SwipeDetector swipeDetector = new SwipeDetector();




        //swipe to delete functionality
        mDetector = new GestureDetectorCompat(view.getContext(), new MyGestureListener());


        expandableListView.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    System.out.println(v.toString());
                         mDetector.onTouchEvent(event);
                    return false;
                }
            });

//        childElementView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                )
//            }
//
//            });

        //--------------------------------------------
            sundayList = new DailyCalorieList(view.getContext());

        mealListHeader.setText("Day 1");


        sundayList.add("Breakfast", "meal" );
        sundayList.add("Dinner", "meal" );





        mealCell = (ViewGroup) view.findViewById(R.id.shake_cell);
        sundayList.add("Snack", "shake" );
        sundayList.add("Lunch", "shake" );

        //--------------------------------------------



        //used to override back button
        previousLayout = dailyCalorieListLayout;


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



  //      for(int i = 0; i < 4; i++)
//            expandableListView.expandGroup(i);

//        expandableListView.setOnTouchListener(swipeDetector);
//
//        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v, final int groupPosition, final int childPosition, long id) {
//
//
//                if(swipeDetector.swipeDetected()){
//
//                    ViewFlipper showDeleteFlipper = (ViewFlipper) v.findViewById(R.id.showDeleteFlipper);
//
//                    Button deleteButton = (Button) v.findViewById(R.id.testDeleteButton);
//
//                    deleteButton.setOnClickListener(new OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            switch(dayOfWeek){
//                                case "Sunday":
//                                    sundayList.delete(groupPosition, childPosition);
//                                    sundayList.getAdapter().notifyDataSetChanged();
//                                    break;
//                                case "Monday":
//                                    mondayList.delete(groupPosition, childPosition);
//                                    mondayList.getAdapter().notifyDataSetChanged();
//                                    break;
//                                default:
//                                    System.out.println("nothing");
//                            }
//
//
//                        }
//                    });
//
//
//                    Animation anim;
//
//
//                    switch(swipeDetector.getAction()){
//
//                        case RL:
//                            anim = AnimationUtils.loadAnimation(view.getContext(), R.anim.slide_in_left);
//                            showDeleteFlipper.setInAnimation(anim);
//                            anim = AnimationUtils.loadAnimation(view.getContext(), R.anim.slide_out_right);
//                            showDeleteFlipper.setOutAnimation(anim);
//
//                            showDeleteFlipper.setDisplayedChild(1);
//
//                            break;
//                        case LR:
//                            anim = AnimationUtils.loadAnimation(view.getContext(), R.anim.slide_in_right);
//                            showDeleteFlipper.setInAnimation(anim);
//                            anim = AnimationUtils.loadAnimation(view.getContext(), R.anim.slide_out_left);
//                            showDeleteFlipper.setOutAnimation(anim);
//
//                            showDeleteFlipper.setDisplayedChild(0);
//
//                            break;
//                        default:
//                            showDeleteFlipper.setDisplayedChild(0);
//                            break;
//                    }
//
//
//                }
//
//                return false;
//            }
//        });






        //set intro layout to be the first visible view
        //dailyCalorieListLayout.setVisibility(View.VISIBLE);
        dailyCalorieListLayout.setVisibility(VISIBLE);





        return view;
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


    //used to override back button functionality
    @Override
    public void onResume() {
        super.onResume();

//        mealNameTextField.setOnKeyListener(new OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                    mealNameTextField.clearFocus();
//
//                }
//                return false;
//            }
//        });
//
//
//
//
//        mealNameTextField.clearFocus();
//
//
//
//        getView().setFocusableInTouchMode(true);
//        getView().requestFocus();
//
//          getView().setOnKeyListener(new OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
//
//                }
//                return false;
//            }
//        });

    }


    void switchLayout(ViewGroup v1, ViewGroup v2){
        v1.setVisibility(INVISIBLE);
        v2.setX(1000f);
        v2.setVisibility(VISIBLE);
        v2.animate().translationXBy(-1000f);
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


class MyGestureListener extends GestureDetector.SimpleOnGestureListener{

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {

        System.out.println("just flang");
        return true;
    }

    @Override
    public boolean onDown(MotionEvent event) {
        return true;
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




