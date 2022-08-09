package com.brainsMedia.FMW.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.brainsMedia.FMW.Adapters.WallpaperAdapter;
import com.brainsMedia.FMW.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class USACategory extends Fragment {


    private ProgressBar usaProg;
    private DatabaseReference usaRef;
    private RecyclerView usaRV;
    private ArrayList<String> usaLIst;
    private WallpaperAdapter usaAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_u_s_a_category, container, false);


        usaRef = FirebaseDatabase.getInstance().getReference().child("USA");

        usaProg = view.findViewById(R.id.usaProgress);
        usaRV = view.findViewById(R.id.usaRecycler);

        getUSAData();


        return view;
    }

    private void getUSAData(){

        usaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usaProg.setVisibility(View.GONE);
                usaLIst = new ArrayList<>();

                for(DataSnapshot shot: snapshot.getChildren()){

                    String data= shot.getValue().toString();
                    usaLIst.add(data);

                }

                usaRV.setLayoutManager(new GridLayoutManager(getContext(),2));
                usaRV.setHasFixedSize(true);
                usaRV.setItemViewCacheSize(13);
                usaAdapter = new WallpaperAdapter(getContext(),usaLIst);
                usaRV.setAdapter(usaAdapter);
                usaProg.setVisibility(View.GONE);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                usaProg.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}