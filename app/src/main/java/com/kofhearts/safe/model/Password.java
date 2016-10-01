package com.kofhearts.safe.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.kofhearts.safe.data.SafeDbHelper;
import com.kofhearts.safe.exception.NoRecordFoundException;

/**
 * A class that represents password of the user. Password is used to authenticate user to the Safe app.
 */

public class Password extends ActiveRecord{

    private long id;

    private String email;
    private String password;

    private String hint;


    public Password(long id, String email, String password, String hint){
        this.id = id;
        this.email = email;
        this.password = password;
        this.hint = hint;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    /**
     *
     * Gets the total counts of passwords from the database.
     *
     * @return Number of password entries  from database.
     */

    public static int getTotalCount(){

        String countQuery = "SELECT * FROM " + SafeDbHelper.SQL_PASSWORD_TABLE_NAME;

        Cursor cursor = readableDatabase.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        return count;

    }


    /**
     *
     * Fetches the first Password entry from database.
     *
     * @return Password record
     * @throws NoRecordFoundException If there are no password entries in database then this exception is thrown.
     */

    public static Password first() throws NoRecordFoundException{

        String [] columns = {SafeDbHelper.SQL_ID_COLUMN_NAME, SafeDbHelper.SQL_PASSWORD_TABLE_EMAIL_COLUMN_NAME, SafeDbHelper.SQL_PASSWORD_TABLE_PASSWORD_COLUMN_NAME, SafeDbHelper.SQL_PASSWORD_TABLE_HINT_COLUMN_NAME};

        Cursor cur = readableDatabase.query(SafeDbHelper.SQL_PASSWORD_TABLE_NAME, columns, null, null,null,null,"1");

        cur.moveToFirst();

        if(cur.getCount() == 0){

            cur.close();
            throw new NoRecordFoundException("The table was empty so it doesn't have any items");

        }


        long id = cur.getLong(cur.getColumnIndex(SafeDbHelper.SQL_ID_COLUMN_NAME));
        String email = cur.getString(cur.getColumnIndex(SafeDbHelper.SQL_PASSWORD_TABLE_EMAIL_COLUMN_NAME));
        String password = cur.getString(cur.getColumnIndex(SafeDbHelper.SQL_PASSWORD_TABLE_PASSWORD_COLUMN_NAME));
        String hint = cur.getString(cur.getColumnIndex(SafeDbHelper.SQL_PASSWORD_TABLE_HINT_COLUMN_NAME));

        cur.close();

        return new Password(id, email, password, hint);

    }

    /**
     *
     * Creates a password entry.
     *
     * @param email user email
     * @param password user password
     * @param hint hint in case this password is forgotten
     * @return Id of password record.
     */

    public static long create(String email, String password, String hint){

        ContentValues values = new ContentValues();

        values.put(SafeDbHelper.SQL_PASSWORD_TABLE_EMAIL_COLUMN_NAME, email);
        values.put(SafeDbHelper.SQL_PASSWORD_TABLE_PASSWORD_COLUMN_NAME, password);
        values.put(SafeDbHelper.SQL_PASSWORD_TABLE_HINT_COLUMN_NAME, hint);

        return writableDatabase.insert(SafeDbHelper.SQL_PASSWORD_TABLE_NAME, null, values);

    }


    /**
     *
     * Fetches the password entry with given id.
     *
     * @param id Password entry's id
     * @return fetched Password entry from database
     * @throws NoRecordFoundException This exception is thrown if a password doesn't exist with given id.
     */

    public static Password get(long id) throws NoRecordFoundException{

        String [] columns = {SafeDbHelper.SQL_PASSWORD_TABLE_EMAIL_COLUMN_NAME, SafeDbHelper.SQL_PASSWORD_TABLE_PASSWORD_COLUMN_NAME, SafeDbHelper.SQL_PASSWORD_TABLE_HINT_COLUMN_NAME};

        String selection = SafeDbHelper.SQL_ID_COLUMN_NAME + " = ?";

        String [] selectionArgs = {String.valueOf(id)};

        Cursor cur = readableDatabase.query(SafeDbHelper.SQL_PASSWORD_TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        cur.moveToFirst();

        if(cur.getCount() == 0){

            cur.close();
            throw new NoRecordFoundException("The table was empty so it doesn't have any items");

        }


        String email = cur.getString(cur.getColumnIndex(SafeDbHelper.SQL_PASSWORD_TABLE_EMAIL_COLUMN_NAME));
        String password = cur.getString(cur.getColumnIndex(SafeDbHelper.SQL_PASSWORD_TABLE_PASSWORD_COLUMN_NAME));
        String hint = cur.getString(cur.getColumnIndex(SafeDbHelper.SQL_PASSWORD_TABLE_HINT_COLUMN_NAME));
        cur.close();

        return new Password(id, email, password, hint);


    }

    /**
     *
     * Saves the password entry to database. This is normally called when a password entry is updated.
     *
     * @return Total number of rows affected or row updated.
     */

    public int save(){

        ContentValues values = new ContentValues();

        values.put(SafeDbHelper.SQL_PASSWORD_TABLE_EMAIL_COLUMN_NAME, this.email);

        values.put(SafeDbHelper.SQL_PASSWORD_TABLE_PASSWORD_COLUMN_NAME, this.password);

        values.put(SafeDbHelper.SQL_PASSWORD_TABLE_HINT_COLUMN_NAME, this.hint);

        String [] selectionArgs = {String.valueOf(this.id)};

        return writableDatabase.update(SafeDbHelper.SQL_PASSWORD_TABLE_NAME, values, SafeDbHelper.SQL_ID_COLUMN_NAME + " = ?", selectionArgs);

    }

    /**
     * Deletes the password from database. This method is normally called on a password object intended to be deleted.
     *
     *
     * @return Total number of rows affected.
     */

    public int delete(){

        String [] selectionArgs = {String.valueOf(id)};

        return writableDatabase.delete(SafeDbHelper.SQL_PASSWORD_TABLE_NAME, SafeDbHelper.SQL_ID_COLUMN_NAME + " = ?", selectionArgs);


    }


    /**
     * Fetches all password entries from database.
     *
     * @return List of password entries.
     */

    public static Password[] list(){

        String [] columns = {SafeDbHelper.SQL_ID_COLUMN_NAME, SafeDbHelper.SQL_PASSWORD_TABLE_EMAIL_COLUMN_NAME, SafeDbHelper.SQL_PASSWORD_TABLE_PASSWORD_COLUMN_NAME, SafeDbHelper.SQL_PASSWORD_TABLE_HINT_COLUMN_NAME};

        Cursor cur = readableDatabase.query(SafeDbHelper.SQL_PASSWORD_TABLE_NAME, columns, null, null, null, null, null);

        cur.moveToFirst();

        int size = cur.getCount();

        Password [] entries = new Password[size];

        for(int i=0; i<size; i++){

            long id = cur.getLong(cur.getColumnIndex(SafeDbHelper.SQL_ID_COLUMN_NAME));

            String email = cur.getString(cur.getColumnIndex(SafeDbHelper.SQL_PASSWORD_TABLE_EMAIL_COLUMN_NAME));

            String password = cur.getString(cur.getColumnIndex(SafeDbHelper.SQL_PASSWORD_TABLE_PASSWORD_COLUMN_NAME));

            String hint = cur.getString(cur.getColumnIndex(SafeDbHelper.SQL_PASSWORD_TABLE_HINT_COLUMN_NAME));

            entries[i] = new Password(id, email, password, hint);

            cur.moveToNext();

        }

        cur.close();

        return entries;

    }



}
