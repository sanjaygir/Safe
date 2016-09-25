package com.kofhearts.safe.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.kofhearts.safe.data.SafeDbHelperReal;

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
     */


    public static int getTotalCount(){

        String countQuery = "SELECT * FROM " + SafeDbHelperReal.SQL_ENTRY_TABLE_NAME;

        Cursor cursor = readableDatabase.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        return count;

    }

    public static long create(String title, String content){

        ContentValues values = new ContentValues();

        values.put(SafeDbHelperReal.SQL_ENTRY_TABLE_TITLE_COLUMN_NAME, title);
        values.put(SafeDbHelperReal.SQL_ENTRY_TABLE_CONTENT_COLUMN_NAME, content);


        return writableDatabase.insert(SafeDbHelperReal.SQL_ENTRY_TABLE_NAME, null, values);

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

        String [] columns = {SafeDbHelperReal.SQL_ENTRY_TABLE_TITLE_COLUMN_NAME, SafeDbHelperReal.SQL_ENTRY_TABLE_CONTENT_COLUMN_NAME};

        String selection = SafeDbHelperReal.SQL_ID_COLUMN_NAME + " = ?";

        String [] selectionArgs = {String.valueOf(id)};

        Cursor cur = readableDatabase.query(SafeDbHelperReal.SQL_ENTRY_TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        cur.moveToFirst();

        String title = cur.getString(cur.getColumnIndex(SafeDbHelperReal.SQL_ENTRY_TABLE_TITLE_COLUMN_NAME));
        String content = cur.getString(cur.getColumnIndex(SafeDbHelperReal.SQL_ENTRY_TABLE_CONTENT_COLUMN_NAME));

        cur.close();

        return new Entry(id, title, content);


    }

    public int save(){

        ContentValues values = new ContentValues();

        values.put(SafeDbHelperReal.SQL_ENTRY_TABLE_TITLE_COLUMN_NAME, this.title);
        values.put(SafeDbHelperReal.SQL_ENTRY_TABLE_CONTENT_COLUMN_NAME, this.content);

        String [] selectionArgs = {String.valueOf(this.id)};

        return writableDatabase.update(SafeDbHelperReal.SQL_ENTRY_TABLE_NAME, values, SafeDbHelperReal.SQL_ID_COLUMN_NAME + " = ?", selectionArgs);

    }

    public int delete(){

        String [] selectionArgs = {String.valueOf(id)};

        return writableDatabase.delete(SafeDbHelperReal.SQL_ENTRY_TABLE_NAME, SafeDbHelperReal.SQL_ID_COLUMN_NAME + " = ?", selectionArgs);

    }

}
