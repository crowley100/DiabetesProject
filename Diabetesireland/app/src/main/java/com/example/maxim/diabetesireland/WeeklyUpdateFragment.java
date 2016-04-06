package com.example.maxim.diabetesireland;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


public class WeeklyUpdateFragment extends Fragment {
    View view;
    //FETCH Steps of the week from DATABASE
    int sundaySteps=2000,mondaySteps=5000,tuesdaySteps=9000,wedSteps=8000,thursSteps=5000,fridaySteps=7000,saturSteps=2000;
    DatabaseHelper mydb;
    boolean stepsSet = true, carbsSet = false;
    public static Button steps, carbs;
    GraphView graphView;
    BarGraphSeries<DataPoint> stepSeries;
    LineGraphSeries<DataPoint>  carbSeries;

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
        mydb = new DatabaseHelper(getActivity());
        view =inflater.inflate(R.layout.fragment_weekly_update, container, false);
        graphView = (GraphView) view.findViewById(R.id.graph);
        steps = (Button) view.findViewById(R.id.stepButton);
        carbs = (Button) view.findViewById(R.id.carbButton);
        int [] weeklySteps = mydb.getWeeklySteps();
        double [] weeklyCarbs = mydb.getWeeklyCarbs();

        //STEPS TAKEN ARRAY FOR GRAPH (Last datapoint is for padding)
        DataPoint[] stepsTaken = {new DataPoint(0.15,weeklySteps[0]), new DataPoint(0.64,weeklySteps[1]),
                new DataPoint(1.16,weeklySteps[2]), new DataPoint(1.74,weeklySteps[3]),
                new DataPoint(2.34,weeklySteps[4]), new DataPoint(2.9,weeklySteps[5]),
                new DataPoint(3.35,weeklySteps[6]),new DataPoint(5.1,0)};
        //CARBS CONSUMED ARRAY FOR GRAPH
        DataPoint[] carbsConsumed = {new DataPoint(0,weeklyCarbs[0]), new DataPoint(1,weeklyCarbs[1]), new DataPoint(2,weeklyCarbs[2]),
                new DataPoint(3,weeklyCarbs[3]), new DataPoint(4,weeklyCarbs[4]), new DataPoint(5,weeklyCarbs[5]), new DataPoint(6,weeklyCarbs[6])};

        //GRAPHVIEW FOR STEPS
        stepSeries = new BarGraphSeries<DataPoint>(stepsTaken);
        graphView.addSeries(stepSeries);
        graphView.setTitle("         Weekly Report");
        stepSeries.setSpacing(50);
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graphView);
        staticLabelsFormatter.setHorizontalLabels(new String[] {"Sun","   Mon","Tues","Wed","Thurs","Fri","Sat"});
        graphView.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        graphView.getGridLabelRenderer().setPadding(50);
        graphView.getGridLabelRenderer().setVerticalAxisTitle("Steps");
        graphView.getGridLabelRenderer().setHorizontalAxisTitle("                    Days");
        graphView.getViewport().setMinX(0);
        graphView.getViewport().setMaxX(3.5);
        graphView.getViewport().setXAxisBoundsManual(true);
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
                    graphView.getGridLabelRenderer().setHorizontalAxisTitle("                    Days");
                    graphView.removeAllSeries();
                    graphView.addSeries(stepSeries);
                    stepSeries.setSpacing(50);
                    //stepSeries.setSpacing(50);

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
                    graphView.getGridLabelRenderer().setHorizontalAxisTitle("            Days");
                    graphView.removeAllSeries();
                    graphView.addSeries(carbSeries);
                }
            }
        }));
        return view;
    }
}
