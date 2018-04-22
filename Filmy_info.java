package com.example.edy.kino;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

public class Filmy_info extends AppCompatActivity {
    int ID ;
    TextView nazov;
    TextView dlzka;
    TextView rok;
    TextView popis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filmy_info);
        setTitle("Informacie o filme");
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        nazov = (TextView) findViewById(R.id.infoNazov);
        dlzka = (TextView) findViewById(R.id.infoDlzka);
        rok = (TextView) findViewById(R.id.infoRok);
        popis = (TextView) findViewById(R.id.infoPopis);
        popis.setMovementMethod(new ScrollingMovementMethod());

        ID = Integer.parseInt(intent.getStringExtra("id"));
        nazov.setText(intent.getStringExtra("nazov"));
        dlzka.setText(intent.getStringExtra("dlzka"));
        rok.setText(intent.getStringExtra("rok"));
        popis.setText(intent.getStringExtra("popis"));
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
