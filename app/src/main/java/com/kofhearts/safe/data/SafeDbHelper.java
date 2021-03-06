package com.kofhearts.safe.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Safe DB Helper Class. Responsible for creating database, upgrading database. Also can be considered entry point for database operations. Provides access to database for database operations.
 */

public class SafeDbHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;

    public static final String SQL_PASSWORD_TABLE_NAME = "password";

    public static final String SQL_ENTRY_TABLE_NAME = "entry";

    public static final String SQL_PASSWORD_TABLE_EMAIL_COLUMN_NAME = "email";

    public static final String SQL_PASSWORD_TABLE_HINT_COLUMN_NAME = "hint";

    public static final String SQL_PASSWORD_TABLE_PASSWORD_COLUMN_NAME = "password";


    public static final String SQL_ENTRY_TABLE_TITLE_COLUMN_NAME = "title";

    public static final String SQL_ENTRY_TABLE_CONTENT_COLUMN_NAME = "content";

    public static final String SQL_ID_COLUMN_NAME = "id";

    public static final String SQL_CREATE_PASSWORD =
            "CREATE TABLE IF NOT EXISTS " + SQL_PASSWORD_TABLE_NAME +" ("+SQL_ID_COLUMN_NAME+" INTEGER PRIMARY KEY, "+SQL_PASSWORD_TABLE_EMAIL_COLUMN_NAME + " VARCHAR(255), " + SQL_PASSWORD_TABLE_PASSWORD_COLUMN_NAME + " VARCHAR(255), "  + SQL_PASSWORD_TABLE_HINT_COLUMN_NAME + " VARCHAR(255))";

    public static final String SQL_DELETE_PASSWORD =
            "DROP TABLE IF EXISTS " + SQL_PASSWORD_TABLE_NAME;

    public static final String SQL_CREATE_ENTRY =
            "CREATE TABLE IF NOT EXISTS " + SQL_ENTRY_TABLE_NAME + " ("+SQL_ID_COLUMN_NAME+" INTEGER PRIMARY KEY, "+SQL_ENTRY_TABLE_TITLE_COLUMN_NAME+" VARCHAR(255), "+SQL_ENTRY_TABLE_CONTENT_COLUMN_NAME+" TEXT)";

    public static final String SQL_DELETE_ENTRY =
            "DROP TABLE IF EXISTS " + SQL_ENTRY_TABLE_NAME;


    public static final String DATABASE_NAME_REAL = "safe.db";

    public static final String DATABASE_NAME_TEST = "safetest.db";


    public SafeDbHelper(Context context, boolean test){

        super(context, test? DATABASE_NAME_TEST :DATABASE_NAME_REAL , null, DATABASE_VERSION);

    }


    /**
     *
     * Overridden method that will create the necessary tables.
     *
     * @param sqLiteDatabase database
     */

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_PASSWORD);
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRY);
    }

    /**
     * Overridden method that is responsible for upgrading the database.
     *
     * @param sqLiteDatabase Database
     * @param i old database version
     * @param i1 new database version
     */

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL(SQL_DELETE_PASSWORD);
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRY);

        onCreate(sqLiteDatabase);


    }

}
