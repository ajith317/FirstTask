package com.example.firsttask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

public class DayThreeTask extends AppCompatActivity implements View.OnClickListener {
    Button serviceStart,serviceStop,broadcastReciver,contentProvider,localBroadCastReciver;
    ConnectionReceiver connectionReceiver;



    AlertDialog.Builder builder;
    ConnectionReceiver wifiStateReceiver=new ConnectionReceiver();
    WifiManager wifiManager;
    BatteryManager batteryManager;
    boolean cont=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_three_task);

        connectionReceiver = new ConnectionReceiver();


        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        batteryManager=(BatteryManager)getApplicationContext().getSystemService(Context.BATTERY_SERVICE);

        serviceStart=(Button) findViewById(R.id.start_service_btn);
        serviceStop=(Button) findViewById(R.id.stop_service_btn);
        contentProvider=(Button)findViewById(R.id.content_provider_btn);

        localBroadCastReciver=(Button)findViewById(R.id.local_broadCast_btn);



        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,new IntentFilter("local_broadcast_eg"));

        localBroadCastReciver.setText("Local Broadcast");

        builder=new AlertDialog.Builder(this);

        broadcastReciver=(Button)findViewById(R.id.broadCast_btn);

        serviceStart.setOnClickListener(this);
        serviceStop.setOnClickListener(this);
        broadcastReciver.setOnClickListener(this);
        contentProvider.setOnClickListener(this);
        localBroadCastReciver.setOnClickListener(this);



    }
    public  void onClick(View view)
    {

        if(view==serviceStart)
        {
            if(cont!=false)
                Toast.makeText(this, "Already Start!", Toast.LENGTH_SHORT).show();
            else{
                cont=true;
                startService(new Intent(this,ServiceEg.class));
            }

        }
        else if(view==serviceStop)
        {
            if(cont==false)
                Toast.makeText(this, " Start the Service", Toast.LENGTH_SHORT).show();
            else{
                cont=false;
                stopService(new Intent(this,ServiceEg.class));}
        }

        else if(view==broadcastReciver)
        {

            broadCastEg();
        }
        else if(view == contentProvider)
        {
            startActivity(new Intent(this,ContentProviderEg.class));
        }
        else if(view == localBroadCastReciver){
            Intent intent = new Intent("local_broadcast_eg");
            intent.putExtra("msg","Values Change");
            LocalBroadcastManager.getInstance(DayThreeTask.this).sendBroadcast(intent);
        }

    }


    public void broadCastEg()
    {
            builder.setMessage("DO YOU WANT ON") .setTitle("WIFI ");
            builder.setMessage("Do you want to ON WIFI")
                    .setCancelable(false)
                    .setPositiveButton("ON", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            wifiManager.setWifiEnabled(true);
                        }
                    })
                    .setNegativeButton("OFF", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            wifiManager.setWifiEnabled(false);
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.setTitle("WIFI SETTING");
            alert.show();
        }


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("msg");
            localBroadCastReciver.setText(message);
        }
    };
    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        IntentFilter batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(wifiStateReceiver,intentFilter);
        registerReceiver(wifiStateReceiver,batteryLevelFilter);
    }
    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(wifiStateReceiver);
    }




}