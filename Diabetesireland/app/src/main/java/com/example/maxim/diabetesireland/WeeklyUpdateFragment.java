package com.example.maxim.diabetesireland;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class WeeklyUpdateFragment extends Fragment {
    View view;
    public static WeeklyUpdateFragment newInstance() {
        return new WeeklyUpdateFragment();
    }
    public WeeklyUpdateFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_weekly_update, container, false);
        return view;
    }
}
