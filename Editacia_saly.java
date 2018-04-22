package com.example.edy.kino;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Editacia_saly extends AppCompatActivity {
    int ID;
    DBHelper helper;
    EditText cislo;
    EditText kapacita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editacia_saly);
        setTitle("Editovanie premietacej sály");

        Intent intent = getIntent();
        cislo = (EditText) findViewById(R.id.editCisloSaly);
        kapacita = (EditText) findViewById(R.id.editKapacitaSaly);

        ID = Integer.parseInt(intent.getStringExtra("id"));
        cislo.setText(intent.getStringExtra("cislo"));
        kapacita.setText(intent.getStringExtra("kapacita"));
        helper = new DBHelper(this);
    }


    public void ulozit(View v) {
        if (cislo.getText().toString().equals("") || kapacita.getText().toString().equals(""))
            Toast.makeText(this, "Niektoré pole zostalo prázne", Toast.LENGTH_SHORT).show();
        else {
            helper.updateSala(new Sala(ID, cislo.getText().toString(),Integer.parseInt(kapacita.getText().toString())));
            finish();
            Intent i = new Intent(Editacia_saly.this, Saly.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            Toast.makeText(this, "Sála bola upravená", Toast.LENGTH_SHORT).show();
            startActivity(i);
        }
    }

    public void zrusit(View v) {
        finish();
        Intent i = new Intent(Editacia_saly.this, Saly.class);
        startActivity(i);
    }
}
