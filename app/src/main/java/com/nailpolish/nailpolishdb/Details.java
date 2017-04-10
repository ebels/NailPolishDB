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

        TextView tvname = (TextView) findViewById(R.id.textView_name);
        TextView tvnpid = (TextView) findViewById(R.id.textView_id);
        TextView tvbrand = (TextView) findViewById(R.id.textView_brand);
        TextView tvcollection = (TextView) findViewById(R.id.textView_collection);
        TextView tvcolor = (TextView) findViewById(R.id.textView_color);
        TextView tvfinish = (TextView) findViewById(R.id.textView_finish);


        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String npid = intent.getStringExtra("npid");
        String brand = intent.getStringExtra("brand");
        String collection = intent.getStringExtra("collection");
        String color = intent.getStringExtra("color");
        String finish = intent.getStringExtra("finish");

        //Set custom Toolbar title
        Toolbar.setTitle("Details " + name);

        //Set TextViews
        tvname.setText(name);
        tvnpid.setText(npid);
        tvbrand.setText(brand);
        tvcollection.setText(collection);
        tvcollection.setText(color);
        tvfinish.setText(finish);
    }
}
