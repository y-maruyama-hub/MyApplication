package com.example.myapplication;

//import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import android.widget.Button;
//import android.app.TimePickerDialog;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Switch;
import android.os.Build;
import android.content.Intent;
import android.content.Context;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.Toast;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.content.DialogInterface;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.view.ViewGroup.LayoutParams;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import io.realm.RealmConfiguration;

import java.util.Calendar;


public class TestActivity extends AppCompatActivity  {

    Switch alarmSwitch;
    ListView lv;
    Z1Fragment z1=null;

    private long DURATION=300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        if (savedInstanceState == null) {

            lv = findViewById(R.id.lv);

            TimerAdapter ta = new TimerAdapter(this);

            Realm.init(getApplicationContext());
            RealmConfiguration config = new RealmConfiguration.
                    Builder().
                    deleteRealmIfMigrationNeeded().
                    build();
            Realm realm = Realm.getInstance(config);

            //Realm realm = Realm.getInstance(config);

            try {
                ArrayList<TimerItem> ar = getTimerList(realm);
                ta.setList(ar);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
            finally{
                realm.close();
            }

            lv.setAdapter(ta);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent,View view, final int position, long id){

                    final ListView listView = (ListView) parent;
                    TimerAdapter ta = (TimerAdapter) listView.getAdapter();

                    int chidx  = position-listView.getFirstVisiblePosition();

                    if(id==R.id.abtn) {

                       final View v = listView.getChildAt(chidx);
                        //View c = v.findViewWithTag("ln2");
                        View c1 = v.findViewById(R.id.ln1);
                        View c2 = v.findViewById(R.id.ln2);

                        Handler handler = new Handler();

                        Runnable runnable = new Runnable(){
                            @Override
                            public void run(){
                                //listView.setSelectionFromTop(position,0);
                                listView.smoothScrollToPositionFromTop(position, 0);
                            };
                        };

                        for(int i=0;i<listView.getCount();i++) {
                            listView.setSelection(i);
                            View ln1 = listView.getChildAt(0).findViewById(R.id.ln1);
                            View ln2 = listView.getChildAt(0).findViewById(R.id.ln2);

                            if(ln2.getHeight()>0){
                                toggle(ln1,ln1.getHeight(),0);
                                toggle(ln2,-ln2.getHeight(),ln2.getHeight());
                            }
                        }

                        if(c2.getHeight()>0) {
                            toggle(c1,ta.getC1h(),0);
                            toggle(c2,-c2.getHeight(),c2.getHeight());

                            handler.postDelayed(runnable,300);

                        }
                        else{
                            toggle(c2,ta.getC2h(),0);
                            toggle(c1,-c1.getHeight(),c1.getHeight());

                            handler.postDelayed(runnable,300);
                        }

                    }
                    else if(id==R.id.chgbtn) {
                        TimerItem ti = (TimerItem) listView.getItemAtPosition(position);

                        pop(ti);
                    }
                    else if(id==R.id.ln1){
                        View v = listView.getChildAt(chidx);
                        Switch sw = v.findViewById(R.id.sq);

                        TimerItem ti = (TimerItem) listView.getItemAtPosition(position);

                        changeTimerState(ti,!sw.isChecked());

                        sw.setChecked(!sw.isChecked());
                    }
                }
            });

            lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                @Override
                public boolean onItemLongClick(AdapterView<?> parent,View view, int position, long id){

                    if(id==R.id.ln1) {
                        TimerItem ti = (TimerItem) parent.getAdapter().getItem(position);

                        if (deleteTimer(ti.getId())) {
                            unregister(ti.getId());
                        }
                    }

                    return true;
                }
            });




            Button pcButton = findViewById(R.id.pc_button);
            pcButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Realm.init(getApplicationContext());
                    Realm realm = Realm.getDefaultInstance();

                    int tid=-1;

                    try {
                        realm.beginTransaction();
                        RealmResults<TimerSetting> tmlist = realm.where(TimerSetting.class).findAll().sort("id", Sort.DESCENDING);

                        int mxc=10;

                        if(tmlist.size()>=mxc) {
                            Toast.makeText(getApplicationContext(), "登録上限オーバーです。", Toast.LENGTH_LONG).show();
                        }
                        else {

                            if(tmlist.size() == 0)  tid=0;
                            else{
                                if(tmlist.first().getId()==mxc-1){

                                }
                                else    tid = tmlist.first().getId() + 1;
                            }

                            //tid = tmlist.size() == 0 ? 0 : tmlist.first().getId() + 1;

                            TimerItem ti = new TimerItem();
                            ti.setId(tid);
                            pop(ti);
                        }
                    }
                    catch(Exception ex) {
                        ex.printStackTrace();
                    }
                    finally {
                        realm.close();
                    }

                }
            });

            alarmSwitch = findViewById(R.id.alarm_switch);

            alarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    if (isChecked) {
                        z1=Z1Fragment.newInstance("Message");

                        fragmentTransaction.replace(R.id.container, z1);
                        fragmentTransaction.commit();
                    }
                    else{
                        fragmentTransaction.hide(z1);
                        fragmentTransaction.commit();
                    }
                }
            });


