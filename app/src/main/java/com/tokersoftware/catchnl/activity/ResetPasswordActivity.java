package com.tokersoftware.catchnl.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.tokersoftware.catchnl.R;
import com.tokersoftware.catchnl.databinding.ActivityResetPasswordBinding;
import com.tokersoftware.catchnl.model.response.ErrorMessage;
import com.tokersoftware.catchnl.model.response.Login;
import com.tokersoftware.catchnl.service.API;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResetPasswordActivity extends AppCompatActivity {

    ActivityResetPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.resetPasswordBtn.setOnClickListener(v -> {
            if (!binding.email.getText().toString().isEmpty())
                sendRequest(binding.email.getText().toString());
            else
                Toast.makeText(this, "vul de e-mailbox in", Toast.LENGTH_SHORT).show();
        });
    }

    private void sendRequest(String email) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API service = retrofit.create(API.class);
        Call<ErrorMessage> errorMessageCall = service.resetPassword(email);

        errorMessageCall.enqueue(new Callback<ErrorMessage>() {
            @Override
            public void onResponse(Call<ErrorMessage> call, Response<ErrorMessage> response) {
                ErrorMessage body = response.body();
                if (body.getError() == 0){
                    Toast.makeText(ResetPasswordActivity.this, "De resetlink is naar uw e-mailadres verzonden.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ResetPasswordActivity.this, body.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ErrorMessage> call, Throwable t) {
                Toast.makeText(ResetPasswordActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}