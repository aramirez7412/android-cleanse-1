package com.nanonimbus.cleanseapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import me.itangqi.waveloadingview.WaveLoadingView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WaterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WaterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WaterFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    View view;
    Button drinkWaterButton;
    WaveLoadingView waveView;
    int ounces;
    TextView ounceTextView;
    Button plusOunce;
    Button minusOunce;

    int subtractOunce;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public WaterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WaterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WaterFragment newInstance(String param1, String param2) {
        WaterFragment fragment = new WaterFragment();
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
        view = inflater.inflate(R.layout.fragment_water, container, false);

        subtractOunce = 8;


            plusOunce = (Button) view.findViewById(R.id.plusOunceButton);
            minusOunce = (Button) view.findViewById(R.id.minusOunceButton);

        drinkWaterButton = (Button) view.findViewById(R.id.drinkWaterButton);


        plusOunce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    subtractOunce++;
                    drinkWaterButton.setText("Drink " + subtractOunce + " Ounces");
                }
            });

            minusOunce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(subtractOunce != 1){
                        subtractOunce--;
                        drinkWaterButton.setText("Drink " + subtractOunce + " Ounces");
                    }
                }
            });


            waveView = (WaveLoadingView) view.findViewById(R.id.waveLoadingView);
            ounceTextView = (TextView) view.findViewById(R.id.ounceTextView);
            ounces = 64;

            ounceTextView.setText("You are " + ounces + " ounces away from completing your daily goal!");

            drinkWaterButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ounces-=subtractOunce;
                    waveView.setProgressValue(waveView.getProgressValue()-(int)(subtractOunce*1.8));


                    if(ounces <= 0) {

                        ounceTextView.setText("Congratulations! You have met your daily water goal!");
                    }
                    else{
                        ounceTextView.setText("You are " + ounces + " ounces away from completing your daily goal!");
                    }


                }
            });




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
