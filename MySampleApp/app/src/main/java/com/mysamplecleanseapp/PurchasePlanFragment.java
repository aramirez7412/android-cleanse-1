package com.mysamplecleanseapp;

import android.content.Context;

import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;




/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PurchasePlanFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PurchasePlanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PurchasePlanFragment extends Fragment {

    View view;
    TextView planNameTextView;
    TextView planDescriptionTextView;
    Button confirmPurchaseButton;
    String temp = "[{\"mealplan\":\"sample meal plan\",\"days\":[{\"day\":1,\"meals\":[{\"meal\":\"Fast Metabolism Cleanse\",\"time\":\"breakfast\"},{\"meal\":\"1/2 cup blueberries & 1/2 cup min. carrots\",\"time\":\"snack\"},{\"meal\":\"Fast Metabolism Cleanse\",\"time\":\"lunch\"},{\"meal\":\"Apple & 1/2 Cup Min. Celery Stalks\",\"time\":\"snack\"},{\"meal\":\"Chili * (Freeze Leftovers For Future Use)\",\"time\":\"dinner\"}],\"at-a-glance\":[\"2 Shakes\",\"1 Meal\",\"2 Snacks\"]},{\"day\":2,\"meals\":[{\"meal\":\"Fast Metabolism Cleanse\",\"time\":\"breakfast\"},{\"meal\":\"1/2 cup blueberries & 1/2 cup min. carrots\",\"time\":\"snack\"},{\"meal\":\"Fast Metabolism Cleanse\",\"time\":\"lunch\"},{\"meal\":\"1/2 Cup Berries & 1/2 Cup Min. Cucumbers & Radishes\",\"time\":\"snack\"},{\"meal\":\"Chicken & Broccoli Bowl*\",\"time\":\"dinner\"}],\"at-a-glance\":[\"2 Shakes\",\"1 Meal\",\"2 Snacks\"]},{\"day\":3,\"meals\":[{\"meal\":\"Fast Metabolism Cleanse\",\"time\":\"breakfast\"},{\"meal\":\"1 Sliced Apple with 1/2 cup Min. Jicama\",\"time\":\"snack\"},{\"meal\":\"Fast Metabolism Cleanse\",\"time\":\"lunch\"},{\"meal\":\"Fast Metabolism Cleanse\",\"time\":\"snack\"},{\"meal\":\"Turkey Soup* (Freeze Leftovers For Future Use)\",\"time\":\"dinner\"}],\"at-a-glance\":[\"3 Shakes\",\"1 Meal\",\"1 Snacks\"]},{\"day\":4,\"meals\":[{\"meal\":\"Fast Metabolism Cleanse\",\"time\":\"breakfast\"},{\"meal\":\"Fast Metabolism Cleanse\",\"time\":\"snack\"},{\"meal\":\"Leftover Chili*\",\"time\":\"lunch\"},{\"meal\":\"Fast Metabolism Cleanse\",\"time\":\"snack\"},{\"meal\":\"Fast Metabolism Cleanse\",\"time\":\"dinner\"}],\"at-a-glance\":[\"4 Shakes\",\"1 Meal\"]},{\"day\":5,\"meals\":[{\"meal\":\"Fast Metabolism Cleanse\",\"time\":\"breakfast\"},{\"meal\":\"Fast Metabolism Cleanse\",\"time\":\"snack\"},{\"meal\":\"Fast Metabolism Cleanse\",\"time\":\"lunch\"},{\"meal\":\"Fast Metabolism Cleanse\",\"time\":\"snack\"},{\"meal\":\"Fast Metabolism Cleanse\",\"time\":\"dinner\"}],\"at-a-glance\":[\"5 Shakes (Unlimited Veggies)\"]},{\"day\":6,\"meals\":[{\"meal\":\"Fast Metabolism Cleanse\",\"time\":\"breakfast\"},{\"meal\":\"Fast Metabolism Cleanse\",\"time\":\"snack\"},{\"meal\":\"Fast Metabolism Cleanse\",\"time\":\"lunch\"},{\"meal\":\"Fast Metabolism Cleanse\",\"time\":\"snack\"},{\"meal\":\"Fast Metabolism Cleanse\",\"time\":\"dinner\"}],\"at-a-glance\":[\"5 Shakes (Unlimited Veggies)\"]},{\"day\":7,\"meals\":[{\"meal\":\"Fast Metabolism Cleanse\",\"time\":\"breakfast\"},{\"meal\":\"Fast Metabolism Cleanse\",\"time\":\"snack\"},{\"meal\":\"Fast Metabolism Cleanse\",\"time\":\"lunch\"},{\"meal\":\"Fast Metabolism Cleanse\",\"time\":\"snack\"},{\"meal\":\"Fast Metabolism Cleanse\",\"time\":\"dinner\"}],\"at-a-glance\":[\"5 Shakes (Unlimited Veggies)\"]},{\"day\":8,\"meals\":[{\"meal\":\"Fast Metabolism Cleanse\",\"time\":\"breakfast\"},{\"meal\":\"Fast Metabolism Cleanse\",\"time\":\"snack\"},{\"meal\":\"Fast Metabolism Cleanse\",\"time\":\"lunch\"},{\"meal\":\"Fast Metabolism Cleanse\",\"time\":\"snack\"},{\"meal\":\"Leftover Turkey Soup*\",\"time\":\"dinner\"}],\"at-a-glance\":[\"4 Shakes\",\"1 Meal\"]},{\"day\":9,\"meals\":[{\"meal\":\"Fast Metabolism Cleanse\",\"time\":\"breakfast\"},{\"meal\":\"Fast Metabolism Cleanse\",\"time\":\"snack\"},{\"meal\":\"Fast Metabolism Cleanse\",\"time\":\"lunch\"},{\"meal\":\"1/4 Cup Raw Almonds\",\"time\":\"snack\"},{\"meal\":\"Brown Rice Fusilli*\",\"time\":\"dinner\"}],\"at-a-glance\":[\"3 Shakes\",\"1 Meal\",\"1 Snack\"]},{\"day\":10,\"meals\":[{\"meal\":\"Fast Metabolism Cleanse\",\"time\":\"breakfast\"},{\"meal\":\"1/2 Avocado & 1/2 Cup Min. Sliced Peppers\",\"time\":\"snack\"},{\"meal\":\"Fast Metabolism Cleanse\",\"time\":\"lunch\"},{\"meal\":\"2 Tablespoon Raw Almond Butter & 1/2 Cup Min. Celery\",\"time\":\"snack\"},{\"meal\":\"Shrimp & Asparagus Stiry Fry*\",\"time\":\"dinner\"}],\"at-a-glance\":[\"2 Shakes\",\"1 Meal\",\"2 Snacks\"]}],\"id\":\"d56a99f257da9bf6\"}]";



    private static final String USER_AGENT = "Mozilla/5.0";

    private static final String GET_URL = "http://ec2-52-52-65-150.us-west-1.compute.amazonaws.com:3000/meal-plans";

    private static final String POST_URL = "http://localhost:9090/SpringMVCExample/home";

    private static final String POST_PARAMS = "userName=Pankaj";


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PLAN_NAME = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String planName;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PurchasePlanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PurchasePlanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PurchasePlanFragment newInstance(String param1, String param2) {
        PurchasePlanFragment fragment = new PurchasePlanFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PLAN_NAME, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            planName = getArguments().getString(ARG_PLAN_NAME);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        //not sure what this does, maybe there is another way to get this to work needed for json pulling
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String planName;
        String planDesc;
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            planName = bundle.getString("planName");
            planDesc = bundle.getString("planDesc");
        }
        else{
            planName = "NO PLAN";
            planDesc = "NO DESC";
        }


        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_purchase_plan, container, false);

        confirmPurchaseButton = (Button) view.findViewById(R.id.confirmPurchaseButton);
        planNameTextView = (TextView) view.findViewById(R.id.planNameTextView);
        planNameTextView.setText(planName);
        planDescriptionTextView = (TextView) view.findViewById(R.id.planDescriptionTextView);
        planDescriptionTextView.setText(planDesc);



        //this does not yet work correctly
        //planNameTextView.setText(planName);

        confirmPurchaseButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    String jsonPlan = sendGET();

                    if(!jsonPlan.isEmpty()){
                        ((MainActivity)getActivity()).setPlan(jsonPlan);
                        ((MainActivity)getActivity()).setPlanInt(1);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


                ((MainActivity)getActivity()).switchToPlanFragment();

            }
        });

        return view;
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
                    getFragmentManager().popBackStackImmediate();

                }

                return true;
            }



            });
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



    private static String sendGET() throws IOException {
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

    private static void sendPOST() throws IOException {
        URL obj = new URL(POST_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);

        // For POST only - START
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(POST_PARAMS.getBytes());
        os.flush();
        os.close();
        // For POST only - END

        int responseCode = con.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { //success
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
        } else {
            System.out.println("POST request not worked");
        }

    }






}


