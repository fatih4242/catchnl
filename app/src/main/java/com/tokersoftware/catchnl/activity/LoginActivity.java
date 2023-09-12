package com.tokersoftware.catchnl.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.tokersoftware.catchnl.MainActivity;
import com.tokersoftware.catchnl.R;
import com.tokersoftware.catchnl.databinding.ActivityLoginBinding;
import com.tokersoftware.catchnl.general.General;
import com.tokersoftware.catchnl.helper.Dialog;
import com.tokersoftware.catchnl.helper.LocalDataManager;
import com.tokersoftware.catchnl.model.response.Login;
import com.tokersoftware.catchnl.model.response.Register;
import com.tokersoftware.catchnl.service.API;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.registerBtn.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

        binding.forgetPassword.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
        });

        binding.loginBtn.setOnClickListener(v -> {
            dialog = new Dialog(LoginActivity.this);
            dialog.ShowDialog("Een ogenblik gedult", "We zijn bezig met het aanmaken van uw account");
            checkInputs();
        });
    }

    private void checkInputs() {
        if (binding.email.getText().toString().isEmpty()){
            dialog.DismissDialog();
            Toast.makeText(this, "Vul het e-mailadres gedeelte in", Toast.LENGTH_SHORT).show();
        } else if (binding.password.getText().toString().isEmpty()) {
            dialog.DismissDialog();
            Toast.makeText(this, "Vul het wachtwoord gedeelte in", Toast.LENGTH_SHORT).show();
        } else {
            makeRequest();
        }
    }

    private void makeRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API service = retrofit.create(API.class);
        Call<Login> loginCall = service.sendLoginRequest(
                binding.email.getText().toString(),
                binding.password.getText().toString()
        );

        loginCall.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                Login body = response.body();
                if (body.getError() == 0){
                    General.user = body;

                    LocalDataManager localDataManager = new LocalDataManager();
                    localDataManager.setSharedPreference(LoginActivity.this, "email", binding.email.getText().toString());
                    localDataManager.setSharedPreference(LoginActivity.this, "password", binding.password.getText().toString());

                    dialog.DismissDialog();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    dialog.DismissDialog();
                    Toast.makeText(LoginActivity.this, body.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                dialog.DismissDialog();
                Toast.makeText(LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}