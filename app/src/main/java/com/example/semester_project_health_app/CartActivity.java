package com.example.semester_project_health_app;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class CartActivity extends AppCompatActivity {
    HashMap<String, String> item;
    ArrayList list;
    private String[][] packages={};
    SimpleAdapter sa;
    TextView tvTotal;
    ListView listvew;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Button dateButton, timeButton,btnCheck,btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        dateButton=findViewById(R.id.buttonCartDatePicker);
        timeButton=findViewById(R.id.buttonCartTimePicker);
        btnCheck=findViewById(R.id.buttonCartCheckout);
        btnBack=findViewById(R.id.buttonCartBack);
        tvTotal=findViewById(R.id.textViewCartTotalCost);
        listvew=findViewById(R.id.listViewBM);

        SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username = sharedpreferences.getString("userName", "").toString();

DataBase datab=new DataBase(getApplicationContext(),"healthcare",null,1);

float totalAmount=0;
ArrayList dbData=datab.getCartData(username,"lab");
packages=new String[dbData.size()][];
for(int i=0;i<packages.length;i++){
    packages[i]=new String[5];;

}
        for (int i = 0; i < dbData.size(); i++) {
            String arrData = dbData.get(i).toString();
            String[] strData = arrData.split(java.util.regex.Pattern.quote("$"));
            packages[i][0] = strData[0];
            packages[i][4] = "Cost: " + strData[1] + "/-";
            totalAmount = totalAmount + Float.parseFloat(strData[1]);
        }

tvTotal.setText("Total Cost : "+totalAmount);
        list=new ArrayList<>();
        for (int i = 0; i < packages.length; i++) {
            item = new HashMap<String, String>();
            item.put("line1", packages[i][0]);
            item.put("line2", packages[i][1]);
            item.put("line3", packages[i][2]);
            item.put("line4", packages[i][3]);
            item.put("line5", packages[i][4]);
            list.add(item);
        }
        sa = new SimpleAdapter(this, list,
                R.layout.multi_lines_view,
                new String[] {"line1", "line2", "line3", "line4", "line5"},
                new int[] {R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e}
        );
        listvew.setAdapter(sa);


        initDatePicker();
        initTimePicker();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartActivity.this,LabTestActivity.class));
            }
        });
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(CartActivity.this, LabTestBookActivity.class);
                it.putExtra("price", tvTotal.getText());
                it.putExtra("date", dateButton.getText());
                it.putExtra("time", timeButton.getText());
                startActivity(it);
            }
        });


    }

    private void initDatePicker() {
        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
        constraintsBuilder.setValidator(DateValidatorPointForward.from(System.currentTimeMillis() + 86400000)); // Minimum date is tomorrow

        // Build the MaterialDatePicker
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select a Date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())  // Default date
                .setCalendarConstraints(constraintsBuilder.build())         // Apply constraints
                .build();

        // Show the MaterialDatePicker when the button is clicked
        dateButton.setOnClickListener(v -> datePicker.show(getSupportFragmentManager(), "DATE_PICKER"));

        // Handle date selection
        datePicker.addOnPositiveButtonClickListener(selection -> {
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.setTimeInMillis(selection);

            int day = selectedDate.get(Calendar.DAY_OF_MONTH);
            int month = selectedDate.get(Calendar.MONTH) + 1; // Month is 0-indexed
            int year = selectedDate.get(Calendar.YEAR);

            String date = day + "/" + month + "/" + year;
            dateButton.setText(date);
        });
    }

    private void initTimePicker() {
        timeButton.setOnClickListener(v -> {
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
                timeButton.setText(formattedTime);  // Set the formatted time on the button
            });
        });
    }
}