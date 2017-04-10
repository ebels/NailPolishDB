package com.nailpolish.nailpolishdb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

/**
 * Created by ebelsheiser on 10.04.2017.
 */

public class Details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        /** sets the toolbar as the app bar for the activity */
        Toolbar Toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(Toolbar);
        ActionBar arrowup = getSupportActionBar(); // Get a support actionBar corresponding to this toolbar
        arrowup.setDisplayHomeAsUpEnabled(true); // Enable the "up" button

        TextView tvname = (TextView) findViewById(R.id.textView_id);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String id = intent.getStringExtra("id");
        tvname.setText(id);

        //Set custom Toolbar title
        Toolbar.setTitle("Details " + name);


    }
}
