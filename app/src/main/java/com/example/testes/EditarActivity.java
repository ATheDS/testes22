package com.example.testes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    private TextView cursostextview,nometextview,turnotextview;
    private Aluno aluno;

    String UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            aluno = (Aluno) bundle.get("aluno");
        }

        Button salvar = findViewById(R.id.salvar);
        iniciarcomponentes();

        salvar.setOnClickListener(view -> {
            salvardados();

            finish();
        });



    }
    private void salvardados() {
        String nome = editnome.getText().toString();
        String turno = editturno.getText().toString();
        String curso = editcursos.getText().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> Usuarios = new HashMap<>();


        if (!curso.isEmpty()) {
            Usuarios.put("curso", curso);


        }
        if (!nome.isEmpty()) {
            Usuarios.put("nome", nome);
        }

        if (!turno.isEmpty()) {
            Usuarios.put("turno", turno);

        }
        DocumentReference documentReference = db.collection("usuarios").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        documentReference.set(Usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        });
    }

    private void iniciarcomponentes(){
        cursostextview = findViewById(R.id.cursostextview);
        turnotextview = findViewById(R.id.turnotextview);
        nometextview = findViewById(R.id.nometextview);

        editnome = findViewById(R.id.nomeedittext);
        editcursos = findViewById(R.id.cursosedittext);
        editturno = findViewById(R.id.turnoedittext);

        if (aluno != null) {
            editnome.setText(aluno.getNome());
            editturno.setText(aluno.getTurno());
            editcursos.setText(aluno.getCursos().toString());
        }
    }
}