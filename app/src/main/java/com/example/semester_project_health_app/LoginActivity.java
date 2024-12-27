package com.example.semester_project_health_app;

import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {

    EditText uname;
    EditText upassword;
    Button btn;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        uname=findViewById(R.id.editTextLoginUserName);
        upassword=findViewById(R.id.editTextLoginPassword);
        btn=findViewById(R.id.btnLogin);
        tv=findViewById(R.id.textViewNewUser);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernmelogin = uname.getText().toString();
                String upass=upassword.getText().toString();
                if(usernmelogin.length()==0 || upass.length()==0){
                    Toast.makeText(getApplicationContext(),"Empty Username or Password",Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_LONG).show();

                }
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}