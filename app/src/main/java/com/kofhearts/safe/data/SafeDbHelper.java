package com.kofhearts.safe.data;

/**
 * Created by Sanjay1 on 9/25/2016.
 */

public interface SafeDbHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String SQL_PASSWORD_TABLE_NAME = "password";

    public static final String SQL_ENTRY_TABLE_NAME = "entry";

    public static final String SQL_PASSWORD_TABLE_PASSWORD_COLUMN_NAME = "password";

    public static final String SQL_ENTRY_TABLE_TITLE_COLUMN_NAME = "title";

    public static final String SQL_ENTRY_TABLE_CONTENT_COLUMN_NAME = "content";

    public static final String SQL_ID_COLUMN_NAME = "id";

    public static final String SQL_CREATE_PASSWORD =
            "CREATE TABLE IF NOT EXISTS " + SQL_PASSWORD_TABLE_NAME +" ("+SQL_ID_COLUMN_NAME+" INTEGER PRIMARY KEY, "+SQL_PASSWORD_TABLE_PASSWORD_COLUMN_NAME+" VARCHAR(255))";

    public static final String SQL_DELETE_PASSWORD =
            "DROP TABLE IF EXISTS " + SQL_PASSWORD_TABLE_NAME;

    public static final String SQL_CREATE_ENTRY =
            "CREATE TABLE IF NOT EXISTS " + SQL_ENTRY_TABLE_NAME + " ("+SQL_ID_COLUMN_NAME+" INTEGER PRIMARY KEY, "+SQL_ENTRY_TABLE_TITLE_COLUMN_NAME+" VARCHAR(255), "+SQL_ENTRY_TABLE_CONTENT_COLUMN_NAME+" TEXT)";

    public static final String SQL_DELETE_ENTRY =
            "DROP TABLE IF EXISTS " + SQL_ENTRY_TABLE_NAME;


}
