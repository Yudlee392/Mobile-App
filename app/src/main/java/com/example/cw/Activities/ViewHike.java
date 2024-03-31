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

import com.example.cw.Adapters.ContactAdapter;
import com.example.cw.Database.DatabaseHelper;
import com.example.cw.Models.Hike;
import com.example.cw.R;

import java.util.List;

public class ViewHike extends AppCompatActivity implements ContactAdapter.OnDeleteClickListener{

    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerView;
    private ContactAdapter adapter;
    List<Hike> hikes;
    Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_hike);
        databaseHelper = Room.databaseBuilder(getApplicationContext(), DatabaseHelper.class, "CW")
                .allowMainThreadQueries() // For simplicity, don't use this in production
                .build();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        hikes = databaseHelper.hikeDao().getAllHikes();

        adapter = new ContactAdapter(hikes,this);
        recyclerView.setAdapter(adapter);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewHike.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onDeleteClick(Hike hike) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Contact")
                .setMessage("Are you sure you want to delete this contact?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    // Remove from the database
                    databaseHelper.hikeDao().deleteHike(hike);

                    // Update the list
                    hikes.remove(hike);
                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}