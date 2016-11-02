package com.nanonimbus.cleanseapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


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
import java.net.URI;
import java.net.URISyntaxException;
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
    IabHelper mHelper;
    LinearLayout myLayout;
    ProgressDialog progress;


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


       // mHelper = new IabHelper(getContext(), MainActivity.pullItIn(getResources().getString(R.string.wave_string),((MainActivity)getActivity()).getThis()));

        mHelper = new IabHelper(getContext(), base64EncodedPublicKey);

        mHelper.startSetup(new

                                   IabHelper.OnIabSetupFinishedListener() {
                                       public void onIabSetupFinished(IabResult result)
                                       {
                                           if (!result.isSuccess()) {
                                               System.out.println("failure");
                                               view.findViewById(R.id.errorLayout).setVisibility(View.VISIBLE);
                                               hideWheel();

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

        view = inflater.inflate(R.layout.fragment_purchase, container, false);

        progress = new ProgressDialog(getContext());
        showWheel();


        Button button;
        button = (Button) view.findViewById(R.id.buttonToCleanseStore);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uriUrl = Uri.parse("https://hayliepomroy.com/product/the-fast-metabolism-cleanse-10-day-protocol-with-free-22-page-program-guide/");
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });

        if(!isSuccessfull) {
            view.findViewById(R.id.errorLayout).setVisibility(View.VISIBLE);
            view.findViewById(R.id.noIssuesLayout).setVisibility(View.GONE);
            hideWheel();
        }

        myLayout = (LinearLayout) view.findViewById(R.id.StoreLinearLayout);




        return view;
    }



    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener
            = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result,
                                          Purchase purchase)
        {
            if (result.isFailure()) {
                // Handle error
                //view.findViewById(R.id.noAvailableLayout).setVisibility(View.VISIBLE);
                if(result.getResponse() == 7){
                    new AlertDialog.Builder(getContext())
                            .setTitle("UNABLE TO BUY ITEM: ")
                            .setMessage("Item Already Owned")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                    System.out.println("error");
                return;
            }
            //else if (purchase.getSku().equals(ITEM_SKU)) {
            else{
                System.out.println("twas made");

                Toast.makeText(getContext(),"Download Started", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).helperDownloadSet(purchase.getSku());



                //consumeItem();
                //buyButton.setEnabled(false);
            }

        }
    };

    public void buyClick(View view, String id) {
        if (mHelper != null) mHelper.flagEndAsync();

        System.out.println("purchase id is " + id);
        mHelper.launchPurchaseFlow(getActivity(), id, 1,
                mPurchaseFinishedListener, "mypurchasetoken");
    }

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
        System.out.println("do i get here");
        if (!mHelper.handleActivityResult(requestCode,
                resultCode, data)) {
            System.out.println("whu here");
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

            if (result != null) {

                Context c = result.context;
                ArrayList<PurchaseHelperClass> setsJson = result.purchases;

                ArrayList<StoreListing> sampleListings = new ArrayList<>();


                try {


                    for (int i = 0; i < setsJson.size(); i++) {


                        JSONObject setJson = new JSONObject(setsJson.get(i).getLink());

                        JSONArray setAr = setJson.getJSONArray("recipes");

                        StoreListing sL = new StoreListing();

                        sL.setTitle(setJson.getString("name"));

                        //loop through each meal
                        for (int j = 0; j < setAr.length(); j++) {
                            JSONObject mealObject = setAr.getJSONObject(j);

                            if (j == 2) {
                                sL.setImageLocation(mealObject.getString("imgUrl"));
                            }

                            sL.AddRecipeName(mealObject.getString("name"));

                            // mealItem.setImageUrl(mealObject.getString("imgUrl"));

                        }

                        sL.setId(setsJson.get(i).getId());

                        sampleListings.add(sL);

                        hideWheel();

                    }

                   //ListView listView = (ListView) view.findViewById(R.id.listingListView);
                    StoreListAdapter storeListAdapter = new StoreListAdapter(getContext(), sampleListings);
                   // listView.setAdapter(storeListAdapter);

                    for (int i = 0; i < storeListAdapter.getCount() ; i++) {
                        myLayout.addView(storeListAdapter.getView(i,null,myLayout));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            else{
                view.findViewById(R.id.errorLayout).setVisibility(View.VISIBLE);
                view.findViewById(R.id.noIssuesLayout).setVisibility(View.GONE);
                hideWheel();
            }
        }


    }



    void setupPurchaseList(){





        final ArrayList<String> skuList = new ArrayList<String> ();

        skuList.add("b4sxjh1xf9f2etljzcys");
        skuList.add("ikljk47wes24pd1ommc6");
        skuList.add("c3g3ch7wv0t0bsmr775j");
        skuList.add("z1svnijdpvocv1wtdbyk");
        skuList.add("hxtnyqzr4248x6sukmz4");
        skuList.add("eqmr76e7338s0pxba32y");
        skuList.add("j07sthayvow5crpmeezx");
        skuList.add("9z7bmxvuxxm04dnejxjz");
        skuList.add("odzuipiofggowx837rua");
        skuList.add("joc7f37ndcjny6zq07f4");
//            Bundle querySkus = new Bundle();
//            querySkus.putStringArrayList("ITEM_ID_LIST", skuList);

        IabHelper.QueryInventoryFinishedListener
                queryFinishedListener = new IabHelper.QueryInventoryFinishedListener() {
            public void onQueryInventoryFinished(IabResult result, Inventory inventory) {



//                if (inventory.hasPurchase("android.test.purchased")) {
//                    mHelper.consumeAsync(inventory.getPurchase("android.test.purchased"),null);
//                    System.out.println("consumin");
//                }

                if (result.isFailure()) {
                    // handle error
                    System.out.println("failure query");
                    view.findViewById(R.id.errorLayout).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.noIssuesLayout).setVisibility(View.GONE);
                    hideWheel();


//                    ArrayList<PurchaseHelperClass> available = new ArrayList<>();
//
//                    PurchaseHelperClass p = new PurchaseHelperClass("android.test.purchased",((tempAddress + "original")));
//                    available.add(p);
//                    available.add(p);
//
//
//
//                // update the UI
//                MyTaskParams mtp = new MyTaskParams(available, getContext(), true);
//                DownloadSetDetails task = new DownloadSetDetails();
//                task.execute(mtp);
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
                    view.findViewById(R.id.noAvailableLayout);
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
            final ImageView listingImageView = (ImageView) convertView.findViewById(R.id.listingImageView);
            Button listingPurchaseButton = (Button) convertView.findViewById(R.id.purchaseSetButton);
            // Populate the data into the template view using the data object
            listingTitle.setText(listing.getTitle());

            listingPurchaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buyClick(view, listing.getId());
                }
            });


            //File file = new File(getContext().getFilesDir(), listing.getImageLocation());
            //Bitmap bmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            //Bitmap bm = BitmapFactory.decodeResource(getContext().getResources(), listing.getImageLocation());
            //RoundImage roundedImage = new RoundImage(bmap);
            //listingImageView.setImageDrawable(roundedImage);
            Picasso.with(getContext()).load(listing.getImageLocation()).transform(new CircleTransform()).into(listingImageView);


            String tempRecipes = "";

            for (int i = 0; i < listing.getRecipeCount() ; i++) {
                tempRecipes += (listing.getRecipeName(i) + "\n");
            }

            listingRecipes.setText(tempRecipes);
            // Return the completed view to render on screen
            return convertView;
        }

    }

    public void hideWheel(){
        progress.dismiss();
    }

    public void showWheel(){

        progress.setMessage("LOADING AVAILABLE RECIPE SETS...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
    }

}





