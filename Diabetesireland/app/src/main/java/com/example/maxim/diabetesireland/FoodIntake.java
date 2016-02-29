package com.example.maxim.diabetesireland;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class FoodIntake extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_intake);
        Button showPopUpButton = (Button) findViewById(R.id.plus1);
        showPopUpButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showPopUp3();
            }
        });
        Button showPopUpButton2 = (Button) findViewById(R.id.plus2);
        showPopUpButton2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showPopUp3();
            }
        });
        Button showPopUpButton3 = (Button) findViewById(R.id.plus3);
        showPopUpButton3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showPopUp3();
            }
        });
        Button showPopUpButton4 = (Button) findViewById(R.id.plus4);
        showPopUpButton4.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showPopUp3();
            }
        });
        Button showPopUpButton5 = (Button) findViewById(R.id.plus5);
        showPopUpButton5.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showPopUp3();
            }
        });
        Button showPopUpButton6 = (Button) findViewById(R.id.plus6);
        showPopUpButton6.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showPopUp3();
            }
        });
        Button showPopUpButton7 = (Button) findViewById(R.id.plus7);
        showPopUpButton7.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showPopUp3();
            }
        });
        Button showPopUpButton8 = (Button) findViewById(R.id.plus8);
        showPopUpButton8.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showPopUp3();
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    private void showPopUp3() {

        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setTitle("Portion Size");
        helpBuilder.setMessage("Select food portion eaten:");

        LayoutInflater inflater = getLayoutInflater();
        View checkboxLayout = inflater.inflate(R.layout.portionlayout, null);
        helpBuilder.setView(checkboxLayout);

        helpBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                    }
                });

        // Remember, create doesn't show the dialog
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();

    }
}
