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


public class AsianCategory extends Fragment {

    private ProgressBar asianProg;
    private DatabaseReference asianRef;
    private RecyclerView asianRV;
    private ArrayList<String> asianLIst;
    private WallpaperAdapter asianAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_asian_category, container, false);

        asianRef = FirebaseDatabase.getInstance().getReference().child("Asian");

        asianProg = view.findViewById(R.id.asianProgress);
        asianRV = view.findViewById(R.id.asianRecycler);

        getAsianData();

        return view;
    }

    private void getAsianData(){

        asianRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                asianProg.setVisibility(View.GONE);
                asianLIst = new ArrayList<>();

                for(DataSnapshot shot: snapshot.getChildren()){

                    String data= shot.getValue().toString();
                    asianLIst.add(data);

                }

                asianRV.setLayoutManager(new GridLayoutManager(getContext(),2));
                asianRV.setHasFixedSize(true);
                asianRV.setItemViewCacheSize(13);
                asianAdapter= new WallpaperAdapter(getContext(),asianLIst);
                asianRV.setAdapter(asianAdapter);
                asianProg.setVisibility(View.GONE);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                asianProg.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}