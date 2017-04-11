package com.nailpolish.nailpolishdb;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by ebelsheiser on 11.04.2017.
 */

public class Edit extends AppCompatActivity {

    private Spinner spinnercolor, spinnerfinish;
    private ArrayAdapter adapter;
    private EditText editTextName, editTextID, editTextBrand,editTextCollection;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        /** sets the toolbar as the app bar for the activity */
        Toolbar Toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(Toolbar);
        ActionBar arrowup = getSupportActionBar(); // Get a support actionBar corresponding to this toolbar
        arrowup.setDisplayHomeAsUpEnabled(true); // Enable the "up" button

        /* ------- SPINNER NAILPOLISH COLOR ------- */
        // get the selected dropdown list value - Spinner element
        spinnercolor = (Spinner) findViewById(R.id.spinner_color);
        // Create an ArrayAdapter using the string array and a default spinner layout
        adapter = ArrayAdapter.createFromResource(this, R.array.npcolor_arrays, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnercolor.setAdapter(adapter);

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
    }
}
