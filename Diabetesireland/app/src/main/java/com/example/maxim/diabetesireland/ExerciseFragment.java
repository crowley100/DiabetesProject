package com.example.maxim.diabetesireland;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


/**
 * A placeholder fragment containing a simple view.
 */
public class ExerciseFragment extends Fragment {
    View view;
    private Spinner exerciseSpinner;
    private static final String[] exerciseList = {"Exercise", "Jogging", "Swimming", "Walking"};

    public static ExerciseFragment newInstance() {
        return new ExerciseFragment();
    }
    public ExerciseFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_exercise, container, false);
        // exerciseSpinner = (Spinner) view.findViewById(R.id.spinner);
        // ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
        //        android.R.layout.simple_spinner_item, exerciseList);
        // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //exerciseSpinner.setAdapter(adapter);
        return view;
    }
}
