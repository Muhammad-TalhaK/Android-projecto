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
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

        private TextInputEditText username,password;
        private Button loginBtn;
        private ProgressBar pbLogin;
        private TextView SignupRedirection;
        private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.submitBtn);
        pbLogin = findViewById(R.id.pbLogin);
        SignupRedirection = findViewById(R.id.SignupRedirection);
        mAuth = FirebaseAuth.getInstance();

        SignupRedirection.setOnClickListener(view -> {
            Intent i = new Intent(Login.this,Registration.class);
            startActivity(i);
        });

        loginBtn.setOnClickListener(view -> {
           String uname = username.getText().toString();
           String pwd = password.getText().toString();
           pbLogin.setVisibility(View.VISIBLE);

           if(TextUtils.isEmpty(uname)&&TextUtils.isEmpty(pwd)){
               Toast.makeText(Login.this, "Fields can't be empty", Toast.LENGTH_SHORT).show();
           }
           else{
               mAuth.signInWithEmailAndPassword(uname,pwd).addOnCompleteListener(task -> {
                   if(task.isSuccessful()){
                       pbLogin.setVisibility(View.GONE);
                       Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                       Intent i = new Intent(Login.this,MainActivity.class);
                       startActivity(i);
                   }
                   else{
                       pbLogin.setVisibility(View.GONE);
                       Toast.makeText(Login.this, "Username or password is incorrect", Toast.LENGTH_SHORT).show();
                   }
               });
           }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            Intent i = new Intent(Login.this,MainActivity.class);
            startActivity(i);
            this.finish();
        }
    }
}