package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import java.util.Properties;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {

            //TextView titleText = findViewById(R.id.title_text);
            //titleText.setText(getString(R.string.app_name));

            Properties p =new Properties();

            try{
                p.loadFromXML(getApplicationContext().getAssets().open("app.properties.xml"));
                System.out.println(p.getProperty("key1"));
            }
            catch(Exception ex) {
               ex.printStackTrace();
            }




            Button goButton =  findViewById(R.id.go_button);

            goButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    next();
                }
            });

            Button m1Button =  findViewById(R.id.m1_button);
            m1Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    m1();
                }
            });

            Button m2Button =  findViewById(R.id.m2_button);
            m2Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    m2();
                }
            });
        }
    }

    private void next() {
        Intent intent = new Intent(this,NextActivity.class);
        intent.putExtra("aa","bb");
        startActivity(intent);
    }

    private void m1() {
//        Intent intent = new Intent(this,TestActivity.class);
        Intent intent = new Intent(this,TestActivity.class);
        startActivity(intent);
    }


    private void m2() {
//        Intent intent = new Intent(this,TestActivity.class);
        Intent intent = new Intent(this,ScrollActivity.class);
        startActivity(intent);
    }

}
