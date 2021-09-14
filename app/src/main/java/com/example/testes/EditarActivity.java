package com.example.testes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class EditarActivity extends AppCompatActivity {

    private EditText editnome,editcursos,editturno;

    String UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        Button salvar = findViewById(R.id.salvar);
        iniciarcomponentes();




        salvar.setOnClickListener(view -> {
            salvardados();
            Intent intent = new Intent(EditarActivity.this,Home.class);
            startActivity(intent);
            finish();
        });



    }
    private void salvardados(){
        String nome = editnome.getText().toString();
        String turno = editturno.getText().toString();
        String curso = editcursos.getText().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String,Object> Usuarios = new HashMap<>();



        if (!curso.isEmpty()) {
            Usuarios.put("curso",curso);

        }
        if (!nome.isEmpty()) {
            Usuarios.put("nome",nome);
        }

        if (!turno.isEmpty()) {
            Usuarios.put("turno",turno);

        }
        DocumentReference documentReference = db.collection("usuarios").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if(documentSnapshot !=null){
                    editnome.setText(documentSnapshot.getString("nome"));
                    Usuarios.put("nome",nome);


                    editcursos.setText(documentSnapshot.getString("curso"));
                    Usuarios.put("curso",curso);


                    editturno.setText(documentSnapshot.getString("turno"));
                    Usuarios.put("turno",turno);


                }
            }
        });

        UserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        documentReference.set(Usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("db","sucesso ao salvar os dados");

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("db_error","erro ao salvar os dados"+e.toString());

                    }
                });



    }

    private void iniciarcomponentes(){
        editnome = findViewById(R.id.nomeedittext);
        editcursos = findViewById(R.id.cursosedittext);
        editturno = findViewById(R.id.turnoedittext);


    }
}