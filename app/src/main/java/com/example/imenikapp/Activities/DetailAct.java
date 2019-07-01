package com.example.imenikapp.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.imenikapp.R;
import com.example.imenikapp.db.DatabaseHelper;
import com.example.imenikapp.db.model.Kontakt;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.io.IOException;
import java.sql.SQLException;

public class DetailAct extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private SharedPreferences prefs;
    private Kontakt kontakt;
    private EditText ime;
    private EditText prezime;
    private EditText adresa;
    private ImageView image;
    private static final int SELECT_PICTURE = 1;
    private String imagePath = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kontakt);


        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        int key = getIntent().getExtras().getInt(MainActivity.KONTAKT_KEY);
        try {
            kontakt = getDatabaseHelper().getKontaktDao().queryForId(key);
            ime=(EditText)findViewById(R.id.edit_ime_detail);
            prezime =(EditText)findViewById(R.id.edit_prezime_detail);
            adresa =(EditText)findViewById(R.id.edit_prezime_detail);
            image=(ImageView) findViewById(R.id.kontakt_image);

            ime.setText(kontakt.getIme());
            prezime.setText(kontakt.getPrezime());
            adresa.setText(kontakt.getAdresa());


        }catch (SQLException e) {
            e.printStackTrace();
        }
onDeleteClicked();onEditClicked();onImageChangeClicked();

    }
    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }
    public void onDeleteClicked() {

        final Button delete = (Button) findViewById(R.id.delete_kontakt);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getDatabaseHelper().getKontaktDao().delete(kontakt);

                    Toast.makeText(DetailAct.this, "Obrisan kontakt", Toast.LENGTH_LONG).show();
                    finish();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    public void onEditClicked(){
        final Button edit=(Button) findViewById(R.id.edit_contact);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kontakt.setIme(ime.getText().toString());
                kontakt.setPrezime(prezime.getText().toString());
                kontakt.setAdresa(adresa.getText().toString());
                kontakt.setSlika(imagePath);
                try {
                    getDatabaseHelper().getKontaktDao().update(kontakt);

                    Toast.makeText(DetailAct.this, "Kontakt izmenjen", Toast.LENGTH_LONG).show();

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });

    }
    public void onImageChangeClicked(){
        Button choosebtn = (Button) findViewById(R.id.image_add);
        choosebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image = (ImageView) findViewById(R.id.kontakt_image);
                selectPicture();

            }
        });

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();


        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
    private void selectPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    if (selectedImageUri != null){
                        imagePath = selectedImageUri.toString();
                    }

                    if (image != null){
                        image.setImageBitmap(bitmap);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }




}
