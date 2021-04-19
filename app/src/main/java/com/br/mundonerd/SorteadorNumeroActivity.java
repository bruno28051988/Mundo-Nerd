package com.br.mundonerd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class SorteadorNumeroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorteador_numero);
    }

    public void voltar(View view){
        finish();
    }

    public void sortearDez(View view){
        TextView texto = findViewById(R.id.txtResultado);

        int num = new Random().nextInt(11);
        texto.setText("Número sorteado foi: "+ num);
    }

    public void sortearCem(View view){
        TextView texto = findViewById(R.id.txtResultado);

        int num = new Random().nextInt(101);
        texto.setText("Número sorteado foi: "+ num);
    }

    public void sortearMil(View view){
        TextView texto = findViewById(R.id.txtResultado);

        int num = new Random().nextInt(1001);
        texto.setText("Número sorteado foi: "+ num);
    }

    public void sortearDezMil(View view){
        TextView texto = findViewById(R.id.txtResultado);

        int num = new Random().nextInt(10001);
        texto.setText("Número sorteado foi: "+ num);
    }
}