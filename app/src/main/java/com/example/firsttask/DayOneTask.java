package com.example.firsttask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DayOneTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_one_task);
        String temp="";
        TextView tv=findViewById(R.id.text_pattern);
        for(int i=0; i<10; i++)
        {
            String t = "";
            for(int j=0; j<=i; j++)
            {
                t = t + "*";
            }
            temp = temp + t + "\n";
        }

      tv.setText(temp);
    }
}