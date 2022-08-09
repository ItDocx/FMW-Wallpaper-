package com.brainsMedia.FMW.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.brainsMedia.FMW.Adapters.WallpaperAdapter;
import com.brainsMedia.FMW.MainActivity;
import com.brainsMedia.FMW.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class IndianCategory extends Fragment {
    private Boolean onBackPressed = false;
    private ProgressBar indianProg;
    private DatabaseReference indianRef;
    private RecyclerView indianRV;
    private ArrayList<String> indianLIst;
    private WallpaperAdapter indianAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_indian_category, container, false);
        indianRef = FirebaseDatabase.getInstance().getReference().child("Indian");

        indianProg = view.findViewById(R.id.indianProgress);
        indianRV = view.findViewById(R.id.indianRecycler);



        getIndianData();

        return view;
    }

    private void getIndianData(){

        indianRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                indianProg.setVisibility(View.GONE);
                indianLIst = new ArrayList<>();

                for(DataSnapshot shot: snapshot.getChildren()){

                    String data= shot.getValue().toString();
                    indianLIst.add(data);

                }

                indianRV.setLayoutManager(new GridLayoutManager(getContext(),2));
                indianRV.setHasFixedSize(true);
                indianRV.setItemViewCacheSize(13);
                indianAdapter = new WallpaperAdapter(getContext(),indianLIst);
                indianRV.setAdapter(indianAdapter);
                indianProg.setVisibility(View.GONE);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                indianProg.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

 /*   // Initializing Dialogue
    private void getDialog() {


        IndianDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT );
        IndianDialog.setCancelable(false);

        ImageButton share_btn = IndianDialog.findViewById(R.id.share_btn);
        Button close_btn = IndianDialog.findViewById(R.id.close_btn);
        TextView contact_txt = IndianDialog.findViewById(R.id.email);

        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareApp();

            }
        });

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IndianDialog.dismiss();
            }
        });

    }

  */


}