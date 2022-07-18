package com.example.flashlight;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.flashlight.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
 ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        binding.btnFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.btnFlash.getText().toString().equals("Turn On")){
                    binding.btnFlash.setText(R.string.turnoff);
                    binding.flashlightImage.setImageResource(R.drawable.flashlight);
                    changeFlashState(true);
                }
                else{
                    binding.btnFlash.setText(R.string.turnon);
                    binding.flashlightImage.setImageResource(R.drawable.flashlightoff);
                    changeFlashState(false);
                }
            }
        });
    }

    private void changeFlashState(boolean state) {
        CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        String camId = null;

        try {
            camId = cameraManager.getCameraIdList()[0];
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode(camId,state);
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.btnFlash.setText(R.string.turnon);
    }
}