package com.example.firsttask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ContentProviderEg extends AppCompatActivity {
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider_eg);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }
    public void onClickAddDetails(View view) {
        ContentValues values = new ContentValues();

        values.put(TaskScheduleProvider.name, ((EditText) findViewById(R.id.txtName)).getText().toString());
        try {
            getContentResolver().insert(TaskScheduleProvider.CONTENT_URI, values);
        }
        catch (Exception e)
        {
            Log.d("Content Erro :", String.valueOf(e));
        }

        Toast.makeText(getBaseContext(), "New Record Inserted", Toast.LENGTH_LONG).show();
    }

    public void onClickShowDetails(View view) {
        TextView resultView= (TextView) findViewById(R.id.res);
        try {
            Cursor cursor = getContentResolver().query(Uri.parse("content://com.example.firsttask/task"), null, null, null, null);
            if (cursor.moveToFirst()) {
                StringBuilder strBuild = new StringBuilder();
                while (!cursor.isAfterLast()) {
                    strBuild.append("\n" + cursor.getString(cursor.getColumnIndex("id")) + "-" + cursor.getString(cursor.getColumnIndex("name")));
                    cursor.moveToNext();
                }
                resultView.setText(strBuild);
            } else {
                resultView.setText("No Records Found");
            }
        }catch (Exception e){
            Log.d("Show Error", String.valueOf(e));
        }
    }
}