package com.example.firsttask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firsttask.databinding.FragmentViewBindingBinding


class FragmentViewBinding : Fragment() {


    lateinit var binding: FragmentViewBindingBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        binding= FragmentViewBindingBinding.inflate(layoutInflater)

        binding.fragmentBind.text="Fragment TextView Binding"
        return binding.root
    }


}