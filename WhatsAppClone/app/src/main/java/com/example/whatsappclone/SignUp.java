package com.example.whatsappclone;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.whatsappclone.Models.User;
import com.example.whatsappclone.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    ActivitySignUpBinding binding;
    FirebaseAuth auth;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary));

        myRef = FirebaseDatabase.getInstance().getReference();
        ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setTitle("Creating User");
        progressBar.setMessage("User is being created!");


        getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();
        binding.signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.show();
                if(TextUtils.isEmpty(binding.Name.getText().toString())){
                    binding.Name.setError("Enter Username");
                }
                if(TextUtils.isEmpty(binding.EmailSignup.getText().toString())){
                    binding.EmailSignup.setError("Enter your Email");
                }
                if(TextUtils.isEmpty(binding.PasswordSignup.getText().toString())){
                    binding.PasswordSignup.setError("Enter your Password");
                }
                auth.createUserWithEmailAndPassword(binding.EmailSignup.getText().toString(),binding.PasswordSignup.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressBar.dismiss();
                            User user = new User(binding.Name.getText().toString(),binding.EmailSignup.getText().toString(),binding.PasswordSignup.getText().toString());
                            String id = task.getResult().getUser().getUid();
                            user.setuId(id);
                            myRef.child("Users").child(id).setValue(user);
                            Toast.makeText(SignUp.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUp.this,MainActivity.class));
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUp.this, "User Failed to Register", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        binding.signInRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this,SignIn.class));
            }
        });
    }
}