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

public class LoginActivity extends AppCompatActivity {

    EditText uname;
    EditText upassword;
    Button btn;
    TextView tv;
    DataBase db=new DataBase(getApplicationContext(),"healthcare",null,1);

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
                String usernamelogin = uname.getText().toString();
                String upass=upassword.getText().toString();
                if(usernamelogin.isEmpty() || upass.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Empty Username or Password",Toast.LENGTH_LONG).show();

                }else{
                    if(db.login(usernamelogin,upass)==1){
                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
                         Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
                        SharedPreferences sharedPreferences=getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("username",usernamelogin);
                        editor.apply();
                         startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Invalid username and password", Toast.LENGTH_LONG).show();

                    }
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