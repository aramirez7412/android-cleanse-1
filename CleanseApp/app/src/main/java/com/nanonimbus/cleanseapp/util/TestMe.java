package com.nanonimbus.cleanseapp.util;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nanonimbus.cleanseapp.FoodListFragment;
import com.nanonimbus.cleanseapp.MyPagerAdapter;
import com.nanonimbus.cleanseapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TestMe.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TestMe#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestMe extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    MyPagerAdapter pagerAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TestMe() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TestMe.
     */
    // TODO: Rename and change types and number of parameters
    public static TestMe newInstance(String param1, String param2) {
        TestMe fragment = new TestMe();
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
        View v = inflater.inflate(R.layout.fragment_test_me, container, false);

        ViewPager mViewPager = (ViewPager)v.findViewById(R.id.pager);
        pagerAdapter = new MyPagerAdapter(getChildFragmentManager());
        //pagerAdapter.setFragments(getContext());
        mViewPager.setAdapter(pagerAdapter);

        mViewPager.setCurrentItem(0);



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


class MyAdapter extends FragmentPagerAdapter {

    // Three simple fragments
    FoodListFragment frag0;
    TestMe frag1;
    TestMe frag2;
    TestMe frag3;
    TestMe frag4;
    TestMe frag5;
    TestMe frag6;
    TestMe frag7;
    TestMe frag8;
    TestMe frag9;

    public MyAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragments(Context c){



        // Set up the simple base fragments
        Bundle args = new Bundle();
        args.putInt("day_of_plan", 0);
        frag0 =  new FoodListFragment();
        frag0.setArguments(args);

//        args = new Bundle();
//        args.putInt("day_of_plan", 1);
//        frag1 =  new TestMe ();
//        frag1.setArguments(args);

//        args = new Bundle();
//        args.putInt("day_of_plan", 2);
//        frag2 =  new TestMe ();
//        frag2.setArguments(args);
//
//        args = new Bundle();
//        args.putInt("day_of_plan", 3);
//        frag3 =  new TestMe ();
//        frag3.setArguments(args);
//
//        args = new Bundle();
//        args.putInt("day_of_plan", 4);
//        frag4 =  new TestMe ();
//        frag4.setArguments(args);
//
//        args = new Bundle();
//        args.putInt("day_of_plan", 5);
//        frag5 =  new TestMe ();
//        frag5.setArguments(args);
//
//        args = new Bundle();
//        args.putInt("day_of_plan", 6);
//        frag6 =  new TestMe ();
//        frag6.setArguments(args);
//
//        args = new Bundle();
//        args.putInt("day_of_plan", 7);
//        frag7 =  new TestMe ();
//        frag7.setArguments(args);
//
//        args = new Bundle();
//        args.putInt("day_of_plan", 8);
//        frag8 =  new TestMe ();
//        frag8.setArguments(args);
//
//        args = new Bundle();
//        args.putInt("day_of_plan", 9);
//        frag9 =  new TestMe ();
//        frag9.setArguments(args);

    }

    @Override
    public Fragment getItem(int position) {
        // TODO: Make this more efficient, use a list or such, also comment more
        Fragment frag = null;
        if(position == 0){
            frag = frag0;
        }
        else if(position == 1){
            frag = frag1;
        }
        else if(position == 2){
            frag = frag2;
        }
        else if(position == 3){
            frag = frag3;
        }
        else if(position == 4){
            frag = frag4;
        }
        else if(position == 5){
            frag = frag5;
        }
        else if(position == 6){
            frag = frag6;

        }
        else if(position == 7){
            frag = frag7;
        }
        else if(position == 8){
            frag = frag8;
        }
        else if(position == 9){
            frag = frag9;
        }


        return frag;
    }

    @Override
    public int getCount() {
        return 10;
    }
}



