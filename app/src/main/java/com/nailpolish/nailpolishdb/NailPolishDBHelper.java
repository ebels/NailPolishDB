package com.nailpolish.nailpolishdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ebelsheiser on 03.04.2017.
 */

public class NailPolishDBHelper extends SQLiteOpenHelper {

    /* ------- DEFINE CONSTANTS ------- */
    public static final String DB_NAME = "nailpolish_list.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "nailpolish_list";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "product";
    public static final String COLUMN_NPID = "npid";
    public static final String COLUMN_BRAND = "brand";
    public static final String COLUMN_COLLECTION = "collection";
    public static final String COLUMN_COLOR = "color";
    public static final String COLUMN_FINISH = "finish";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT" +
                    COLUMN_NPID + "INTEGER" +
                    COLUMN_BRAND + "TEXT" +
                    COLUMN_COLLECTION + "TEXT" +
                    COLUMN_COLOR + "TEXT" +
                    COLUMN_FINISH + "TEXT)";

    /* ------- CONSTRUCTOR ------- */
    public NailPolishDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // Creating tables
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create tables again
        onCreate(db);
    }

    public void insertNP (String name, String id, String brand, String collection, String color, String finish) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, name);
        values.put(COLUMN_NPID, id);
        values.put(COLUMN_BRAND, brand);
        values.put(COLUMN_COLLECTION, collection);
        values.put(COLUMN_COLOR, color);
        values.put(COLUMN_FINISH, finish);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Cursor getallNPs () {
        String query = "SELECT * FROM " + TABLE_NAME;
        
    }
}
