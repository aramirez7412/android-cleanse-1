package com.example.mattcorrente.testingpages;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import me.itangqi.waveloadingview.WaveLoadingView;


public class MainActivity extends AppCompatActivity {


    Button drinkWaterButton;
    WaveLoadingView waveView;
    int ounces;
    TextView ounceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.water_tracker_sample);

        drinkWaterButton = (Button) findViewById(R.id.drinkWaterButton);
        waveView = (WaveLoadingView) findViewById(R.id.waveLoadingView);
        ounceTextView = (TextView) findViewById(R.id.ounceTextView);
        ounces = 64;

        ounceTextView.setText("You are " + ounces + " ounces away from completing your daily goal!");

        drinkWaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waveView.setProgressValue(waveView.getProgressValue()-13);

                ounces-=8;

                if(ounces <= 0) {

                    ounceTextView.setText("Congratulations! You have met your daily water goal!");
                }
                else{
                    ounceTextView.setText("You are " + ounces + " ounces away from completing your daily goal!");
                }


            }
        });

    }
}


