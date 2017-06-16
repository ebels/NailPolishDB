package com.nailpolish.nailpolishdb;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class AddNew extends AppCompatActivity implements View.OnClickListener {

    private static final int RESULT_LOAD_IMAGE = 1;
    private Spinner spinnercolor, spinnerfinish;
    private ArrayAdapter adaptercolor, adapterfinish;
    private EditText editTextName, editTextID, editTextBrand,editTextCollection;
    private Button btnAdd;
    private static final String TAG = AddNew.class.getSimpleName();
    private ImageButton btnimg;

    ImageView viewImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        /* ------- TOOLBAR ------- */
        Toolbar Toolbar = (Toolbar) findViewById(R.id.toolbar); /** sets the toolbar as the app bar for the activity */
        setSupportActionBar(Toolbar);
        ActionBar arrowup = getSupportActionBar();  // Get a support actionBar corresponding to this toolbar
        this.getSupportActionBar().setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);
        arrowup.setDisplayHomeAsUpEnabled(true);    // Enable the "up" button

        /* ------- SPINNER NAILPOLISH COLOR ------- */
        spinnercolor = (Spinner) findViewById(R.id.spinner_color);  // get the selected dropdown list value - Spinner element
        adaptercolor = ArrayAdapter.createFromResource(this, R.array.npcolor_arrays, android.R.layout.simple_spinner_item); // Create an ArrayAdapter using the string array and a default spinner layout
        adaptercolor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);    // Specify the layout to use when the list of choices appears
        spinnercolor.setAdapter(adaptercolor);  // Apply the adapter to the spinner

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

        /* ------- BUTTONS ------- */
        btnAdd = (Button) findViewById(R.id.button_addnp);
        btnAdd.setOnClickListener(this);

        btnimg = (ImageButton) findViewById(R.id.imageButton);
        btnimg.setOnClickListener(this);

        viewImage=(ImageView)findViewById(R.id.imageView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //Add new NP
            case R.id.button_addnp:
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
                            DbHelper.insertNP(name,npid,brand,collection,color,finish);
                            Log.d(TAG, "onClick() Successfully created entry..." );
                            Toast.makeText(getApplicationContext(), "Successfully created entry", Toast.LENGTH_SHORT).show();

                            // go back to Main Activity
                            Log.d(TAG, "AddNew() go back to MainActivity()..." );
                            Intent intent = new Intent(this, MainActivity.class); /** runtime binding between mainactivity and addnew activity */
                            startActivity(intent);
                        }
                    }
                }
                break;

            //select image from gallery
            case R.id.imageButton:
                /*Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);*/
                selectImage();
                break;
        }
    }

    private void selectImage() {

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(AddNew.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    //todo: check if code works in simple "show picture" app
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);

                    viewImage.setImageBitmap(bitmap);

                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {

                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.w("path image gallery:", picturePath+"");
                viewImage.setImageBitmap(thumbnail);
            }
        }
    }
}
