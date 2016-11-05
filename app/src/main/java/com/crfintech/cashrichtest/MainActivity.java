package com.crfintech.cashrichtest;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    public SQLiteDatabase db;
    private DataBaseHelper dbHelper;
    ArrayAdapter<String> listAdapter;
    DataBaseAdapter dba;
    public static ArrayList<String> rows = new ArrayList<String>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dba = new DataBaseAdapter(this);
        final ListView listView = (ListView) findViewById(R.id.listview);
        dba = dba.open();
        dba.insertEntry("Darshan", "darshan@hotmail.com", 1234567, 26, "Male");
        dba.insertEntry("Darshan", "darshan@hotmail.com", 1234567, 26, "Male");
        dba.insertEntry("Darshan", "darshan@hotmail.com", 1234567, 26, "Male");
        dba.insertEntry("Darshan", "darshan@hotmail.com", 1234567, 26, "Male");

        //db=dbHelper.getWritableDatabase();

        dba.getAllCategorys();


        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, rows);
        listView.setAdapter(listAdapter);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                dba.open();
                dba.insertEntry("Darshan", "darshan@hotmail.com", 1234567, 26, "Male");
                listAdapter.notifyDataSetChanged();
                handler.postDelayed(this, 5 * 1000);
            }
        }, 5 * 1000);
    }
}
