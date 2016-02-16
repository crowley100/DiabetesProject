package com.example.maxim.diabetesireland;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    boolean touchedMale = false;
    boolean touchedFemale = false;
    Button maleButton;
    Button femaleButton;
    Button nextScreen;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nextScreen = (Button) findViewById(R.id.continueButton);
        maleButton = (Button) findViewById(R.id.maleButton);
        femaleButton = (Button) findViewById(R.id.femaleButton);

        maleButton.setOnClickListener(this);
        femaleButton.setOnClickListener(this);
        nextScreen.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.maleButton:
                if (!touchedMale) {
                    touchedFemale = false;
                    touchedMale = true;
                    femaleButton.setBackgroundColor(0xffadadad);//purple
                    maleButton.setBackgroundColor(0xff432687); //grey
                }
                break;
            case R.id.femaleButton:
                if(!touchedFemale)
                {
                    touchedFemale = true;
                    touchedMale = false;
                    femaleButton.setBackgroundColor(0xff432687); //purple
                    maleButton.setBackgroundColor(0xffadadad); //grey
                }

                break;
            case R.id.continueButton:
                Intent intent = new Intent(MainActivity.this, Plan.class);
                startActivity(intent);

                break;

        }
    }
}
