package com.br.mundonerd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class SortearDadoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sortear_dado);

    }

    public void voltar(View view){
        finish();
    }

    public void sortearDadoSeis(View view){
        TextView texto = findViewById(R.id.textResultadoDado);
        ImageView imgRes = findViewById(R.id.imgDado);

        imgRes.setImageResource(R.drawable.dado);
        int num1 = new Random().nextInt(7);
        texto.setText("" + num1);

    }

    public void sortearDadoDoze(View view){
        TextView texto = findViewById(R.id.textResultadoDado);
        ImageView imgRes = findViewById(R.id.imgDado);

        imgRes.setImageResource(R.drawable.dado);
        int num1 = new Random().nextInt(13);
        texto.setText("" + num1);


    }

    public void sortearDadoVinte(View view){
        TextView texto = findViewById(R.id.textResultadoDado);
        ImageView imgRes = findViewById(R.id.imgDado);

        imgRes.setImageResource(R.drawable.dado);
        int num1 = new Random().nextInt(21);
        texto.setText("" + num1);

    }

    public void sortearDadoCem(View view){
        TextView texto = findViewById(R.id.textResultadoDado);
        ImageView imgRes = findViewById(R.id.imgDado);

        imgRes.setImageResource(R.drawable.dado);
        int num1 = new Random().nextInt(101);
        texto.setText("" + num1);

    }
}