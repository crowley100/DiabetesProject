package com.example.maxim.diabetesireland;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class WeeklyUpdateFragment extends Fragment {
    View view;
    //FETCH Steps of the week from DATABASE
    double goalsReached=0,allowances=0;
    boolean stepsSet = true, carbsSet = false;
    int averageVar=0;
    public static Button steps, carbs;
    TextView average,thisWeek,goals;
    GraphView graphView;
    int [] weeklySteps;
    double [] weeklyCarbs;
    DatabaseHelper mydb;
    float carbLatest = 0;
    int stepsLatest=0;
    BarGraphSeries<DataPoint> stepSeries;
    BarGraphSeries<DataPoint>  carbSeries;
    DataPoint[] stepsTaken = new DataPoint[8],carbsConsumed = new DataPoint[8];

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
        double [] x_axis={0.15,0.64,1.16,1.74,2.34,2.9,3.35};

        weeklySteps = mydb.getWeeklySteps();
        weeklyCarbs = mydb.getWeeklyCarbs();
        //ARRAY FOR GRAPHS (Last datapoint is for padding)

            if(carbLatest == 0 || stepsLatest ==0) {
                for(int i=0;i<7;i++) {
                    stepsTaken[i] = new DataPoint(x_axis[i], weeklySteps[i]);
                    carbsConsumed[i] = new DataPoint(x_axis[i], weeklyCarbs[i]);
                }
            }
            else {
                for(int i=0;i<6;i++) {
                    stepsTaken[i] = new DataPoint(x_axis[i], weeklySteps[i]);
                    carbsConsumed[i] = new DataPoint(x_axis[i], weeklyCarbs[i]);
                }
                stepsTaken[6]=new DataPoint(x_axis[6],stepsLatest);
                carbsConsumed[6]=new DataPoint(x_axis[6],carbLatest);
            }
        stepsTaken[7]=new DataPoint(5.1,0);
        carbsConsumed[7]=new DataPoint(5.1,0);

        graphView = (GraphView) view.findViewById(R.id.graph);
        steps = (Button) view.findViewById(R.id.stepButton);
        carbs = (Button) view.findViewById(R.id.carbButton);
        goals = (TextView) view.findViewById(R.id.goals);
        average =(TextView)view.findViewById(R.id.average);
        thisWeek=(TextView)view.findViewById(R.id.thisWeek);
        String [] labels = createLabels();
        allowances=(averageVar*10)-sumAll(weeklyCarbs[0],weeklyCarbs[1],weeklyCarbs[2],weeklyCarbs[3],weeklyCarbs[4],weeklyCarbs[5],weeklyCarbs[6]);
        goals.setText("Goals Reached\n" + goalsReached);
        goals.setTextSize(18);
        average.setText("Average Steps Per Day\n " + getAverage(weeklySteps[0],weeklySteps[1],weeklySteps[2],weeklySteps[3],weeklySteps[4],weeklySteps[5],weeklySteps[6]));
        average.setTextSize(18);
        thisWeek.setText("Steps This Week\n " + sumAll(weeklySteps[0],weeklySteps[1],weeklySteps[2],weeklySteps[3],weeklySteps[4],weeklySteps[5],weeklySteps[6]));
        thisWeek.setTextSize(18);
        //GRAPHVIEW FOR STEPS
        stepSeries = new BarGraphSeries<DataPoint>(stepsTaken);
        graphView.addSeries(stepSeries);
        graphView.setTitle("         Weekly Report");
        stepSeries.setSpacing(50);
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graphView);
        staticLabelsFormatter.setHorizontalLabels(labels);
        graphView.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        graphView.getGridLabelRenderer().setPadding(50);
        graphView.getGridLabelRenderer().setVerticalAxisTitle("Steps");
        graphView.getGridLabelRenderer().setHorizontalAxisTitle("                    Days");
        graphView.getViewport().setMinX(0);
        graphView.getViewport().setMaxX(3.5);
        graphView.getViewport().setXAxisBoundsManual(true);
        //GRAPHVIEW FOR CARBS
        carbSeries = new BarGraphSeries<DataPoint>(carbsConsumed);

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
                    goals.setText("Goals Reached\n"+ goalsReached);
                    goals.setTextSize(18);
                    average.setText("Average Steps Per Day\n"+getAverage(weeklySteps[0],weeklySteps[1],weeklySteps[2],weeklySteps[3],weeklySteps[4],weeklySteps[5],weeklySteps[6]));
                    average.setTextSize(18);
                    thisWeek.setText("Steps This Week\n" + sumAll(weeklySteps[0],weeklySteps[1],weeklySteps[2],weeklySteps[3],weeklySteps[4],weeklySteps[5],weeklySteps[6]));
                    thisWeek.setTextSize(18);

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
                    goals.setText("Total Carbohydrates Allowances For The Week:\n" + allowances);
                    goals.setTextSize(18);
                    average.setText("Average Carbohydrate Portion Per Day:\n" +getAverage(weeklyCarbs[0],weeklyCarbs[1],weeklyCarbs[2],weeklyCarbs[3],weeklyCarbs[4],weeklyCarbs[5],weeklyCarbs[6]));
                    average.setTextSize(18);
                    thisWeek.setText("Total Portion of Carbohydrates This Week:\n" + sumAll(weeklyCarbs[0],weeklyCarbs[1],weeklyCarbs[2],weeklyCarbs[3],weeklyCarbs[4],weeklyCarbs[5],weeklyCarbs[6]));
                    thisWeek.setTextSize(18);
                }
            }
        }));
        return view;
    }

    public String[] createLabels(){
      //
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String [] labels=new String[7];
        // 3 letter name form of the day
        String day= (new SimpleDateFormat("EE", Locale.ENGLISH).format(date.getTime()));
        switch(day){
            case "Sun":
                averageVar=1;
                if(weeklySteps[0] >= 10000 ){
                    goalsReached=averageVar;
                }else {}

                labels[0]="Mon";
                labels[1]="   Tues";
                labels[2]="Wed";
                labels[3]="Thur";
                labels[4]=" Fri";
                labels[5]="Sat";
                labels[6]="Sun";
                break;
            case "Mon":
                averageVar=2;
                if(weeklySteps[1] >= 10000 ){
                    goalsReached=averageVar;
                }else {}

                labels[6]="   Mon";
                labels[0]="Tues";
                labels[1]="    Wed";
                labels[2]="  Thur";
                labels[3]="Fri";
                labels[4]=" Sat";
                labels[5]="Sun";
                break;
            case "Tue":
                averageVar=3;
                if(weeklySteps[2] >= 10000 ){
                    goalsReached=averageVar;
                }else {}

                labels[5]="Mon";
                labels[6]="Tues";
                labels[0]="Wed";
                labels[1]="    Thur";
                labels[2]="Fri";
                labels[3]="Sat";
                labels[4]="Sun";
                break;
            case "Wed":
                averageVar=4;
                if(weeklySteps[3] >= 10000 ){
                    goalsReached=averageVar;
                }else {}

                labels[4]="Mon";
                labels[5]="Tues";
                labels[6]=" Wed";
                labels[0]="Thur";
                labels[1]="    Fri";
                labels[2]="Sat";
                labels[3]="Sun";
                break;
            case "Thu":
                averageVar=5;
                if(weeklySteps[4] >= 10000 ){
                    goalsReached=averageVar;
                }else {}

                labels[3]="Mon";
                labels[4]="Tues";
                labels[5]="Wed";
                labels[6]=" Thur";
                labels[0]="Fri";
                labels[1]="   Sat";
                labels[2]="Sun";
                break;
            case "Fri":
                averageVar=6;
                if(weeklySteps[5] >= 10000 ){
                    goalsReached=averageVar;
                }else {}

                labels[2]="Mon";
                labels[3]="Tues";
                labels[4]="Wed";
                labels[5]="Thur";
                labels[6]="Fri";
                labels[0]="Sat";
                labels[1]="  Sun";
                break;
            case "Sat":
                averageVar=7;
                if(weeklySteps[6] >= 10000 ){
                    goalsReached=averageVar;
                }else {}

                labels[1]="    Mon";
                labels[2]="Tues";
                labels[3]="Wed";
                labels[4]="Thur";
                labels[5]="Fri";
                labels[6]="Sat";
                labels[0]="Sun";
                break;
        }
        return labels;
    }

    public double getAverage(double sun,double mon,double tue,double wed, double thu ,double fri, double sat){
        if(averageVar!=0) {
            double sum = sun + mon + tue + wed + thu + fri + sat;
            return Math.round(sum / averageVar);
        }
        else {
            return 0;
        }
    }

     public double sumAll(double sun,double mon,double tue,double wed, double thu ,double fri, double sat){
        double sum= sun+mon+tue+wed+thu+fri+sat;
        return sum;
    }

    public void setGraphs(float portion,int steps){
        if(weeklyCarbs!=null && weeklySteps != null) {
            weeklySteps[6] = steps;
            weeklyCarbs[6] = portion;
        }
        else {

            stepsTaken[6] = new DataPoint(3.35,steps);
            //CARBS CONSUMED ARRAY FOR GRAPH (Last datapoint is for padding)
            carbsConsumed[6] =new DataPoint(3.35,portion);

        }
    }

}
