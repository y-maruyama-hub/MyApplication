package com.example.myapplication;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import android.preference.PreferenceManager;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class NextActivity extends Activity /*implements View.OnClickListener*/ {

    EditText nameEdit;
    ListView lv;

    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        if (savedInstanceState == null) {

            lv = findViewById(R.id.lv);

            MyAdapter ma = new MyAdapter(this);
            ArrayList<ListItem> ar = new ArrayList<>();

            ma.setList(ar);
            lv.setAdapter(ma);

            changeList(0);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent,View view, int position, long id){
//                    String msg = position + "番目のアイテムがクリックされました";
                    ListItem item = (ListItem)parent.getAdapter().getItem(position);
                    Toast.makeText(getApplicationContext(), item.getPath(), Toast.LENGTH_LONG).show();

                    changeList(position);
                }
            });

            lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                @Override
                public boolean onItemLongClick(AdapterView<?> parent,View view, int position, long id){

                    ListItem item = (ListItem)parent.getAdapter().getItem(position);
                    Editor editor = sharedPref.edit();
                    editor.putString("path", item.getPath());
                    editor.commit();

                    Toast.makeText(getApplicationContext(), String.format("%sを設定しました。",item.getName()), Toast.LENGTH_LONG).show();

                    finish();
                    return true;
                }

            });


 /*
            String[] str = {"リンゴ","ゴリラ","ラジオ"};

            ArrayAdapter<String> aa= new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);

            for(int i=0;i<str.length;i++) {
                aa.add(str[i]);
            }

            lv.setAdapter(aa);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> parent,View view, int position, long id){
//                    String msg = position + "番目のアイテムがクリックされました";
                    String msg = (String)parent.getAdapter().getItem(position);
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                }

            });
*/

            nameEdit =  findViewById(R.id.name_edit);
            String title = getIntent().getStringExtra("aa");
            nameEdit.setText(title);
            Button clearBtn =  findViewById(R.id.clear_edit_button);
            clearBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    finish();
                }
            });

            sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        }
    }

    private void changeList(int pos){
        MyAdapter ma  = (MyAdapter)lv.getAdapter();
        ma.removeList();

        int mx=pos+20;

        for(int i=pos;i<mx;i++) {
            ListItem item = new ListItem();

            item.setName(String.format("test%d",i+1));
            item.setPath(String.format("test_path%d",i+1));

            ma.addItem(item);
        }
        ma.notifyDataSetChanged();
    }

}
