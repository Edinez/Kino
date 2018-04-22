package com.example.edy.kino;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Editacia_filmy extends AppCompatActivity {
    int ID;
    DBHelper helper;
    EditText nazov;
    EditText dlzka;
    EditText rok;
    EditText popis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editacia_filmy);
        setTitle("Editovanie filmov");
        Intent intent = getIntent();
        helper = new DBHelper(this);
        nazov = (EditText) findViewById(R.id.editNazovFilmu);
        dlzka = (EditText) findViewById(R.id.editDlzkaFilmu);
        rok = (EditText) findViewById(R.id.editRokFilmu);
        popis = (EditText) findViewById(R.id.editPopisFilmu);

        ID = Integer.parseInt(intent.getStringExtra("id"));
        nazov.setText(intent.getStringExtra("nazov"));
        dlzka.setText(intent.getStringExtra("dlzka"));
        rok.setText(intent.getStringExtra("rok"));
        popis.setText(intent.getStringExtra("popis"));
    }


    public void ulozitButton(View v) {
        if (nazov.getText().toString().equals("") || dlzka.getText().toString().equals("")|| rok.getText().toString().equals("")|| popis.getText().toString().equals(""))
            Toast.makeText(this, "Niektoré pole zostalo prázne", Toast.LENGTH_SHORT).show();
        else {
            helper.updateFilm(new Film(ID, nazov.getText().toString(),Integer.parseInt(dlzka.getText().toString()),Integer.parseInt(rok.getText().toString()), popis.getText().toString()));
            finish();
            Intent i = new Intent(Editacia_filmy.this, Filmy.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            Toast.makeText(this, "Film bol úspešne upravený", Toast.LENGTH_SHORT).show();
            startActivity(i);
        }
    }

    public void zrusitButton(View v) {
        finish();
        Intent i = new Intent(Editacia_filmy.this, Filmy.class);
        startActivity(i);
    }
}
