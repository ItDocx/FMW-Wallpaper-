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


public class SpanishCategory extends Fragment {

    private ProgressBar spanishProg;
    private DatabaseReference spanishRef;
    private RecyclerView spanishRV;
    private ArrayList<String> spanishLIst;
    private WallpaperAdapter spanishAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_spanish_category, container, false);

        spanishRef = FirebaseDatabase.getInstance().getReference().child("Spanish");

        spanishProg = view.findViewById(R.id.spanishProgress);
        spanishRV = view.findViewById(R.id.spanishRecycler);

        getSpanishData();


        return view;
    }


    private void getSpanishData(){

        spanishRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                spanishProg.setVisibility(View.GONE);
                spanishLIst = new ArrayList<>();

                for(DataSnapshot shot: snapshot.getChildren()){

                    String data= shot.getValue().toString();
                    spanishLIst.add(data);

                }

                spanishRV.setLayoutManager(new GridLayoutManager(getContext(),2));
                spanishRV.setHasFixedSize(true);
                spanishRV.setItemViewCacheSize(13);
                spanishAdapter = new WallpaperAdapter(getContext(),spanishLIst);
                spanishRV.setAdapter(spanishAdapter);
                spanishProg.setVisibility(View.GONE);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                spanishProg.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}