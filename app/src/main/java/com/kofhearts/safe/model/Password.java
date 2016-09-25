package com.kofhearts.safe.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.kofhearts.safe.data.SafeDbHelper;
import com.kofhearts.safe.exception.NoRecordFoundException;

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

        String countQuery = "SELECT * FROM " + SafeDbHelper.SQL_PASSWORD_TABLE_NAME;

        Cursor cursor = readableDatabase.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        return count;

    }


    public static Password first() throws NoRecordFoundException{

        String [] columns = {SafeDbHelper.SQL_ID_COLUMN_NAME, SafeDbHelper.SQL_PASSWORD_TABLE_PASSWORD_COLUMN_NAME};

        Cursor cur = readableDatabase.query(SafeDbHelper.SQL_PASSWORD_TABLE_NAME, columns, null, null,null,null,"1");

        cur.moveToFirst();

        if(cur.getCount() == 0){

            cur.close();
            throw new NoRecordFoundException("The table was empty so it doesn't have any items");

        }


        long id = cur.getLong(cur.getColumnIndex(SafeDbHelper.SQL_ID_COLUMN_NAME));
        String password = cur.getString(cur.getColumnIndex(SafeDbHelper.SQL_PASSWORD_TABLE_PASSWORD_COLUMN_NAME));

        cur.close();

        return new Password(id, password);

    }

    public static long create(String password){

        ContentValues values = new ContentValues();

        values.put(SafeDbHelper.SQL_PASSWORD_TABLE_PASSWORD_COLUMN_NAME, password);


        return writableDatabase.insert(SafeDbHelper.SQL_PASSWORD_TABLE_NAME, null, values);

    }


    public static Password get(long id) throws NoRecordFoundException{

        String [] columns = {SafeDbHelper.SQL_PASSWORD_TABLE_PASSWORD_COLUMN_NAME};

        String selection = SafeDbHelper.SQL_ID_COLUMN_NAME + " = ?";

        String [] selectionArgs = {String.valueOf(id)};

        Cursor cur = readableDatabase.query(SafeDbHelper.SQL_PASSWORD_TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        cur.moveToFirst();

        if(cur.getCount() == 0){

            cur.close();
            throw new NoRecordFoundException("The table was empty so it doesn't have any items");

        }


        String password = cur.getString(cur.getColumnIndex(SafeDbHelper.SQL_PASSWORD_TABLE_PASSWORD_COLUMN_NAME));

        cur.close();

        return new Password(id, password);


    }

    public int save(){


        ContentValues values = new ContentValues();

        values.put(SafeDbHelper.SQL_PASSWORD_TABLE_PASSWORD_COLUMN_NAME, this.password);

        String [] selectionArgs = {String.valueOf(this.id)};

        return writableDatabase.update(SafeDbHelper.SQL_PASSWORD_TABLE_NAME, values, SafeDbHelper.SQL_ID_COLUMN_NAME + " = ?", selectionArgs);

    }


    public int delete(){

        String [] selectionArgs = {String.valueOf(id)};

        return writableDatabase.delete(SafeDbHelper.SQL_PASSWORD_TABLE_NAME, SafeDbHelper.SQL_ID_COLUMN_NAME + " = ?", selectionArgs);


    }



    public static Password[] list(){

        String [] columns = {SafeDbHelper.SQL_ID_COLUMN_NAME, SafeDbHelper.SQL_PASSWORD_TABLE_PASSWORD_COLUMN_NAME};

        Cursor cur = readableDatabase.query(SafeDbHelper.SQL_PASSWORD_TABLE_NAME, columns, null, null, null, null, null);

        cur.moveToFirst();

        int size = cur.getCount();

        Password [] entries = new Password[size];

        for(int i=0; i<size; i++){

            long id = cur.getLong(cur.getColumnIndex(SafeDbHelper.SQL_ID_COLUMN_NAME));
            String password = cur.getString(cur.getColumnIndex(SafeDbHelper.SQL_PASSWORD_TABLE_PASSWORD_COLUMN_NAME));

            entries[i] = new Password(id, password);

            cur.moveToNext();

        }

        cur.close();

        return entries;

    }



}
