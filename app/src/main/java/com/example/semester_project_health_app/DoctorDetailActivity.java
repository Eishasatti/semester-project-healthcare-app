package com.example.semester_project_health_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class DoctorDetailActivity extends AppCompatActivity {
TextView tv;
SimpleAdapter sa;
 private String[][] doctor_details1= {
        {"Doctor Name : Siraj", "Hospital Address : Rawalpindi", "Exp : 20yrs", "Mobile no: 03334454578", "600"},
        {"Doctor Name :Hadi", "Hospital Address : Karachi", "Exp : 15yrs", "Mobile no: 0333285245", "400"},
        {"Doctor Name : Ali","Hospital Address : Lahore","Exp : 18yrs","Mobile no: 033344982308","900"},
        {"Doctor Name : Hania","Hospital Address : Islamabad","Exp : 6yrs","Mobile no: 033367827","300"},
        {"Doctor Name : Kinza","Hospital Address : Sialkot","Exp : 4yrs","Mobile no: 0333434559","800"}
};
    private String[][] doctor_details2= {
            {"Doctor Name : Akhtar", "Hospital Address : Rawalpindi", "Exp : 20yrs", "Mobile no: 03334454578", "600"},
            {"Doctor Name :Ahmed", "Hospital Address : Karachi", "Exp : 5yrs", "Mobile no: 0333285245", "400"},
            {"Doctor Name : Sania","Hospital Address : Lahore","Exp : 18yrs","Mobile no: 033344982308","900"},
            {"Doctor Name : Rimsha","Hospital Address : Islamabad","Exp : 6yrs","Mobile no: 033367827","300"},
            {"Doctor Name : Zulaikha","Hospital Address : Sialkot","Exp : 4yrs","Mobile no: 0333434559","800"}
    };
    private String[][] doctor_details3= {
            {"Doctor Name : Kainat", "Hospital Address : Rawalpindi", "Exp : 21yrs", "Mobile no: 03334454578", "600"},
            {"Doctor Name : Asma", "Hospital Address : Karachi", "Exp : 13yrs", "Mobile no: 0333285245", "400"},
            {"Doctor Name : Sidra","Hospital Address : Lahore","Exp : 11yrs","Mobile no: 033344982308","900"},
            {"Doctor Name : Ehtisham","Hospital Address : Islamabad","Exp : 7yrs","Mobile no: 033367827","300"},
            {"Doctor Name : Adnan","Hospital Address : Sialkot","Exp : 14yrs","Mobile no: 0333434559","800"}
    };
    private String[][] doctor_details4= {
            {"Doctor Name : Saif", "Hospital Address : Rawalpindi", "Exp : 2yrs", "Mobile no: 03334454578", "600"},
            {"Doctor Name :Zain", "Hospital Address : Karachi", "Exp : 5yrs", "Mobile no: 0333285245", "400"},
            {"Doctor Name : Aness","Hospital Address : Lahore","Exp : 8yrs","Mobile no: 033344982308","900"},
            {"Doctor Name : Usama","Hospital Address : Islamabad","Exp : 16yrs","Mobile no: 033367827","300"},
            {"Doctor Name : Zara","Hospital Address : Sialkot","Exp : 4yrs","Mobile no: 0333434559","800"}
    };
    private String[][] doctor_details5= {
            {"Doctor Name : Qirat", "Hospital Address : Rawalpindi", "Exp : 20yrs", "Mobile no: 03334454578", "600"},
            {"Doctor Name :Ayza", "Hospital Address : Karachi", "Exp : 15yrs", "Mobile no: 0333285245", "400"},
            {"Doctor Name : Adeel","Hospital Address : Lahore","Exp : 18yrs","Mobile no: 033344982308","900"},
            {"Doctor Name : Saad","Hospital Address : Islamabad","Exp : 6yrs","Mobile no: 033367827","300"},
            {"Doctor Name : Fahad","Hospital Address : Sialkot","Exp : 4yrs","Mobile no: 0333434559","800"}
    };
Button btn;
String[][] doctors_detail={};
ArrayList list;
HashMap<String,String> item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doctor_detail);
        tv=findViewById(R.id.textViewHA);
        btn=findViewById(R.id.btnExitDDetail);


        Intent intent=getIntent();
        String title=intent.getStringExtra("title");
        tv.setText(title);
        if(title.compareTo("Family Physician")==0){
            doctors_detail=doctor_details1;
        } else if (title.compareTo("Dietician")==0) {

            doctors_detail=doctor_details2;
        }
        else if (title.compareTo("Surgeon")==0) {

             doctors_detail=doctor_details3;
        }
        else if (title.compareTo("Cardiologist")==0) {

              doctors_detail=doctor_details4;
        }

        else{
            doctors_detail=doctor_details5;
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorDetailActivity.this,FindDoctorActivity.class));

            }
        });


list=new ArrayList<>();
for(int i=0;i<doctors_detail.length;i++){
    item=new HashMap<String,String>();
    item.put("line1",doctors_detail[i][0]);
    item.put("line2",doctors_detail[i][1]);
    item.put("line3",doctors_detail[i][2]);
    item.put("line4",doctors_detail[i][3]);
    item.put("line5","Cons Fee: Rs "+doctors_detail[i][4]+"/-");
    list.add(item);

}

sa=new SimpleAdapter(this,
        list,
        R.layout.multi_lines_view,
        new String[]{"line1","line2","line3","line4","line5"},
        new int[]{R.id.line_a,R.id.line_b,R.id.line_c,R.id.line_d,R.id.line_e});

        ListView lst=findViewById(R.id.ListViewDoctorDetails);
        lst.setAdapter(sa);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             Intent intent=new Intent(DoctorDetailActivity.this, BookAppointmentActivity.class);
             intent.putExtra("text1",title);
             intent.putExtra("text2",doctors_detail[position][0]);
                intent.putExtra("text3",doctors_detail[position][1]);
                intent.putExtra("text4",doctors_detail[position][3]);
                intent.putExtra("text5",doctors_detail[position][4]);
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