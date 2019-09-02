package com.example.myapplication;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Z1Fragment extends Fragment {

    public static Z1Fragment newInstance(String str){
        Z1Fragment z1=new Z1Fragment();

        Bundle barg = new Bundle();

        barg.putString("msg", str);

        z1.setArguments(barg);

        return z1;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.z1fragment,container, false);

        Bundle args = getArguments();

        if(args != null ){
            String str = args.getString("msg");
            TextView z1text = view.findViewById(R.id.z1txt);
            z1text.setText(str);
        }

        return view;
    }
}