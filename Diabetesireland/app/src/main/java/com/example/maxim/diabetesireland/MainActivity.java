package com.example.maxim.diabetesireland;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    boolean touchedMale = false;
    boolean touchedFemale = false;
    Button maleButton;
    Button femaleButton;
    Button nextScreen;
    Intent intent;



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
                    maleButton.setBackgroundColor(0xff432687); //purple
                    maleButton.setTextColor(0xffffffff); //white
                    femaleButton.setBackgroundColor(0xffdbdbdb); //light grey
                    femaleButton.setTextColor(0xffadadad); //dark grey
                }
                break;
            case R.id.femaleButton:
                if(!touchedFemale)
                {
                    touchedFemale = true;
                    touchedMale = false;
                    femaleButton.setBackgroundColor(0xff432687);//purple
                    femaleButton.setTextColor(0xffffffff); // white text
                    maleButton.setBackgroundColor(0xffdbdbdb); // dark grey background
                    maleButton.setTextColor(0xffadadad); //light grey text
                }
                break;
            case R.id.continueButton:
                intent = new Intent(MainActivity.this, Triangle.class);
                startActivity(intent);
                break;


        }
    }
}
