package com.example.to_dolist.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.to_dolist.MainActivity;
import com.example.to_dolist.R;
import com.example.to_dolist.databinding.ActivityInsertBinding;

public class InsertActivity extends AppCompatActivity {

    ActivityInsertBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInsertBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent();
                intent.putExtra("title",binding.etTitle.getText().toString());
                intent.putExtra("Description",binding.etDesc.getText().toString());
                setResult(RESULT_OK,intent);
                finish();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}