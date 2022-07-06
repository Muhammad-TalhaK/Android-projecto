package com.example.firebasecrud;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class Registration extends AppCompatActivity {

    private TextInputEditText username,password,confirmpwd;
    private Button submitBtn;
    private ProgressBar pbRegistration;
    private TextView loginRedirection;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
       confirmpwd = findViewById(R.id.confirmPwd);
        submitBtn = findViewById(R.id.submitBtn);
        pbRegistration = findViewById(R.id.pbRegistration);
        loginRedirection = findViewById(R.id.loginRedirection);
        mAuth = FirebaseAuth.getInstance();

        loginRedirection.setOnClickListener(view -> {
            Intent i = new Intent(Registration.this,Login.class);
            startActivity(i);
        });

        submitBtn.setOnClickListener(view -> {
            String uname = username.getText().toString();
            String pwd = password.getText().toString();
            String cpwd = confirmpwd.getText().toString();

            if(!pwd.equals(cpwd)){
                Toast.makeText(Registration.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(uname)&&TextUtils.isEmpty(pwd)&&TextUtils.isEmpty(cpwd)){
                Toast.makeText(Registration.this, "Fields can't be Empty", Toast.LENGTH_SHORT).show();
            }
            else{
                pbRegistration.setVisibility(View.VISIBLE);
               mAuth.createUserWithEmailAndPassword(uname,pwd).addOnCompleteListener(task -> {

                   if(task.isSuccessful()){
                       pbRegistration.setVisibility(View.GONE);
                       Toast.makeText(Registration.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                       Intent i = new Intent(Registration.this,Login.class);
                       startActivity(i);
                       finish();
                   }
                   else{
                       pbRegistration.setVisibility(View.GONE);
                       Toast.makeText(Registration.this, "Failed to Register User i.e. Password must be 8 characters long", Toast.LENGTH_SHORT).show();
                   }

               });
            }
        });


    }
}