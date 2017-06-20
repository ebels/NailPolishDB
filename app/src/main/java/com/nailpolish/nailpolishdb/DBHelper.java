package com.nailpolish.nailpolishdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ebelsheiser on 03.04.2017.
 */

public class DBHelper extends SQLiteOpenHelper{

    private static final String TAG = DBHelper.class.getSimpleName();

    /* ------- DEFINE CONSTANTS ------- */
    public static final String DB_NAME = "nailpolish_list.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "nailpolish_list";

    public static final String COLUMN_ID = " _id ";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_NPID = "npid";
    public static final String COLUMN_BRAND = "brand";
    public static final String COLUMN_COLLECTION = "collection";
    public static final String COLUMN_COLOR = "color";
    public static final String COLUMN_FINISH = "finish";
    public static final String COLUMN_IMAGE = "image";


    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_NPID + " INTEGER, " +
                    COLUMN_BRAND + " TEXT, " +
                    COLUMN_COLLECTION + " TEXT, " +
                    COLUMN_COLOR + " TEXT, " +
                    COLUMN_FINISH + " TEXT)" +
                    COLUMN_IMAGE+"BLOBNOTNULL";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    /* ------- CONSTRUCTOR ------- */
    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES); // Creating tables
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "Upgrading database from version "+oldVersion + " to " +newVersion + ", which will destroy all data");
        db.execSQL(SQL_DELETE_ENTRIES);   // Drop older table if existed
        onCreate(db);   // Create tables again
    }

    public void insertNP (String name, String npid, String brand, String collection, String color, String finish, byte[] imageBytes) {
        Log.d(TAG, "insertNP() making database writable");
        SQLiteDatabase db = this.getWritableDatabase(); // Gets the data repository in write mode

        Log.d(TAG, "insertNP() Content values");
        ContentValues values = new ContentValues(); // Create a new map of values, where column names are the keys
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_NPID, npid);
        values.put(COLUMN_BRAND, brand);
        values.put(COLUMN_COLLECTION, collection);
        values.put(COLUMN_COLOR, color);
        values.put(COLUMN_FINISH, finish);
        values.put(COLUMN_IMAGE, imageBytes);

        Log.d(TAG, "insertNP() executing query, inserting entries");
        db.insert(TABLE_NAME, null, values);    // Insert the new row
        db.close();
        Log.d(TAG, "insertNP() insert successfull, close database connection");
    }

    public void updateNP (String id, String name, String npid, String brand, String collection, String color, String finish) {
        Log.d(TAG, "updateNP() making database writable");
        SQLiteDatabase db = this.getWritableDatabase();

        Log.d(TAG, "updateNP() insert new values...");
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_NPID, npid);
        values.put(COLUMN_BRAND, brand);
        values.put(COLUMN_COLLECTION, collection);
        values.put(COLUMN_COLOR, color);
        values.put(COLUMN_FINISH, finish);

        String selection = COLUMN_ID + " LIKE ?";   // Which row to update, based on id
        String[] selectionArgs = {id};              //specify arguments

        Log.d(TAG, "updateNP() executing query, inserting entries");
        db.update(TABLE_NAME, values, selection, selectionArgs);
        db.close();
        Log.d(TAG, "updateNP() update successfull, close database connection");
    }

    public Cursor fetchAllNPs() {
        SQLiteDatabase db = this.getReadableDatabase();

        // Define a projection that specifies which columns from the database  will actually be used after this query
        String[] projection = {
                COLUMN_ID,
                COLUMN_NAME,
                COLUMN_NPID,
                COLUMN_BRAND,
                COLUMN_COLLECTION,
                COLUMN_COLOR,
                COLUMN_FINISH,
                COLUMN_IMAGE
        };

        Log.d(TAG, "fetchallNPs() Cursor..." );
        Cursor cursor = db.query(
                TABLE_NAME, // Table name
                projection, // columns to return
                null,       // filter results (columns for the WHERE clause ...)
                null,       // filter results (values for the WHERE clause ...)
                null,       // goup the rows
                null,       // filter by groups
                null        // sort order
                );
        Log.d(TAG, "fetchallNPs() return cursor..." );
        return cursor;
    }

    public void deleteNP(String id) {
        Log.d(TAG, "deleteNP() make database writable...");
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = COLUMN_ID + " LIKE ?";   // Which row to update, based on id
        String[] selectionArgs = {id};              //specify arguments

        Log.d(TAG, "deleteNP() exec SQL statement...");
        db.delete(TABLE_NAME, selection, selectionArgs);
        Log.d(TAG, "deleteNP() close database connection...");
        db.close();
    }

    public Cursor fetchentireNP(String id) {
        //String query = "SELECT * FROM " + TABLE_NAME + " WHERE _id = " + id;
        //Cursor cursor = getReadableDatabase().rawQuery(query,null);
        //return cursor;

        SQLiteDatabase db = this.getReadableDatabase();

        // Define a projection that specifies which columns from the database  will actually be used after this query
        String[] projection = {
                COLUMN_NAME,
                COLUMN_NPID,
                COLUMN_BRAND,
                COLUMN_COLLECTION,
                COLUMN_COLOR,
                COLUMN_FINISH,
                COLUMN_IMAGE
        };

        // Filter results WHERE "_id" = 'id'
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {id};

        Log.d(TAG, "fetchentireNPs() Cursor..." );
        Cursor cursor = db.query(
                TABLE_NAME,     // Table name
                projection,     // columns to return
                selection,      // filter results (columns for the WHERE clause ...)
                selectionArgs,  // filter results (values for the WHERE clause ...)
                null,           // goup the rows
                null,           // filter by groups
                null            // sort order
        );
        Log.d(TAG, "fetchentireNPs() return cursor..." );
        return cursor;
    }
}
