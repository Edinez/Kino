package com.example.edy.kino;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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

public class Filmy extends AppCompatActivity {
    List<HashMap<String,String>> zoznam;
    List<HashMap<String,String>> zoznam2;
    SimpleCursorAdapter myAdapter;
    DBHelper dbh = new DBHelper(this);
    Cursor c = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filmy);
        setTitle("Zoznam filmov");
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        zoznam = dbh.getFilmy();
        zoznam2 = dbh.getFilmyInfo();
        PripojAdapter();
    }

    private void PripojAdapter() {
        c = dbh.VratKurzor();
        myAdapter = new SimpleCursorAdapter(this, R.layout.list_layout,
                c, new String[]{MyContract.Film.COLUMN_ID, MyContract.Film.COLUMN_NAZOV, MyContract.Film.COLUMN_DLZKA, MyContract.Film.COLUMN_ROK},
                new int[]{R.id.id1, R.id.name1, R.id.email1, R.id.age1},0);

        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(myAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView lv = (ListView) findViewById(R.id.listView);
                Cursor cursor = ((SimpleCursorAdapter) lv.getAdapter()).getCursor();
                cursor.moveToPosition(position);
                long ID = cursor.getLong(cursor.getColumnIndex(MyContract.Film.COLUMN_ID));
                Intent intent = new Intent(Filmy.this,Filmy_info.class);
                intent.putExtra("id",zoznam2.get(position).get("id"));
                intent.putExtra("nazov",zoznam2.get(position).get("nazov"));
                intent.putExtra("dlzka",zoznam2.get(position).get("dlzka"));
                intent.putExtra("rok",zoznam2.get(position).get("rok"));
                intent.putExtra("popis",zoznam2.get(position).get("popis"));
                Log.v("MainAct",intent.putExtra("nazov",zoznam2.get(position).get("nazov"))+"");
                startActivity(intent);

               // Toast.makeText(Filmy.this, "" + ID, Toast.LENGTH_SHORT).show();
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
                builder.setMessage("Prajete si film upraviť alebo vymazať ?");
                builder.setTitle("Úpravy")
                        .setNegativeButton("Upraviť", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Filmy.this,Editacia_filmy.class);
                                intent.putExtra("id",zoznam.get(position).get("id"));
                                intent.putExtra("nazov",zoznam.get(position).get("nazov"));
                                intent.putExtra("dlzka",zoznam.get(position).get("dlzka"));
                                intent.putExtra("rok",zoznam.get(position).get("rok"));
                                intent.putExtra("popis",zoznam.get(position).get("popis"));
                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("Vymazať", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ListView lv = (ListView) findViewById(R.id.listView);
                                Cursor cursor = ((SimpleCursorAdapter) lv.getAdapter()).getCursor();
                                cursor.moveToPosition(position);
                                long ID = cursor.getLong(cursor.getColumnIndex(MyContract.Film.COLUMN_ID));
                                dbh.deleteFilm(ID);
                                dialog.dismiss();
                                c = dbh.VratKurzor();
                                myAdapter.changeCursor(c);
                                Toast.makeText(Filmy.this, "Film bol vymazaný z databázy", Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pridanie_filmu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home) {
            this.finish();
        }

        if (id == R.id.pridat_film) {
            startActivityForResult(new Intent(this, Pridaj_film.class), 1);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
