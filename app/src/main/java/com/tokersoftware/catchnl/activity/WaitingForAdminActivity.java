package com.tokersoftware.catchnl.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.tokersoftware.catchnl.MainActivity;
import com.tokersoftware.catchnl.R;
import com.tokersoftware.catchnl.databinding.ActivityWaitingForAdminBinding;
import com.tokersoftware.catchnl.helper.LocalDataManager;

public class WaitingForAdminActivity extends AppCompatActivity {

    ActivityWaitingForAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWaitingForAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.actionbar.infoBtn.setOnClickListener(v -> {
            startActivity(new Intent(WaitingForAdminActivity.this, InfoActivity.class));
        });

        binding.useAnAnotherAccount.setOnClickListener(v -> {
            startActivity(new Intent(WaitingForAdminActivity.this, LoginActivity.class));
            finish();
        });
    }
}