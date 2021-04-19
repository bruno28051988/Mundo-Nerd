package com.br.mundonerd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import javax.annotation.Nullable;

import helpers.ConfiguracaoFirebase;
import helpers.UsuarioFirebase;
import model.Usuario;

public class HomeActivity extends AppCompatActivity {

    private static final int SELECAO_GALERIA = 200;

    private FirebaseAuth autenticacao;
    private ImageView imgPerfil;
    private String idUsuarioLogado;
    private TextView txtNome;

    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        autenticacao = ConfiguracaoFirebase.getFirebaseAuth();

        storageReference = ConfiguracaoFirebase.getStorageReference();

        idUsuarioLogado = UsuarioFirebase.getIdUsuario();

        //REFERENCIA PARA O CAMPO USUARIOS NO FIREBASE
        DatabaseReference database = ConfiguracaoFirebase.getFirebase().child("usuarios");

        //METODO DO FIREBASE PARA BUSCAR EM TEMPO REAL NO BANCO "UMA ESPECIE DE OBSERVADOR"
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot keyNode : snapshot.getChildren()){
                    Usuario usuario = keyNode.getValue(Usuario.class);
                    txtNome.setText(usuario.getNome());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        inicializaComponentes();


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Mundo Nerd");
        setSupportActionBar(toolbar);

        //Configuração a imagem
        imgPerfil.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                );

                if (i.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(i, SELECAO_GALERIA);
                }
            }
        });

    }
    public void sortearNumero(View view){
        startActivity(new Intent(this, SorteadorNumeroActivity.class));

    }

    public void sortearDado(View view){
        startActivity(new Intent(this, SortearDadoActivity.class));

    }

    public void sortearFrase(View view){
        startActivity(new Intent(this, FraseActivity.class));

    }

    private void inicializaComponentes() {
        imgPerfil = findViewById(R.id.imgPerfil);
        txtNome = findViewById(R.id.txtNome);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_usuario, menu);

        //MenuItem item = menu.findItem(R.id.toolbar);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuSair:
                autenticacao.signOut();
                finish();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            Bitmap imagem = null;

            try {
                switch (requestCode){
                    case SELECAO_GALERIA:
                        Uri localImagem = data.getData();
                        imagem = MediaStore.Images
                                .Media
                                .getBitmap(getContentResolver(),localImagem);
                        break;
                }

                //verifica se a imagem foi escolhida e já faz upload
                if (imagem != null){
                    imgPerfil.setImageBitmap(imagem);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imagem.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                    byte[] dadosImagem = baos.toByteArray();

                    //configurando o Storage
                    StorageReference imagemRef = storageReference
                            .child("imagens")
                            .child("usuarios")
                            .child(idUsuarioLogado + "jpeg");

                    //Tarefa de Upload
                    UploadTask uploadTask = imagemRef.putBytes(dadosImagem);

                    // Em caso de falha no upload
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(HomeActivity.this,
                                    "Erro ao fazer o upload da imagem",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(HomeActivity.this,
                                    "Sucesso ao fazer upload da imagem",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


}