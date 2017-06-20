package com.nailpolish.nailpolishdb;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ViewDatabase extends AppCompatActivity {

    private static final String TAG = ViewDatabase.class.getSimpleName();
    private SimpleCursorAdapter dataAdapter;    // This is the Adapter being used to display the list's data
    public ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_database);
        Log.d(TAG, "onCreate() start Activity..." );

        /* ------- TOOLBAR ------- */
        final Toolbar Toolbar = (Toolbar) findViewById(R.id.toolbar);   /** sets the toolbar as the app bar for the activity */
        setSupportActionBar(Toolbar);
        ActionBar arrowup = getSupportActionBar();  // Get a support actionBar corresponding to this toolbar
        arrowup.setDisplayHomeAsUpEnabled(true);    // Enable the "up" button

        /* ------- SHOW ITEMS FROM DB IN LISTVIEW ------- */
        Log.d(TAG, "onCreate() create cursor fetchallNPs()..." );

        DBHelper dbHelper = new DBHelper(getApplicationContext());
        Cursor cursor = dbHelper.fetchAllNPs();

        // For the cursor adapter, specify which columns go into which views
        final String[] fromColumns = new String[]{dbHelper.COLUMN_NAME, dbHelper.COLUMN_BRAND, dbHelper.COLUMN_COLOR};
        int[] toViews = {R.id.name_tv, R.id.brand_tv, R.id.color_tv};

        Log.d(TAG, "onCreate() create Data Adapter..." );
        // Create an adapter that will be used to display the loaded data.
        dataAdapter = new SimpleCursorAdapter(this,
                R.layout.items, cursor,
                fromColumns, toViews, 0);   // pass null for the cursor, then update it in onLoadFinished()

        Log.d(TAG, "onCreate() Assign ListView to Adapter..." );
        listView = (ListView) findViewById(R.id.listview1);
        listView.setAdapter(dataAdapter);   //Assign adpter to ListView

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick() Cursor..." );
                // Get nail polish id of selected item
                Cursor item = (Cursor) dataAdapter.getItem(position);
                String _id = item.getString(item.getColumnIndexOrThrow("_id"));

                Log.d(TAG, "onItemClick() Intent()..." );
                Intent intent = new Intent(ViewDatabase.this, Details.class);
                intent.putExtra("id", _id);
                startActivity(intent);
            }
        });

        Toast.makeText(getApplicationContext(), "Total number of entries: " + listView.getAdapter().getCount(), Toast.LENGTH_SHORT).show();
    }
    //todo: pass count of nailpolishes to settings.java
    public int countNPs() {
        int count = listView.getAdapter().getCount();
        return count;
    }
}