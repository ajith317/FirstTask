package com.example.firsttask

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.company_name_header.*

class DayTenTask : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawer:DrawerLayout
    lateinit var navigationView:NavigationView

    private val SHARED_PREF_NAME = "sharePrefEg"
    lateinit var outputText:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_ten_task)

        drawer=findViewById(R.id.drawer)
        navigationView=findViewById(R.id.day10_nav_eg)
        toggle= ActionBarDrawerToggle(this, drawer, 0, 0)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)




        val storeBtn=findViewById<Button>(R.id.day10_store_btn)
        val showBtn=findViewById<Button>(R.id.day10_show_btn)
        val textMsg=findViewById<EditText>(R.id.day10_text_ed)
         outputText=findViewById<TextView>(R.id.day10_textMsg_tv)
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)



        storeBtn.setOnClickListener(View.OnClickListener {
            val name: String = textMsg.text.toString()
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("name_key", name)
            editor.apply()
            editor.commit()
        })

        showBtn.setOnClickListener()
        {
            val sharedNameValue = sharedPreferences.getString("name_key", "defaultValue")
            if(sharedNameValue.equals("defaultValue"))
                outputText.setText("default name: ${sharedNameValue}").toString()
            else
                outputText.setText(sharedNameValue).toString()
           // startActivity(Intent(applicationContext, MainActivity::class.java))
        }



        navigationView.setNavigationItemSelectedListener {
            when(it.itemId)
            {
                R.id.Home_nav ->
                    startActivity(Intent(applicationContext, MainActivity::class.java))

                R.id.day2_nav ->
                    startActivity(Intent(applicationContext, DayTwoTask::class.java))

                R.id.day4_nav ->
                    startActivity(Intent(applicationContext, DayFourTask::class.java))

            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            true
        }
        return super.onOptionsItemSelected(item)
    }




    }

