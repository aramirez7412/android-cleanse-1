package com.nanonimbus.cleanseapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.nanonimbus.cleanseapp.util.IabHelper;
import com.nanonimbus.cleanseapp.util.IabResult;
import com.nanonimbus.cleanseapp.util.Purchase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PurchaseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PurchaseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PurchaseFragment extends Fragment {







    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    boolean isSuccessfull;
    View view;
    private static final String TAG = "<your domain>.inappbilling";
    IabHelper mHelper;
    static final String ITEM_SKU = "android.test.purchased";

    private Button clickButton;
    private Button buyButton;

    private OnFragmentInteractionListener mListener;

    public PurchaseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PurchaseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PurchaseFragment newInstance(String param1, String param2) {
        PurchaseFragment fragment = new PurchaseFragment();
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

        //set to true for testing
        isSuccessfull = true;

        String base64EncodedPublicKey =
        "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnTsqg5d3UsezoOO5wzcMVQe29CJtDssAViTPYFc3uU44b6VnYFCLEfXty09fGDXibEzB2ndtdQEH9I57QNrZRtP5+UUvhrS+OC20uhTcOkW1On6wkfTUsr0fWYV+S1ZyHSOomXk5YvX3ODkHUyo9RCV2xV95tmIGqF1Sqftwt58gciJmnUBFupNKdB1yJLEA+qk86SZdG1pFFNlYrA4UNFJSLX1R2yKprR3yOogEmNw78QA/APb/Jn+b/HLm8btUABlGXWrxR7v2Z/nKJ6oxRnOQGbFhVMrQOSk2rxCWeVbtAlxziIYAHvLkJsGq8PQN/KSurJ6vak5ykQ6ghdfDvQIDAQAB";

        mHelper = new IabHelper(getContext(), base64EncodedPublicKey);

        mHelper.startSetup(new

                                   IabHelper.OnIabSetupFinishedListener() {
                                       public void onIabSetupFinished(IabResult result)
                                       {
                                           if (!result.isSuccess()) {
                                               System.out.println("failure");
                                           } else {
                                               isSuccessfull = true;
                                               System.out.println("success");
                                              // buyClick(view);
                                           }
                                       }
                                   });

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        if(isSuccessfull){

            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_purchase, container, false);


            ListView listView = (ListView) view.findViewById(R.id.listingListView);

            ArrayList<StoreListing> sampleListings = new ArrayList<>();

            StoreListing sL = new StoreListing();


            sL.AddRecipeName("Avocado Egg Salad");
            sL.AddRecipeName("Basque_Lamb_Stew.jpg");
            sL.AddRecipeName("Black Bean Quinoa Veggie Burger");
            sL.AddRecipeName("Chicken Chili Fajita Bowl");
            sL.AddRecipeName("Chile Verde (Slow Cooker Pork and Green Chile Stew");
            sL.AddRecipeName("D-Burn Recipe Italian Wonder");
            sL.AddRecipeName("H-Burn Recipe Basil Shrimp");
            sL.AddRecipeName("H-Burn-Crunchy Thai Salad");
            sL.AddRecipeName("I-Burn Recipe Halibut with Vegetables");
            sL.AddRecipeName("Lamb stew In a Jiffy");
            sL.AddRecipeName("Roasted Chicken, Avocado & Grapefruit Salad");

            sL.setTitle("Recipe Set 1");


            sampleListings.add(sL);
            sampleListings.add(sL);
            sampleListings.add(sL);
            sampleListings.add(sL);


            StoreListAdapter storeListAdapter = new StoreListAdapter(getContext(), sampleListings);


            listView.setAdapter(storeListAdapter);


        }
        else{




            // Inflate the layout for this fragment
             view = inflater.inflate(R.layout.failed_connection, container, false);


        }




        return view;
    }

    public void buyClick(View view) {
        if (mHelper != null) mHelper.flagEndAsync();

        mHelper.launchPurchaseFlow(getActivity(), ITEM_SKU, 10001,
                mPurchaseFinishedListener, "mypurchasetoken");
    }

    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener
            = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result,
                                          Purchase purchase)
        {
            if (result.isFailure()) {
                // Handle error
                return;
            }
            else if (purchase.getSku().equals(ITEM_SKU)) {
                System.out.println("twas made");
                //consumeItem();
                //buyButton.setEnabled(false);
            }

        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) mHelper.dispose();
        mHelper = null;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data)
    {
        if (!mHelper.handleActivityResult(requestCode,
                resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
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

//        if (mHelper != null) try {
//            mHelper.dispose();
//        } catch (IabHelper.IabAsyncInProgressException e) {
//            e.printStackTrace();
//        }
//        mHelper = null;
//
//        if (mService != null) {
//            getContext().unbindService(mServiceConn);
//            System.out.println("unbinding service");
//        }

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


    public class StoreListAdapter extends ArrayAdapter<StoreListing> {
        public StoreListAdapter(Context context, ArrayList<StoreListing> users) {
            super(context, 0, users);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            StoreListing listing = getItem(position);

            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.available_item, parent, false);
            }
            // Lookup view for data population
            TextView listingTitle = (TextView) convertView.findViewById(R.id.listingTitleTextView);
            TextView listingRecipes = (TextView) convertView.findViewById(R.id.listingRecipesTextView);
            ImageView listingImageView = (ImageView) convertView.findViewById(R.id.listingImageView);
            Button listingPurchaseButton = (Button) convertView.findViewById(R.id.purchaseSetButton);
            // Populate the data into the template view using the data object
            listingTitle.setText(listing.getTitle());

            listingPurchaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buyClick(view);
                }
            });

            Bitmap bm = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.blue_blur);
            RoundImage roundedImage = new RoundImage(bm);
            listingImageView.setImageDrawable(roundedImage);


            String tempRecipes = "";

            for (int i = 0; i < listing.getRecipeCount() ; i++) {
                tempRecipes += (listing.getRecipeName(i) + "\n");
            }

            listingRecipes.setText(tempRecipes);
            // Return the completed view to render on screen
            return convertView;
        }

    }
}
