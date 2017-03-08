package com.nailpolish.nailpolishdb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.nailpolish.nailpolishdb.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /** sets the toolbar as the app bar for the activity */
        Toolbar Toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(Toolbar);
    }

    /** Called when the user taps the "addnew" button */
    public void addnew (View view) {
        /** runtime binding between mainactivity and addnew activity */
        Intent intent = new Intent(this, AddNew.class);
        startActivity(intent);
    }

    /** Called when the user taps the "viewdatabase" button */
    public void viewdatabase (View view) {
        /** runtime binding between mainactivity and viewdatabase activity */
        Intent intent = new Intent(this, ViewDatabase.class);
        startActivity(intent);
    }
}
