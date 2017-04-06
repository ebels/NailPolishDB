package com.nailpolish.nailpolishdb;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ViewDatabase extends AppCompatActivity {

    private static final String TAG = ViewDatabase.class.getSimpleName();
    NailPolishDBHelper dbHelper;
    private SimpleCursorAdapter dataAdapter;    // This is the Adapter being used to display the list's data
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_database);
        Log.d(TAG, "onCreate() start Activity..." );

        /* ------- TOOLBAR ------- */
        /** sets the toolbar as the app bar for the activity */
        Toolbar Toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(Toolbar);
        // Get a support actionBar corresponding to this toolbar
        ActionBar arrowup = getSupportActionBar();
        // Enable the "up" button
        arrowup.setDisplayHomeAsUpEnabled(true);

        Log.d(TAG, "onCreate() create cursor fetchallNPs()..." );
        cursor = dbHelper.fetchAllNPs();

        // For the cursor adapter, specify which columns go into which views
        String[] fromColumns = new String[]{dbHelper.COLUMN_NAME, dbHelper.COLUMN_NPID, dbHelper.COLUMN_BRAND, dbHelper.COLUMN_COLLECTION, dbHelper.COLUMN_COLOR, dbHelper.COLUMN_FINISH};
        int[] toViews = {R.id.name_tv, R.id.id_tv, R.id.brand_tv, R.id.collection_tv, R.id.color_tv, R.id.finish_tv};

        Log.d(TAG, "onCreate() create Data Adapter..." );
        // Create an empty adapter we will use to display the loaded data.
        // We pass null for the cursor, then update it in onLoadFinished()
        dataAdapter = new SimpleCursorAdapter(this,
                R.layout.items, cursor,
                fromColumns, toViews, 0);

        Log.d(TAG, "onCreate() Assign ListView to Adapter..." );
        ListView listView = (ListView) findViewById(R.id.listview1);
        //Assign adpter to ListView
        listView.setAdapter(dataAdapter);
    }
}
