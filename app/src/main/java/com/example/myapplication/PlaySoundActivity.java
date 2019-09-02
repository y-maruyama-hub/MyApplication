package com.example.myapplication;

import android.app.PendingIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import java.util.Calendar;

public class PlaySoundActivity extends AppCompatActivity {

	Button btnstop;
	Button btnsnooze;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_play_sound);

		btnstop = findViewById(R.id.btnstop);
		btnsnooze = findViewById(R.id.btnsnooze);

		TimerItem ti = (TimerItem)getIntent().getSerializableExtra("timer");

		if(ti.getIntrv()==0)	btnsnooze.setVisibility(View.GONE);

		setListener();

		startService(new Intent(this, PlaySoundService.class));
	}

	private void setListener() {

		btnstop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				stopService(new Intent(PlaySoundActivity.this, PlaySoundService.class));

				TimerItem ti = (TimerItem)getIntent().getSerializableExtra("timer");
				//set next alerm;

				long nexttime = AlarmRegister.getNextTime(ti.getTm());

				repeat(nexttime,ti);
				finish();
			}
		});

		btnsnooze.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				stopService(new Intent(PlaySoundActivity.this, PlaySoundService.class));

				TimerItem ti = (TimerItem)getIntent().getSerializableExtra("timer");

				//int repmin = getIntent().getIntExtra("repmin",0);

				if(ti!=null) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.MINUTE, ti.getIntrv());
					repeat(cal.getTimeInMillis(),ti);

					finish();
				}
			}
		});
	}

	private void repeat(long nexttime,TimerItem ti) {

//		int tid = getIntent().getIntExtra("tid",-1);
		int tid =  ti.getId();

		if(tid>=0)
		{
			Intent intent = new Intent(this, AlarmReceiver.class);
			intent.setClass(this, AlarmReceiver.class);
			intent.putExtra("timer",ti);
//			intent.putExtra("tid",tid);
//			intent.putExtra("repmin",repmin);

			PendingIntent pendingIntent = PendingIntent.getBroadcast(this, tid, intent, PendingIntent.FLAG_CANCEL_CURRENT);

			AlarmRegister alr = new AlarmRegister(this);
			alr.alarmSet(pendingIntent, nexttime);
		}
	}
}