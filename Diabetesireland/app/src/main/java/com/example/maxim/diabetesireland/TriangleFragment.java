package com.example.maxim.diabetesireland;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.design.widget.TabLayout;
import android.widget.Button;

/**
 * A placeholder fragment containing a simple view.
 */
public class TriangleFragment extends Fragment{
    View view;
    public static TriangleFragment newInstance() {
        return new TriangleFragment();
    }
    public TriangleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_triangle, container, false);
        Button nextScreen = (Button) view.findViewById(R.id.button3);
        Button nextScreen1 = (Button) view.findViewById(R.id.button4);
        Button nextScreen2 = (Button) view.findViewById(R.id.button6);
        nextScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TabActivity.class);
                intent.putExtra("position",3);
                startActivity(intent);
            }
        });

        nextScreen1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getActivity(), TabActivity.class);
                intent.putExtra("position",4);
                startActivity(intent);
            }
        });

        nextScreen2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TabActivity.class);
                intent.putExtra("position",1);
                startActivity(intent);
            }
        });
        return view;
    }
}
