package com.kofhearts.safe.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kofhearts.safe.data.SafeDbHelper;

/**
 * Created by Sanjay1 on 9/24/2016.
 */

public class Entry {

    private static SafeDbHelper dbHelper;
    private static SQLiteDatabase readableDatabase;
    private static SQLiteDatabase writableDatabase;

    private int id;
    private String title;
    private String content;

    public Entry(Context context, int id, String title, String content){

        if(context != null) {
            dbHelper = new SafeDbHelper(context);
            readableDatabase = dbHelper.getReadableDatabase();
            writableDatabase = dbHelper.getWritableDatabase();
        }

        this.id = id;
        this.title = title;
        this.content = content;

    }



    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public static int getTotalCount(){

        String countQuery = "SELECT * FROM " + SafeDbHelper.SQL_ENTRY_TABLE_NAME;

        Cursor cursor = readableDatabase.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        return count;

    }

    public static long createEntry(String title, String content){

        ContentValues values = new ContentValues();

        values.put(SafeDbHelper.SQL_ENTRY_TABLE_TITLE_COLUMN_NAME, title);
        values.put(SafeDbHelper.SQL_ENTRY_TABLE_CONTENT_COLUMN_NAME, content);


        return writableDatabase.insert(SafeDbHelper.SQL_ENTRY_TABLE_NAME, null, values);

    }

    public static Entry get(int id){

        String [] columns = {SafeDbHelper.SQL_ENTRY_TABLE_TITLE_COLUMN_NAME, SafeDbHelper.SQL_ENTRY_TABLE_CONTENT_COLUMN_NAME};

        String selection = SafeDbHelper.SQL_ID_COLUMN_NAME + " = ?";

        String [] selectionArgs = {String.valueOf(id)};

        Cursor cur = readableDatabase.query(SafeDbHelper.SQL_ENTRY_TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        cur.moveToFirst();

        String title = cur.getString(cur.getColumnIndex(SafeDbHelper.SQL_ENTRY_TABLE_TITLE_COLUMN_NAME));
        String content = cur.getString(cur.getColumnIndex(SafeDbHelper.SQL_ENTRY_TABLE_CONTENT_COLUMN_NAME));

        cur.close();

        return new Entry(null, id, title, content);


    }

    public int save(){

        ContentValues values = new ContentValues();

        values.put(SafeDbHelper.SQL_ENTRY_TABLE_TITLE_COLUMN_NAME, this.title);
        values.put(SafeDbHelper.SQL_ENTRY_TABLE_CONTENT_COLUMN_NAME, this.content);

        String [] selectionArgs = {String.valueOf(this.id)};

        return writableDatabase.update(SafeDbHelper.SQL_ENTRY_TABLE_NAME, values, SafeDbHelper.SQL_ID_COLUMN_NAME + " = ?", selectionArgs);

    }


    public int delete(){

        String [] selectionArgs = {String.valueOf(id)};

        return writableDatabase.delete(SafeDbHelper.SQL_ENTRY_TABLE_NAME, SafeDbHelper.SQL_ID_COLUMN_NAME + " = ?", selectionArgs);


    }




}
