package com.nailpolish.nailpolishdb;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;


public class AddNew extends AppCompatActivity implements View.OnClickListener {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_PICK_IMAGE = 2;
    private Spinner spinnercolor, spinnerfinish;
    private ArrayAdapter adaptercolor, adapterfinish;
    private EditText editTextName, editTextID, editTextBrand,editTextCollection;
    private Button btnAdd;
    private static final String TAG = AddNew.class.getSimpleName();
    private ImageButton btnimg;
    private ImageView viewImage;
    private String srcImagePath;
    private Bitmap imageBitmap;



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

        /* ------- BUTTONS + IMAGEVIEW------- */
        btnAdd = (Button) findViewById(R.id.button_addnp);
        btnAdd.setOnClickListener(this);
        btnimg = (ImageButton) findViewById(R.id.imageButton);
        btnimg.setOnClickListener(this);
        viewImage=(ImageView)findViewById(R.id.imageView_addnew);
    }

    /* ------- ONCLICK BUTTONS------- */
    // onClick event, switch/case for multiple onClickListener events in one activty
    @Override
    //todo: onClicklistener like in details.java? > search in android documentation for it
    public void onClick(View v) {
        switch (v.getId()) {
            /* ------- ADD NEW NAILPOLISH ------- */
            case R.id.button_addnp:
                String name = editTextName.getText().toString();
                String npid = editTextID.getText().toString();
                String brand = editTextBrand.getText().toString();
                String collection = editTextCollection.getText().toString();
                String color = spinnercolor.getSelectedItem().toString();
                String finish = spinnerfinish.getSelectedItem().toString();
                String selectedcolor = "Select a color";
                String selectedfinish = "Select a finish";

                // get image form ImageView and convert into byte array
                //Before inserting into database, need to convert Bitmap image into byte array first then apply it using database query
                //When retrieving from database, you certainly have a byte array of image, what you need to do is to convert byte array back to original image. So, you have to make use of BitmapFactory to decode
                    //bitmap = ((BitmapDrawable) viewImage.getDrawable()).getBitmap();
                    //image = ImageHelper.getImageBytes(bitmap);

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
                            String intStorageDirectory = getFilesDir().toString();
                            File imgFolder = new File(intStorageDirectory, "NailPolishImages");
                            if (imgFolder.exists()) {
                                Log.d(TAG, "AddNew() Directoy "+ imgFolder + " exists!");
                            } else {
                                Log.d(TAG, "AddNew() Creating Directory " + imgFolder+ " ...");
                                imgFolder.mkdirs();
                            }

                            // todo: copy image to imgFolder directory and save image path in db
                            Random generator = new Random();
                            int n = 10000;
                            n = generator.nextInt(n);
                            String fname = "Image-"+ n +".jpg";
                            File file = new File (imgFolder, fname);
                            if (file.exists ()) file.delete ();
                            try {
                                FileOutputStream out = new FileOutputStream(file);
                                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                                out.flush();
                                out.close();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            // Insert Data to database
                            DBHelper DbHelper = new DBHelper(getApplicationContext());
                            Log.d(TAG, "onClick() Write data in database..." );
                            //DbHelper.insertNP(name,npid,brand,collection,color,finish,image);
                            DbHelper.insertNP(name,npid,brand,collection,color,finish);
                            Log.d(TAG, "onClick() Successfully created entry..." );
                            Toast.makeText(getApplicationContext(), "Successfully created entry", Toast.LENGTH_SHORT).show();

                            Log.d(TAG, "AddNew() go back to MainActivity()..." );
                            Intent intent = new Intent(this, MainActivity.class); /** runtime binding between mainactivity and addnew activity */
                            startActivity(intent);
                        }
                    }
                }
                break;

            /* ------- SELECT IMAGE ------- */
            case R.id.imageButton:
                selectImage();
                break;
        }
    }

    //select image from gallery or take photo
    private void selectImage() {
        //Create AlertDialog: choose camera or gallery
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(AddNew.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            //OnClick event for different choices: camera, gallery, cancel
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {   //Take photo / camera
                    Intent IntentTakePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (IntentTakePicture.resolveActivity(getPackageManager()) != null) {
                        Log.d(TAG, "AddNew() if condition startActivityForResult...");
                        startActivityForResult(IntentTakePicture, REQUEST_IMAGE_CAPTURE);
                    }
                }
                else if (options[item].equals("Choose from Gallery"))
                {   //Choose image from gallery
                    Intent IntentPickPhoto = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(IntentPickPhoto, REQUEST_PICK_IMAGE);

                }
                else if (options[item].equals("Cancel")) {
                    //cancel dialog
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        //todo: get src path of selected image
        if (reqCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            viewImage.setImageBitmap(imageBitmap);
        } else if (reqCode == REQUEST_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            if (selectedImage != null) {
                viewImage.setImageURI(selectedImage);
            }
        } else {
            Toast.makeText(AddNew.this, "You haven't picked an image",Toast.LENGTH_LONG).show();
        }
    }
}
