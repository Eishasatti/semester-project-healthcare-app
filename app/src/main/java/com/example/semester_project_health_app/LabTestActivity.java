package com.example.semester_project_health_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class LabTestActivity extends AppCompatActivity {
private String[][] pack={
        {"Package 1:Full Body Checkup","","","999"},
        {"Package 2:Blood Glucose Fasting","","","949"},
        {"Package 3:Covid-19 Antibody","","","699"},
        {"Package 4:Thyroid Check","","","950"},
        {"Package 5:Immunity Check","","","300"}
    };
    private String[] package_details = {
            "Blood Glucose Fasting\n" +
                    "   Complete Hemogram\n" +
                    "   HbA1c\n" +
                    "   Iron Studies\n" +
                    "   Kidney Function Test\n" +
                    "   LDH Lactate Dehydrogenase, Serum\n" +
                    "   Lipid Profile\n" +
                    "   Liver Function Test",

            "Blood Glucose Fasting\n" +
                    "COVID-19 Antibody - IgG",

            "Thyroid Profile-Total (T3, T4 & TSH Ultra-sensitive)",

            "Complete Hemogram\n" +
                    "   CRP (C Reactive Protein) Quantitative, Serum\n" +
                    "   Iron Studies\n" +
                    "   Kidney Function Test\n" +
                    "   Vitamin D Total-25 Hydroxy\n" +
                    "   Liver Function Test\n" +
                    "   Lipid Profile"
    };
HashMap<String,String> item;
ArrayList list;
SimpleAdapter sa;
Button btnCart,btnBac;
ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lab_test);
        btnCart=findViewById(R.id.btnlDGotocart);
        btnBac=findViewById(R.id.btnlDBack);
        listview=findViewById(R.id.ListViewlTest);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnBac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LabTestActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        list = new ArrayList();
        for(int i=0; i<pack.length; i++){
            item = new HashMap<String, String>();
            item.put("line1", pack[i][0]);
            item.put("line2", pack[i][1]);
            item.put("line3", pack[i][2]);
            item.put("line4", pack[i][3]);
            item.put("line5", "Total Cost: " + pack[i][4] + "/-");
            list.add(item);
        }
        sa = new SimpleAdapter(
                this, list,
                R.layout.multi_lines_view,
                new String[] { "line1", "line2", "line3", "line4", "line5" },
                new int[] { R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e }
        );


        listview.setAdapter(sa);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent(LabTestActivity.this,LabTestDetailActivity.class);
                intent.putExtra("text1",pack[position][0]);
                intent.putExtra("text2",package_details[position]);
                intent.putExtra("text3",pack[position][4]);
                startActivity(intent);

            }
        });

    }
}