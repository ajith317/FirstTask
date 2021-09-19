package com.example.firsttask

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_day_nine_task.*


class DayNineTask : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_nine_task)

        day9_viewBinding_btn.setOnClickListener({

            startActivity(Intent(applicationContext, ViewBindingEg::class.java))
        })

        day9_dataBinding_btn.setOnClickListener({
            startActivity(Intent(applicationContext, DataBindingEg::class.java))

        })

        var imgs = listOf<Int>(R.drawable.fresher_task,R.drawable.logo, R.drawable.ic_baseline_camera_24, R.drawable.ic_baseline_image_24)


        var adapter = ImageSlideAdapter(imgs, this)


        day9_viewPager.adapter = adapter


    }


}