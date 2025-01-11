package com.example.semester_project_health_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FindDoctorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_find_doctor);
        CardView exitid=findViewById(R.id.cardFdExit);
        exitid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FindDoctorActivity.this,HomeActivity.class));
            }
        });
CardView familyphy=findViewById(R.id.cardFamilyPhys);
familyphy.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(FindDoctorActivity.this,DoctorDetailActivity.class);
intent.putExtra("title","Family Physician");
startActivity(intent);
    }
});
        CardView ditetion=findViewById(R.id.cardFdDietitian);
        ditetion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FindDoctorActivity.this,DoctorDetailActivity.class);
                intent.putExtra("title","Dietician");
                startActivity(intent);
            }
        });
        CardView den=findViewById(R.id.cardFdDentisit);
        den.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FindDoctorActivity.this,DoctorDetailActivity.class);
                intent.putExtra("title","Dentist");
                startActivity(intent);
            }
        });
        CardView surg=findViewById(R.id.cardFdSurgeon);
        surg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FindDoctorActivity.this,DoctorDetailActivity.class);
                intent.putExtra("title","Surgeon");
                startActivity(intent);
            }
        });
        CardView cardio=findViewById(R.id.cardFdCardiologist);
        cardio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FindDoctorActivity.this,DoctorDetailActivity.class);
                intent.putExtra("title","Cardiologist");
                startActivity(intent);
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}