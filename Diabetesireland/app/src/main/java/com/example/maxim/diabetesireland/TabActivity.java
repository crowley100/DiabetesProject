package com.example.maxim.diabetesireland;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

public class TabActivity extends AppCompatActivity implements FoodIntakeFragment.foodIntakeFragmentListener,TriangleFragment.triangleFragmentListener{
    int defaultval=2;
    private  ViewPagerAdapter adapter;
    int portionSize=0;
    int steps=0;
    private ServiceConnection mConnection;
    PedometerService mService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        Intent myIntent = getIntent(); // gets the previously created intent

        int position= myIntent.getIntExtra("position",defaultval);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        adapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setTabsFromPagerAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(position);
        mConnection = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName className,
                                           IBinder service) {
                // bind service and cast binder
               PedometerService.LocalBinder binder= (PedometerService.LocalBinder) service;
                TabActivity.this.mService =binder.getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName arg0) {

            }
        };
        Intent intent = new Intent(this, PedometerService.class);
        intent.putExtra("steps", steps);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    public void sendPortionSize(float portionSize,String type) {
                TriangleFragment frag = (TriangleFragment) adapter.getItem(2);
               frag.setPortion(portionSize, type);
    }

    public void sendGraphData(float carbPortion,int steps){
        WeeklyUpdateFragment frag = (WeeklyUpdateFragment) adapter.getItem(4);
        frag.setGraphs(carbPortion, steps);
    }
    public void checkSteps(){
        if(steps<10000) {
            steps+=mService.sendSteps();
        }else{
          //  unbindService(mConnection);
        }
    }

    public void sendProcessSteps(int steps){
        TriangleFragment frag = (TriangleFragment) adapter.getItem(2);
        frag.setPedometerText(steps);
    }
}
