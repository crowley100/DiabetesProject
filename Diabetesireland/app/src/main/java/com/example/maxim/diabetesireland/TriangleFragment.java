package com.example.maxim.diabetesireland;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akexorcist.roundcornerprogressbar.IconRoundCornerProgressBar;


public class TriangleFragment extends Fragment{
    View view;
    int portion=0;


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
        IconRoundCornerProgressBar carbProgress = (IconRoundCornerProgressBar) view.findViewById(R.id.carb_prog);
        IconRoundCornerProgressBar waterProgress = (IconRoundCornerProgressBar) view.findViewById(R.id.carb_prog);
        IconRoundCornerProgressBar fgProgress = (IconRoundCornerProgressBar) view.findViewById(R.id.carb_prog);
        IconRoundCornerProgressBar dairyProgress = (IconRoundCornerProgressBar) view.findViewById(R.id.carb_prog);
        IconRoundCornerProgressBar proteinProgress = (IconRoundCornerProgressBar) view.findViewById(R.id.carb_prog);
        IconRoundCornerProgressBar alcProgress = (IconRoundCornerProgressBar) view.findViewById(R.id.carb_prog);
        IconRoundCornerProgressBar oilProgress = (IconRoundCornerProgressBar) view.findViewById(R.id.carb_prog);
        IconRoundCornerProgressBar treatsProgress = (IconRoundCornerProgressBar) view.findViewById(R.id.carb_prog);

        return view;
    }
    public void setPortion(int portionSize) {
       portion += portionSize;
    }



}
