package com.example.maxim.diabetesireland;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProfileFragment extends Fragment {
    View view;
    double age;
    double  db_weight,db_height;
    TextView age_text,bmi_text,gender_text;
    String gender,bmi;
    double [] user_data = new double [3];
    double profile_user_height,profile_user_weight=0;
    TextView weight_text,height_text;
    final String [] weight_metrics = {"kg","lbs","st and lbs"};
    final String [] height_metrics = {"cm","ft and in"};
    DatabaseHelper mydb;
    public static final int MAX_HEIGHT = 270,
            MIN_HEIGHT = 10,
            MAX_WEIGHT = 300,
            MIN_WEIGHT = 10;
    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mydb = new DatabaseHelper(getActivity());
        user_data = mydb.fetchUserData();
        view=inflater.inflate(R.layout.fragment_profile, container, false);
        weight_text = (TextView) view.findViewById(R.id.updated_weight);
        //FETCH WEIGHT FROM DATABASE
        db_weight = user_data[2];
        weight_text.setText("Weight: " +db_weight + " kg");
        height_text = (TextView) view.findViewById(R.id.updated_height);
        //FETCH HEIGHT FROM DATABASE
        db_height = user_data[1];
        height_text.setText("Height: "+db_height + " cm");
        age_text = (TextView) view.findViewById(R.id.age_text);
        //FETCH AGE FROM DATA BASE
        age = user_data[0];
        age_text.setText("Age: " + (int) age + " years old");
        bmi_text = (TextView) view.findViewById(R.id.bmi_text);
        profile_user_height=db_height;
        profile_user_weight=db_weight;
        bmi_text.setText(calcBMI());
        gender_text = (TextView) view.findViewById(R.id.gender_text);
        //FETCH GENDER TO DATABASE
        gender = mydb.fetchUserGender();
        gender_text.setText("Gender: " +gender);
        Button height_update = (Button) view.findViewById(R.id.height_update);
        Button weight_update = (Button) view.findViewById(R.id.weight_update);
        weight_update.setOnClickListener((new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String msg ="What is your weight?";
                showPopUp2(weight_metrics, msg);
            }
        }));
        height_update.setOnClickListener((new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String msg = "What is your height?";
                showPopUp2(height_metrics,msg);
            }
        }));
        return view;
    }

    private void showPopUp2(String [] spinner,String title) {

        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(getActivity());
        helpBuilder.setTitle(title);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.weight_height_layout, null);
        final EditText input = (EditText) view.findViewById(R.id.cmFt);
        final EditText input2 = (EditText) view.findViewById(R.id.inches);
        final Spinner metrics = (Spinner) view.findViewById(R.id.metrics_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        metrics.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if ((metrics.getSelectedItem().toString()).equals("cm") || (metrics.getSelectedItem().toString()).equals("ft and in")) {
                    if ((metrics.getSelectedItem().toString()).equals("ft and in")) {
                        input2.setVisibility(view.VISIBLE);
                        input.getLayoutParams().width = 200;
                        input2.getLayoutParams().width = 200;
                        input2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
                        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
                    } else {
                        input2.setVisibility(view.GONE);
                        input.getLayoutParams().width = 300;
                        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
                    }

                } else {
                    if ((metrics.getSelectedItem().toString()).equals("st and lbs")) {
                        input2.setVisibility(view.VISIBLE);
                        input.getLayoutParams().width = 200;
                        input2.getLayoutParams().width = 200;
                        input2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
                        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
                    } else {
                        input2.setVisibility(view.GONE);
                        input.getLayoutParams().width = 300;
                        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
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
                if (!(input.getText().toString()).matches("")) {
                    if ((metrics.getSelectedItem().toString()).equals("cm") || (metrics.getSelectedItem().toString()).equals("ft and in")) {
                        if ((metrics.getSelectedItem().toString()).equals("ft and in")) {
                            if (checkHeightValid(input, input2)) {
                                height_text.setText("Height: " + input.getText() + " ft " + input2.getText() + " in");
                                //ADD TO DATABASE
                                profile_user_height = getHeight(input, input2);
                                mydb.updateHeight(profile_user_weight);
                                bmi_text.setText(calcBMI());
                            } else {
                                CharSequence text = "Invalid Height";
                                Toast toast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                        } else {
                            if (checkHeightValid(input, input2)) {
                                height_text.setText("Height: "+ input.getText() + " cm");
                                //ADD TO DATABASE
                                profile_user_height = getHeight(input, input2);
                                mydb.updateHeight(profile_user_weight);
                                bmi_text.setText(calcBMI());
                            } else {
                                CharSequence text = "Invalid Height";
                                Toast toast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                        }
                    } else {
                        if ((metrics.getSelectedItem().toString()).equals("st and lbs")) {
                            if (checkWeightValid(input, input2, "st and lbs")) {
                                weight_text.setText("Weight: "+ input.getText() + " st " + input2.getText() + " lbs");
                                //ADD TO DATABASE
                                profile_user_weight = getWeight(input, input2, "st and lbs");
                                mydb.updateWeight(profile_user_weight);
                                bmi_text.setText(calcBMI());
                            } else {
                                CharSequence text = "Invalid Weight";
                                Toast toast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }

                        } else {
                            if ((metrics.getSelectedItem().toString()).equals("lbs")) {
                                if (checkWeightValid(input, input2, "lbs")) {
                                    weight_text.setText("Weight: "+ input.getText() + " lbs");
                                    //ADD TO DATABASE
                                    profile_user_weight = getWeight(input, input2, "lbs");
                                    mydb.updateWeight(profile_user_weight);
                                    bmi_text.setText(calcBMI());
                                } else {
                                    CharSequence text = "Invalid Weight";
                                    Toast toast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                }
                            } else {
                                if (checkWeightValid(input, input2, "kg")) {
                                    weight_text.setText("Weight: "+ input.getText() + " kg");
                                    //ADD TO DATABASE
                                    profile_user_weight = getWeight(input, input2, "kg");
                                    mydb.updateWeight(profile_user_weight);
                                    bmi_text.setText(calcBMI());
                                } else {
                                    CharSequence text = "Invalid Weight";
                                    Toast toast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                }
                            }
                        }
                    }
                } else {
                    dialog.dismiss();
                }
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

    public static boolean checkHeightValid(EditText first, EditText second){
        if((second.getText().toString()).equals("")){
            return (Double.parseDouble(first.getText().toString())) <= MAX_HEIGHT
                    && (Double.parseDouble(first.getText().toString())) >= MIN_HEIGHT;
        }
        else{
            double feet = (Double.parseDouble(first.getText().toString())) * 30.48;
            double inches = (Double.parseDouble(second.getText().toString())) * 2.54;
            return feet + inches <= MAX_HEIGHT && feet + inches >= MIN_HEIGHT;
        }
    }
    public static boolean checkWeightValid(EditText first, EditText second, String type){
        if((second.getText().toString()).equals("")) {
            if (type.equals("lbs")) {
                return ((Double.parseDouble(first.getText().toString())) * 0.453592) < MAX_WEIGHT
                        && ((Double.parseDouble(first.getText().toString())) * 0.453592) > MIN_WEIGHT;
            } else {
                return ((Double.parseDouble(first.getText().toString()))) < MAX_WEIGHT
                        && (Double.parseDouble(first.getText().toString())) > MIN_WEIGHT;
            }
        }
        else{
            double stoneNum = (Double.parseDouble(first.getText().toString())) * 6.35029;
            double pounds = (Double.parseDouble(second.getText().toString())) * 0.453592;
            double weight = stoneNum + pounds;
            return weight < MAX_WEIGHT && weight > MIN_WEIGHT;
        }

    }

    //Returns height in centimeters
    private double getHeight(EditText first,EditText second) {
        if ((second.getText().toString()).equals("")) {
            if ((first.getText().toString()).equals("")) {
                return 0;
            } else {
                // ADD TO DATABASE => height
                return(Double.parseDouble(first.getText().toString()));
            }
        } else {
            if (((first.getText().toString()).equals("")) && ((second.getText().toString()).equals(""))) {
                return 0;
            } else {
                double feet = (Double.parseDouble(first.getText().toString())) * 30.48;
                double inches = (Double.parseDouble(second.getText().toString())) * 2.54;
                // ADD TO DATABASE => height
                return (feet + inches);
            }
        }
    }
    //Returns weight in kilogram
    private double getWeight(EditText first,EditText second,String type){
        if((second.getText().toString()).equals("")) {
            if ((first.getText().toString()).equals("")) {
                return 0;
            } else {
                if (type.equals("lbs")) {
                    // ADD TO DATABASE => weight
                    return((Double.parseDouble(first.getText().toString())) * 0.453592);
                } else {
                    return((Double.parseDouble(first.getText().toString())));
                }

            }
        }
        else {
            if (((first.getText().toString()).equals("")) && ((second.getText().toString()).equals(""))) {
                return 0;
            } else {
                double stoneNum = (Double.parseDouble(first.getText().toString())) * 6.35029;
                double pounds = (Double.parseDouble(second.getText().toString())) * 0.453592;
                // ADD TO DATABASE => weight
                return(stoneNum + pounds);
            }
        }
    }
    private String calcBMI(){
        if(profile_user_weight!=0 && profile_user_height!=0) {
            double calc_bmi = (profile_user_weight) / ((profile_user_height * 0.01) * (profile_user_height * 0.01));
            if (calc_bmi < 18.5) {
                return "BMI: Underweight   " +(double) Math.round(calc_bmi * 100) / 100;
            } else if ((calc_bmi > 18.5) && (calc_bmi < 24.9)) {
                return "BMI: Healthy Weight   " + (double) Math.round(calc_bmi * 100) / 100;
            } else if ((calc_bmi > 25.0) && (calc_bmi < 29.9)) {
                return "BMI: OverWeight  " + (double) Math.round(calc_bmi * 100) / 100;
            } else {
                return "BMI: Obese   " + (double) Math.round(calc_bmi * 100) / 100;
            }
        }else{
            return "No Specified Weight or Height";
        }
    }
}
