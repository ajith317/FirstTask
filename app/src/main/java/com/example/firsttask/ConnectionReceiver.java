package com.example.firsttask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.util.Log;
import android.widget.Toast;

public class ConnectionReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        String message=intent.getStringExtra("message");
        

        int wifiStateExtra = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                WifiManager.WIFI_STATE_UNKNOWN);

        switch (wifiStateExtra) {
            case WifiManager.WIFI_STATE_ENABLED:
                break;
            case WifiManager.WIFI_STATE_DISABLED:
                break;
        }

        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,  0);
        String battery = String.valueOf(level);
        if(level!=0)
            Toast.makeText(context, "Battery Status is "+ battery, Toast.LENGTH_SHORT).show();


//        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//        if (activeNetwork != null) {
//            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
//                Toast.makeText(context, "Wifi enabled", Toast.LENGTH_LONG).show();
//            }
//            else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
//                Toast.makeText(context, "Mobile data enabled", Toast.LENGTH_LONG).show();
//            }
//        }
//        else {
//            Toast.makeText(context, "No internet is available", Toast.LENGTH_LONG).show();
//        }

    }

}
