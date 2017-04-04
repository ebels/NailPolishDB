package com.nailpolish.nailpolishdb;

import android.content.ContentValues;
import android.content.Context;
import android.content.SearchRecentSuggestionsProvider;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ebelsheiser on 03.04.2017.
 */

public class NailPolishDBHelper extends SQLiteOpenHelper {

    /* ------- DEFINE CONSTANTS ------- */
    /** If you change the database schema, you must increment the database version. */
    public static final String DB_NAME = "nailpolish_list.db";  //Databasename
    public static final int DB_VERSION = 1;     //Databaseversion
    public static final String TABLE_NAILPOLISH_LIST = "nailpolish_list";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "product";
    public static final String COLUMN_NPID = "npid";
    public static final String COLUMN_BRAND = "brand";
    public static final String COLUMN_COLLECTION = "collection";
    public static final String COLUMN_COLOR = "color";
    public static final String COLUMN_FINISH = "finish";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAILPOLISH_LIST + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT NOT NULL, " +
                    COLUMN_NPID + "INTEGER NOT NULL" +
                    COLUMN_BRAND + "TEXT NOT NULL" +
                    COLUMN_COLLECTION + "TEXT NOT NULL" +
                    COLUMN_COLOR + "TEXT NOT NULL" +
                    COLUMN_FINISH + " INTEGER NOT NULL);";

    /* ------- CONSTRUCTOR ------- */
    public NailPolishDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // Creating tables
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertNP (String name, String id, String brand, String collection, String color, String finish) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NailPolishDBHelper.COLUMN_ID, COLUMN_ID);
        values.put(NailPolishDBHelper.COLUMN_NAME, name);
        values.put(NailPolishDBHelper.COLUMN_BRAND, brand);
        values.put(NailPolishDBHelper.COLUMN_COLLECTION, collection);
        values.put(NailPolishDBHelper.COLUMN_COLOR, color);
        values.put(NailPolishDBHelper.COLUMN_FINISH, finish);

        db.insert(TABLE_NAILPOLISH_LIST, null, values);
        db.close();
    }

    public void ListViewNP (){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                COLUMN_ID,
                COLUMN_NAME,
                COLUMN_NPID,
                COLUMN_BRAND,
                COLUMN_COLLECTION,
                COLUMN_COLOR,
                COLUMN_FINISH
        };

        Cursor cursor = db.query(
                TABLE_NAILPOLISH_LIST,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        List NailPolishes = new ArrayList<>();
        while(cursor.moveToNext()){
            long nailpolish = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID));
            NailPolishes.add(nailpolish);
        }
        cursor.close();
    }
}
