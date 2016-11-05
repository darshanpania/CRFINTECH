package com.crfintech.cashrichtest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Darshan on 05-11-2016.
 */
public class DataBaseAdapter {
    static final String DATABASE_NAME = "login.db";
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;

    // SQL Statement to create a new database.
    static final String DATABASE_CREATE = "create table " + "PERSONAL" +
            "( " + "ID" + " integer primary key autoincrement," + "NAME  text,EMAIL text, PHONE integer, AGE integer, GENDER text); ";

    public SQLiteDatabase db;
    private final Context context;
    private DataBaseHelper dbHelper;

    public DataBaseAdapter(Context _context) {
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DataBaseAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

    public SQLiteDatabase getDatabaseInstance() {
        return db;
    }

    public void insertEntry(String name, String email, int phone, int age, String gender) {
        ContentValues newValues = new ContentValues();

        newValues.put("NAME", name);
        newValues.put("EMAIL", email);
        newValues.put("Phone", phone);
        newValues.put("AGE", age);
        newValues.put("GENDER", gender);


        db.insert("PERSONAL", null, newValues);

    }

    /*public int deleteEntry(String UserName)
    {

        String where="USERNAME=?";
        int numberOFEntriesDeleted= db.delete("LOGIN", where, new String[]{UserName}) ;

        return numberOFEntriesDeleted;
    }*/
    public String getSinlgeEntry(String userName) {
        Cursor cursor = db.query("PERSONAL", null, null, null, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password = cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return password;
    }

    /*public void  updateEntry(String userName,String password)
    {
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        updatedValues.put("USERNAME", userName);
        updatedValues.put("PASSWORD",password);

        String where="USERNAME = ?";
        db.update("LOGIN",updatedValues, where, new String[]{userName});
    }*/
    public List<Personal> getAllCategorys() {
        ArrayList<Personal> nameList = new ArrayList<Personal>();
        // Select All Query
        String selectQuery = "SELECT  * FROM PERSONAL";
        //SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        try {
            if (cursor.moveToFirst()) {
                do {
                    Personal p = new Personal();
                    p.setName(cursor.getString(1));
                    p.setEmail(cursor.getString(2));
                    p.setPhone(Integer.parseInt(cursor.getString(3)));
                    p.setAge(Integer.parseInt(cursor.getString(4)));
                    p.setGender(cursor.getString(5));
                    // Adding category to list
                    String row = cursor.getString(1)+"\t"+cursor.getString(2)+"\t"+cursor.getString(3)+"\t"+cursor.getString(4)+"\t"+cursor.getString(5);
                    MainActivity.rows.add(row);
                    nameList.add(p);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        db.close();
        // return category list
        return nameList;
    }
}

