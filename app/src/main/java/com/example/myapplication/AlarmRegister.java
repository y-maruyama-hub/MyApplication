package com.example.myapplication;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;

import java.util.Calendar;

public class AlarmRegister {

	private Context ctx;

	public AlarmRegister(Context ctx){
		this.ctx=ctx;
	}

	public static long getNextTime(int h,int m){

		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.HOUR_OF_DAY,h);
		cal.set(Calendar.MINUTE,m);
		cal.set(Calendar.SECOND,0);

		return next(cal);

	}

	public static long getNextTime(java.util.Date dt){

		Calendar cal = Calendar.getInstance();

		cal.setTime(dt);
		cal.set(Calendar.SECOND,0);

		return next(cal);
	}

	private static long next(Calendar cal){

		long time = cal.getTimeInMillis();

		if(System.currentTimeMillis()<time)	    return time;

		cal.add(Calendar.DATE,1);

		return cal.getTimeInMillis();
	}

	public void alarmSet(PendingIntent pendingIntent, long alarmTimeMillis){
		AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(alarmTimeMillis, null), pendingIntent);
		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTimeMillis, pendingIntent);
		} else {
			alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTimeMillis, pendingIntent);
		}
	}

	public void alarmDelete(PendingIntent pendingIntent){

		AlarmManager alarmManager = (AlarmManager)ctx.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(pendingIntent);
	}
}