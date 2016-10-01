package com.kofhearts.safe.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.kofhearts.safe.data.SafeDbHelper;
import com.kofhearts.safe.exception.NoRecordFoundException;

/**
 * Entry class represents an entry that is defined by three properties namely id, title and content. For example: An entry can be a bank account information in which case
 * the title can be Bank Account Number and content can be 123812673
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


    /**
     *
     * Method to create a new Entry record in database.
     *
     * @param title Title of Entry
     * @param content Contents of Entry
     * @return id of the Entry record in database
     */

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
     * @exception NoRecordFoundException If a record doesn't exist in database with given id then this exception is thrown.
     */

    public static Entry get(long id) throws NoRecordFoundException{

        String [] columns = {SafeDbHelper.SQL_ENTRY_TABLE_TITLE_COLUMN_NAME, SafeDbHelper.SQL_ENTRY_TABLE_CONTENT_COLUMN_NAME};

        String selection = SafeDbHelper.SQL_ID_COLUMN_NAME + " = ?";

        String [] selectionArgs = {String.valueOf(id)};

        Cursor cur = readableDatabase.query(SafeDbHelper.SQL_ENTRY_TABLE_NAME, columns, selection, selectionArgs, null, null, null);


        cur.moveToFirst();

        if(cur.getCount() == 0){

            cur.close();
            throw new NoRecordFoundException("The table was empty so it doesn't have any items");

        }


        String title = cur.getString(cur.getColumnIndex(SafeDbHelper.SQL_ENTRY_TABLE_TITLE_COLUMN_NAME));
        String content = cur.getString(cur.getColumnIndex(SafeDbHelper.SQL_ENTRY_TABLE_CONTENT_COLUMN_NAME));

        cur.close();

        return new Entry(id, title, content);

    }


    /**
     *
     * Fetches all Entry records from database.
     *
     *
     * @return A list or array of entries
     */

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


    /**
     *
     * Fetches the first Entry record from database.
     *
     * @return Fetched Entry record
     * @throws NoRecordFoundException If there are no entry records then this exception is thrown.
     */

    public static Entry first() throws NoRecordFoundException{

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


    /**
     *Saves the Entry record in database. This is normally called when an Entry record is fetched from database and then updated.
     *
     *
     * @return Number representing number of rows affected or updated.
     */

    public int save(){

        ContentValues values = new ContentValues();

        values.put(SafeDbHelper.SQL_ENTRY_TABLE_TITLE_COLUMN_NAME, this.title);
        values.put(SafeDbHelper.SQL_ENTRY_TABLE_CONTENT_COLUMN_NAME, this.content);

        String [] selectionArgs = {String.valueOf(this.id)};

        return writableDatabase.update(SafeDbHelper.SQL_ENTRY_TABLE_NAME, values, SafeDbHelper.SQL_ID_COLUMN_NAME + " = ?", selectionArgs);

    }

    /**
     *
     *Deletes the Entry record from the database. Normally this method is called after an Entry is fetched from the database. Example usage: Entry.get(1).delete()
     *
     * @return Number representing number of rows affected or deleted.
     */

    public int delete(){

        String [] selectionArgs = {String.valueOf(id)};

        return writableDatabase.delete(SafeDbHelper.SQL_ENTRY_TABLE_NAME, SafeDbHelper.SQL_ID_COLUMN_NAME + " = ?", selectionArgs);

    }

}
