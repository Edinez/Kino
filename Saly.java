package com.example.edy.kino;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

public class Saly extends AppCompatActivity {
    List<HashMap<String, String>> zoznam;
    SimpleCursorAdapter myAdapter;
    DBHelper dbh = new DBHelper(this);
    Cursor c = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saly);
        setTitle("Zoznam sál");
        zoznam = dbh.getSalas();
        PripojAdapter();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }


    private void PripojAdapter() {
        c = dbh.VratKurzorSala();
        myAdapter = new SimpleCursorAdapter(this, R.layout.layout_pre_rozpis_saly,
                c, new String[]{MyContract.Sala.COLUMN_ID, MyContract.Sala.COLUMN_CISLO, MyContract.Sala.COLUMN_KAPACITA},
                new int[]{R.id.salaId, R.id.cisloSaly, R.id.kapacitaSaly}, 0);

        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(myAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView lv = (ListView) findViewById(R.id.listView);
                Cursor cursor = ((SimpleCursorAdapter) lv.getAdapter()).getCursor();
                cursor.moveToPosition(position);
                long ID = cursor.getLong(cursor.getColumnIndex(MyContract.Sala.COLUMN_ID));
                 Toast.makeText(Saly.this, "" + ID, Toast.LENGTH_SHORT).show();
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());

                builder.setMessage("Prajete si premietaciu sálu upraviť alebo vymazať ? ");
                builder.setTitle("Úpravy")
                        .setNegativeButton("Upraviť", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Saly.this, Editacia_saly.class);
                                intent.putExtra("id", zoznam.get(position).get("id"));
                                intent.putExtra("cislo", zoznam.get(position).get("cislo"));
                                intent.putExtra("kapacita", zoznam.get(position).get("kapacita"));
                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("Vymazať", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ListView lv = (ListView) findViewById(R.id.listView);
                                Cursor cursor = ((SimpleCursorAdapter) lv.getAdapter()).getCursor();
                                cursor.moveToPosition(position);
                                long ID = cursor.getLong(cursor.getColumnIndex(MyContract.Sala.COLUMN_ID));
                                dbh.deleteSalu(ID);
                                Log.v("MainAct",ID+"");
                                dialog.dismiss();
                                c = dbh.VratKurzorSala();
                                myAdapter.changeCursor(c);
                                Toast.makeText(Saly.this, "Sála bola vymazaná z databázy", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Saly.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
                builder.show();
                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pridanie_saly, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            this.finish();
        }

        if (id == R.id.pridat_salu) {
            startActivityForResult(new Intent(this, Pridaj_salu.class), 1);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
