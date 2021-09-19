package com.example.firsttask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.AlertDialog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.Toast;

public class DayThreeTask extends AppCompatActivity implements View.OnClickListener {
    Button serviceStart,serviceStop,broadcastReciver,contentProvider,localBroadCastReciver,endLessService;
    ConnectionReceiver connectionReceiver;

    AlertDialog.Builder builder;

    WifiManager wifiManager;
   // BatteryManager batteryManager;
    boolean cont=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_three_task);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);
        actionBar.setDisplayHomeAsUpEnabled(true);

        connectionReceiver = new ConnectionReceiver();

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);


        serviceStart=(Button) findViewById(R.id.start_service_btn);
        serviceStop=(Button) findViewById(R.id.stop_service_btn);
        contentProvider=(Button)findViewById(R.id.content_provider_btn);
        endLessService=(Button)findViewById(R.id.end_less_stop_service_btn);
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
        endLessService.setOnClickListener(this);


    }
    public  void onClick(View view)
    {

        if(view==serviceStart)
        {
            Toast.makeText(this, "vana", Toast.LENGTH_SHORT).show();
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

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                Intent panelIntent = new Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY);
                                startActivityForResult(panelIntent, 0);
                            } else {
                                wifiManager.setWifiEnabled(true);
                            }
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            Intent panelIntent = new Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY);
                            startActivityForResult(panelIntent, 0);
                        } else{
                            wifiManager.setWifiEnabled(false);
                        }
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Wifi enable?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();

            //broadCastEg();
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
        else if(view==endLessService)
        {

            startActivity(new Intent(this,EndlessServiceMain.class));
        }

    }


    public void broadCastEg()
    {

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
        registerReceiver(connectionReceiver,intentFilter);
        registerReceiver(connectionReceiver,batteryLevelFilter);
    }
    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(connectionReceiver);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
    

}