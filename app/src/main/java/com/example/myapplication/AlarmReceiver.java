package com.example.myapplication;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.app.PendingIntent;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {
//    private static final String TAG = AlarmReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {


/*
        if(tid>=0) {

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE,repmin);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, tid, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        //    ((TestActivity)context.getApplicationContext()).alarmSet(pendingIntent,cal.getTimeInMillis());

            AlarmRegister alr = new AlarmRegister(context);
            alr.alarmSet(pendingIntent,cal.getTimeInMillis());

 //           AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
  //          alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
        }
*/
        Intent startActivityIntent = new Intent(context, PlaySoundActivity.class);

        startActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityIntent.putExtra("timer",intent.getSerializableExtra("timer"));
 //       startActivityIntent.putExtra("tid",tid);
//        startActivityIntent.putExtra("repmin",repmin);

        context.startActivity(startActivityIntent);
    }
}