package com.example.firsttask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DayTwoTask extends AppCompatActivity implements View.OnClickListener, FragResponse {
    Button btnFragA, btnFragB, btnFragC;
    String TAG = "LifeCycle";
    Fragment1 fragA;
    Fragment2 fragB;

    boolean frag1=true,frag2=true,act=true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_two_task);
        Log.d(TAG, "ACT onCreate()");
        btnFragA = (Button) findViewById(R.id.btnfragA);
        btnFragB = (Button) findViewById(R.id.btnfragB);
        btnFragC = (Button) findViewById(R.id.btnfragC);


        btnFragA.setOnClickListener(this);
        btnFragB.setOnClickListener(this);
        btnFragC.setOnClickListener(this);
        fragA = new Fragment1();
        fragB = new Fragment2();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "ACT onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "ACT onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "ACT onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "ACT onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "ACT onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "ACT onDestroy()");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d(TAG, "ACT onBackPressed()");
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "ACT onClick()");

        switch (view.getTag().toString()) {
            case "A":
                if(frag1==true)
                {
                    getSupportActionBar().setTitle("Fragment A");
                    replaceFragment(fragA);
                    frag1=false;
                    frag2=true;
                    act=true;
                }else
                    Toast.makeText(this, "Already in Fragment 1 !", Toast.LENGTH_SHORT).show();
                break;
            case "B":
                if(frag2==true)
                {
                    getSupportActionBar().setTitle("Fragment B");
                    replaceFragment(fragB);
                    frag1 = true;
                    frag2=false;
                    act=true;
                }else
                Toast.makeText(this, "Already in Fragment 2 !", Toast.LENGTH_SHORT).show();
                break;
            case "C":
                if(act==true)
                {
                    frag1 = true;
                    frag2=true;
                    act=false;
                    getSupportActionBar().setTitle("Activity");
                    removeFragment(fragA);
                    removeFragment(fragB);
                }
                else
                    Toast.makeText(this, "Your in Activity ", Toast.LENGTH_SHORT).show();
        }
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(android.R.id.content, fragment);
        transaction.commit();
    }

    public void addFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(android.R.id.content, fragment);
        transaction.commit();
    }

    public void removeFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.remove(fragment);
        transaction.commit();
    }

    @Override
    public void onFragClick(View view) {
        Log.d(TAG, "ACT onFragClick()");
        switch (view.getTag().toString()) {
            case "A":
                if(frag1==true) {
                    getSupportActionBar().setTitle("Fragment A");
                    replaceFragment(fragA);
                    frag1=false;
                    frag2=true;
                    act=true;
                }else
                    Toast.makeText(this, "Already in Fragment 1!", Toast.LENGTH_SHORT).show();
                break;
            case "B":
                if(frag2==true)
                {
                    getSupportActionBar().setTitle("Fragment B");
                    replaceFragment(fragB);

                    frag2=false;
                    frag1 = true;
                    act=true;
                }else
                    Toast.makeText(this, "Already in Fragment 2!", Toast.LENGTH_SHORT).show();

                break;
            case "C":
                if(act==true)
                {
                    frag1 = true;
                    frag2=true;
                    act=false;
                    getSupportActionBar().setTitle("Activity");
                    removeFragment(fragA);
                    removeFragment(fragB);
                }
                else
                    Toast.makeText(this, "Your in Activity ", Toast.LENGTH_SHORT).show();
        }
    }


}