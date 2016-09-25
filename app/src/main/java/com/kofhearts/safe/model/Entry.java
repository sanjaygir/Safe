package com.kofhearts.safe.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.kofhearts.safe.data.SafeDbHelper;
import com.kofhearts.safe.exception.NoRecordFoundException;

/**
 * Entry class is an entry with id, title and content
 */

public class Entry extends ActiveRecord{

    private long id;
    private String title;
    private String content;

    public Entry(long id, String title, String content){

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

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    /**
     *
     * Gets the total Entry records
     *
     * @return Entry Total records in Entry table
     *
     *
     */


    public static int getTotalCount(){

        String countQuery = "SELECT * FROM " + SafeDbHelper.SQL_ENTRY_TABLE_NAME;

        Cursor cursor = readableDatabase.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        return count;

    }

    public static long create(String title, String content){

        ContentValues values = new ContentValues();

        values.put(SafeDbHelper.SQL_ENTRY_TABLE_TITLE_COLUMN_NAME, title);
        values.put(SafeDbHelper.SQL_ENTRY_TABLE_CONTENT_COLUMN_NAME, content);


        return writableDatabase.insert(SafeDbHelper.SQL_ENTRY_TABLE_NAME, null, values);

    }

    /**
     *
     * Gets the Entry record from database with given id.
     *
     * @param id Entry id of the Entry seeking to be taken out from database
     * @return Entry record
     *
     *
     */

    public static Entry get(long id){

        String [] columns = {SafeDbHelper.SQL_ENTRY_TABLE_TITLE_COLUMN_NAME, SafeDbHelper.SQL_ENTRY_TABLE_CONTENT_COLUMN_NAME};

        String selection = SafeDbHelper.SQL_ID_COLUMN_NAME + " = ?";

        String [] selectionArgs = {String.valueOf(id)};

        Cursor cur = readableDatabase.query(SafeDbHelper.SQL_ENTRY_TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        cur.moveToFirst();

        String title = cur.getString(cur.getColumnIndex(SafeDbHelper.SQL_ENTRY_TABLE_TITLE_COLUMN_NAME));
        String content = cur.getString(cur.getColumnIndex(SafeDbHelper.SQL_ENTRY_TABLE_CONTENT_COLUMN_NAME));

        cur.close();

        return new Entry(id, title, content);

    }


    public static Entry[] list(){

        String [] columns = {SafeDbHelper.SQL_ID_COLUMN_NAME, SafeDbHelper.SQL_ENTRY_TABLE_TITLE_COLUMN_NAME, SafeDbHelper.SQL_ENTRY_TABLE_CONTENT_COLUMN_NAME};


        Cursor cur = readableDatabase.query(SafeDbHelper.SQL_ENTRY_TABLE_NAME, columns, null, null, null, null, null);

        cur.moveToFirst();

        int size = cur.getCount();

        Entry [] entries = new Entry[size];

        for(int i=0; i<size; i++){

            long id = cur.getLong(cur.getColumnIndex(SafeDbHelper.SQL_ID_COLUMN_NAME));
            String title = cur.getString(cur.getColumnIndex(SafeDbHelper.SQL_ENTRY_TABLE_TITLE_COLUMN_NAME));
            String content = cur.getString(cur.getColumnIndex(SafeDbHelper.SQL_ENTRY_TABLE_CONTENT_COLUMN_NAME));


            entries[i] = new Entry(id, title, content);

            cur.moveToNext();

        }

        cur.close();

        return entries;

    }


    public static Entry first() throws Exception{

        String [] columns = {SafeDbHelper.SQL_ID_COLUMN_NAME, SafeDbHelper.SQL_ENTRY_TABLE_TITLE_COLUMN_NAME, SafeDbHelper.SQL_ENTRY_TABLE_CONTENT_COLUMN_NAME};

        Cursor cur = readableDatabase.query(SafeDbHelper.SQL_ENTRY_TABLE_NAME, columns, null, null,null,null,"1");

        cur.moveToFirst();

        if(cur.getCount() == 0){

            cur.close();
            throw new NoRecordFoundException("The table was empty so it doesn't have any items");

        }

        long id = cur.getLong(cur.getColumnIndex(SafeDbHelper.SQL_ID_COLUMN_NAME));
        String title = cur.getString(cur.getColumnIndex(SafeDbHelper.SQL_ENTRY_TABLE_TITLE_COLUMN_NAME));
        String content = cur.getString(cur.getColumnIndex(SafeDbHelper.SQL_ENTRY_TABLE_CONTENT_COLUMN_NAME));

        cur.close();

        return new Entry(id, title, content);

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
