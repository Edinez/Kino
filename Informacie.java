package com.example.edy.kino;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class Informacie extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_informacie);
    }

    public void zrusit(View view) {
        Intent intent = new Intent(Informacie.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
