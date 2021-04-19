package com.br.mundonerd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class FraseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frase);
    }

    public void voltar(View view){
        finish();
    }

    public void sortearFrase(View view){

        String[] frases ={
                "Frase 1",
                "Frase 2",
                "Frase 3",
                "Frase 4"
        };

        int numero = new Random().nextInt(4);

        TextView texto = findViewById(R.id.fraseResultado);
        texto.setText(frases[numero]);
    }
}