package com.example.testes;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.List;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import java.util.ArrayList;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public ImageButton editar;
    public TextView emailtext,nometext,cursotext,turnotext;
    private Aluno aluno;
    private List AdmList;
    private UserList Users;


    public PerfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilFragment newInstance(String param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {


            AdmList = (List) bundle.get("aList");
            Users = (UserList) bundle.get("Users");

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_perfil, container, false);
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();






        emailtext = view.findViewById(R.id.emailtextview);
        nometext = view.findViewById(R.id.nometextview);
        turnotext = view.findViewById(R.id.turnotextview);
        cursotext = view.findViewById(R.id.cursostextview);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference documentReference = db.collection("usuarios").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if(documentSnapshot !=null){

                    String nome = documentSnapshot.getString("nome");
                    String turno = documentSnapshot.getString("turno");
                    String cursos = documentSnapshot.getString("curso");
                    if(AdmList.contains(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                        Log.d("tag","Contem");

                    }


                    nometext.setText(nome);
                    cursotext.setText(cursos);
                    turnotext.setText(turno);

                    aluno = new Aluno();

                        aluno.setNome(nome);




                        aluno.setTurno(turno);
                        if(cursos != null){
                            aluno.setCursos(Integer.parseInt(cursos));

                        }






                    Users.add(aluno);





                }
            }
        });

        emailtext.setText(email);

        editar = view.findViewById(R.id.editar);
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),EditarActivity.class);
                intent.putExtra("aluno", aluno);
                startActivity(intent);
            }
        });

        Button deslogar = view.findViewById(R.id.deslogar);
        deslogar.setOnClickListener(view2 -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getActivity(),MainActivity.class);
            startActivity(intent);
            getActivity().finish();

        });

        return view;
    }


}