package com.mysampleapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuizFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizFragment extends Fragment {

    View view;
    String questionsAr[];
    Boolean answersAr[];
    String[] detoxStringArray;
    TextView quizQuestion;
    int counter;
    Button yesButton;
    Button noButton;
    ViewGroup quizQuestionsLayout;
    ViewGroup quizHomeLayout;
    ViewGroup quizEndLayout;
    int plan1Counter;
    int plan2Counter;
    int plan3Counter;
    TextView recommendQuizTextView;
    ProgressBar quizProgressBar;
    WebView webView;
    ViewGroup detoxPlanWebViewLayout;
    String[] detoxPlanLinks;
    String linkToLoad;
    String planName;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public QuizFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuizFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuizFragment newInstance(String param1, String param2) {
        QuizFragment fragment = new QuizFragment();
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
        view = inflater.inflate(R.layout.fragment_quiz, container, false);


        questionsAr = new String[]{
                //candida questions
                "Have you experienced slurred speech or a loss of muscle co-ordination or vision?",
                        "Do you have any allergies to foods and/or airborne chemicals?",
                        "Have you had athlete’s foot, hives, ringworm, jock itch or toenail fungus within the last month?",
                        "Do you feel tired and worn down after eating?",
                        "Do you have difficulty concentrating, poor memory, lack of focus, ADD, ADHD and/or brain fog?",
                        "Had you had skin issues like eczema, psoriasis, hives and/or rashes?\n",
                        "Have you been experiencing irritability, mood swings, anxiety or depression?",
                        "Have you had vaginal infections, urinary tract infections, rectal itching or vaginal itching?",
                        "Are you experiencing severe seasonal allergies or itchy ears?",
                        "Do you have sugar and or carbohydrate cravings?",
                //parasite questions
                "Have you had unexplained and sudden weight loss of at least 10 pounds over two months?",
                        "Have you traveled internationally and remember getting traveler’s diarrhea or loose stool while abroad?",
                        "Do you have skin irritations or unexplained rashes, hives, rosacea or eczema?",
                        "Do you have cramping and abdominal pain?", "Have you recently had unexplained constipation, diarrhea, gas, or other symptoms of IBS?",
                        "Do you have trouble sleeping or do you wake up throughout the night?",
                        "Have you experienced food poisoning and your digestion has not been the same since?",
                        "Do you have any pain or aching in your muscles or joints?",
                        "Are you experiencing fatigue, exhaustion, depression, or frequent feelings of apathy?",
                        "Do you never feel satisfied or full after your meals?",
                //heavy metal questions
                "Do you have headaches, migraines or dizziness?",
                        "Ave you been having chest pains or heart problems?",
                        "Do you have dark circles under the eyes?",
                        "Do you have an intolerance to medications and/or vitamins?",
                        "Is your body temperature low?",
                        "Do you have a metallic taste in your mouth?",
                        "Have you had any muscle tics or twitches?",
                        "Are your teeth sensitive?",
                        "Are you sensitive to smells like tobacco smoke, perfumes, paint fumes and chemical odors?",
                        "Do you have sore gums or have small black spots on your gums?"};

        detoxStringArray = new String[]  {"Candida", "Parasite", "Heavy Metal"};

        detoxPlanLinks = new String[] {"https://hayliepomroy.com/product/14-day-candida-cleanse",
                "https://hayliepomroy.com/product/14-day-parasite-cleanse",
                "https://hayliepomroy.com/product/14-day-heavy-metal-cleanse"};

        answersAr = new Boolean[30];
        quizHomeLayout = (ViewGroup) view.findViewById(R.id.quizHomeLayout);
        quizQuestionsLayout = (ViewGroup) view.findViewById(R.id.quizQuestionsLayout);
        quizEndLayout = (ViewGroup) view.findViewById(R.id.quizEndLayout);
        quizQuestion = (TextView) view.findViewById(R.id.quizTextView);
        yesButton = (Button) view.findViewById(R.id.yesButton);
        noButton = (Button) view.findViewById(R.id.noButton);
        quizProgressBar = (ProgressBar) view.findViewById(R.id.quizProgressBar);
        detoxPlanWebViewLayout = (ViewGroup) view.findViewById(R.id.detoxPlanWebViewLayout);
        counter = -1;

        recommendQuizTextView = (TextView) view.findViewById(R.id.recommendQuizTextView);

        plan1Counter = 0;
        plan2Counter = 0;
        plan3Counter = 0;



        //setup webview
        //-------------------------------------------------------------------------------------------
        webView = (WebView) view.findViewById(R.id.detoxPlanWebView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setDomStorageEnabled(true);

        //when these are uncommented webView on emulator does not freeze
        webView.getSettings().setAppCacheEnabled(false);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (Uri.parse(url).getHost().equals("hayliepomroy.com")) {
                    // Designate Urls that you want to load in WebView still.
                    view.loadUrl(url);
                    return true;
                }
                // Otherwise, give the default behavior (open in browser)
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }

        });
        //-------------------------------------------------------------------------------------------


        recommendQuizTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity)getActivity()).switchToPurchaseFragment(planName);
                //webView.loadUrl(linkToLoad);
                //switchLayout(quizEndLayout, detoxPlanWebViewLayout);
               // counter++;
            }
        });


        quizHomeLayout.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switchLayout(quizHomeLayout, quizQuestionsLayout);
                counter++;
                quizQuestion.setText((counter+1) + ": " + questionsAr[counter]);
                return false;
            }
        });

        quizEndLayout.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switchLayout(quizEndLayout, quizHomeLayout);
                counter = -1;
                return false;
            }
        });


        yesButton.setOnClickListener(new View.OnClickListener(){
                 @Override
                 public void onClick(View v) {

                     if(counter == 29){
                         answersAr[counter] = true;
                         counter = 30;
                         endQuiz();
                     }
                     else {
                         answersAr[counter] = true;
                         counter++;
                         quizQuestion.setText((counter + 1) + ": " + questionsAr[counter]);

                         quizProgressBar.incrementProgressBy(1);

                     }
                 }
             }
        );

        noButton.setOnClickListener(new View.OnClickListener(){

                 @Override
                 public void onClick(View v) {
                     if(counter == 29){
                         answersAr[counter] = false;
                         counter = 30;
                         endQuiz();

                     }
                     else {

                         answersAr[counter] = false;
                         counter++;
                         quizQuestion.setText((counter + 1) + ": " + questionsAr[counter]);
                         quizProgressBar.incrementProgressBy(1);

                     }

                 }

             }
        );


        return view;
    }



    //called after all questions are answered
    void endQuiz() {
            for (int i = 0; i < 10; i++) {
                if (answersAr[i])
                    plan1Counter++;
            }

            for (int i = 10; i < 20; i++) {
                if (answersAr[i])
                    plan2Counter++;
            }


            for (int i = 20; i < 30; i++) {
                if (answersAr[i])
                    plan3Counter++;
            }


            String temp;

            if (plan1Counter > plan2Counter) {

                if (plan1Counter > plan3Counter) {
                    temp = detoxStringArray[0];
                    linkToLoad = detoxPlanLinks[0];
                } else {
                    temp = detoxStringArray[2];
                    linkToLoad = detoxPlanLinks[2];
                }
            } else {

                if (plan2Counter > plan3Counter) {
                    temp = detoxStringArray[1];
                    linkToLoad = detoxPlanLinks[1];
                } else {
                    temp = detoxStringArray[2];
                    linkToLoad = detoxPlanLinks[2];
                }

            }

            planName = temp;

            recommendQuizTextView.setText("Based on your quiz answers the best suited plan for you is the " + planName + " Detox Plan!\n" +
                    "Click here for more details.");

              switchLayout(quizQuestionsLayout, quizEndLayout);
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



    //used to override back button functionality
    @Override
    public void onResume() {
        super.onResume();


        yesButton.clearFocus();
        noButton.clearFocus();


        getView().setFocusableInTouchMode(true);
        getView().requestFocus();

        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                System.out.println("counter " + counter);

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    if(counter == -1) {
                        Toast.makeText(getActivity(), "Back press", Toast.LENGTH_SHORT).show();
                        getActivity().onBackPressed();
                    }
                    else if(counter == 0) {
                            //display the main quiz screen
                            counter--;
                            switchLayout(quizQuestionsLayout, quizHomeLayout);
                    }
                    else if(counter == 30){
                        //reverse through questions
                        counter--;
                        quizQuestion.setText((counter+1) + ": " + questionsAr[counter]);
                        switchLayout(quizEndLayout, quizQuestionsLayout);
                    }
                    else if(counter == 31){
                       // webView.stopLoading();

                        //then user is currently in webView
                            if(webView.canGoBack()) {
                                webView.goBack();
                            }
                            else {
                                switchLayout(detoxPlanWebViewLayout, quizEndLayout);
                                counter--;
                            }

                    }
                    else {

                        counter--;
                        quizQuestion.setText((counter+1) + ": " + questionsAr[counter]);
                        quizProgressBar.setProgress(quizProgressBar.getProgress()-1);

                    }

                        return true;
                    }

                return false;
            }
        });

    }

    void switchLayout(ViewGroup v1, ViewGroup v2){
        v1.setVisibility(INVISIBLE);
        v2.setX(1000f);
        v2.setVisibility(VISIBLE);
        v2.animate().translationXBy(-1000f);
    }

}
