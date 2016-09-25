package com.kofhearts.safe.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.kofhearts.safe.data.SafeDbHelper;
import com.kofhearts.safe.data.SafeDbHelperReal;
import com.kofhearts.safe.data.SafeDbHelperTest;

/**
 * Created by Sanjay1 on 9/25/2016.
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

            if(test){

                dbHelper = new SafeDbHelperTest(context);

                readableDatabase = ((SafeDbHelperTest)dbHelper).getReadableDatabase();
                writableDatabase = ((SafeDbHelperTest)dbHelper).getWritableDatabase();

            }
            else{

                dbHelper = new SafeDbHelperReal(context);

                readableDatabase = ((SafeDbHelperReal)dbHelper).getReadableDatabase();
                writableDatabase = ((SafeDbHelperReal)dbHelper).getWritableDatabase();

            }


        }

    }


}
