package com.example.maxim.diabetesireland;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.IconRoundCornerProgressBar;


public class TriangleFragment extends Fragment implements SensorEventListener {
    View view;
    IconRoundCornerProgressBar carbProgress,fgProgress,waterProgress,dairyProgress,proteinProgress,alcProgress,oilProgress,treatsProgress;
    float carb=0;float fg=0;float water=0;float dairy=0;float protein=0; float alc=0; float oil=0; float treats =0;
    private SensorManager mSensorManager;
    private Sensor mStepCounterSensor;
    private Sensor mStepDetectorSensor;
    private TextView pedometer;
    int steps = 0;


    public static TriangleFragment newInstance() {
        return new TriangleFragment();
    }
    public TriangleFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_triangle, container, false);
         carbProgress = (IconRoundCornerProgressBar) view.findViewById(R.id.carb_prog);
        //FETCH carb count from DATABASE
        carbProgress.setProgress(carb);
         waterProgress = (IconRoundCornerProgressBar) view.findViewById(R.id.water_prog);
        //FETCH water count from DATABASE
        waterProgress.setProgress(water);
         fgProgress = (IconRoundCornerProgressBar) view.findViewById(R.id.fg_prog);
        //FETCH fruit and veg count from DATABASE
        fgProgress.setProgress(fg);
         dairyProgress = (IconRoundCornerProgressBar) view.findViewById(R.id.dairy_prog);
        //FETCH dairy count from DATABASE
        dairyProgress.setProgress(dairy);
         proteinProgress = (IconRoundCornerProgressBar) view.findViewById(R.id.protein_prog);
        //FETCH protein count from DATABASE
        proteinProgress.setProgress(protein);
         alcProgress = (IconRoundCornerProgressBar) view.findViewById(R.id.alcohol_prog);
        //FETCH alcohol count from DATABASE
        alcProgress.setProgress(alc);
         oilProgress = (IconRoundCornerProgressBar) view.findViewById(R.id.oil_prog);
        //FETCH oil count from DATABASE
        if(oil >0) {
            oilProgress.setMax(oil);
        }
        oilProgress.setProgress(oil);
         treatsProgress = (IconRoundCornerProgressBar) view.findViewById(R.id.treats_prog);
        //FETCH treats count from DATABASE
        if(treats >0) {
            treatsProgress.setMax(treats);
        }
        treatsProgress.setProgress(treats);
        pedometer = (TextView) view.findViewById(R.id.step_number);
        mSensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        mStepCounterSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mStepDetectorSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        //FETCH steps count from DATABASE
        pedometer.setText(steps+"");
        return view;
    }


    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        float[] values = event.values;

        if (values.length > 0) {
            // UPDATE steps TO DATABASE
            steps = (int) event.values[0];
        }

    }

    public void onResume() {

        super.onResume();

        mSensorManager.registerListener(this, mStepCounterSensor, SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(this, mStepDetectorSensor,SensorManager.SENSOR_DELAY_FASTEST);

    }

    public void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this, mStepCounterSensor);
        mSensorManager.unregisterListener(this, mStepDetectorSensor);
    }


}
