package com.example.firsttask

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import android.widget.RelativeLayout
import com.impulsive.zoomimageview.ZoomImageView

class ImageSlideAdapter(var list:List<Int>,var ctx:Context): PagerAdapter() {

    private lateinit var ImgList:List<Int>

    lateinit var layoutInflater:LayoutInflater
    lateinit var context:Context

    override fun getCount(): Int {
        return list.size

    }


    override fun isViewFromObject(view: View, `object`: Any): Boolean {

        return view.equals(`object`)

    }



    override fun instantiateItem(container: ViewGroup, position: Int): Any {


        layoutInflater = LayoutInflater.from(ctx)


        var view = layoutInflater.inflate(R.layout.image_slider,container,false)


        //val img = view.findViewById<ImageView>(R.id.image_view)

        val img=view.findViewById<ZoomImageView>(R.id.image_view)


        img.setImageResource(list[position])


        container.addView(view,0)


        return view


    }



    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {


        container.removeView(`object` as View)


    }
}