package com.example.imenikapp.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.example.imenikapp.Dialog.AboutDialog;
import com.example.imenikapp.Preferences.PrefAct;
import com.example.imenikapp.R;
import com.example.imenikapp.db.DatabaseHelper;
import com.example.imenikapp.db.model.Kontakt;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MainActivity extends AppCompatActivity  {
    private static final int SELECT_PICTURE = 1;
    private DatabaseHelper databaseHelper;
    private SharedPreferences prefs;
    private ImageView preview;
    private String imagePath = null;
    public static String KONTAKT_KEY="KONTAKT_KEY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);



        final ListView listView = (ListView) findViewById(R.id.kontakt_list);

        try {
            List<Kontakt> list = getDatabaseHelper().getKontaktDao().queryForAll();
            ListAdapter adapter = new ArrayAdapter<>(MainActivity.this, R.layout.list_item, list);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Kontakt kontakt = (Kontakt) listView.getItemAtPosition(position);

                    Intent intent = new Intent(MainActivity.this, DetailAct.class);
                    intent.putExtra(KONTAKT_KEY, kontakt.getId());
                    startActivity(intent);
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_main:
                AlertDialog alertDialog = new AboutDialog(this).prepareDialog();
                alertDialog.show();
                break;
            case R.id.settings_main:
                startActivity(new Intent(MainActivity.this, PrefAct.class));
                break;
            case R.id.add_kontakt_main:
                try {
                    addKontakt();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
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

                    if (preview != null){
                        preview.setImageBitmap(bitmap);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void addKontakt() throws  SQLException {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_kontakt);

        Button choosebtn = (Button) dialog.findViewById(R.id.choose);
        choosebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preview = (ImageView) dialog.findViewById(R.id.preview_image);
                selectPicture();
            }
        });

        final EditText kontaktIme = (EditText) dialog.findViewById(R.id.kontakt_ime);
        final EditText kontaktPrezime = (EditText) dialog.findViewById(R.id.kontakt_prezime);
        final EditText kontaktAdresa = (EditText) dialog.findViewById(R.id.kontakt_adresa);

        Button ok = (Button) dialog.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {


                    String name = kontaktIme.getText().toString();
                    String prezime = kontaktPrezime.getText().toString();
                    String adresa = kontaktAdresa.getText().toString();


                    if (preview == null || imagePath == null) {
                        Toast.makeText(MainActivity.this, "You must choose image", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (name.isEmpty()) {
                        Toast.makeText(MainActivity.this, "You must enter name", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (prezime.isEmpty()) {
                        Toast.makeText(MainActivity.this, "You must enter lastname", Toast.LENGTH_SHORT).show();
                        return;

                    }
                    if (adresa.isEmpty()) {
                        Toast.makeText(MainActivity.this, "You must enter adress", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Kontakt kontakt = new Kontakt();
                    kontakt.setIme(name);
                    kontakt.setAdresa(adresa);
                    kontakt.setPrezime((prezime));
                    kontakt.setSlika(imagePath);


                        getDatabaseHelper().getKontaktDao().create(kontakt);
                        refresh();
                        Toast.makeText(MainActivity.this, "Contact added", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                        reset();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }


            });

            Button cancel = (Button) dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View v){
                dialog.dismiss();
            }
            });

        dialog.show();
        }


    private void reset(){
        imagePath = "";
        preview = null;
    }



    private void selectPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    private void refresh() {
        ListView listview = (ListView) findViewById(R.id.products);

        if (listview != null) {
            ArrayAdapter<Kontakt> adapter = (ArrayAdapter<Kontakt>) listview.getAdapter();

            if (adapter != null) {
                try {
                    adapter.clear();
                    List<Kontakt> list = getDatabaseHelper().getKontaktDao().queryForAll();

                    adapter.addAll(list);

                    adapter.notifyDataSetChanged();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
