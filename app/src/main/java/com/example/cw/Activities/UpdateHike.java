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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cw.Database.DatabaseHelper;
import com.example.cw.Models.Hike;
import com.example.cw.R;

import java.time.LocalDate;
import java.util.List;

public class UpdateHike extends AppCompatActivity {
    EditText txtLocation, txtNameHike, txtLength, txtDescription;
    TextView txtDoH;
    RadioGroup radioGroup;
    RadioButton rbYes,rbNo;
    DatabaseHelper databaseHelper;
    String selectedDifficulty;
    Button btnUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_hike);
        txtLocation = findViewById(R.id.txtLocationUpdate);
        txtNameHike = findViewById(R.id.txtNameHikeUpdate);
        txtLength = findViewById(R.id.txtLengthUpdate);
        txtDescription = findViewById(R.id.txtDescriptionUpdate);
        Spinner spinner = findViewById(R.id.list_item_update);
        String[] options = {"Easy", "Normal", "High", "Very Hard"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        rbYes= findViewById(R.id.rbYesUpdate);
        rbNo= findViewById(R.id.rbNoUpdate);
        radioGroup = findViewById(R.id.radioGroupUpdate);
        txtDoH = findViewById(R.id.txtDoHUpdate);
        btnUpdate = findViewById(R.id.btnUpdate);

        // You can add your code to retrieve data and update UI elements here

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call your updateHike method or perform update operation here
                updateHike();
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedDifficulty = options[position];
                // Handle the selected option
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });
        txtDoH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new UpdateHike.DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Check which radio button is selected
                if (checkedId == R.id.rbYes) {
                } else if (checkedId == R.id.rbNo) {
                }
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
            return new DatePickerDialog(requireActivity(), this, year, month -1, day);}
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day){
            LocalDate dob = LocalDate.of(year, month+1, day);
            ((UpdateHike)requireActivity()).updateDOH(dob);
        }
    }
    public void updateDOH(LocalDate dob){
        TextView dobControl = findViewById(R.id.txtDoHUpdate);
        dobControl.setText(dob.toString());
    }
    private void updateHike() {
        // Get references to the EditText views and read their content
        String location = txtLocation.getText().toString();
        String nameHike = txtNameHike.getText().toString();
        String length = txtLength.getText().toString();
        int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        handleRadioButtonSelection(checkedRadioButtonId);
        String description = txtDescription.getText().toString();
        String hikeDate = txtDoH.getText().toString();
        boolean parkingAvailable = ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString().equalsIgnoreCase("Yes");
        Toast.makeText(this, "Hike updated successfully", Toast.LENGTH_SHORT).show();

        Hike existingHike = databaseHelper.hikeDao().getHikeByName(nameHike);

        if (existingHike != null) {
            // Update the existing hike
            existingHike.location = location;
            existingHike.length = Integer.parseInt(length);
            existingHike.doh = hikeDate;
            existingHike.parking = parkingAvailable ? "Yes" : "No";
            existingHike.difficult = selectedDifficulty;
            existingHike.description = description;
            int rowsUpdated = databaseHelper.hikeDao().updateHike(existingHike);
            if (rowsUpdated > 0) {
                // Update was successful
                System.out.println("Success");
            } else {
                // Update failed
                System.out.println("Failed");
            }
        }
        displayNextAlert();
    }
    private void handleRadioButtonSelection(int radioButtonId) {
        RadioButton selectedRadioButton = findViewById(radioButtonId);

        // Check if the RadioButton is not null before accessing its properties
        if (selectedRadioButton != null) {
            String selectedOption = selectedRadioButton.getText().toString();
            // Further processing based on the selected option
            // ...
        } else {
            // Handle

        }
    }
    public void displayNextAlert() {
        new AlertDialog.Builder(this)
                .setTitle("Success")
                .setMessage("Data was saved")
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(UpdateHike.this, ViewHike.class);
                        startActivity(intent);
                    }
                })
                .show();
    }
}