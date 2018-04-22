package com.example.edy.kino;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    SimpleCursorAdapter myAdapter;
    DBHelper dbh = new DBHelper(this);
    Cursor c = null;
    Random rnd = new Random();
    int farba = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Program kino");
       // final View CoordinatorLayout;
      //  CoordinatorLayout = findViewById(R.id.CoordinatorLayout);
        PripojAdapter2();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToogle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToogle);
        mToogle.syncState();
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Pridavanie_udalosti.class);
                startActivity(intent);
            }
        });
        NavigationView nv = findViewById(R.id.nav_view);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_item_one:
                        startActivity(new Intent(MainActivity.this, Filmy.class));
                        break;
                    case R.id.nav_item_two:
                        startActivity(new Intent(MainActivity.this, Saly.class));
                        break;
                    case R.id.nav_item_tree:
                        startActivity(new Intent(MainActivity.this, Udalost.class));
                        break;
                    case R.id.nav_item_four:
                        final Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Developed by Eduard Gábel", Snackbar.LENGTH_LONG)

                                .setAction("Viac info", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivity(new Intent(MainActivity.this, Informacie.class));
                                    }
                                });
                        View sbView = snackbar.getView();
                        sbView.setBackgroundColor(farba);
                        snackbar.show();
                        farba = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                        break;
                }
                return true;
            }
        });
    }

    private void PripojAdapter2() {
        c = dbh.ArrayFilm();
        myAdapter = new SimpleCursorAdapter(this, R.layout.activity_main_layout,
                c, new String[]{MyContract.Cas.COLUMN_ID, MyContract.Film.COLUMN_NAZOV, MyContract.Sala.COLUMN_CISLO, MyContract.Cas.COLUMN_DEN, MyContract.Cas.COLUMN_CAS, MyContract.Cas.COLUMN_CENA},
                new int[]{R.id.casId, R.id.casIdFilm, R.id.casIdSala, R.id.casDen, R.id.casCas, R.id.casCena}, 0);

        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(myAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView lv = (ListView) findViewById(R.id.listView);
                Cursor cursor = ((SimpleCursorAdapter) lv.getAdapter()).getCursor();
                cursor.moveToPosition(position);
                long ID = cursor.getLong(cursor.getColumnIndex(MyContract.Cas.COLUMN_ID));
                Toast.makeText(MainActivity.this, "" + ID, Toast.LENGTH_SHORT).show();
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());

                builder.setMessage("Vymazať naozaj?");
                builder.setTitle("Mazanie ...")
                        .setPositiveButton("Ano", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ListView lv = (ListView) findViewById(R.id.listView);
                                Cursor cursor = ((SimpleCursorAdapter) lv.getAdapter()).getCursor();
                                cursor.moveToPosition(position);
                                long ID = cursor.getLong(cursor.getColumnIndex(MyContract.Cas.COLUMN_ID));
                                dialog.dismiss();
                                c = dbh.VratKurzorCas();
                                myAdapter.changeCursor(c);
                                Toast.makeText(MainActivity.this, "mazem", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Toast.makeText(MainActivity.this, "nic sa nedeje", Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.show();
                return false;
            }
        });
    }
}
