package com.example.maxim.diabetesireland;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    boolean touchedMale, touchedFemale= false;
    String registered = "notRegistered";String gender = "Male",db_weight,db_height;
    int user_age;
    double user_height,user_weight;
    public static Button maleButton, femaleButton,nextScreen,dateOfBirth,weight,height;
    RadioButton weightButton, activeButton , healthButton;

    Intent intent;
    Calendar calendar = Calendar.getInstance();
    final String [] weight_metrics = {"kg","lbs","st and lbs"};
    final String [] height_metrics = {"cm","ft and in"};
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registered_check();
        setContentView(R.layout.activity_main);
        nextScreen = (Button) findViewById(R.id.continueButton);
        maleButton = (Button) findViewById(R.id.maleButton);
        femaleButton = (Button) findViewById(R.id.femaleButton);
        weight = (Button) findViewById(R.id.weight_button);
        height = (Button) findViewById(R.id.height_button);
        dateOfBirth = (Button) findViewById(R.id.datepicker);
        weightButton = (RadioButton) findViewById(R.id.weightRadio);
        activeButton = (RadioButton) findViewById(R.id.activeRadio);
        healthButton = (RadioButton) findViewById(R.id.healthRadio);
        maleButton.setOnClickListener(this);
        femaleButton.setOnClickListener(this);
        nextScreen.setOnClickListener(this);
        weight.setOnClickListener((new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String msg ="What is your weight?";
                showPopUp(weight_metrics, msg);
            }
        }));
        height.setOnClickListener((new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String msg = "What is your height?";
                showPopUp(height_metrics,msg);
            }
        }));
        dateOfBirth.setOnClickListener(this);
    }

    private boolean registered_check() {
        // fetch String registered from database
        if((registered.equals("registered"))){
            Intent i =  new Intent(MainActivity.this, TabActivity.class);
            startActivity(i);
        }
        return false;
    }

    private void getHeight(double height,EditText first,EditText second){
        if((second.getText().toString()).equals("")) {
            // ADD TO DATABASE => height
            height =(Double.parseDouble(first.getText().toString()));
        }
        else{
            double feet = (Double.parseDouble(first.getText().toString())) * 30.48;
            double inches = (Double.parseDouble(second.getText().toString())) * 2.54;
            // ADD TO DATABASE => height
            height= feet + inches;
        }
    }
    private void getWeight(double weight,EditText first,EditText second,String type){
        if((second.getText().toString()).equals("")) {
            if(type.equals("lbs")) {
                // ADD TO DATABASE => weight
                weight = ((Double.parseDouble(first.getText().toString())) * 0.453592);
            }
            else {
                weight = ((Double.parseDouble(first.getText().toString())));
            }

        }
        else {
            double stoneNum = (Double.parseDouble(first.getText().toString())) * 6.35029;
            double pounds = (Double.parseDouble(second.getText().toString())) * 0.453592;
            // ADD TO DATABASE => weight
            weight = stoneNum + pounds;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.maleButton:
                if (!touchedMale) {
                    touchedFemale = false;
                    touchedMale = true;
                    // ADD TO DATABASE
                    gender="Male";
                    maleButton.setBackgroundColor(0xff3F1451); //purple
                    maleButton.setTextColor(0xffffffff); //white
                    femaleButton.setBackgroundColor(0xffdbdbdb); //light grey
                    femaleButton.setTextColor(0xffffffff); //dark grey
                }
                break;
            case R.id.femaleButton:
                if (!touchedFemale) {
                    touchedFemale = true;
                    touchedMale = false;
                    //ADD TO DATABASE
                    gender="Female";
                    femaleButton.setBackgroundColor(0xff3F1451);//purple
                    femaleButton.setTextColor(0xffffffff); // white text
                    maleButton.setBackgroundColor(0xffdbdbdb); // dark grey background
                    maleButton.setTextColor(0xffffffff); //light grey text
                }
                break;
            case R.id.continueButton:
                // ADD TO DATABASE = registered
                registered="registered";
                intent = new Intent(MainActivity.this, TabActivity.class);
                startActivity(intent);
                break;
            case R.id.datepicker:
                new DatePickerDialog(MainActivity.this, R.style.DialogTheme,listener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;

        }
    }

    private void showPopUp(String [] spinner,String title) {

        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(MainActivity.this);
        helpBuilder.setTitle(title);
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        view = inflater.inflate(R.layout.weight_height_layout, null);
        final EditText input = (EditText) view.findViewById(R.id.cmFt);
        final EditText input2 = (EditText) view.findViewById(R.id.inches);
        final Spinner metrics = (Spinner) view.findViewById(R.id.metrics_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        metrics.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
             if((metrics.getSelectedItem().toString()).equals("cm") || (metrics.getSelectedItem().toString()).equals("ft and in")){
                if((metrics.getSelectedItem().toString()).equals("ft and in")) {
                    input2.setVisibility(view.VISIBLE);
                    input.getLayoutParams().width =200;
                    input2.getLayoutParams().width=200;

                }
                else{
                    input2.setVisibility(view.GONE);
                    input.getLayoutParams().width = 300;

                }

            }else{
                 if((metrics.getSelectedItem().toString()).equals("st and lbs")) {
                     input2.setVisibility(view.VISIBLE);
                     input.getLayoutParams().width =200;
                     input2.getLayoutParams().width=200;
                 }
                 else{
                     input2.setVisibility(view.GONE);
                     input.getLayoutParams().width = 300;
                 }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        metrics.setAdapter(adapter);
        helpBuilder.setView(view);
        helpBuilder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                if ((metrics.getSelectedItem().toString()).equals("cm") || (metrics.getSelectedItem().toString()).equals("ft and in")) {
                    if ((metrics.getSelectedItem().toString()).equals("ft and in")) {
                        height.setText(input.getText() + " ft" + input2.getText() + " in");
                        //ADD TO DATABASE
                        db_height=height.getText().toString();
                        getHeight(user_height, input, input2);
                    } else {
                        height.setText(input.getText() + " cm");
                        //ADD TO DATABASE
                        db_height=height.getText().toString();
                        getHeight(user_height, input, input2);
                    }
                } else {
                    if ((metrics.getSelectedItem().toString()).equals("st and lbs")) {
                        weight.setText(input.getText() + " st" + input2.getText() + " lbs");
                        //ADD TO DATABASE
                        db_weight=weight.getText().toString();
                        getWeight(user_weight, input, input2,"st and lbs");
                    } else {
                        if ((metrics.getSelectedItem().toString()).equals("lbs")) {
                            weight.setText(input.getText() + " lbs");
                            //ADD TO DATABASE
                            db_weight=weight.getText().toString();
                            getWeight(user_weight, input, input2,"lbs");
                        } else {
                            weight.setText(input.getText() + " kg");
                            //ADD TO DATABASE
                            db_weight=weight.getText().toString();
                            getWeight(user_weight, input, input2,"kg");
                        }
                    }
                }
                dialog.dismiss();
            }
        });
        helpBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
            }
        });
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
    }




    DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateOfBirth.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            if(year != 0){
                GregorianCalendar cal = new GregorianCalendar();
                int y, m, d;
                y = cal.get(Calendar.YEAR);
                m = cal.get(Calendar.MONTH);
                d = cal.get(Calendar.DAY_OF_MONTH);
                cal.set(year, monthOfYear, dayOfMonth);
                user_age = y - cal.get(Calendar.YEAR);
                if ((m < cal.get(Calendar.MONTH)) || ((m == cal.get(Calendar.MONTH)) && (d < cal.get(Calendar.DAY_OF_MONTH)))) {
                    --user_age;
                }
                if(user_age < 0) {
                    throw new IllegalArgumentException("Age < 0");
                }
                else{
                    // ADD age to DATABASE
                }
            }
            }};


}



