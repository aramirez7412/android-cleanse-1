package com.nanonimbus.cleanseapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
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
import com.nanonimbus.cleanseapp.util.Inventory;
import com.nanonimbus.cleanseapp.util.Purchase;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


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
                                               setupPurchaseList();
                                               System.out.println("success");
                                              // buyClick(view);
                                           }
                                       }
                                   });

    }



    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        if(isSuccessfull){

            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_purchase, container, false);






        }
        else{




            // Inflate the layout for this fragment
             view = inflater.inflate(R.layout.failed_connection, container, false);


        }




        return view;
    }

    public void buyClick(View view, String id) {
        if (mHelper != null) mHelper.flagEndAsync();

        mHelper.launchPurchaseFlow(getActivity(), id, 10001,
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

    class DownloadSetDetails extends AsyncTask<MyTaskParams, Void, MyTaskParams> {


        @Override
        protected MyTaskParams doInBackground(MyTaskParams... params) {

            try {

                ArrayList<PurchaseHelperClass> purchaseList  = params[0].purchases;
                ArrayList<PurchaseHelperClass> newJsons = new ArrayList<>();

                // String GET_URL = params[0].jsonURL;
                Context c = params[0].context;
                String USER_AGENT = "Mozilla/5.0";
                // String GET_URL = "http://ec2-52-52-65-150.us-west-1.compute.amazonaws.com:3000/meal-plans";

                for (int i = 0; i < purchaseList.size(); i++) {

                    URL obj = new URL(purchaseList.get(i).getLink());
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

                        PurchaseHelperClass temp = new PurchaseHelperClass(purchaseList.get(i).getId(),response.toString());

                        newJsons.add(temp);

                    } else {
                        System.out.println("GET request not worked");
                        return null;
                    }
                }

                return new MyTaskParams(newJsons, c, true);


            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }



            return null;
        }

        @Override
        protected void onPostExecute(MyTaskParams result) {

            Context c = result.context;
            ArrayList<PurchaseHelperClass> setsJson = result.purchases;

            ArrayList<StoreListing> sampleListings = new ArrayList<>();


            try {



                for (int i = 0; i < setsJson.size(); i++) {


                    JSONObject setJson = new JSONObject(setsJson.get(i).getLink());

                    JSONArray setAr = setJson.getJSONArray("recipes");

                    StoreListing sL = new StoreListing();

                    sL.setTitle("Recipe Set");

                    //loop through each meal
                    for(int j = 0; j < setAr.length(); j++) {
                        JSONObject mealObject = setAr.getJSONObject(j);

                        sL.AddRecipeName(mealObject.getString("name"));

                       // mealItem.setImageUrl(mealObject.getString("imgUrl"));

                    }

                    sL.setId(setsJson.get(i).getId());

                    sampleListings.add(sL);

                }

                ListView listView = (ListView) view.findViewById(R.id.listingListView);
                StoreListAdapter storeListAdapter = new StoreListAdapter(getContext(), sampleListings);
                listView.setAdapter(storeListAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }



    void setupPurchaseList(){



        final ArrayList<String> skuList = new ArrayList<String> ();
        skuList.add("GgYfZrkrpcgtyYS7kDjVZ7Ju");
        skuList.add("PSGLCwzhmqcnsW8uWwpycSA9");
        skuList.add("A4sdNWEJ2WbD2jjbjQaWKBVX");
        skuList.add("c3NTNeL8Pzd2DtV8hjBVRBFS");
        skuList.add("5NWYXuQ69vkGq4aLYkJq7PVL");
        skuList.add("JNCgFfPeLvNnF8SLhENTu2A6");
        skuList.add("eJ4g4hEHv6g3AM4TQmeRYqwB");
        skuList.add("UrrynqFHYmrT6YFkhnf5WmLM");
        skuList.add("72APKqPhNyxUMw8UzKAVkvAJ");
        skuList.add("MtrpLJ5ZNchacH6GFctgSfbY");
//            Bundle querySkus = new Bundle();
//            querySkus.putStringArrayList("ITEM_ID_LIST", skuList);

        IabHelper.QueryInventoryFinishedListener
                queryFinishedListener = new IabHelper.QueryInventoryFinishedListener() {
            public void onQueryInventoryFinished(IabResult result, Inventory inventory) {

                if (result.isFailure()) {
                    // handle error
                    System.out.println("failure query");
                    return;
                }


                String tempAddress = "http://52.52.65.150:8080/recipe/set/";
                ArrayList<PurchaseHelperClass> available = new ArrayList<>();


                for (int i = 0; i < skuList.size(); i++) {
                    if (inventory.hasDetails(skuList.get(i))) {
                        PurchaseHelperClass p = new PurchaseHelperClass(skuList.get(i),(tempAddress + skuList.get(i)));
                        available.add(p);
                    }
                }

                if (available.size() == 0) {
                    PurchaseHelperClass p = new PurchaseHelperClass("android.test.purchased",((tempAddress + "original")));
                    available.add(p);
                }

                // update the UI
                MyTaskParams mtp = new MyTaskParams(available, getContext(), true);
                DownloadSetDetails task = new DownloadSetDetails();
                task.execute(mtp);
            }
        };

            mHelper.queryInventoryAsync(true,skuList,queryFinishedListener);

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
            final StoreListing listing = getItem(position);

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
                    buyClick(view, listing.getId());
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
