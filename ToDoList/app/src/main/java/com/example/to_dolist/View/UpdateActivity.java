package com.example.to_dolist.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.to_dolist.MainActivity;
import com.example.to_dolist.R;
import com.example.to_dolist.databinding.ActivityUpdateBinding;

public class UpdateActivity extends AppCompatActivity {
  ActivityUpdateBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.etTitle.setText(getIntent().getStringExtra("titleUp"));
        binding.etDesc.setText(getIntent().getStringExtra("DescUp"));
        int id = getIntent().getIntExtra("id",0);

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent();
                intent.putExtra("titleUp",binding.etTitle.getText().toString());
                intent.putExtra("DescriptionUp",binding.etDesc.getText().toString());
                intent.putExtra("id",id);
                setResult(RESULT_OK,intent);
                finish();

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
    }
}