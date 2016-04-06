package com.example.maxim.diabetesireland;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.IconRoundCornerProgressBar;
import com.github.glomadrian.dashedcircularprogress.DashedCircularProgress;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class TriangleFragment extends Fragment implements SensorEventListener {
    View view;
    DatabaseHelper mydb;
    IconRoundCornerProgressBar carbProgress,fgProgress,waterProgress,dairyProgress,proteinProgress,alcProgress,oilProgress,treatsProgress;
    float carb=0;float fg=0;float water=0;float dairy=0;float protein=0; float alc=0; float oil=0; float treats =0;
    SensorManager mSensorManager;
    Sensor mStepCounterSensor;
    private Sensor mStepDetectorSensor;
    private TextView pedometer;
    private DashedCircularProgress dashedCircularProgress;
    int steps = 0;


    public static TriangleFragment newInstance() {
        return new TriangleFragment();
    }
    public TriangleFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        mStepCounterSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mydb = new DatabaseHelper(getActivity());
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.UK).format(new Date());
        if (!(mydb.recordExists(date))){
            mydb.initDailyData();
        }
        view = inflater.inflate(R.layout.fragment_triangle, container, false);
        float [] foodAndDrink = mydb.fetchFoodDrink();
        pedometer = (TextView) view.findViewById(R.id.step_number);

        // FETCH steps count from DATABASE
        steps = mydb.getSteps();
        dashedCircularProgress = (DashedCircularProgress) view.findViewById(R.id.circleView);
        animate();
        dashedCircularProgress.setOnValueChangeListener(
                new DashedCircularProgress.OnValueChangeListener() {
                    @Override
                    public void onValueChange(float value) {
                        pedometer.setText((int) steps + "");

                    }
                });

        carbProgress = (IconRoundCornerProgressBar) view.findViewById(R.id.carb_prog);
        //FETCH carb count from DATABASE
        carb = foodAndDrink[4];
        carbProgress.setProgress(carb);
         waterProgress = (IconRoundCornerProgressBar) view.findViewById(R.id.water_prog);
        //FETCH water count from DATABASE
        water = foodAndDrink[6];
        waterProgress.setProgress(water);
         fgProgress = (IconRoundCornerProgressBar) view.findViewById(R.id.fg_prog);
        //FETCH fruit and veg count from DATABASE
        fg = foodAndDrink[3];
        fgProgress.setProgress(fg);
         dairyProgress = (IconRoundCornerProgressBar) view.findViewById(R.id.dairy_prog);
        //FETCH dairy count from DATABASE
        dairy = foodAndDrink[2];
        dairyProgress.setProgress(dairy);
         proteinProgress = (IconRoundCornerProgressBar) view.findViewById(R.id.protein_prog);
        //FETCH protein count from DATABASE
        protein = foodAndDrink[1];
        proteinProgress.setProgress(protein);
         alcProgress = (IconRoundCornerProgressBar) view.findViewById(R.id.alcohol_prog);
        //FETCH alcohol count from DATABASE
        oil = foodAndDrink[5];
        alcProgress.setProgress(alc);
         oilProgress = (IconRoundCornerProgressBar) view.findViewById(R.id.oil_prog);
        //FETCH oil count from DATABASE
        carb = foodAndDrink[0];
        if(oil >0) {
            oilProgress.setMax(oil);
        }
        oilProgress.setProgress(oil);
         treatsProgress = (IconRoundCornerProgressBar) view.findViewById(R.id.treats_prog);
        //FETCH treats count from DATABASE
        treats = foodAndDrink[7];
        if(treats >0) {
            treatsProgress.setMax(treats);
        }
        treatsProgress.setProgress(treats);

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

    public void onSensorChanged(SensorEvent event) {
        synchronized (this) {

            if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) {
                return;
            }

            final float x = event.values[0];
            final float y = event.values[1];
            final float z = event.values[2];
            final float g = Math.abs((x * x + y * y + z * z))/ (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);

            if (g >= 2) {
                steps++;
                mydb.stepsUpdate(steps);
                pedometer.setText("" + steps);
                animate();
            }

        }
    }

    public void onResume() {

        super.onResume();
        mSensorManager.registerListener(this, mStepCounterSensor,
                SensorManager.SENSOR_DELAY_UI);
        pedometer.setText(""+ steps);
    }

    public void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this, mStepCounterSensor);
    }


}
