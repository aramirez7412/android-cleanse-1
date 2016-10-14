package com.nanonimbus.cleanseapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//import com.android.vending.billing.IInAppBillingService;
//import com.mysamplecleanseapp.util.IabHelper;
//import com.mysamplecleanseapp.util.IabResult;
//import com.mysamplecleanseapp.util.Inventory;

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

//    IabHelper mHelper;
    View view;
//    IInAppBillingService mService;
//
//    ServiceConnection mServiceConn = new ServiceConnection() {
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            mService = null;
//        }
//
//        @Override
//        public void onServiceConnected(ComponentName name,
//                                       IBinder service) {
//            mService = IInAppBillingService.Stub.asInterface(service);
//        }
//    };





    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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

//        Intent serviceIntent =
//                new Intent("com.android.vending.billing.InAppBillingService.BIND");
//        serviceIntent.setPackage("com.android.vending");
//        getContext().bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);
//
//        ArrayList<String> skuList = new ArrayList<String> ();
//        skuList.add("recipetest1");
//        Bundle querySkus = new Bundle();
//        querySkus.putStringArrayList("ITEM_ID_LIST", skuList);
//
//
//        try {
//            Bundle skuDetails = mService.getSkuDetails(3, getContext().getPackageName(), "inapp", querySkus);
//
//
//            int response = skuDetails.getInt("RESPONSE_CODE");
//
//            if (response == 0) {
//                ArrayList<String> responseList
//                        = skuDetails.getStringArrayList("DETAILS_LIST");
//
//                for (String thisResponse : responseList) {
//                    JSONObject object = new JSONObject(thisResponse);
//                    String sku = object.getString("productId");
//                    String price = object.getString("price");
//
//                    System.out.println("the sku is " + sku + "   and the price " + price);
//                }
//            }
//
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }





     //   mHelper = ((MainActivity) getActivity()).getIabHelper();




        List<String> additionalSkuList = new ArrayList();
        additionalSkuList.add("recipetest1");

//
//        IabHelper.QueryInventoryFinishedListener
//                mQueryFinishedListener = new IabHelper.QueryInventoryFinishedListener() {
//            public void onQueryInventoryFinished(IabResult result, Inventory inventory)
//            {
//                if (result.isFailure()) {
//                    // handle error
//                    return;
//                }
//
//                System.out.println(inventory.hasPurchase("recipetest1"));
//               // String applePrice = inventory.getSkuDetails("recipetest1").getPrice();
//
//                // update the UI
//                //System.out.println("the price is: " + applePrice);
//            }
//        };
//
//
//        try {
//            mHelper.queryInventoryAsync(true, additionalSkuList , null, mQueryFinishedListener);
//        } catch (IabHelper.IabAsyncInProgressException e) {
//            e.printStackTrace();
//        }

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_purchase, container, false);


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
}
