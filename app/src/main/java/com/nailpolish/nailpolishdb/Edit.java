package com.nailpolish.nailpolishdb;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by ebelsheiser on 11.04.2017.
 */

public class Edit extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = Edit.class.getSimpleName();
    public String id, name, npid, brand, collection, color, finish;

    private Spinner spinnercolor, spinnerfinish;
    private ArrayAdapter adaptercolor, adapterfinish;
    private EditText editTextName, editTextID, editTextBrand, editTextCollection;
    private Button btnsave;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar Toolbar = (Toolbar) findViewById(R.id.toolbar); /** sets the toolbar as the app bar for the activity */
        setSupportActionBar(Toolbar);
        ActionBar arrowup = getSupportActionBar(); // Get a support actionBar corresponding to this toolbar
        this.getSupportActionBar().setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);
        arrowup.setDisplayHomeAsUpEnabled(true); // Enable the "up" button
        //todo: save button in action bar

        /* ------- SPINNER NAILPOLISH COLOR ------- */
        spinnercolor = (Spinner) findViewById(R.id.spinner_color);  // get the selected dropdown list value - Spinner element
        adaptercolor = ArrayAdapter.createFromResource(this, R.array.npcolor_arrays, android.R.layout.simple_spinner_item);  // Create an ArrayAdapter using the string array and a default spinner layout
        adaptercolor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Specify the layout to use when the list of choices appears
        spinnercolor.setAdapter(adaptercolor);   // Apply the adapter to the spinner

        /* ------- SPINNER NAILPOLISH FINISH ------- */
        spinnerfinish = (Spinner) findViewById(R.id.spinner_finish);
        adapterfinish = ArrayAdapter.createFromResource(this, R.array.npfinish_arrays, android.R.layout.simple_spinner_item);
        adapterfinish.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerfinish.setAdapter(adapterfinish);

        /* ------- EDITTEXT ------- */
        editTextName = (EditText) findViewById(R.id.editText_name);
        editTextID = (EditText) findViewById(R.id.editText_id);
        editTextBrand = (EditText) findViewById(R.id.editText_brand);
        editTextCollection = (EditText) findViewById(R.id.editText_collection);

        //get id from details activity
        Intent intent = getIntent();
        id = intent.getStringExtra("recordid");
        Log.d(TAG, "get it from details.activity() ID= " + id);

        // TODO: method in dbhelper class? needed to set name from database to edittext; also needed in details class
        //read data from database
        Log.d(TAG, "Call method fetchentireNP() ...");
        NailPolishDBHelper dbHelper = new NailPolishDBHelper(getApplicationContext());
        Cursor cursor = dbHelper.fetchentireNP(id);

        cursor.moveToFirst();
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    Log.d(TAG, "get String from cursor() ...");
                    name = cursor.getString(cursor.getColumnIndex("name"));
                    npid = cursor.getString(cursor.getColumnIndex("npid"));
                    brand = cursor.getString(cursor.getColumnIndex("brand"));
                    collection = cursor.getString(cursor.getColumnIndex("collection"));
                    color = cursor.getString(cursor.getColumnIndex("color"));
                    finish = cursor.getString(cursor.getColumnIndex("finish"));
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        Log.d(TAG, "fetched from db:" + id + name + npid + brand + collection + color + finish);

        //fill data in edittext fields
        editTextName.setText(name);
        editTextID.setText(npid);
        editTextBrand.setText(brand);
        editTextCollection.setText(collection);

        //set text (color+finish) as selected spinner item
        int colorPosition = adaptercolor.getPosition(color);
        spinnercolor.setSelection(colorPosition);

        int finishPosition = adapterfinish.getPosition(finish);
        spinnerfinish.setSelection(finishPosition);

        btnsave = (Button) findViewById(R.id.button_savechanges);
        btnsave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String name = editTextName.getText().toString();
        String npid = editTextID.getText().toString();
        String brand = editTextBrand.getText().toString();
        String collection = editTextCollection.getText().toString();
        String color = spinnercolor.getSelectedItem().toString();
        String finish = spinnerfinish.getSelectedItem().toString();
        String selectedcolor = "Select a color";
        String selectedfinish = "Select a finish";

        // Check if fields are empty then display message, else insert entries in database
        if (name.matches("") && npid.matches("") && brand.matches("") && collection.matches("")) {
            Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
        } else {

            if (spinnercolor.getSelectedItem().toString().equals(selectedcolor)) {  //check if color + finish is selected
                Toast.makeText(this, "Please select a color!", Toast.LENGTH_SHORT).show();
            } else {
                if (spinnerfinish.getSelectedItem().toString().equals(selectedfinish)) {    //check if color + finish is selected
                    Toast.makeText(this, "Please select a finish!", Toast.LENGTH_SHORT).show();
                } else {
                    // Insert Data to database
                    NailPolishDBHelper DbHelper = new NailPolishDBHelper(getApplicationContext());
                    Log.d(TAG, "onClick() Write data in database..." );
                    DbHelper.updateNP(id,name,npid,brand,collection,color,finish);
                    Log.d(TAG, "onClick() Successfully updated entry..." );
                    Toast.makeText(getApplicationContext(), "Successfully updated entry", Toast.LENGTH_SHORT).show();

                    //pass id to get detail view of saved nail polish
                    Intent intent = new Intent(Edit.this, Details.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                }
            }
        }
    }
}
