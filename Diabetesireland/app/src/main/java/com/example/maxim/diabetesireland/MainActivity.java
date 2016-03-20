package com.example.maxim.diabetesireland;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    boolean touchedMale, touchedFemale = false;
    Button maleButton, femaleButton,nextScreen;
    RadioButton weightButton, activeButton;
    Intent intent;
    EditText height, weight,age;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nextScreen = (Button) findViewById(R.id.continueButton);
        maleButton = (Button) findViewById(R.id.maleButton);
        femaleButton = (Button) findViewById(R.id.femaleButton);
        weightButton = (RadioButton) findViewById(R.id.weightRadio);
        activeButton = (RadioButton) findViewById(R.id.activeRadio);
        maleButton.setOnClickListener(this);
        femaleButton.setOnClickListener(this);
        nextScreen.setOnClickListener(this);
        height = (EditText) findViewById(R.id.heightField);
        weight = (EditText) findViewById(R.id.weightField);
        age = (EditText) findViewById(R.id.ageField);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
                if (!touchedFemale) {
                    touchedFemale = true;
                    touchedMale = false;
                    femaleButton.setBackgroundColor(0xff432687);//purple
                    femaleButton.setTextColor(0xffffffff); // white text
                    maleButton.setBackgroundColor(0xffdbdbdb); // dark grey background
                    maleButton.setTextColor(0xffffffff); //light grey text
                }
                break;
            case R.id.continueButton:
                intent = new Intent(MainActivity.this, TabActivity.class);
                startActivity(intent);
                break;
        }
    }

}



