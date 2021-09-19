package com.example.firsttask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.inflate
import com.example.firsttask.databinding.ActivityContentProviderEgBinding.inflate
import com.example.firsttask.databinding.ActivityDataBindingEgBinding.inflate
import com.example.firsttask.databinding.ActivityViewBindingEgBinding
import com.example.firsttask.databinding.WidgetEgBinding.inflate

class ViewBindingEg : AppCompatActivity() {

    lateinit var binding: ActivityViewBindingEgBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityViewBindingEgBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewBindTv.text="View Binding Text"

        binding.bindFragmentBtn.setOnClickListener{
            val fragmentTransient=supportFragmentManager.beginTransaction()
            fragmentTransient.replace(R.id.viewBind_frameLayout,FragmentViewBinding(),"null")
            fragmentTransient.commit()
        }

    }
}