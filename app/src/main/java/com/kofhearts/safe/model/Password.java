package com.kofhearts.safe.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.kofhearts.safe.data.SafeDbHelperReal;

/**
 * Created by Sanjay1 on 9/25/2016.
 */

public class Password extends ActiveRecord{

    private long id;
    private String password;


    public Password(long id, String password){
        this.id = id;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public static int getTotalCount(){

        String countQuery = "SELECT * FROM " + SafeDbHelperReal.SQL_PASSWORD_TABLE_NAME;

        Cursor cursor = readableDatabase.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        return count;

    }

    public static long create(String password){

        ContentValues values = new ContentValues();

        values.put(SafeDbHelperReal.SQL_PASSWORD_TABLE_PASSWORD_COLUMN_NAME, password);


        return writableDatabase.insert(SafeDbHelperReal.SQL_PASSWORD_TABLE_NAME, null, values);

    }


    public static Password get(long id){

        String [] columns = {SafeDbHelperReal.SQL_PASSWORD_TABLE_PASSWORD_COLUMN_NAME};

        String selection = SafeDbHelperReal.SQL_ID_COLUMN_NAME + " = ?";

        String [] selectionArgs = {String.valueOf(id)};

        Cursor cur = readableDatabase.query(SafeDbHelperReal.SQL_PASSWORD_TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        cur.moveToFirst();

        String password = cur.getString(cur.getColumnIndex(SafeDbHelperReal.SQL_PASSWORD_TABLE_PASSWORD_COLUMN_NAME));

        cur.close();

        return new Password(id, password);


    }

    public int save(){


        ContentValues values = new ContentValues();

        values.put(SafeDbHelperReal.SQL_PASSWORD_TABLE_PASSWORD_COLUMN_NAME, this.password);

        String [] selectionArgs = {String.valueOf(this.id)};

        return writableDatabase.update(SafeDbHelperReal.SQL_PASSWORD_TABLE_NAME, values, SafeDbHelperReal.SQL_ID_COLUMN_NAME + " = ?", selectionArgs);

    }


    public int delete(){

        String [] selectionArgs = {String.valueOf(id)};

        return writableDatabase.delete(SafeDbHelperReal.SQL_PASSWORD_TABLE_NAME, SafeDbHelperReal.SQL_ID_COLUMN_NAME + " = ?", selectionArgs);


    }




}
