package com.example.edy.kino;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Pridaj_film extends AppCompatActivity {
    DBHelper helper;

    EditText nazov;
    EditText dlzka;
    EditText rok;
    EditText popis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pridaj_film);
        setTitle("Pridávanie filmu");
        nazov = (EditText) findViewById(R.id.nazovText);
        dlzka = (EditText) findViewById(R.id.dlzkaText);
        rok = (EditText) findViewById(R.id.rokText);
        popis = (EditText) findViewById(R.id.popisText);
    }

    public void ulozit(View view) {
        String nazov1 = nazov.getText().toString();
        String dlzka1 = dlzka.getText().toString();
        String rok1 = rok.getText().toString();
        String popis1 = popis.getText().toString();

        if (nazov1.equals("") || dlzka1.equals("") || rok1.equals("") || popis1.equals("") ) {
            Toast.makeText(this, "Nevyplnili ste niektore polia!", Toast.LENGTH_SHORT).show();
        } else {
            helper = new DBHelper(this);
            helper.addFilm(new Film(1, nazov1, Integer.parseInt(dlzka1), Integer.parseInt(rok1),popis1));
            Intent intent = new Intent(Pridaj_film.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            Toast.makeText(this, "Film bol úspešne pridaný", Toast.LENGTH_SHORT).show();
        }
    }

    public void zrusit(View view) {
        Intent intent = new Intent(Pridaj_film.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
