package com.nailpolish.nailpolishdb;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by ebelsheiser on 11.04.2017.
 */

public class Edit extends AppCompatActivity {

    private static final String TAG = Edit.class.getSimpleName();
    public String id, name, npid, brand, collection, color, finish;

    private Spinner spinnercolor, spinnerfinish;
    private ArrayAdapter adapter;
    private EditText editTextName, editTextID, editTextBrand,editTextCollection;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar Toolbar = (Toolbar) findViewById(R.id.toolbar); /** sets the toolbar as the app bar for the activity */
        setSupportActionBar(Toolbar);
        ActionBar arrowup = getSupportActionBar(); // Get a support actionBar corresponding to this toolbar
        arrowup.setDisplayHomeAsUpEnabled(true); // Enable the "up" button

        /* ------- SPINNER NAILPOLISH COLOR ------- */
        spinnercolor = (Spinner) findViewById(R.id.spinner_color);  // get the selected dropdown list value - Spinner element
        adapter = ArrayAdapter.createFromResource(this, R.array.npcolor_arrays, android.R.layout.simple_spinner_item);  // Create an ArrayAdapter using the string array and a default spinner layout
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Specify the layout to use when the list of choices appears
        spinnercolor.setAdapter(adapter);   // Apply the adapter to the spinner

        /* ------- SPINNER NAILPOLISH FINISH ------- */
        spinnerfinish = (Spinner) findViewById(R.id.spinner_finish);
        adapter = ArrayAdapter.createFromResource(this, R.array.npfinish_arrays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerfinish.setAdapter(adapter);

        /* ------- EDITTEXT ------- */
        editTextName = (EditText) findViewById(R.id.editText_name);
        editTextID = (EditText) findViewById(R.id.editText_id);
        editTextBrand = (EditText) findViewById(R.id.editText_brand);
        editTextCollection = (EditText) findViewById(R.id.editText_collection);

        //get id from details activity
        Intent intent = getIntent();
        id = intent.getStringExtra("recordid");
        Log.d(TAG, "get it from details.activity() ID= "+id);

        //read data from database
        Log.d(TAG, "Call method fetchentireNP() ..." );
        NailPolishDBHelper dbHelper = new NailPolishDBHelper(getApplicationContext());
        Cursor cursor = dbHelper.fetchentireNP(id);

        cursor.moveToFirst();
        if (cursor.getCount()!=0) {
            if (cursor.moveToFirst()){
                do {
                    Log.d(TAG, "get String from cursor() ..." );
                    name = cursor.getString(cursor.getColumnIndex("name"));
                    npid = cursor.getString(cursor.getColumnIndex("npid"));
                    brand = cursor.getString(cursor.getColumnIndex("brand"));
                    collection = cursor.getString(cursor.getColumnIndex("collection"));
                    color = cursor.getString(cursor.getColumnIndex("color"));
                    finish = cursor.getString(cursor.getColumnIndex("finish"));
                }while (cursor.moveToNext());
            }
        }
        cursor.close();
        Log.d(TAG, "fetched from db:" +id + name + npid + brand + collection + color + finish);

        //fill data in edittext fields
        editTextName.setText(name);
        editTextID.setText(npid);
        editTextBrand.setText(brand);
        editTextCollection.setText(collection);

        //todo: set text (color+finish) as selected spinner item
    }
}
