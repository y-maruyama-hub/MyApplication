package com.example.myapplication;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Switch;
import android.widget.Button;
import android.widget.ListView;
import android.view.ViewGroup.LayoutParams;


import java.text.SimpleDateFormat;

public class TimerAdapter extends BaseAdapter  {

    private LayoutInflater layoutInflater = null;
    private ArrayList<TimerItem> list;
    private SimpleDateFormat tfm = new SimpleDateFormat("HH:mm");

    private int c1h=50;
    private int c2h=150;

    private static class ViewHolder {
        ViewGroup ln1;
        ViewGroup ln2;
        TextView t1;
        Switch sw;
        Button btn;
        TextView t2;
        Button chgbtn;
    }

    public TimerAdapter(Context context) {

        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ViewGroup vg = (ViewGroup)this.layoutInflater.inflate(R.layout.timeritem,null);

        c1h = vg.findViewById(R.id.ln1).getLayoutParams().height;
        c2h = vg.findViewById(R.id.ln2).getLayoutParams().height;
    }

    public int getC1h(){return this.c1h;}
    public int getC2h(){return this.c2h;}

    public void setList(ArrayList<TimerItem> list){
        this.list=list;
    }

    public void removeList() {
        list.clear();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public void addItem(TimerItem item) {
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
    public View getView(final int position, View convertView, final ViewGroup parent) {

        System.out.println("######");

        ViewHolder holder=null;

        if(convertView==null) {
            convertView = layoutInflater.inflate(R.layout.timeritem, parent, false);

            holder = new ViewHolder();

            holder.ln1=convertView.findViewById(R.id.ln1);
            holder.ln2=convertView.findViewById(R.id.ln2);
            holder.t1=convertView.findViewById(R.id.time);
            holder.sw = convertView.findViewById(R.id.sq);
            holder.btn=convertView.findViewById(R.id.abtn);
            holder.t2=convertView.findViewById(R.id.stgs);
            holder.chgbtn=convertView.findViewById(R.id.chgbtn);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }

        //ln1.setVisibility(View.VISIBLE);
        holder.ln1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentPerform((ListView)parent,v,position);
            }

        });

        holder.ln1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ListView lv = (ListView)parent;
                lv.getOnItemLongClickListener().onItemLongClick(lv,v,position,v.getId());
                return true;
            }
        });

        String tm = tfm.format(list.get(position).getTm());

 //       ((TextView) convertView.findViewById(R.id.time)).setText(tm);

        holder.t1.setText(tm);

        //System.out.println(String.format("pos=%d",position));

        holder.sw.setText(tm);
        holder.sw.setChecked(list.get(position).isEnamed());
//        https://qiita.com/maromaro3721/items/6ac3cba4f090662adabf

        holder.btn=convertView.findViewById(R.id.abtn);
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentPerform((ListView)parent,v,position);
            }
        });

//        ln2.setVisibility(View.GONE);
        holder.ln2.getLayoutParams().height=0;
        holder.t2.setText(tm);

        holder.chgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentPerform((ListView)parent,v,position);
            }
        });

        return convertView;
    }

    private void parentPerform(ListView lv,View v,int position){
        lv.performItemClick(v,position,v.getId());
    }

}