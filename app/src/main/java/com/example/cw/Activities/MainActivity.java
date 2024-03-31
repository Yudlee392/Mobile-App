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


public class MainActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    EditText txtLocation, txtNameHike, txtLength, txtDescription;
    TextView txtDoH;
    RadioGroup radioGroup;
    Button btnAddHike,btnObservation,btnSearch;
    RadioButton rbYes,rbNo;
    String selectedDifficulty;
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
            LocalDate dob = LocalDate.of(year, month+1, day);
            ((MainActivity)getActivity()).updateDOB(dob);
        }
    }
    public void updateDOB(LocalDate dob){
        TextView dobControl = findViewById(R.id.txtDoH);
        dobControl.setText(dob.toString());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtLocation = findViewById(R.id.txtLocation);
        txtNameHike = findViewById(R.id.txtNameHike);
        txtLength = findViewById(R.id.txtLength);
        txtDescription = findViewById(R.id.txtDescription);
        Spinner spinner = findViewById(R.id.list_item);
        String[] options = {"Easy", "Normal", "High", "Very Hard"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        rbYes= findViewById(R.id.rbYes);
        rbNo= findViewById(R.id.rbNo);
        btnAddHike = findViewById(R.id.btnAddHike);
        btnAddHike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addHike();
            }
        });
        btnObservation = findViewById(R.id.btnObservation);
        btnObservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the button click and navigate to the next activity
                Intent intent = new Intent(MainActivity.this, ViewObservation.class);
                startActivity(intent);
            }
        });
        radioGroup = findViewById(R.id.radioGroup);
        txtDoH = findViewById(R.id.txtDoH);
        Button btnHome = findViewById(R.id.btnHome);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedDifficulty = options[position];
                // Handle the selected option
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });


        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the button click and navigate to the next activity
                Intent intent = new Intent(MainActivity.this, ViewHike.class);
                startActivity(intent);
            }
        });
        txtDoH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Check which radio button is selected
                if (checkedId == R.id.rbYes) {
                    showToast("Yes selected");
                } else if (checkedId == R.id.rbNo) {
                    showToast("No selected");
                }
            }
        });
        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the button click and navigate to the next activity
                Intent intent = new Intent(MainActivity.this, Search.class);
                startActivity(intent);
            }
        });

        databaseHelper = Room.databaseBuilder(getApplicationContext(), DatabaseHelper.class, "CW")
                .allowMainThreadQueries() // For simplicity, don't use this in production
                .build();
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    private void handleRadioButtonSelection(int checkedId) {
        int selectedId = -1;
        if (checkedId == R.id.rbYes) {
            // Handle the "Yes" radio button selection
            selectedId = R.id.rbYes;
        } else if (checkedId == R.id.rbNo) {
            // Handle the "No" radio button selection
            selectedId = R.id.rbNo;
        }
        String selectedText = ((RadioButton) findViewById(selectedId)).getText().toString();



// Now you can use the selectedId as needed

    }

    private void addHike() {
        // Get references to the EditText views and read their content
        String location = txtLocation.getText().toString();
        String nameHike = txtNameHike.getText().toString();
        String length = txtLength.getText().toString();
        int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        handleRadioButtonSelection(checkedRadioButtonId);
        String description = txtDescription.getText().toString();
        String hikeDate = txtDoH.getText().toString();
        boolean parkingAvailable = ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString().equalsIgnoreCase("Yes");
        Toast.makeText(this, "Hike added successfully", Toast.LENGTH_SHORT).show();

        // Optionally, navigate to another activity or perform additional actions
        Hike hike = new Hike();
        hike.location = location;
        hike.name = nameHike;
        hike.length = Integer.parseInt(length);
        hike.doh = hikeDate;
        hike.parking = parkingAvailable ? "Yes" : "No";
        hike.difficult = selectedDifficulty;
        hike.description = description;
        Toast.makeText(this, "Hike added successfully", Toast.LENGTH_SHORT).show();

        displayNextAlert(nameHike, location, length, hikeDate, parkingAvailable, description);
        // Calls the insertDetails method we created
        long hikeId = databaseHelper.hikeDao().insertHike(hike);
        if (hikeId != -1) {
            System.out.println("Success");
        } else {
            System.out.println("Failed");
        }
//        if (!isValidName(name)) {
//            // Show an error message or perform any required action
//            Toast.makeText(this, "Please fill name and must have at least 3 character", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if (!isValidDateOfBirth(hike)) {
//            // Show an error message or perform any required action
//            Toast.makeText(this, "Please fill date of hike", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (!isValidEmail(location)) {
//            // Show an error message or perform any required action
//            Toast.makeText(this, "Please fill email address", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (!isRadioButtonChecked()) {
//            // Show an error message or perform any required action
//            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (selectedImageResId != -1) {
//        }
//        // Shows a toast with the automatically generated id
//        Toast.makeText(this, "Person has been created",
//                Toast.LENGTH_LONG
//        ).show();
        displayNextAlert(nameHike, location, length, hikeDate, parkingAvailable, description);
    }
    public void displayNextAlert(String nameHike, String location, String length, String hikeDate, boolean parkingAvailable, String description) {
        String message = "Hike Name: " + nameHike + "\n" +
                "Location: " + location + "\n" +
                "Length: " + length + "\n" +
                "Date: " + hikeDate + "\n" +
                "Parking Available: " + parkingAvailable + "\n" +
                "Description: " + description;
        new AlertDialog.Builder(this)
                .setTitle("Success")
                .setMessage(message)
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(MainActivity.this, ViewHike.class);
                        startActivity(intent);
                    }
                })
                .show();
    }
}