package com.nailpolish.nailpolishdb;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ebelsheiser on 10.04.2017.
 */

public class Details extends AppCompatActivity {
    private static final String TAG = Details.class.getSimpleName();
    public String id, name, npid, brand, collection, color, finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar Toolbar = (Toolbar) findViewById(R.id.toolbar); /** sets the toolbar as the app bar for the activity */
        setSupportActionBar(Toolbar);
        ActionBar arrowup = getSupportActionBar(); // Get a support actionBar corresponding to this toolbar
        arrowup.setDisplayHomeAsUpEnabled(true); // Enable the "up" button

        TextView tvname = (TextView) findViewById(R.id.textView_name);
        TextView tvnpid = (TextView) findViewById(R.id.textView_id);
        TextView tvbrand = (TextView) findViewById(R.id.textView_brand);
        TextView tvcollection = (TextView) findViewById(R.id.textView_collection);
        TextView tvcolor = (TextView) findViewById(R.id.textView_color);
        TextView tvfinish = (TextView) findViewById(R.id.textView_finish);

        //todo: method in dbhelper class? needed to set name from database to textview; also needed in edit.java
        Log.d(TAG, "onCreate() Get Intent from ViewDatabase class..." );
        final Intent intent = getIntent();
        final String _id = intent.getStringExtra("id");
        Log.d(TAG, "onCreate() Record ID :" + _id );

        //get nail polish data from database
        Log.d(TAG, "Call method fetchentireNP() ...");
        NailPolishDBHelper dbHelper = new NailPolishDBHelper(getApplicationContext());
        Cursor cursor = dbHelper.fetchentireNP(_id);

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
        Log.d(TAG, "fetched from db:" + _id + name + npid + brand + collection + color + finish);

        //Set TextViews
        tvname.setText(name);
        tvnpid.setText(npid);
        tvbrand.setText(brand);
        tvcollection.setText(collection);
        tvcolor.setText(color);
        tvfinish.setText(finish);

        Button delete = (Button) findViewById(R.id.button_delete);
        Button edit = (Button) findViewById(R.id.button_edit);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertdelete = new AlertDialog.Builder(Details.this);
                alertdelete.setTitle(R.string.alert_delete_title);
                alertdelete.setMessage(R.string.alert_delete_message);
                alertdelete.setNegativeButton(R.string.alert_button_cancel, new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        // User canceled dialog
                    }
                });
                alertdelete.setPositiveButton(R.string.button_delete_nailpolish, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        NailPolishDBHelper dbHelper = new NailPolishDBHelper(getApplicationContext());
                        Log.d(TAG, "delete button() calling method deleteNP ...");
                        dbHelper.deleteNP(_id);
                        Intent intent = new Intent(Details.this, ViewDatabase.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Successfully deleted record", Toast.LENGTH_LONG).show();
                    }
                });
                alertdelete.show();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentedit = new Intent(Details.this, Edit.class);
                intentedit.putExtra("recordid", _id);   //pass id to edit activity
                startActivity(intentedit);
            }
        });
    }
}
