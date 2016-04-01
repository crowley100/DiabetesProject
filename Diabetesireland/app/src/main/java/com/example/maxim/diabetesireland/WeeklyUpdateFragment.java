package com.example.maxim.diabetesireland;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


public class WeeklyUpdateFragment extends Fragment {
    View view;
    boolean stepsSet = true, carbsSet = false;
    public static Button steps, carbs;
    GraphView graphView;
    LineGraphSeries<DataPoint> stepSeries, carbSeries;
    //STEPS TAKEN ARRAY FOR GRAPH
    DataPoint[] stepsTaken = {new DataPoint(0,1), new DataPoint(1,5), new DataPoint(2,3), new DataPoint(3,2), new DataPoint(4,6), new DataPoint(5,6), new DataPoint(6,6)};
    //CARBS CONSUMED ARRAY FOR GRAPH
    DataPoint[] carbsConsumed = {new DataPoint(0,7), new DataPoint(1,6), new DataPoint(2,5), new DataPoint(3,4), new DataPoint(4,3), new DataPoint(5,2), new DataPoint(6,1)};

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
        graphView = (GraphView) view.findViewById(R.id.graph);
        steps = (Button) view.findViewById(R.id.stepButton);
        carbs = (Button) view.findViewById(R.id.carbButton);


        //GRAPHVIEW FOR STEPS
        stepSeries = new LineGraphSeries<DataPoint>(stepsTaken);
        graphView.addSeries(stepSeries);
        graphView.setTitle("WeeklyReport");
        graphView.getGridLabelRenderer().setVerticalAxisTitle("Steps");
        graphView.getGridLabelRenderer().setHorizontalAxisTitle("Days");
        //GRAPHVIEW FOR CARBS
        carbSeries = new LineGraphSeries<DataPoint>(carbsConsumed);

        steps.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!stepsSet) {
                    stepsSet = true;
                    carbsSet = false;
                    steps.setBackgroundColor(0xff3F1451);
                    steps.setTextColor(0xffffffff);
                    carbs.setBackgroundColor(0xffdbdbdb);
                    carbs.setTextColor(0xffffffff);
                    graphView.getGridLabelRenderer().setVerticalAxisTitle("Steps");
                    graphView.removeAllSeries();
                    graphView.addSeries(stepSeries);
                }
            }
        }));
        carbs.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!carbsSet)
                {
                    carbsSet = true;
                    stepsSet = false;
                    carbs.setBackgroundColor(0xff3F1451);
                    carbs.setTextColor(0xffffffff);
                    steps.setBackgroundColor(0xffdbdbdb);
                    steps.setTextColor(0xffffffff);
                    graphView.getGridLabelRenderer().setVerticalAxisTitle("Carbohydrates");
                    graphView.removeAllSeries();
                    graphView.addSeries(carbSeries);
                }
            }
        }));
        return view;
    }
}
