package com.example.edy.kino;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class Editacia_udalost extends AppCompatActivity {
    int ID;
    DBHelper helper;
    EditText cas;
    EditText den;
    EditText cena;
    Spinner idFilm;
    Spinner idSala;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editacia_udalost);
        setTitle("Editácia udalostí");
        helper = new DBHelper(this);
        Intent intent = getIntent();
        cas = (EditText) findViewById(R.id.editCasCas);
        den = (EditText) findViewById(R.id.editCasDen);
        cena = (EditText) findViewById(R.id.editCasCena);
        idFilm = (Spinner) findViewById(R.id.spinnerCasFilm);
        idSala = (Spinner) findViewById(R.id.spinnerCasSala);
        ArrayList<String> poleFilmy = new ArrayList();
        ArrayList<String> poleSaly = new ArrayList();
        for (int i = 0; i < helper.getFilmy().size(); i++) {
            poleFilmy.add(helper.getFilmy().get(i).get("nazov"));
            Log.v("MainAct", helper.getFilmy().get(i).get("id") + " = ID_filmy v aktivite Editacia_udalosti");
        }
        for (int i = 0; i < helper.getSalas().size(); i++) {
            poleSaly.add(helper.getSalas().get(i).get("cislo"));
            Log.v("MainAct", helper.getFilmy().get(i).get("id") + " = ID_saly v aktivite Editacia_udalosti");
        }
        ArrayAdapter<String> adapterFilmy = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, poleFilmy);
        ArrayAdapter<String> adapterSaly = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, poleSaly);
        adapterFilmy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterSaly.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        idFilm.setAdapter(adapterFilmy);
        idSala.setAdapter(adapterSaly);
        idFilm.getSelectedItem();
        idSala.getSelectedItem();

        ID = Integer.parseInt(intent.getStringExtra("id"));
        cas.setText(intent.getStringExtra("cas"));
        den.setText(intent.getStringExtra("den"));
        cena.setText(intent.getStringExtra("cena"));
    }


    public void ulozit(View v) {
        String cas1 = cas.getText().toString();
        String den1 = den.getText().toString();
        String cena1 = cena.getText().toString();
        int idFilmy1 = helper.zistiPodlaNazvuFilm(idFilm.getSelectedItem().toString());
        int idSaly1 = helper.zistiPodlaNazvuSalu(idSala.getSelectedItem().toString());

        if (cas1.equals("") || den1.equals("") || cena1.equals("")) {
            Toast.makeText(this, "Nevyplnili ste niektore polia!", Toast.LENGTH_SHORT).show();
        } else {
            helper = new DBHelper(this);
            helper.updateCas(new Cas(ID, cas1, den1, Integer.parseInt(cena1), idFilmy1, idSaly1));
            Log.v("MainAct", "Čas: " + cas1 + " Deň :" + den1 + " cena: " + Integer.parseInt(cena1) + " Filmy_id: " + idFilmy1 + " Saly_id: " + idSaly1);
            Intent intent = new Intent(Editacia_udalost.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            Toast.makeText(this, "Udalosť bola úspešne upravená", Toast.LENGTH_SHORT).show();
        }
    }

    public void zrusit(View v) {
        finish();
        Intent i = new Intent(Editacia_udalost.this, Saly.class);
        startActivity(i);
    }
}
