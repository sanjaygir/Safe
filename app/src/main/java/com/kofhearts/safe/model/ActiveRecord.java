package com.kofhearts.safe.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.kofhearts.safe.data.SafeDbHelper;

/**
 *
 * Base or parent class for all models/domains. Responsible for doing the database initializations before carrying out database operations.
 *
 */

public abstract class ActiveRecord {

    protected static SafeDbHelper dbHelper;
    protected static SQLiteDatabase readableDatabase;
    protected static SQLiteDatabase writableDatabase;


    /**
     *
     * Gets a readable database so that read operations can be requested.
     *
     * @return SQLite database
     */

    public static SQLiteDatabase getReadableDatabase(){

        return readableDatabase;

    }


    /**
     *
     * This will initialize the database. The first thing to do before creating and updating database records is to call initialize static method from onCreate method of activity.
     *
     *
     * @param context context of activity
     * @param test Test Mode. test will be true if doing unit or integration tests. In real usage test will be false.
     */

    public static void initialize(Context context, boolean test){

        if(context != null) {

            dbHelper = new SafeDbHelper(context, test);

            readableDatabase = dbHelper.getReadableDatabase();
            writableDatabase = dbHelper.getWritableDatabase();


        }

    }





}
