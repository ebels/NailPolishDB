package com.nailpolish.nailpolishdb;

import android.nfc.Tag;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class AddNew extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinnercolor, spinnerfinish;
    private ArrayAdapter adapter;
    private EditText editTextName, editTextID, editTextBrand,editTextCollection;
    private Button btnAdd;
    private static final String TAG = AddNew.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        /* ------- TOOLBAR ------- */
        /** sets the toolbar as the app bar for the activity */
        Toolbar Toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(Toolbar);
        // Get a support actionBar corresponding to this toolbar
        ActionBar arrowup = getSupportActionBar();
        // Enable the "up" button
        arrowup.setDisplayHomeAsUpEnabled(true);

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

        /* ------- BUTTONS ------- */
        btnAdd = (Button) findViewById(R.id.button_addnp);
        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String name = editTextName.getText().toString();
        String npid = editTextID.getText().toString();
        String brand = editTextBrand.getText().toString();
        String collection = editTextCollection.getText().toString();
        String color = spinnercolor.getSelectedItem().toString();
        String finish = spinnerfinish.getSelectedItem().toString();

        // Check if fields are empty then display message, else insert entries in database
        if (name.matches("") && npid.matches("") && brand.matches("") && collection.matches(""))  {
            Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
        } else {
            // Insert Data to database
            NailPolishDBHelper DbHelper = new NailPolishDBHelper(getApplicationContext());
            Log.d(TAG, "onClick() Write data in database..." );
            DbHelper.insertNP(name,npid,brand,collection,color,finish);
            Log.d(TAG, "onClick() Successfully created entry..." );
            Toast.makeText(getApplicationContext(), "Successfully created entry", Toast.LENGTH_SHORT).show();

            // making input field text blank
            editTextName.setText("");
            editTextID.setText("");
            editTextBrand.setText("");
            editTextCollection.setText("");
        }
    }
}
