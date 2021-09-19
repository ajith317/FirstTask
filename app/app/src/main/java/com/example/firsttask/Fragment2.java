package com.example.firsttask;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Fragment2 extends Fragment implements View.OnClickListener {

    String TAG = "LifeCycle";
    FragResponse response;
    Button btnFragA,btnFragB,btnFragC;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "Frag B onAttach()");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Frag B onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "Frag B onCreateView()");
        View rootView = inflater.inflate(R.layout.fragment2,container,false);
        btnFragA = rootView.findViewById(R.id.btnfragA);
        btnFragB = rootView.findViewById(R.id.btnfragB);
        btnFragC = rootView.findViewById(R.id.btnfragC);
        response = (FragResponse)getContext();
        btnFragA.setOnClickListener(this);
        btnFragB.setOnClickListener(this);
        btnFragC.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "Frag B onActivityCreated()");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "Frag B onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "Frag B onResume()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "Frag B onStop()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "Frag B onPause()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "Frag B onDetach()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Frag B onDestroy()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "Frag B onDestroyView()");
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "Frag B onClick()");
        response.onFragClick(view);
    }
}