package com.example.semester_project_health_app;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;

public class BookAppointmentActivity extends AppCompatActivity {
    EditText ed1, ed2, ed3, ed4;
    TextView tv;
    Button btn, timebtn,btnbook,btnBack;
    private DatePickerDialog datepickerdilog1;
    private TimePickerDialog timepickerdialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_appointment);

        tv = findViewById(R.id.textViewAppBook);
        ed1 = findViewById(R.id.editTextAppFullname);
        ed2 = findViewById(R.id.editTextAppAdress);
        ed3 = findViewById(R.id.editTextContNum);
        ed4 = findViewById(R.id.editTextAppFee);
        btn = findViewById(R.id.btnAppDate);
        timebtn = findViewById(R.id.btnAppTime);
        btnbook=findViewById(R.id.btnBookAppoint);
        btnBack=findViewById(R.id.btnBookBack);


        ed1.setKeyListener(null);
        ed2.setKeyListener(null);
        ed3.setKeyListener(null);
        ed4.setKeyListener(null);

        Intent it = getIntent();
        String title = it.getStringExtra("text1");
        String full_name = it.getStringExtra("text2");
        String address = it.getStringExtra("text3");
        String contact = it.getStringExtra("text4");
        String fees = it.getStringExtra("text5");

        tv.setText(title);
        ed1.setText(full_name);
        ed2.setText(address);
        ed3.setText(contact);
        ed4.setText(fees);

        // Initialize the DatePicker and TimePicker, no need for duplicate OnClickListener here
        initDatePicker();
        initTimePicker();
           btnBack.setOnClickListener(new View.OnClickListener()
           {
    @Override
              public void onClick(View v) {
                  Intent intent=new Intent(BookAppointmentActivity.this,FindDoctorActivity.class);
                   startActivity(intent);
                   }
            });
           btnbook.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

               }
           });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initDatePicker() {
        // Configure constraints to set the minimum date to tomorrow
        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
        constraintsBuilder.setValidator(DateValidatorPointForward.from(System.currentTimeMillis() + 86400000)); // Minimum date is tomorrow

        // Build the MaterialDatePicker
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select a Date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())  // Default date
                .setCalendarConstraints(constraintsBuilder.build())         // Apply constraints
                .build();

        // Show the MaterialDatePicker when the button is clicked
        btn.setOnClickListener(v -> datePicker.show(getSupportFragmentManager(), "DATE_PICKER"));

        // Handle date selection
        datePicker.addOnPositiveButtonClickListener(selection -> {
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.setTimeInMillis(selection);

            int day = selectedDate.get(Calendar.DAY_OF_MONTH);
            int month = selectedDate.get(Calendar.MONTH) + 1; // Month is 0-indexed
            int year = selectedDate.get(Calendar.YEAR);

            String date = day + "/" + month + "/" + year;
            btn.setText(date);
        });
    }

    private void initTimePicker() {
        // Create and show the MaterialTimePicker when the button is clicked
        timebtn.setOnClickListener(v -> {
            MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H) // Use CLOCK_24H or CLOCK_12H based on your preference
                    .setHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) // Default hour
                    .setMinute(Calendar.getInstance().get(Calendar.MINUTE))   // Default minute
                    .setTitleText("Select Time")  // Title text
                    .build();

            timePicker.show(getSupportFragmentManager(), "TIME_PICKER");

            // Handle time selection
            timePicker.addOnPositiveButtonClickListener(dialog -> {
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();
                String formattedTime = String.format("%02d:%02d", hour, minute);
                timebtn.setText(formattedTime);  // Set the formatted time on the button
            });
        });
    }
}
