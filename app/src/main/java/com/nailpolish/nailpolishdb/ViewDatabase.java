package com.nailpolish.nailpolishdb;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.List;

public class ViewDatabase extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_database);

        /* ------- TOOLBAR ------- */
        /** sets the toolbar as the app bar for the activity */
        Toolbar Toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(Toolbar);
        // Get a support actionBar corresponding to this toolbar
        ActionBar arrowup = getSupportActionBar();
        // Enable the "up" button
        arrowup.setDisplayHomeAsUpEnabled(true);

        //database handler
        NailPolishDBHelper dbHelper = new NailPolishDBHelper(getApplicationContext());

        //get all elements

    }
}
