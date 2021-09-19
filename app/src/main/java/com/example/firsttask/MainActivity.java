package com.example.firsttask;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.impulsive.zoomimageview.ZoomImageView;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    String msg="Status";
    Button day1,day2,day3,day4,day5_6,day7,day8,day9,day10,day11,day12,day13,day15,day16,day17,day18,day19,day20,day21;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        day1=(Button)findViewById(R.id.day_1);
        day2=(Button)findViewById(R.id.day_2);
        day3=findViewById(R.id.day_3);
        day4=findViewById(R.id.day_4);
        day5_6=findViewById(R.id.day_5_6);
        day7=findViewById(R.id.day_7);
        day8=findViewById(R.id.day_8);
        day9=findViewById(R.id.day_9);
        day10=findViewById(R.id.day_10);
        day11=findViewById(R.id.day_11);
        day12=findViewById(R.id.day_12);
        day13=findViewById(R.id.day_13);
        day15=findViewById(R.id.day_15);
        day16=findViewById(R.id.day_16);
        day17=findViewById(R.id.day_17);
        day18=findViewById(R.id.day_18);
        day19=findViewById(R.id.day_19);
        day20=findViewById(R.id.day_20);

        day21=findViewById(R.id.day_21);


        TextView marqueeText=findViewById(R.id.mywidget);
        marqueeText.setSelected(true);

        //Image Popup thirdParty api call

        Picasso.setSingletonInstance(new Picasso.Builder(this).build());
         ImagePopup imagePopup = new ImagePopup(this);

        imagePopup.setHideCloseIcon(true);
        imagePopup.setWindowWidth(800);
        imagePopup.setWindowHeight(800);




        ImageView day1Image=findViewById(R.id.day_one_image);

        imagePopup.initiatePopup(day1Image.getDrawable());


        Button day1Arrow=findViewById(R.id.day1_arrow_btn);

        LinearLayout day1View=findViewById(R.id.day1_view_ll);

        day1Arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(day1View.getVisibility()==View.GONE){
                    day1View.setVisibility(View.VISIBLE);
                    day1Arrow.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                    imagePopup.viewPopup();
                }
                else{
                    day1View.setVisibility(View.GONE);
                    day1Arrow.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                }
            }
        });


        day1Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePopup.viewPopup();
            }
        });


        day1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DayOneTask.class));
            }
        });
        day2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DayTwoTask.class));
            }
        });

        day3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DayThreeTask.class));
            }
        });

        day5_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DayFiveAndSix.class));
            }
        });

        day4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DayFourTask.class));
            }
        });

        day7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Night Mode On Color Not FIX", Toast.LENGTH_SHORT).show();
               // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        });

        day8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DayEightTask.class));
            }
        });

        day9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DayNineTask.class));
                Toast.makeText(getBaseContext(),"Image Slider with Zoombale",Toast.LENGTH_SHORT).show();
            }
        });

        day10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DayTenTask.class));
            }
        });

        day11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DayElevenTask.class));
            }
        });
        day12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DayTwelveTask.class));
            }
        });
        day13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DayThirteenTask.class));
            }
        });

        day15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DayFifteenTask.class));
            }
        });
        day16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DaySixteenTask.class));
            }
        });

        day17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DaySeventeenTask.class));
            }
        });

        day18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DayEigteenTask.class));
            }
        });

        day19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DayNineTeenTask.class));
            }
        });

        day20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DayTwentyTask.class));
            }
        });
        day21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),DayTwentyOneTask.class));
            }
        });



        LinearLayout header=findViewById(R.id.header_ll);
        ZoomImageView fresherImg=findViewById(R.id.fresher_task_img);
        ImagePopup imgPopUp=new ImagePopup(this);
        imgPopUp.initiatePopup(fresherImg.getDrawable());
        imgPopUp.setFullScreen(true);




        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                    imgPopUp.viewPopup();
            }

        });

    }


    @Override
    public void onClick(View v) {

    }
}