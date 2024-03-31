package com.example.cw.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cw.Database.DatabaseHelper;
import com.example.cw.Models.Hike;
import com.example.cw.R;

import java.util.List;

public class Search extends AppCompatActivity {
    private EditText txtSearch;
    private Button btnSearchHike;
    TextView lblResult;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        txtSearch = findViewById(R.id.txtSearch);
        btnSearchHike = findViewById(R.id.btnSearchHike);
        lblResult = findViewById(R.id.lblResult);
        databaseHelper = Room.databaseBuilder(getApplicationContext(), DatabaseHelper.class, "CW")
                .allowMainThreadQueries() // For simplicity, don't use this in production
                .build();

        btnSearchHike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchHikeByName(txtSearch.getText().toString());
            }
        });
    }
    private void searchHikeByName(String name) {
        List<Hike> hikeList = databaseHelper.hikeDao().getHikesByName(name);

        if (hikeList != null && !hikeList.isEmpty()) {
            StringBuilder resultText = new StringBuilder("Results:\n");
            for (Hike hike : hikeList) {
                resultText.append("Name: ").append(hike.name).append("\n");
                // Add other details as needed
            }
            lblResult.setText(resultText.toString());
        } else {
            lblResult.setText("No results found.");
        }
    }
}