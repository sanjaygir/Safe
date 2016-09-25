package com.kofhearts.safe.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sanjay1 on 9/24/2016.
 */

public class SafeDbHelperReal extends SQLiteOpenHelper implements SafeDbHelper {

    public static final String DATABASE_NAME = "safe.db";

    public SafeDbHelperReal(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_PASSWORD);
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL(SQL_DELETE_PASSWORD);
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRY);

        onCreate(sqLiteDatabase);


    }
}
