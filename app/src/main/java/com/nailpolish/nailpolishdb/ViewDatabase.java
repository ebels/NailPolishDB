package com.nailpolish.nailpolishdb;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class ViewDatabase extends AppCompatActivity {

    private static final String TAG = ViewDatabase.class.getSimpleName();
    private SimpleCursorAdapter dataAdapter;    // This is the Adapter being used to display the list's data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_database);
        Log.d(TAG, "onCreate() start Activity..." );

        /* ------- TOOLBAR ------- */
        /** sets the toolbar as the app bar for the activity */
        final Toolbar Toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(Toolbar);
        // Get a support actionBar corresponding to this toolbar
        ActionBar arrowup = getSupportActionBar();
        // Enable the "up" button
        arrowup.setDisplayHomeAsUpEnabled(true);

        /* ------- SHOW ITEMS FROM DB IN LISTVIEW ------- */
        Log.d(TAG, "onCreate() create cursor fetchallNPs()..." );

        NailPolishDBHelper dbHelper = new NailPolishDBHelper(getApplicationContext());
        Cursor cursor = dbHelper.fetchAllNPs();

        // For the cursor adapter, specify which columns go into which views
        final String[] fromColumns = new String[]{dbHelper.COLUMN_NAME,dbHelper.COLUMN_NPID,dbHelper.COLUMN_BRAND,dbHelper.COLUMN_COLLECTION,dbHelper.COLUMN_COLOR,dbHelper.COLUMN_FINISH};
        int[] toViews = {R.id.name_tv, R.id.id_tv, R.id.brand_tv, R.id.collection_tv, R.id.color_tv, R.id.finish_tv};

        Log.d(TAG, "onCreate() create Data Adapter..." );
        // Create an empty adapter we will use to display the loaded data.
        // We pass null for the cursor, then update it in onLoadFinished()
        dataAdapter = new SimpleCursorAdapter(this,
                R.layout.items, cursor,
                fromColumns, toViews, 0);

        Log.d(TAG, "onCreate() Assign ListView to Adapter..." );
        final ListView listView = (ListView) findViewById(R.id.listview1);
        //Assign adpter to ListView
        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get nail polish name of selected item
                Cursor item = (Cursor) dataAdapter.getItem(position);
                String name = item.getString(item.getColumnIndexOrThrow("name"));

                Intent intent = new Intent(ViewDatabase.this, Details.class);
                intent.putExtra("item", name); //provide nail polish name to details activity
                startActivity(intent);
            }
        });
    }
}
