package com.example.cw.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.room.Room;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cw.Database.DatabaseHelper;
import com.example.cw.Models.Hike;
import com.example.cw.Models.Observation;
import com.example.cw.R;

import java.time.LocalDate;

public class AddObservation extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    Button btnBackToObservation,btnAddObservation;
    EditText txtObservation,txtComment;
    TextView txtTOO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_observation);
        btnBackToObservation = findViewById(R.id.btnBackToObservation);
        btnBackToObservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the button click and navigate to the next activity
                Intent intent = new Intent(AddObservation.this, ViewObservation.class);
                startActivity(intent);
            }
        });
        txtObservation = findViewById(R.id.txtObservation);
        txtTOO= findViewById(R.id.txtTOO);
        txtTOO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new AddObservation.DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
        txtComment = findViewById(R.id.txtComment);
        btnAddObservation = findViewById(R.id.btnAddObservation);
        btnAddObservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addObservation();
            }
        });
        databaseHelper = Room.databaseBuilder(getApplicationContext(), DatabaseHelper.class, "CW")
                .allowMainThreadQueries() // For simplicity, don't use this in production
                .build();
    }
    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
        {
            LocalDate d = LocalDate.now();
            int year = d.getYear();
            int month = d.getMonthValue();
            int day = d.getDayOfMonth();
            return new DatePickerDialog(getActivity(), this, year, month -1, day);}
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day){
            LocalDate too = LocalDate.of(year, month+1, day);
            ((AddObservation)getActivity()).updateTOO(too);
        }
    }
    public void updateTOO(LocalDate too){
        TextView tooControl = findViewById(R.id.txtTOO);
        tooControl.setText(too.toString());
    }
    private void addObservation() {
        // Get references to the EditText views and read their content
        String observations = txtObservation.getText().toString();
        String comment = txtComment.getText().toString();
        String TOO = txtTOO.getText().toString();
        Toast.makeText(this, "Observation added successfully", Toast.LENGTH_SHORT).show();

        // Optionally, navigate to another activity or perform additional actions
        Observation observation = new Observation();
        observation.observation = observations;
        observation.too = TOO;
        observation.comments = comment;
        Toast.makeText(this, "Observation added successfully", Toast.LENGTH_SHORT).show();

        displayNextAlert();
        // Calls the insertDetails method we created
        long observationId = databaseHelper.observationDao().insertObservation(observation);
        if (observationId != -1) {
            System.out.println("Success");
        } else {
            System.out.println("Failed");
        }
    }
    public void displayNextAlert() {
        new AlertDialog.Builder(this)
                .setTitle("Success")
                .setMessage("Data was saved")
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(AddObservation.this, ViewHike.class);
                        startActivity(intent);
                    }
                })
                .show();
    }
}