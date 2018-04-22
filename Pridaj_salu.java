package com.example.edy.kino;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Pridaj_salu extends AppCompatActivity {
    DBHelper helper;
    EditText cislo;
    EditText kapacita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pridaj_salu);
        setTitle("Pridávanie novej premietacej sály");
        cislo = (EditText) findViewById(R.id.cisloText);
        kapacita = (EditText) findViewById(R.id.kapacitaText);
    }

    public void ulozit(View view) {
        String cislo1 = cislo.getText().toString();
        String kapacita1 = kapacita.getText().toString();

        if (cislo1.equals("") || kapacita1.equals("")) {
            Toast.makeText(this, "Nevyplnili ste niektore polia!", Toast.LENGTH_SHORT).show();
        } else {
            helper = new DBHelper(this);
            helper.addSalu(new Sala(1,cislo1,Integer.parseInt(kapacita1)));
            Intent intent = new Intent(Pridaj_salu.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            Toast.makeText(this, "Sála bola úspešne pridaná", Toast.LENGTH_SHORT).show();
        }
    }


    public void zrusit(View view) {
        Intent intent = new Intent(Pridaj_salu.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
