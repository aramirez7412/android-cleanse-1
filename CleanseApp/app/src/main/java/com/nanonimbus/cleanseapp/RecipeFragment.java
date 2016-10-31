package com.nanonimbus.cleanseapp;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<ArrayList<MealItem>> recipeSets;
    ArrayList<MealItem> tempSet;
    List<String> headerTitles;
    List<Object> childTitles;
    ExpandableListAdapter recipeAdapter;
    ExpandableListView myList;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    TextView recipeTitle;
    TextView ingredientHeader;
    TextView recipeIngredientsContent;
    TextView recipeDirectionsContent;
    ImageView recipeBoxImageView;
    float pressedX;
    float pressedY;

    View recipeBox;



    public RecipeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecipeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeFragment newInstance(String param1, String param2) {
        RecipeFragment fragment = new RecipeFragment();
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
        View v = inflater.inflate(R.layout.fragment_recipe, container, false);

        recipeBox = v.findViewById(R.id.recipeBoxView);
        recipeBox.setX(1500);
        recipeBox.setAlpha(0);


        recipeBox.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent e) {

                switch (e.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        pressedX = e.getRawX();
                        pressedY = e.getRawY();
                        break;
                    }


                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:


                            if (pressedX < e.getRawX() && ((pressedY - e.getRawY()) > -20) && ((pressedY - e.getRawY()) < 20)) {
                                recipeBox.animate().translationX(1500).alpha(0).setDuration(600).start();
                            }


                }
                return false;
            }

        });


        recipeTitle = (TextView) recipeBox.findViewById(R.id.recipeTitle);
        ingredientHeader = (TextView) recipeBox.findViewById(R.id.ingredientHeader);
        recipeIngredientsContent = (TextView) recipeBox.findViewById(R.id.recipeIngredientsContent);
        recipeDirectionsContent = (TextView) recipeBox.findViewById(R.id.recipeDirectionsContent);
        recipeBoxImageView = (ImageView) recipeBox.findViewById(R.id.recipeBoxImageView);




        recipeSets = new ArrayList<>();
        childTitles = new ArrayList<>();
        headerTitles = new ArrayList<>();


        for (int i = 0; i <  ((MainActivity) getActivity()).getRecipeSetCount(); i++) {

            RecipeSet rs = getSetFromFile(((MainActivity) getActivity()).getSetPath(i));

            try {
                tempSet = rs.getRecipeSet();
                recipeSets.add(tempSet);

                headerTitles.add(rs.getRecipeSetTitle());
                childTitles.add(tempSet);
            }
            catch(NullPointerException ex){

                new AlertDialog.Builder(getContext())
                        .setTitle("ERROR:")
                        .setMessage("Attempting to re-download content. Side menu will be availble when download is complete.")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                ((MainActivity)getActivity()).showHome();
                ((MainActivity)getActivity()).downloadEverything();
                ex.printStackTrace();
            }
        }







        recipeAdapter = new ExpandableListAdapter(getContext(), headerTitles, childTitles);

        myList = (ExpandableListView) v.findViewById(R.id.expandableRecipeList);

        myList.setAdapter(recipeAdapter);


        myList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                MealItem o = recipeSets.get(groupPosition).get(childPosition);

                hideAndShowMealItem(o);
                return false;
            }
        });


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

            System.out.println("successfully loaded recipeSet");
        } catch (FileNotFoundException e) {

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


    void hideAndShowMealItem(MealItem o) {
        recipeTitle.setText(o.getTitle());
        ingredientHeader.setText("Ingredients (Serves " + o.getServings() + ")");
        recipeIngredientsContent.setText(o.getIngredients());
        recipeDirectionsContent.setText(o.getDirections());

        if (!o.getImageUrl().isEmpty()) {
            // Picasso.with(getContext()).load(o.getImageUrl()).into(recipeBoxImageView);
            loadPicassoPicFromFileAsync(recipeBoxImageView, o);
        }

        recipeBox.animate().translationX(0).alpha(1).setDuration(600).start();
    }

    void loadPicassoPicFromFileAsync(final ImageView v, final MealItem o) {


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

            System.out.println("loaded from memory");
            // Picasso.with(getContext()).load("file://" + file.getAbsolutePath()).fit().centerCrop().into(v);
            Picasso.with(getContext()).load("file://" + file.getAbsolutePath()).fit().into(v);


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
}



