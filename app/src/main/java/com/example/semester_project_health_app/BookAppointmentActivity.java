package com.example.semester_project_health_app;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;

public class BookAppointmentActivity extends AppCompatActivity {
    EditText ed1, ed2, ed3, ed4;
    TextView tv;
    String date,formattedTime;
    Button btn, timebtn,btnbook,btnBack;
    private DatePickerDialog datepickerdilog1;
    private TimePickerDialog timepickerdialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_appointment);

        tv = findViewById(R.id.textViewOtitle);
        ed1 = findViewById(R.id.editTextBMBFullname);
        ed2 = findViewById(R.id.editTextBMBAdress);
        ed3 = findViewById(R.id.editTextBMBContNum);
        ed4 = findViewById(R.id.editTextBMBCode);
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
        String fullname = it.getStringExtra("text2");
        String address = it.getStringExtra("text3");
        String contact = it.getStringExtra("text4");
        String fees = it.getStringExtra("text5");

        tv.setText(title);
        ed1.setText(fullname);
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
                   DataBase db = new DataBase(getApplicationContext(), "healthcare", null, 1);
                   SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                   String username = sharedpreferences.getString("username", "").toString();

                   if (checkAppointmentExists(username, title+"=>"+ fullname, address, contact, date, formattedTime) == 1) {
                       Toast.makeText(getApplicationContext(), "Appointment already booked", Toast.LENGTH_LONG).show();
                   } else {
                       db.addOrder(username, title + "=>"+ fullname, address, contact, 0, date, formattedTime, Float.parseFloat(fees), "appointment");
                       Toast.makeText(getApplicationContext(), "Your appointment is done successfully", Toast.LENGTH_LONG).show();
                       startActivity(new Intent(BookAppointmentActivity.this, HomeActivity.class));
                   }

               }
           });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public int checkAppointmentExists(String username, String fullname, String address, String contact, String date, String time) {
        int result = 0;
        String str[] = new String[6];
        str[0] = username;
        str[1] = fullname;
        str[2] = address;
        str[3] = contact;
        str[4] = date;
        str[5] = time;

        DataBase dbHelper = new DataBase(getApplicationContext(), "healthcare", null, 1);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.rawQuery("select * from orderplace where username = ? and fullname = ? and address = ? and contact = ? and date = ? and time = ?", str);
        if (c.moveToFirst()) {
            result = 1;
        }
        db.close();
        return result;
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

            date = day + "/" + month + "/" + year;
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
                 formattedTime = String.format("%02d:%02d", hour, minute);
                timebtn.setText(formattedTime);  // Set the formatted time on the button
            });
        });
    }


}
