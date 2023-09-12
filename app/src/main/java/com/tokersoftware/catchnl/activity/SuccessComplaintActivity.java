package com.tokersoftware.catchnl.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.tokersoftware.catchnl.MainActivity;
import com.tokersoftware.catchnl.R;
import com.tokersoftware.catchnl.databinding.ActivitySuccessComplaintBinding;

public class SuccessComplaintActivity extends AppCompatActivity {

    ActivitySuccessComplaintBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySuccessComplaintBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.goToMainBtn.setOnClickListener(v -> {
            startActivity(new Intent(SuccessComplaintActivity.this, MainActivity.class));
            finish();
        });
    }
}