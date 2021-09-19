package com.example.firsttask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
public class ViewEg extends AppCompatActivity {
    final Calendar myCalendar = Calendar.getInstance();
    EditText edittext;
    String[] devlopment = { "WEB Devloper","Android Develper","Digital Marketing"};
    String[] textAutoComplete={"WEB Devloper","Android Develper","Digital Marketing"};



    AutoCompleteTextView autoCompleteTextView;
    CheckBox termsAndCont;
    ImageButton imgSearchBtn;
    WebView webView;
    RadioButton male,female;
    EditText urlSearch;
    SeekBar seekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_eg);

        male=findViewById(R.id.view_radio_male_rl);
        female=findViewById(R.id.view_radio_female_rl);
        autoCompleteTextView=findViewById(R.id.view_autoComplte_rl);
        Spinner spin = (Spinner) findViewById(R.id.view_spinner_rl);
        termsAndCont=findViewById(R.id.view_checkBox_rl);
        imgSearchBtn=findViewById(R.id.view_imgbtn_ll);
        webView=findViewById(R.id.view_web_view_ll);
        urlSearch=findViewById(R.id.view_url_search_ll);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);
        actionBar.setDisplayHomeAsUpEnabled(true);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,devlopment);
        ArrayAdapter adapter=new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,textAutoComplete);


        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

        autoCompleteTextView.setThreshold(2);
        autoCompleteTextView.setAdapter(adapter);


        edittext= (EditText) findViewById(R.id.view_date_picker);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(ViewEg.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        imgSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url=urlSearch.getText().toString();
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.loadUrl(url);
                urlSearch.getText().clear();

            }
        });


        seekBar=(SeekBar)findViewById(R.id.view_seekBar_ll);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int val=0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {

                val=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(),"seekbar progress : "+val, Toast.LENGTH_SHORT).show();
            }
        });




    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edittext.setText(sdf.format(myCalendar.getTime()));
    }
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        String str="";
        switch(view.getId()) {
            case R.id.view_radio_male_rl:
                if(checked)
                    str = "male";
                break;
            case R.id.view_radio_female_rl:
                if(checked)
                    str = "female";
                break;
        }
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

}

    class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

    }



