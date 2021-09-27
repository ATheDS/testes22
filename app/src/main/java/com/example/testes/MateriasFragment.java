package com.example.testes;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MateriasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MateriasFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Aluno aluno;
    private List AdmList;

    public MateriasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MateriasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MateriasFragment newInstance(String param1, String param2) {
        MateriasFragment fragment = new MateriasFragment();
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





    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        View view  = inflater.inflate(R.layout.fragment_materias, container, false);
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {


            AdmList = (List) bundle.get("aList");
        }
        Button editarm = view.findViewById(R.id.editarmaterias);
        if(!AdmList.contains(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
            editarm.setVisibility(View.GONE);

        }
        editarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(getActivity(),EditMateriasActivity.class);
                    startActivity(intent);


                }




        });







        // Inflate the layout for this fragment
        return view;
    }
}