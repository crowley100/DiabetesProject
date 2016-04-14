package com.example.maxim.diabetesireland;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Scanner;

public class FoodIntakeFragment extends Fragment {
    private View view;
    private View radioLayout;
    float carb = 0,fg = 0, water = 0, dairy = 0, protein = 0, alc = 0, oil = 0, treats = 0;
    //Default value for gender
    String gender="Male";
    DatabaseHelper myDb;
    private foodIntakeFragmentListener mListener;


    public interface foodIntakeFragmentListener {
        void sendPortionSize(float portionSize,String type);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (foodIntakeFragmentListener)context;
        }catch (ClassCastException e){

        }

    }
    public static FoodIntakeFragment newInstance() {
        return new FoodIntakeFragment();
    }

    public FoodIntakeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDb = new DatabaseHelper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_food_intake, container, false);

        // Initalise all food group buttons and if clicked show portion pop up
        Button showPopUpButton = (Button) view.findViewById(R.id.waterbutton);
        showPopUpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showPopUp3("Select Water portion taken:", "Water");
            }
        });
        Button showPopUpButton2 = (Button) view.findViewById(R.id.fruitvegbutton);
        showPopUpButton2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showPopUp3("Select Fruit and Vegetables portion taken:", "Fruit and Veg");
            }
        });
        Button showPopUpButton3 = (Button) view.findViewById(R.id.carbutton);
        showPopUpButton3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showPopUp3("Select Carbohydrates portion taken:", "Carbohydrates");
            }
        });
        Button showPopUpButton4 = (Button) view.findViewById(R.id.dairybutton);
        showPopUpButton4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showPopUp3("Select Dairy portion taken:","Dairy");
            }
        });
        Button showPopUpButton5 = (Button) view.findViewById(R.id.proteinbutton);
        showPopUpButton5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showPopUp3("Select Protein portion taken:", "Protein");
            }
        });
        Button showPopUpButton6 = (Button) view.findViewById(R.id.alcoholbutton);
        showPopUpButton6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showPopUp3("Select Alcohol portion taken:", "Alcohol");
            }
        });
        Button showPopUpButton8 = (Button) view.findViewById(R.id.oilbutton);
        showPopUpButton8.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showPopUp3("Select Fats and Oils portion taken:", "Fats");
            }
        });
        Button showPopUpButton7 = (Button) view.findViewById(R.id.treatsbutton);
        showPopUpButton7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showPopUp3("Select Treats portion taken: (Only eat occasionally).", "Treats");
            }
        });

        return view;
    }

    // Shows food portion pop up
    // Arguments:
    // String msg = contains message to be displayed in the pop up
    // String type = describes the type of food
    private void showPopUp3(String msg,String type) {

        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(getActivity());
        helpBuilder.setTitle("Portion Size");
        helpBuilder.setMessage(msg);
        final String food = type;
        LayoutInflater inflater = getActivity().getLayoutInflater();
        radioLayout = inflater.inflate(R.layout.portionlayout, null);
        helpBuilder.setView(radioLayout);
        final RadioGroup radioGroup = (RadioGroup) radioLayout.findViewById(R.id.radioGroup1);

        if(type.equals("Water")) {
            ((RadioButton) radioGroup.getChildAt(0)).setText("1 glass(200 ml)");
            ((RadioButton) radioGroup.getChildAt(1)).setText("1/2 glass(100 ml)");
            ((RadioButton) radioGroup.getChildAt(2)).setText("1/4 glass(50 ml)");
        }
        else if(type.equals("Alcohol")) {
            ((RadioButton) radioGroup.getChildAt(0)).setText("5 units");
            ((RadioButton) radioGroup.getChildAt(1)).setText("3 units");
            ((RadioButton) radioGroup.getChildAt(2)).setText("1 units");
        }
        else{
            radioLayout = inflater.inflate(R.layout.portionlayout, null);
        }


        helpBuilder.setPositiveButton("Submit",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        if(radioGroup.getCheckedRadioButtonId() == -1) {

                        }else{
                            int selectedId = radioGroup.getCheckedRadioButtonId();
                            final RadioButton radioButton = (RadioButton) radioLayout.findViewById(selectedId);
                            float portion = (parse(radioButton.getText().toString()));
                            setPortion(portion, food);
                            mListener.sendPortionSize(portion,food);
                            if(food.equals("Alcohol"))
                            { /*Limit for alcohol*/
                                if (gender.equals("Male")) {
                                    if(alc >= 14) {
                                        showAlert("alcohol");
                                    }
                                }
                                else{
                                    if(alc >= 11) {
                                        showAlert("alcohol");
                                    }
                                }
                            } if(food.equals("Carbohydrates"))
                            { /*Limit for alcohol*/
                                if (gender.equals("Male")) {
                                    if(carb >= 6) {
                                        showAlert("alcohol");
                                    }
                                }
                                else{
                                    if(carb >= 5) {
                                        showAlert("alcohol");
                                    }
                                }
                            }
                            else dialog.dismiss();
                        }
                    }
                });
        helpBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
            }
        });
        // Remember, create doesn't show the dialog
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();

    }

    private void showAlert(String foodType) {
        AlertDialog.Builder alertPopUp = new AlertDialog.Builder(getActivity());
        alertPopUp.setTitle("Warning");
        alertPopUp.setMessage("You have exceeded your daily recommended intake of " + foodType);
        alertPopUp.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }
        );
        AlertDialog alertDialog = alertPopUp.create();
        alertDialog.show();
    }

    public float parse(String ratio) {
        String []portion = ratio.split(" ");

            if (portion[0].contains("/")) {
                String[] rat = portion[0].split("/");
                return Float.parseFloat(rat[0]) / Float.parseFloat(rat[1]);
            }
        else{
            return Float.parseFloat(portion[0]);
        }
    }
    // Increments the global variables of a food group once user adds food portion of the particular food group
    // Arguments:
    // float portionSize = selected portion size of the user
    // String type = describes the type of food
    public void setPortion(float portionSize,String type) {
        switch (type){
            case "Water":
                //UPDATE Water count on database
                water += portionSize;
                myDb.updateDailyFood(portionSize, "WATER_INTAKE");
                break;
            case "Fruit and Veg":
                // UPDATE Fruits and Veg count on database
                fg+= portionSize;
                myDb.updateDailyFood(portionSize, "FRUITandVEG_INTAKE");
                break;
            case "Carbohydrates":
                // UPDATE carb count on database
                carb+=portionSize;
                myDb.updateDailyFood(portionSize, "CARBOHYDRATE_INTAKE");
                break;
            case "Dairy":
                //UPDATE
                dairy+=portionSize;
                myDb.updateDailyFood(portionSize, "DAIRY_INTAKE");
                break;
            case "Protein":
                protein+=portionSize;
                myDb.updateDailyFood(portionSize, "PROTEIN_INTAKE");
                break;
            case "Alcohol":
                alc+=portionSize;
                myDb.updateDailyFood(portionSize, "ALCOHOL_INTAKE");
                break;
            case "Fats":
                oil+=portionSize;
                myDb.updateDailyFood(portionSize, "FAT_INTAKE");
                break;
            case "Treats":
                treats+=portionSize;
                myDb.updateDailyFood(portionSize, "TREATS_INTAKE");
                break;
        }
    }

}
