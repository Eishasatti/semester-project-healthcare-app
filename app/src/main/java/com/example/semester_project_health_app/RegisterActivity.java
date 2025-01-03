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

public class RegisterActivity extends AppCompatActivity {
EditText username_reg,email_reg,reg_pass,reg_conf_pass;
Button regBtn;
TextView tvToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        username_reg=findViewById(R.id.editTextRegisterUserName);
        email_reg=findViewById(R.id.editTextRegisteremail);
        reg_pass=findViewById(R.id.editTextRegisterPassword);
        reg_conf_pass=findViewById(R.id.editTextRegisterConfPassword2);
        regBtn=findViewById(R.id.btnRegister);
        tvToLogin=findViewById(R.id.textViewExistingUser);


        tvToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reg_uname=username_reg.getText().toString();
                String reg_email=email_reg.getText().toString();
            String reg_password=reg_pass.getText().toString();
            String reg_confirm=reg_conf_pass.getText().toString();
            DataBase db=new DataBase(getApplicationContext(),"healthcare",null,1);

            if(reg_uname.isEmpty() || reg_email.isEmpty() ||reg_password.isEmpty() ||reg_confirm.isEmpty()){
                Toast.makeText(RegisterActivity.this, "No empty field acceptable", Toast.LENGTH_SHORT).show();

            }
            else {
                if(reg_password.compareTo(reg_confirm)==0)
                {

                      if(isValid(reg_password)){
                          db.register(reg_uname,reg_email,reg_password);

                               Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                                       startActivity(intent);
                       }
                      else
                       {
                           Toast.makeText(RegisterActivity.this, "Password should be atleast 8 characters\nPassword must contain character ,digits , special symbols", Toast.LENGTH_SHORT).show();
                       }

                }
                else{
                    Toast.makeText(RegisterActivity.this, "Password mismatched", Toast.LENGTH_SHORT).show();

                }

            }
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public static boolean isValid(String passwordhere){
        int f1=0,f2=0,f3=0;
        if(passwordhere.length()<8){
            return false;
        }else{
            for(int i=0;i<passwordhere.length();i++){
                if(Character.isLetter(passwordhere.charAt(i)))
                {
                    f1=1;
                }
            }
            for(int r=0;r<passwordhere.length();r++){
                if(Character.isDigit(passwordhere.charAt(r))){
                    f2=1;

                }
            }
            for(int s=0;s<passwordhere.length();s++){
                char c=passwordhere.charAt(s);
                if(c>=33 && c<=46 || c==64){
                    f3=1;
                }
            }
            if(f1==1&&f2==1 && f3==1)
                return true;
            return false;
        }
    }
}