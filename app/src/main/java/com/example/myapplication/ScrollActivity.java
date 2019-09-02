package com.example.myapplication;

import java.util.ArrayList;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Switch;
import android.widget.Button;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import io.realm.RealmConfiguration;


public class ScrollActivity extends Activity  {

    ScrollView sv;
    LinearLayout scrin;

    private long DURATION=300;
    private int L0H=0;
    private int L1H=100;
    private int L2H=500;

    private SimpleDateFormat tfm = new SimpleDateFormat("HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);
        if (savedInstanceState == null) {

            sv = findViewById(R.id.scrollView);
            scrin = findViewById(R.id.scrinner);

            scrin.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    System.out.println("aaa");
                }
            });

            System.out.println(sv.getLayoutParams().height);

            L2H = sv.getLayoutParams().height;

            Realm.init(getApplicationContext());
            RealmConfiguration config = new RealmConfiguration.
                    Builder().
                    deleteRealmIfMigrationNeeded().
                    build();
            Realm realm = Realm.getInstance(config);

            try {
                ArrayList<TimerItem> ar = getTimerList(realm);
                createList(ar);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
            finally{
                realm.close();
            }
        }
    }

    private void createList(ArrayList<TimerItem> ar){

        for(int i=0;i<ar.size();i++){

            final int position=i;

            View view = View.inflate(this, R.layout.timeritem2,null);

            String tm = tfm.format(ar.get(i).getTm());

            TextView t1 = view.findViewById(R.id.time);
            t1.setText(tm);

            Switch sw = view.findViewById(R.id.sw);
            sw.setText(tm);

            final Button btn = view.findViewById(R.id.abtn);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chg((ViewGroup)v.getParent(),position);
                }
            });

            TextView t2 = view.findViewById(R.id.stgs);
            t2.setText(tm);

            Button chgbtn = view.findViewById(R.id.chgbtn);

            chgbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scrin.performClick();
                }
            });

            final LinearLayout ln1 = view.findViewById(R.id.ln1);
            L1H=ln1.getLayoutParams().height;

            LinearLayout ln2 = view.findViewById(R.id.ln2);
            ln2.getLayoutParams().height=0;

            if(i==0) {
                ViewTreeObserver btnvto = btn.getViewTreeObserver();
                btnvto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        L0H=btn.getHeight();
                    }
                });
            }

            scrin.addView(view);
        }



    }

    private void chg(ViewGroup vg,final int position){
        View c1 = vg.findViewById(R.id.ln1);
        View c2 = vg.findViewById(R.id.ln2);

        if(c2.getHeight()>0) {

            toggle(c1,L1H,0);
            toggle(c2,-c2.getHeight(),c2.getHeight());
        }
        else{
            Handler handler = new Handler();

            Runnable runnable = new Runnable(){
                @Override
                public void run(){
                    sv.smoothScrollTo(0, position*(L0H+L1H));
                }
            };

            toggle(c2,L2H-L0H,0);
            toggle(c1,-c1.getHeight(),c1.getHeight());

            handler.postDelayed(runnable,DURATION);

        }
    }

    private void toggle(View v,int val,int fromSize){
        ResizeAnimation collapseAnimation = new ResizeAnimation(v, val, fromSize);
        collapseAnimation.setDuration(DURATION);
        v.startAnimation(collapseAnimation);
    }

    private ArrayList<TimerItem> getTimerList(Realm realm){

        ArrayList<TimerItem> ar = new ArrayList<>();

        try{
            RealmResults<TimerSetting> tmlist = realm.where(TimerSetting.class).findAll().sort("tm", Sort.ASCENDING);

            for (int i=0; i<tmlist.size(); i++) {

                TimerItem ti=new TimerItem();

                ti.setId(tmlist.get(i).getId());
                ti.setTm(tmlist.get(i).getTm());
                ti.setIntrv(tmlist.get(i).getIntrv());
                ti.setEnabled(tmlist.get(i).isEnabled());

                ar.add(ti);
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        finally{
            realm.close();
        }
        return ar;
    }
}
