package com.nailpolish.nailpolishdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * Created by ebelsheiser on 03.04.2017.
 */

public class NailPolishDBHelper extends SQLiteOpenHelper{

    private static final String TAG = NailPolishDBHelper.class.getSimpleName();

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


    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_NPID + " INTEGER, " +
                    COLUMN_BRAND + " TEXT, " +
                    COLUMN_COLLECTION + " TEXT, " +
                    COLUMN_COLOR + " TEXT, " +
                    COLUMN_FINISH + " TEXT)";

    /* ------- CONSTRUCTOR ------- */
    public NailPolishDBHelper (Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // Creating tables
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "Upgrading database from version "+oldVersion + " to " +newVersion + ", which will destroy all data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);   // Drop older table if existed
        onCreate(db);   // Create tables again
    }

    public void insertNP (String name, String npid, String brand, String collection, String color, String finish) {
        Log.d(TAG, "insertNP() making database writable");
        SQLiteDatabase db = this.getWritableDatabase();

        Log.d(TAG, "insertNP() Content values");
        ContentValues values = new ContentValues(6);

        values.put(COLUMN_NAME, name);
        values.put(COLUMN_NPID, npid);
        values.put(COLUMN_BRAND, brand);
        values.put(COLUMN_COLLECTION, collection);
        values.put(COLUMN_COLOR, color);
        values.put(COLUMN_FINISH, finish);

        Log.d(TAG, "insertNP() executing query, inserting entries");
        getWritableDatabase().insert(TABLE_NAME, null, values);
        db.close();
        Log.d(TAG, "insertNP() insert successfull, close database connection");
    }

    public Cursor fetchAllNPs() {
        Log.d(TAG, "fetchallNPs() Cursor..." );
        Cursor cursor = getReadableDatabase().query(TABLE_NAME, new String[] {COLUMN_ID, COLUMN_NAME, COLUMN_NPID, COLUMN_BRAND, COLUMN_COLLECTION, COLUMN_COLOR, COLUMN_FINISH}, null, null, null, null, null);
        Log.d(TAG, "fetchallNPs() return cursor..." );
        return cursor;
    }

    public void deleteNP(String id) {
        Log.d(TAG, "deleteNP() make database writable...");
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "deleteNP() exec SQL statement...");
        db.delete(TABLE_NAME, "_id=?", new String[]{id});
        Log.d(TAG, "deleteNP() close database connection...");
        db.close();
    }
}
