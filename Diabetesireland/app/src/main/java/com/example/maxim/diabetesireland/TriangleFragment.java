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
    int portion=0;
    IconRoundCornerProgressBar carbProgress,fgProgress,waterProgress,dairyProgress,proteinProgress,alcProgress,oilProgress,treatsProgress;
    float carb,fg,water,dairy,protein,alc,oil,treats;
    private SensorManager mSensorManager;
    private Sensor mStepCounterSensor;
    private Sensor mStepDetectorSensor;
    private TextView pedometer;


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
         waterProgress = (IconRoundCornerProgressBar) view.findViewById(R.id.water_prog);
         fgProgress = (IconRoundCornerProgressBar) view.findViewById(R.id.fg_prog);
         dairyProgress = (IconRoundCornerProgressBar) view.findViewById(R.id.dairy_prog);
         proteinProgress = (IconRoundCornerProgressBar) view.findViewById(R.id.protein_prog);
         alcProgress = (IconRoundCornerProgressBar) view.findViewById(R.id.alcohol_prog);
         oilProgress = (IconRoundCornerProgressBar) view.findViewById(R.id.oil_prog);
         treatsProgress = (IconRoundCornerProgressBar) view.findViewById(R.id.treats_prog);
        pedometer = (TextView) view.findViewById(R.id.step_number);
        mSensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        mStepCounterSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mStepDetectorSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        pedometer.setText(0+"");
        return view;
    }
    public void setPortion(int portionSize,String type) {
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

    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        float[] values = event.values;
        int value = -1;

        if (values.length > 0) {
            value = (int) event.values[0];
        }
        pedometer.setText(value + " ");
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
