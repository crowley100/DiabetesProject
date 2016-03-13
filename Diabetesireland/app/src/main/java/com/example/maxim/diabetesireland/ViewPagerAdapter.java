package com.example.maxim.diabetesireland;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private String [] titles ={"Profile","Exercise","Home","Diet","Graphs"};
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Fragment getItem(int position) {
     switch(position){
         case 0:
             return new ProfileFragment();
         case 1:
             return new ExerciseFragment();
         case 2:
             return new TriangleFragment();
         case 3:
             return new FoodIntakeFragment();
         case 4:
             return new WeeklyUpdateFragment();
         default:
             return new TriangleFragment();
     }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

}