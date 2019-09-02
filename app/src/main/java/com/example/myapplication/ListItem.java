package com.example.myapplication;

public class ListItem {

	private long id;
	private String name;
	private String path;

	public void setId(long id){
		this.id=id;
	}

	public long getId(){
		return this.id;
	}

	public void setName(String name){
		this.name=name;
	}

	public String getName(){
		return this.name;
	}

	public void setPath(String path){
		this.path=path;
	}

	public String getPath(){
		return this.path;
	}

}