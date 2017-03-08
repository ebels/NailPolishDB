package com.nailpolish.nailpolishdb;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class AddNew extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        /** sets the toolbar as the app bar for the activity */
        Toolbar Toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(Toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar arrowup = getSupportActionBar();

        // Enable the Up button
        arrowup.setDisplayHomeAsUpEnabled(true);
    }
}
