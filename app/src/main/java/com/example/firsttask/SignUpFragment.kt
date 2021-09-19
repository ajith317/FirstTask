package com.example.firsttask

import android.content.ContentValues
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_sign_up.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SignUpFragment : Fragment(),View.OnClickListener{

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController=Navigation.findNavController(view)
        view.findViewById<Button>(R.id.day8_register_btn).setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.day8_register_btn -> {
                    var userName=day8_register_userName_ed.text.toString()
                    var password=day8_register_Pass_ed.text.toString()
                    var mail=day8_register_mail_ed.text.toString()
                    var confirmPass=day8_register_ConfmPass_ed.text.toString()

                    if(userName.equals("") || password.equals("") || mail.equals(""))
                    {
                        Toast.makeText(context,"Fields required ", Toast.LENGTH_SHORT).show()
                        return
                    }
                    if(confirmPass!=password)
                    {
                        Toast.makeText(context,"Password Missmatch", Toast.LENGTH_SHORT).show()
                        return
                    }

                    else
                    {
                        var value = ContentValues()
                        value.put("name", day8_register_userName_ed.text.toString())
                        value.put("email", day8_register_mail_ed.text.toString())
                        value.put("password", day8_register_Pass_ed.text.toString())
                        context?.contentResolver?.insert(LoginContentProvider.URI, value)
                        navController.navigate(R.id.action_signUpFragment_to_loginFragment)

                        Toast.makeText(requireContext(), "Login Succesfully", Toast.LENGTH_SHORT).show()
                    }
            }
        }

    }
}