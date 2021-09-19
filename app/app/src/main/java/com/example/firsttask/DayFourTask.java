    package com.example.firsttask;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetProvider;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

public class DayFourTask extends AppCompatActivity implements View.OnClickListener {

    Button intentEg,widegetEg,viewEg,notificationEg;
    ImageView imageView;
    private static  final int picId=123;
    private static  final int picGal=124;

    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_four_task);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel=new NotificationChannel("Notification","my notification",NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }

        intentEg=(Button)findViewById(R.id.intent_btn);
        widegetEg=(Button)findViewById(R.id.widget_btn);
        viewEg=(Button)findViewById(R.id.view_btn);
        notificationEg=(Button)findViewById(R.id.notification_btn);
        imageView=(ImageView)findViewById(R.id.d4_imgView);

        intentEg.setOnClickListener(this);
        notificationEg.setOnClickListener(this);
        widegetEg.setOnClickListener(this);
        viewEg.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v==intentEg)
        {
            PopupMenu popupMenu=new PopupMenu(this,intentEg);
            popupMenu.getMenuInflater().inflate(R.menu.intent_menu,popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    String title= (String) item.getTitle();
                    switch (title)
                    {
                        case  "Camera":
                            try {
                                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                if(intent.resolveActivity(getPackageManager())!=null){
                                    startActivityForResult(intent,picId);
                                }else
                                    Toast.makeText(DayFourTask.this, "Not Support", Toast.LENGTH_SHORT).show();
                            }catch (Exception e)
                            {
                                Log.d("ImageError", "onMenuItemClick: "+e);
                            }

                            break;
                        case  "Gallery":
                                Intent gallery=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                                startActivityForResult(gallery,picGal);
                            break;
                        case  "Clear":
                            imageView.setImageResource(0);
                            break;
                    }
                    return  true;
                }
            });
            popupMenu.show();
        }
        else if(v==notificationEg){

                addNotification();
        }
        else if(v==widegetEg)
        {

            Toast.makeText(this, "Check the WIDGET", Toast.LENGTH_SHORT).show();
        }
        else if(v==viewEg)
        {
            startActivity(new Intent(this,ViewEg.class));
        }

    }
    private void addNotification() {
          Toast.makeText(this, "Notification Pushed", Toast.LENGTH_SHORT).show();
            NotificationCompat.Builder builder=new NotificationCompat.Builder(getBaseContext(),"Notification")
                    .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                    .setContentTitle("Notifications Example")
                    .setContentText("Notifcation will be pushed")
                    .setAutoCancel(true);

            Intent notificationIntent = new Intent(this, MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentIntent);
            NotificationManagerCompat managerCompat=NotificationManagerCompat.from(this);
            managerCompat.notify(1,builder.build());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data !=null) {
            if(requestCode==picId){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
            }
            else if(requestCode==picGal){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);}
        }


    }




}