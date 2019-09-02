package com.example.myapplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TimerSetting extends RealmObject {

	@PrimaryKey
	private int id;
	private java.util.Date tm;
	private int intrv=0;
	private boolean enabled=true;

	public void setId(int id){
		this.id=id;
	}

	public int getId(){
		return this.id;
	}

	public void setTm(java.util.Date tm){
		this.tm=tm;
	}

	public java.util.Date getTm(){
		return this.tm;
	}

	public void setIntrv(int intrv){
		this.intrv=intrv;
	}

	public int getIntrv(){
		return this.intrv;
	}

	public void setEnabled(boolean enabled){
		this.enabled=enabled;
	}
	
	public boolean isEnabled(){
		return this.enabled;
	}

}