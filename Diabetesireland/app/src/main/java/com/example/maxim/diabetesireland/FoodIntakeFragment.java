package com.example.maxim.diabetesireland;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    private foodIntakeFragmentListener mListener;

    public interface foodIntakeFragmentListener {
        public void sendPortionSize(int portionSize);
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_food_intake, container, false);
        Button showPopUpButton = (Button) view.findViewById(R.id.waterbutton);
        showPopUpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showPopUp3();
            }
        });
        Button showPopUpButton2 = (Button) view.findViewById(R.id.fruitvegbutton);
        showPopUpButton2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showPopUp3();
            }
        });
        Button showPopUpButton3 = (Button) view.findViewById(R.id.carbutton);
        showPopUpButton3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showPopUp3();
            }
        });
        Button showPopUpButton4 = (Button) view.findViewById(R.id.dairybutton);
        showPopUpButton4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showPopUp3();
            }
        });
        Button showPopUpButton5 = (Button) view.findViewById(R.id.proteinbutton);
        showPopUpButton5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showPopUp3();
            }
        });
        Button showPopUpButton6 = (Button) view.findViewById(R.id.alcoholbutton);
        showPopUpButton6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showPopUp3();
            }
        });
        Button showPopUpButton7 = (Button) view.findViewById(R.id.treatsbutton);
        showPopUpButton7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showPopUp3();
            }
        });

        return view;
    }
    private void showPopUp3() {

        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(getActivity());
        helpBuilder.setTitle("Portion Size");
        helpBuilder.setMessage("Select food portion eaten:");

        LayoutInflater inflater = getActivity().getLayoutInflater();
        radioLayout = inflater.inflate(R.layout.portionlayout, null);
        helpBuilder.setView(radioLayout);
        final RadioGroup radioGroup = (RadioGroup) radioLayout.findViewById(R.id.radioGroup1);
        helpBuilder.setPositiveButton("Submit",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        int selectedId = radioGroup.getCheckedRadioButtonId();
                        final RadioButton  radioButton = (RadioButton) radioLayout.findViewById(selectedId);
                        Scanner userInput = new Scanner(radioButton.getText().toString());
                        int portion = userInput.nextInt();
                        mListener.sendPortionSize(portion);
                        dialog.dismiss();
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

}