/*            Button z1btn = findViewById(R.id.z1_button);
            z1btn.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {

                }
            });
*/

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


    private void pop(final TimerItem ti){

        Calendar almcal= Calendar.getInstance();

        if(ti.getTm()!=null)    almcal.setTime(ti.getTm());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("My Time Picker Dialog");

        final TimePicker tp = new TimePicker(this);
        tp.setIs24HourView(true);
        tp.setCurrentHour(almcal.get(Calendar.HOUR_OF_DAY));
        tp.setCurrentMinute(almcal.get(Calendar.MINUTE));

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                int h = tp.getCurrentHour();
                int m = tp.getCurrentMinute();

                long alarmTimeMillis = AlarmRegister.getNextTime(h,m);

                ti.setTm(new java.util.Date(alarmTimeMillis));

                if(addTimer(ti)){
                    register(ti);
                }
//                register(tid,h,m);

                //alarmSwitch.setChecked(true);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);


        builder.setView(tp);
        builder.create().show();

/*
        TimePickerDialog timePickerDialog = new TimePickerDialog(TestActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                almcal.set(Calendar.YEAR,almcal.get(Calendar.YEAR));
                almcal.set(Calendar.MONTH,almcal.get(Calendar.MONTH));
                almcal.set(Calendar.DATE,almcal.get(Calendar.DATE));
                almcal.set(Calendar.HOUR_OF_DAY,hourOfDay);
                almcal.set(Calendar.MINUTE,minute);
                almcal.set(Calendar.SECOND,0);


System.out.println(almcal.getTime());
                register(almcal.getTimeInMillis());

                alarmSwitch.setChecked(true);
            }
        },hour,minute,true);

        timePickerDialog.show();
*/
    }

    /*
    private long getNextTime(int h,int m){

        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.HOUR_OF_DAY,h);
        cal.set(Calendar.MINUTE,m);

        long time = cal.getTimeInMillis();

        if(System.currentTimeMillis()<time)	    return time;

        cal.add(Calendar.DATE,1);

        return cal.getTimeInMillis();
    }


    private int getMinute(long time){

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);

        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);

        return hour*60+min;
    }
*/

    private void register(TimerItem ti) {

		//long alarmTimeMillis = getNextTime(h,m);

        //Toast.makeText(getApplicationContext(), String.format("%d %d %d",tid,h,m), Toast.LENGTH_LONG).show();

 //       ti.setIntrv(1);

        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.setClass(this, AlarmReceiver.class);
        intent.putExtra("timer",ti);

//        intent.putExtra("tid",tid);
//        intent.putExtra("repmin",1);

//      PendingIntent pendingIntent = PendingIntent.getBroadcast(this, idn, intent, PendingIntent.FLAG_CANCEL_CURRENT);

		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, ti.getId(), intent, PendingIntent.FLAG_CANCEL_CURRENT);
//      alarmSet(pendingIntent,alarmTimeMillis);

        AlarmRegister alr = new AlarmRegister(this);
        alr.alarmSet(pendingIntent,AlarmRegister.getNextTime(ti.getTm()));
    }


	private boolean addTimer(TimerItem ti){

		boolean res = true;

        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        try {
            realm.beginTransaction();

//            if (idn < 0){
                //get max
//                RealmResults<TimerSetting> tmlist = realm.where(TimerSetting.class).findAll().sort("id", Sort.DESCENDING);
//
//               idn = tmlist.size()==0 ? 0 : tmlist.first().getId()+1;
//		    }
//            else{
//                delete pendding intent
//      }

            TimerSetting timersetting = new TimerSetting();

            timersetting.setId(ti.getId());
            timersetting.setTm(ti.getTm());
            timersetting.setIntrv(ti.getIntrv());

            realm.copyToRealmOrUpdate(timersetting);

            realm.commitTransaction();
 //         realm.cancelTransaction();

            TimerAdapter ta = (TimerAdapter)lv.getAdapter();
            ta.removeList();

            ArrayList<TimerItem> ar = getTimerList(realm);
            ta.setList(ar);

			ta.notifyDataSetChanged();
        }
        catch(Exception ex) {
            ex.printStackTrace();
            res=false;
        }
        finally {
            realm.close();
        }

        return res;
	}

    private void unregister(int tid) {

        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, tid, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmRegister alr = new AlarmRegister(this);
        alr.alarmDelete(pendingIntent);

        Toast.makeText(getApplicationContext(), String.format("%d delete", tid), Toast.LENGTH_LONG).show();
    }
    private boolean deleteTimer(int tid){

    	boolean res = true;

        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        try {
            realm.beginTransaction();

            RealmResults<TimerSetting> tmlist = realm.where(TimerSetting.class).equalTo("id",tid).findAll();

            tmlist.deleteAllFromRealm();
 //         tmlist.deleteFromRealm(tid);

            realm.commitTransaction();

            TimerAdapter ta = (TimerAdapter)lv.getAdapter();

            ta.removeList();

            ArrayList<TimerItem> ar = getTimerList(realm);
            ta.setList(ar);

			ta.notifyDataSetChanged();
        }
        catch(Exception ex) {
            ex.printStackTrace();
            res=false;
        }
        finally {
            realm.close();
        }

        return res;
    }

    private void changeTimerState(TimerItem ti,boolean enabled){

        int tid = ti.getId();

        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        try {
            realm.beginTransaction();

            RealmResults<TimerSetting> tmlist = realm.where(TimerSetting.class).equalTo("id",tid).findAll();

            tmlist.get(0).setEnabled(enabled);

            realm.commitTransaction();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        finally{
            realm.close();
        }

        if(enabled) register(ti);
        else        unregister(tid);
    }


}
