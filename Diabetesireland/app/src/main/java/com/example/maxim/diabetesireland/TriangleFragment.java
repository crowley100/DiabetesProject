package com.example.maxim.diabetesireland;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.security.keystore.UserNotAuthenticatedException;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.maxim.diabetesireland.PedometerService;

import com.akexorcist.roundcornerprogressbar.IconRoundCornerProgressBar;
import com.github.glomadrian.dashedcircularprogress.DashedCircularProgress;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class TriangleFragment extends Fragment implements SensorEventListener {
    View view;
    IconRoundCornerProgressBar carbProgress,fgProgress,waterProgress,dairyProgress,proteinProgress,alcProgress,oilProgress,treatsProgress;
    float carb=0;float fg=0;float water=0;float dairy=0;float protein=0; float alc=0; float oil=0; float treats =0;
    SensorManager mSensorManager;
    Sensor mStepCounterSensor;
    boolean mBound=false;
    private TextView pedometer;

    private DashedCircularProgress dashedCircularProgress;
    int steps = 0;
    DatabaseHelper mydb;
    float [] foodAndDrink;

    private triangleFragmentListener mListener;
    public static TriangleFragment newInstance() {
        return new TriangleFragment();
    }
    public TriangleFragment() {
    }



    public interface triangleFragmentListener {
        void sendGraphData(float carbPortions,int totalSteps);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (triangleFragmentListener)context;
        }catch (ClassCastException e){

        }

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        mStepCounterSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mydb = new DatabaseHelper(getActivity());
         foodAndDrink= mydb.fetchFoodDrink();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.UK).format(new Date());
        if (!(mydb.recordExists(date))){
            mydb.initDailyData();
        }
        view = inflater.inflate(R.layout.fragment_triangle, container, false);
        pedometer = (TextView) view.findViewById(R.id.step_number);
        steps = mydb.getSteps();
        dashedCircularProgress = (DashedCircularProgress) view.findViewById(R.id.circleView);
        dashedCircularProgress.setOnValueChangeListener(
                new DashedCircularProgress.OnValueChangeListener() {
                    @Override
                    public void onValueChange(float value) {
                        pedometer.setText((int) steps + "");

                    }
                });

        carbProgress = (IconRoundCornerProgressBar) view.findViewById(R.id.carb_prog);

        if((mydb.fetchUserGender()).equals("Male")){
            carbProgress.setMax(6);
            alcProgress.setMax(5);
        }
        else{
            carbProgress.setMax(5);
            alcProgress.setMax(3);
        }
        waterProgress = (IconRoundCornerProgressBar) view.findViewById(R.id.water_prog);
        waterProgress.setMax(10);

        fgProgress = (IconRoundCornerProgressBar) view.findViewById(R.id.fg_prog);
        fgProgress.setMax(6);

        dairyProgress = (IconRoundCornerProgressBar) view.findViewById(R.id.dairy_prog);
        dairyProgress.setMax(4);

        proteinProgress = (IconRoundCornerProgressBar) view.findViewById(R.id.protein_prog);
        proteinProgress.setMax(2);

        alcProgress = (IconRoundCornerProgressBar) view.findViewById(R.id.alcohol_prog);

        oilProgress = (IconRoundCornerProgressBar) view.findViewById(R.id.oil_prog);
        oilProgress.setMax(2);

        treatsProgress = (IconRoundCornerProgressBar) view.findViewById(R.id.treats_prog);
        treatsProgress.setMax(2);

        if(carbProgress.getProgress()<carbProgress.getMax()) {
            carbProgress.setProgressColor(Color.parseColor("#FFBB33"));
        }
        else{
            carbProgress.setProgressColor(Color.parseColor("#ff0000"));
        }
        carb = foodAndDrink[5];
        carbProgress.setProgress(carb);

        water = foodAndDrink[5];
        waterProgress.setProgress(water);

        fg = foodAndDrink[3];
        fgProgress.setProgress(fg);

        dairy = foodAndDrink[2];
        dairyProgress.setProgress(dairy);

        protein = foodAndDrink[1];
        proteinProgress.setProgress(protein);

        alc=foodAndDrink[7];
        alcProgress.setProgress(alc);

        oil = foodAndDrink[4];
        oilProgress.setProgress(oil);

        treats = foodAndDrink[6];
        return view;
    }

    private void animate() {
        if(steps > 7500){
            dashedCircularProgress.setValue(steps);
            carbProgress.setMax(carbProgress.getMax() + 1);
        }
        dashedCircularProgress.setValue(steps);
    }

      public void onAccuracyChanged(Sensor sensor, int accuracy) {}
    private float   mLimit = 10;
    private float   mLastValues[] = new float[3*2];
    private float   mScale[] = new float[2];
    private float   mYOffset;

    private float   mLastDirections[] = new float[3*2];
    private float   mLastExtremes[][] = { new float[3*2], new float[3*2] };
    private float   mLastDiff[] = new float[3*2];
    private int     mLastMatch = -1;

    public void onSensorChanged(SensorEvent event) {
        synchronized (this) {
            int h = 480;
            mYOffset = h * 0.5f;
            mScale[0] = -(h * 0.5f * (1.0f / (SensorManager.STANDARD_GRAVITY * 2)));
            mScale[1] = -(h * 0.5f * (1.0f / (SensorManager.MAGNETIC_FIELD_EARTH_MAX)));
            synchronized (this) {
                if (mStepCounterSensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                } else {
                    int j = (mStepCounterSensor.getType() == Sensor.TYPE_ACCELEROMETER) ? 1 : 0;
                    if (j == 1) {
                        float vSum = 0;
                        for (int i = 0; i < 3; i++) {
                            final float v = mYOffset + event.values[i] * mScale[j];
                            vSum += v;
                        }
                        int k = 0;
                        float v = vSum / 3;

                        float direction = (v > mLastValues[k] ? 1 : (v < mLastValues[k] ? -1 : 0));
                        if (direction == -mLastDirections[k]) {
                            // Direction changed
                            int extType = (direction > 0 ? 0 : 1);
                            mLastExtremes[extType][k] = mLastValues[k];
                            float diff = Math.abs(mLastExtremes[extType][k] - mLastExtremes[1 - extType][k]);

                            if (diff > mLimit) {

                                boolean isAlmostAsLargeAsPrevious = diff > (mLastDiff[k] * 2 / 3);
                                boolean isPreviousLargeEnough = mLastDiff[k] > (diff / 3);
                                boolean isNotContra = (mLastMatch != 1 - extType);

                                if (isAlmostAsLargeAsPrevious && isPreviousLargeEnough && isNotContra) {
                                    steps++;
                                    mydb.stepsUpdate(steps);
                                    mListener.sendGraphData(carb,steps);
                                    pedometer.setText("" + steps);
                                    mLastMatch = extType;
                                } else {
                                    mLastMatch = -1;
                                }
                            }
                            mLastDiff[k] = diff;
                        }
                        mLastDirections[k] = direction;
                        mLastValues[k] = v;
                    }
                }
            }
        }
    }



    public void onResume() {

        super.onResume();
        mSensorManager.registerListener(this, mStepCounterSensor,
                SensorManager.SENSOR_DELAY_UI);
        pedometer.setText("" + steps);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    public void setPortion(float portionSize,String type) {
        switch (type){
            case "Water":
                water += portionSize;
                waterProgress.setProgress(water);
                break;
            case "Fruit and Veg":
                fg+= portionSize;
                fgProgress.setProgress(fg);
                break;
            case "Carbohydrates":
                carb+=portionSize;
                if(carbProgress.getProgress()<carbProgress.getMax()) {
                    carbProgress.setProgressColor(Color.parseColor("#FFBB33"));
                }
                else{
                    carbProgress.setProgressColor(Color.parseColor("#ff0000"));
                }
                carbProgress.setProgress(carb);
                break;
            case "Dairy":
                dairy+=portionSize;
                dairyProgress.setProgress(dairy);
                break;
            case "Protein":
                protein+=portionSize;
                proteinProgress.setProgress(protein);
                break;
            case "Alcohol":
                alc+=portionSize;
                alcProgress.setProgress(alc);
                break;
            case "Fats":
                oil+=portionSize;
                oilProgress.setProgress(oil);
                break;
            case "Treats":
                treats+=portionSize;
                treatsProgress.setProgress(treats);
                break;
                   }
          }

    public void setPedometerText(int serviceSteps){
        steps+=serviceSteps;
    }

}
