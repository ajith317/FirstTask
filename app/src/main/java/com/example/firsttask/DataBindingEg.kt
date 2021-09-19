package com.example.firsttask

import android.os.Bundle
import android.text.Layout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import com.example.firsttask.databinding.ActivityDataBindingEgBinding
import com.example.firsttask.model.LoginTableModel

class DataBindingEg : AppCompatActivity() {


    lateinit var binding: ActivityDataBindingEgBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding=DataBindingUtil.setContentView(this,R.layout.activity_data_binding_eg)

        val modelEg=LoginTableModel("DataBinding","Example of")

        binding.setVariable(BR.userLoginModel,modelEg)


    }
}