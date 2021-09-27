package com.example.testes;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    private TabItem btn_perfil,btn_agenda, btn_materias;
    private TextView nome, email,cursos,turno;

    private TabLayout tablayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        IniciarComponentes();

        PagerAdapter pagerAdapter = new
                PagerAdapter(getSupportFragmentManager(),
                tablayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });





    }

    public void IniciarComponentes(){
        tablayout = findViewById(R.id.tablayout);
        btn_perfil = findViewById(R.id.perfil_btn);
        btn_agenda = findViewById(R.id.agenda_btn);
        btn_materias = findViewById(R.id.materias_btn);
        nome = findViewById(R.id.nometextview);
        cursos = findViewById(R.id.cursostextview);
        email = findViewById(R.id.emailtextview);
        turno = findViewById(R.id.turnotextview);
        viewPager = findViewById(R.id.viewpager);
    }
}




