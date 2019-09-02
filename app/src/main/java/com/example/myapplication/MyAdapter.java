package com.example.myapplication;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.view.LayoutInflater;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater = null;
    private ArrayList<ListItem> list;

    public MyAdapter(Context context) {

        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setList(ArrayList<ListItem> list){
        this.list=list;
    }

    public void removeList() {
        list.clear();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public void addItem(ListItem item) {
        this.list.add(item);
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.listitem,parent,false);

        ((TextView)convertView.findViewById(R.id.name)).setText(list.get(position).getName());
        ((TextView)convertView.findViewById(R.id.path)).setText(list.get(position).getPath());

        return convertView;
    }
}