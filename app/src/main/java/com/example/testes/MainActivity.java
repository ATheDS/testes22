package com.example.testes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private EditText edit_email, edit_senha;
    private Button btn_login;
    private CheckBox lembrese;
    public List AdmList;
    public UserList Users;

    String[] mensagens = {"Preencha todos os campos", "Credenciais Inválidas"};
    boolean manterlogado;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        IniciarComponentes();
        Users = new UserList();
        AdmList = new ArrayList();
        AdmList.add("rafaeldinizsoaresreal@gmail.com");
        AdmList.add("diniz@arcom.com");
        AdmList.add("alyssom@arcom.com");


        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember", "");


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edit_email.getText().toString();
                String password = edit_senha.getText().toString();


                if (email.isEmpty() || password.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(view, mensagens[0], Snackbar.LENGTH_LONG);

                    snackbar.show();
                } else {

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                if (checkbox.equals("true")) {

                                    home();


                                }


                            } else {
                                Snackbar snackbar = Snackbar.make(view, mensagens[1], Snackbar.LENGTH_LONG);

                                snackbar.show();


                            }
                        }
                    });
                }
            }
        });

    }

    private void IniciarComponentes() {
        edit_email = findViewById(R.id.edit_usuario);
        edit_senha = findViewById(R.id.edit_senha);
        btn_login = findViewById(R.id.btn_login);
        lembrese = findViewById(R.id.lembresedemim);
        Paper.init(this);


    }

    private void login() {
        String email = edit_email.getText().toString();
        String pass = edit_senha.getText().toString();


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser useratual = FirebaseAuth.getInstance().getCurrentUser();
        if (useratual != null) {
            home();
        }
    }

    private void home() {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        intent.putExtra("aList", (Serializable) AdmList);
        intent.putExtra("Users", (Serializable) Users);

        startActivity(intent);

        finish();
    }
}
