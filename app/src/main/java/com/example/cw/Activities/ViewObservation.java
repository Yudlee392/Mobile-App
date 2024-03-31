package com.example.cw.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cw.Adapters.ContactAdapterObservation;
import com.example.cw.Database.DatabaseHelper;
import com.example.cw.Models.Hike;
import com.example.cw.Models.Observation;
import com.example.cw.R;

import java.util.List;

public class ViewObservation extends AppCompatActivity implements ContactAdapterObservation.OnDeleteClickListener{
    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerView;
    private ContactAdapterObservation adapter;
    List<Observation> observations;
    Button btnBackObservation,btnAddObservation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_observation);
        databaseHelper = Room.databaseBuilder(getApplicationContext(), DatabaseHelper.class, "CW")
                .allowMainThreadQueries() // For simplicity, don't use this in production
                .build();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        observations = databaseHelper.observationDao().getAllObservations();

        adapter = new ContactAdapterObservation(observations,this);
        recyclerView.setAdapter(adapter);
        btnBackObservation = findViewById(R.id.btnBackObservation);
        btnBackObservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewObservation.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnAddObservation = findViewById(R.id.btnAddObservation);
        btnAddObservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewObservation.this, AddObservation.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDeleteClick(Observation observation) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Contact")
                .setMessage("Are you sure you want to delete this contact?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    // Remove from the database
                    databaseHelper.observationDao().deleteObservation(observation);

                    // Update the list
                    observations.remove(observation);
                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}