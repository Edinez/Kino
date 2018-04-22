package com.example.edy.kino;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class Pridavanie_udalosti extends AppCompatActivity {

    DBHelper dbh;

    EditText cas;
    EditText den;
    EditText cena;
    Spinner idFilm;
    Spinner idSala;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pridavanie_udalosti);
        setTitle("Pridávanie udalosti");
        dbh= new DBHelper(Pridavanie_udalosti.this);
        cas = (EditText)   findViewById(R.id.udalostCas);
        den = (EditText)   findViewById(R.id.udalostDen);
        cena = (EditText)  findViewById(R.id.udalostCena);
        idFilm = (Spinner) findViewById(R.id.spinnerFilm);
        idSala = (Spinner) findViewById(R.id.spinnerSala);
        ArrayList<String> poleFilmy = new ArrayList();
        ArrayList<String> poleSaly = new ArrayList();
        for(int i=0;i<dbh.getFilmy().size();i++) {
            poleFilmy.add(dbh.getFilmy().get(i).get("nazov"));
            Log.v("MainAct",dbh.getFilmy().get(i).get("id")+" = id_filmy v aktivite Pridavanie_udalosti");
        }
        for(int i=0;i<dbh.getSalas().size();i++) {
            poleSaly.add(dbh.getSalas().get(i).get("cislo"));
            Log.v("MainAct",dbh.getSalas().get(i).get("id")+" = id_saly v aktivite Pridavanie_udalosti");
        }
        ArrayAdapter<String> adapterFilmy = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, poleFilmy);
        ArrayAdapter<String> adapterSaly  = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, poleSaly);
        adapterFilmy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         adapterSaly.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        idFilm.setAdapter(adapterFilmy);
        idSala.setAdapter(adapterSaly);
        idFilm.getSelectedItem();
        idSala.getSelectedItem();
    }

    public void ulozit(View view) {
        String cas1 = cas.getText().toString();
        String den1 = den.getText().toString();
        String cena1 = cena.getText().toString();
        int idFilmy1 = dbh.zistiPodlaNazvuFilm(idFilm.getSelectedItem().toString());
        int idSaly1  = dbh.zistiPodlaNazvuSalu(idSala.getSelectedItem().toString());

        if (cas1.equals("") || den1.equals("") || cena1.equals("")) {
            Toast.makeText(this, "Nevyplnili ste niektore polia!", Toast.LENGTH_SHORT).show();
        } else {
            dbh = new DBHelper(this);
            dbh.addCas(new Cas(1,cas1,den1, Integer.parseInt(cena1),idFilmy1,idSaly1));
            Log.v("MainAct","Čas: "+cas1+" Deň :"+den1+" cena: "+ Integer.parseInt(cena1)+" Filmy_id: "+idFilmy1+" Saly_id: "+idSaly1);
            Intent intent = new Intent(Pridavanie_udalosti.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            Toast.makeText(this, "Udalosť bola úspešne pridaná", Toast.LENGTH_SHORT).show();
        }
    }

    public void zrusit(View view) {
        Intent intent = new Intent(Pridavanie_udalosti.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
