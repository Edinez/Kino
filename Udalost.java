package com.example.edy.kino;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

public class Udalost extends AppCompatActivity {
    List<HashMap<String, String>> zoznam;
    SimpleCursorAdapter myAdapter;
    DBHelper dbh = new DBHelper(this);
    Cursor c = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udalost);
        setTitle("Udalosti");
        zoznam = dbh.getCas();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        PripojAdapter();
    }


    private void PripojAdapter() {
        c = dbh.VratKurzorCas();
        myAdapter = new SimpleCursorAdapter(this, R.layout.activity_main_layout,
                c, new String[]{ MyContract.Cas.COLUMN_ID, MyContract.Film.COLUMN_NAZOV, MyContract.Sala.COLUMN_CISLO,
                MyContract.Cas.COLUMN_DEN, MyContract.Cas.COLUMN_CAS, MyContract.Cas.COLUMN_CENA},
                new int[]{R.id.casId, R.id.casIdFilm, R.id.casIdSala, R.id.casDen, R.id.casCas, R.id.casCena},0);

        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(myAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView lv = (ListView) findViewById(R.id.listView);
                Cursor cursor = ((SimpleCursorAdapter) lv.getAdapter()).getCursor();
                cursor.moveToPosition(position);
                long ID = cursor.getLong(cursor.getColumnIndex(MyContract.Cas.COLUMN_ID));
                Toast.makeText(Udalost.this, "" + ID, Toast.LENGTH_SHORT).show();
                //Log.v("MainAct",ID+" ");
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
                builder.setMessage("Prajete si udalosť upraviť alebo vymazať ?");
                builder.setTitle("Úpravy")
                        .setNegativeButton("Upraviť", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Udalost.this,Editacia_udalost.class);
                                intent.putExtra("id", zoznam.get(position).get("id"));
                                intent.putExtra("cas", zoznam.get(position).get("cas"));
                                intent.putExtra("den", zoznam.get(position).get("den"));
                                intent.putExtra("cena", zoznam.get(position).get("cena"));
                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("Vymazať", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ListView lv = (ListView) findViewById(R.id.listView);
                                Cursor cursor = ((SimpleCursorAdapter) lv.getAdapter()).getCursor();
                                cursor.moveToPosition(position);
                                long ID = cursor.getLong(cursor.getColumnIndex(MyContract.Cas.COLUMN_ID));
                                Log.v("MainAct",ID+" = ID ktore sa vymaze v aktivite udalost");
                                dbh.deleteUdalost(ID);
                                dialog.dismiss();
                                c = dbh.VratKurzorCas();
                                myAdapter.changeCursor(c);
                                Toast.makeText(Udalost.this, "Udalosť bola vymazaná z databázy", Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.show();
                return false;
            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            Intent intent = new Intent(Udalost.this, MainActivity.class);
            startActivity(intent);
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
