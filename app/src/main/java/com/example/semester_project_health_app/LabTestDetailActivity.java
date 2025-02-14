package com.example.semester_project_health_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class LabTestDetailActivity extends AppCompatActivity {
TextView tPackName,tTotalCost;
EditText edDetails;
Button btnBack,btnCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lab_test_detail);
        tPackName=findViewById(R.id.textViewlabDet);
        tTotalCost=findViewById(R.id.textViewldTotalCost);
        edDetails=findViewById(R.id.editTextlDTextMultiLine);
      btnBack=findViewById(R.id.btnlDBack);
      btnCart=findViewById(R.id.btnlDGotocart);

        edDetails.setKeyListener(null);
        Intent it=getIntent();
        tPackName.setText(it.getStringExtra("text1"));
        tTotalCost.setText(it.getStringExtra("text2"));
        edDetails.setText("Total Cost:" + it.getStringExtra("text3")+"/-");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LabTestDetailActivity.this,LabTestActivity.class));

            }
        });
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "").toString();
                String product = tPackName.getText().toString();
                float price = Float.parseFloat(it.getStringExtra("text3").toString());
                DataBase db = new DataBase(getApplicationContext(), "healthcare", null, 1);

                if (db.checkCart(username, product) == 1) {
                    Toast.makeText(getApplicationContext(), "Product Already Added", Toast.LENGTH_SHORT).show();

                } else {
                    db.addCart(username, product, price, "Lab");
                    Toast.makeText(getApplicationContext(), "Record Inserted to Cart", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(LabTestDetailActivity.this,LabTestActivity.class));
                }



            }

        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}