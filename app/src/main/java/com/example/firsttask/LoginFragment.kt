package com.example.firsttask

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_login.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class LoginFragment : Fragment(), View.OnClickListener{

    lateinit var navController: NavController

    lateinit var preferences: SharedPreferences

    private val SHARED_PREF_NAME = "sharePrefEg"


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var rootView=inflater.inflate(R.layout.fragment_login, container, false)


        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController=Navigation.findNavController(view)

        view.findViewById<Button>(R.id.day8_login_btn).setOnClickListener(this)
        view.findViewById<Button>(R.id.day8_signUp_btn).setOnClickListener(this)

        preferences = requireActivity().getSharedPreferences("LoginPref", MODE_PRIVATE)
        val sharedNameValue = preferences.getString("userName", "defaultValue")
        val sharedPassValue=preferences.getString("pass","defaultValue")

        preferences = requireActivity().getSharedPreferences("LoginPref", MODE_PRIVATE)
        var editor: SharedPreferences.Editor = preferences.edit()
        editor.clear()
        editor.commit()

        if( (sharedNameValue!="defaultValue") && (sharedPassValue !="defaultValue")){
            navController.navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }


    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.day8_login_btn -> {

                var un = day8_login_userName_ed.text.toString()
                var pass = day8_login_userPass_ed.text.toString()


                if (un.equals("") && pass.equals("")) {
                    Toast.makeText(context, "Input required", Toast.LENGTH_SHORT).show()
                    return
                }
                var cursor = LoginContentProvider.dbLite.rawQuery("select * from loginDB where Email='$un' and Password='$pass'", null)
                if (cursor.count > 0) {
                    var editor: SharedPreferences.Editor = preferences.edit()
                    editor = preferences.edit()
                    editor.putString("userName", un)
                    editor.putString("pass", pass)
                    editor.apply()
                    editor.commit()
                    navController.navigate(R.id.action_loginFragment_to_homeFragment)

                    Toast.makeText(context,"LoginSucess", Toast.LENGTH_SHORT).show()
                } else {

                    Toast.makeText(requireContext(), "Invalid Data", Toast.LENGTH_SHORT).show()
                }


            }
            R.id.day8_signUp_btn -> {
                navController.navigate(R.id.action_loginFragment_to_signUpFragment2)

            }

        }

    }


        fun logOutPref() {

        }


}