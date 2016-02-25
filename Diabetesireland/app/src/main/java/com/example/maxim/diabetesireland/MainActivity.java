package com.example.maxim.diabetesireland;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    boolean touchedMale = false;
    boolean touchedFemale = false;
    Spinner weight;
    Spinner height;
    String[] heights = new String[] {"Metres","<1.40m","1.4-1.5m", "1.5-1.6m", "1.6-1.7m", "1.7-1.8m","1.8m-1.9m", "1.9-2m", "2m+"};
    String[] weights = new String[]{"Kilograms","<60kg", "60-65kg", "65-70kg","70-75kg","75-80kg","80-85kg","85-90kg","90-95kg","95-100kg","100-110kg","110-120kg","120-130kg","130-140kg","140-150kg","150k-200kg", "200+kg"};
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

        weight = (Spinner)findViewById(R.id.weightSpinner);
        height = (Spinner)findViewById(R.id.hSpinner);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_dropdown_item,heights);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String>adapter2 = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_dropdown_item,weights);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        weight.setAdapter(adapter2);
        height.setAdapter(adapter);

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
                    femaleButton.setTextColor(0xffffffff); //dark grey
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
                    maleButton.setTextColor(0xffffffff); //light grey text
                }
                break;
            case R.id.continueButton:
                intent = new Intent(MainActivity.this, Triangle.class);
                startActivity(intent);
                break;


        }
    }
}
