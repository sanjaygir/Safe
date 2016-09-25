package com.kofhearts.safe.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.kofhearts.safe.data.SafeDbHelper;

/**
 *
 * Base class for all models.
 *
 */

public abstract class ActiveRecord {

    protected static SafeDbHelper dbHelper;
    protected static SQLiteDatabase readableDatabase;
    protected static SQLiteDatabase writableDatabase;


    public static SQLiteDatabase getReadableDatabase(){

        return readableDatabase;

    }


    public static void initialize(Context context, boolean test){

        if(context != null) {

            dbHelper = new SafeDbHelper(context, test);

            readableDatabase = dbHelper.getReadableDatabase();
            writableDatabase = dbHelper.getWritableDatabase();


        }

    }





}
