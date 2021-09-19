package com.example.firsttask;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment1  extends Fragment implements View.OnClickListener {
    String TAG = "LifeCycle";
    FragResponse response;
    Button btnFragA, btnFragB, btnFragC;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "Frag A onAttach()");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Frag A onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "Frag A onCreateView()");
        View rootView = inflater.inflate(R.layout.fragment1, container, false);
        btnFragA = rootView.findViewById(R.id.btnfragA);
        btnFragB = rootView.findViewById(R.id.btnfragB);
        btnFragC = rootView.findViewById(R.id.btnfragC);
        response = (FragResponse) getContext();
        btnFragA.setOnClickListener(this);
        btnFragB.setOnClickListener(this);
        btnFragC.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "Frag A onActivityCreated()");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "Frag A onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "Frag A onResume()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "Frag A onStop()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "Frag A onPause()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "Frag A onDetach()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Frag A onDestroy()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "Frag A onDestroyView()");
    }


    @Override
    public void onClick(View view) {
        response.onFragClick(view);
        Log.d(TAG, "Frag A onClick()");
    }
}
