package com.example.maxim.diabetesireland;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class FoodIntakeFragment extends Fragment {
    private View view;
    private View radioLayout;
    float carb,fg,water,dairy,protein,alc,oil,treats;
    DatabaseHelper myDb;

    public static FoodIntakeFragment newInstance() {
        return new FoodIntakeFragment();
    }

    public FoodIntakeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myDb = new DatabaseHelper(getActivity());
        view= inflater.inflate(R.layout.fragment_food_intake, container, false);
        Button showPopUpButton = (Button) view.findViewById(R.id.waterbutton);
        showPopUpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showPopUp3("Select Water portion taken:","Water");
            }
        });
        Button showPopUpButton2 = (Button) view.findViewById(R.id.fruitvegbutton);
        showPopUpButton2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showPopUp3("Select Fruit and Vegetables portion taken:","Fruit and Veg");
            }
        });
        Button showPopUpButton3 = (Button) view.findViewById(R.id.carbutton);
        showPopUpButton3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showPopUp3("Select Carbohydrates portion taken:","Carbohydrates");
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
                showPopUp3("Select Protein portion taken:","Protein");
            }
        });
        Button showPopUpButton6 = (Button) view.findViewById(R.id.alcoholbutton);
        showPopUpButton6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showPopUp3("Select Alcohol portion taken:","Alcohol");
            }
        });
        Button showPopUpButton8 = (Button) view.findViewById(R.id.oilbutton);
        showPopUpButton8.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showPopUp3("Select Fats and Oils portion taken:","Fats");
            }
        });
        Button showPopUpButton7 = (Button) view.findViewById(R.id.treatsbutton);
        showPopUpButton7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showPopUp3("Select Treats portion taken: (Only eat occasionally","Treats");
            }
        });

        return view;
    }
    private void showPopUp3(String msg,String type) {

        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(getActivity());
        helpBuilder.setTitle("Portion Size");
        helpBuilder.setMessage(msg);
        final String food = type;

        LayoutInflater inflater = getActivity().getLayoutInflater();
        radioLayout = inflater.inflate(R.layout.portionlayout, null);
        helpBuilder.setView(radioLayout);
        final RadioGroup radioGroup = (RadioGroup) radioLayout.findViewById(R.id.radioGroup1);
        helpBuilder.setPositiveButton("Submit",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        if(radioGroup.getCheckedRadioButtonId() == -1) {

                        }else{
                            int selectedId = radioGroup.getCheckedRadioButtonId();
                            final RadioButton radioButton = (RadioButton) radioLayout.findViewById(selectedId);
                            float portion = (parse(radioButton.getText().toString()));
                            setPortion(portion, food);
                            dialog.dismiss();
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

    public void setPortion(float portionSize,String type) {
        switch (type){
            case "Water":
                //UPDATE Water count on database
                water = portionSize;
                Log.v("portion",""+water);
                myDb.updateDailyFood(portionSize, "WATER_INTAKE");
                break;
            case "Fruit and Veg":
                // UPDATE Fruits and Veg count on database
                fg = portionSize;
                myDb.updateDailyFood(portionSize, "FRUITandVEG_INTAKE");
                break;
            case "Carbohydrates":
                // UPDATE carb count on database
                carb = portionSize;
                myDb.updateDailyFood(portionSize, "CARBOHyDRATE_INTAKE");
                break;
            case "Dairy":
                //UPDATE
                dairy = portionSize;
                myDb.updateDailyFood(portionSize, "DAIRY_INTAKE");
                break;
            case "Protein":
                protein = portionSize;
                myDb.updateDailyFood(portionSize, "PROTEIN_INTAKE");
                break;
            case "Alcohol":
                alc = portionSize;
                myDb.updateDailyFood(portionSize, "ALCOHOL_INTAKE");
                break;
            case "Fats":
                oil = portionSize;
                myDb.updateDailyFood(portionSize, "FAT_INTAKE");
                break;
            case "Treats":
                treats = portionSize;
                myDb.updateDailyFood(portionSize, "TREATS_INTAKE");
                break;
        }
    }

}
