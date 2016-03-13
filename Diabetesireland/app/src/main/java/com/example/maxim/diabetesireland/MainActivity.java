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
    Spinner heightSpinner,weightSpinner;
    String [] metrics = {"cm","ft"};
    String [] weightMetrics = {"kg","lbs"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        heightSpinner = (Spinner) findViewById(R.id.spinner2);
        weightSpinner = (Spinner) findViewById(R.id.spinner3);
        ArrayAdapter<String> heightAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, metrics);
        heightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        heightSpinner.setAdapter(heightAdapter);

        ArrayAdapter<String> weightAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, weightMetrics);
        weightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weightSpinner.setAdapter(weightAdapter);
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
            case R.id.spinner2:
                int index = heightSpinner.getSelectedItemPosition();
                double value = Double.parseDouble(height.getText().toString());
                if (index == 1){
                    height.addTextChangedListener(new CustomTextWatcher(height));
                }
                height.setText(value + "cm");
                break;
            case R.id.spinner3:
                int index2 = heightSpinner.getSelectedItemPosition();
                double value2 = Double.parseDouble(height.getText().toString());
                if (index2 == 1){
                    weight.setText(value2+"kgs");
                }
                weight.setText(value2 + "lbs");
                break;
        }
    }


    public double calcBMR(String gender,int age,int weight,int height){
        double BMR=0;
        if(gender.equals("Male")){
            BMR=(10*weight)+(6.25*height)-(5*age)+5;
        }
        BMR=(10*weight)+(6.25*height)-(5*age)-161;
        return BMR;
    }

}
class CustomTextWatcher implements TextWatcher {
    private EditText mEditText;

    public CustomTextWatcher(EditText e) {
        mEditText = e;
    }

    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    public void afterTextChanged(Editable s) {
        int count = s.length();
        String str = s.toString();
        if (count == 1) {
            str = str + "'";
        } else if (count == 3) {
            str = str + "\"";
        } else if ((count > 4) && (str.charAt(str.length() - 1) != '\"') ){
            str = str.substring(0, str.length() - 2) + str.charAt(str.length() - 1)
                    + "\"";
        } else {
            return;
        }
        mEditText.setText(str);
        mEditText.setSelection(mEditText.getText().length());
    }
}


