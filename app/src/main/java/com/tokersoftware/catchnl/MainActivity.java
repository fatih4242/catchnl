package com.tokersoftware.catchnl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.tokersoftware.catchnl.activity.LoginActivity;
import com.tokersoftware.catchnl.activity.RegisterActivity;
import com.tokersoftware.catchnl.activity.WaitingForAdminActivity;
import com.tokersoftware.catchnl.databinding.ActivityMainBinding;
import com.tokersoftware.catchnl.fragment.HistoryFragment;
import com.tokersoftware.catchnl.fragment.HomeFragment;
import com.tokersoftware.catchnl.fragment.ProfileFragment;
import com.tokersoftware.catchnl.general.General;
import com.tokersoftware.catchnl.helper.Dialog;
import com.tokersoftware.catchnl.helper.LocalDataManager;
import com.tokersoftware.catchnl.model.response.Login;
import com.tokersoftware.catchnl.service.API;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    LocalDataManager localDataManager;
    Dialog dialog;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        checkForUser();



    }

    private void initFragments() {
        replaceFragment(new HomeFragment());

        binding.bottomNavigation.setSelectedItemId(R.id.home);
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            System.out.println("click");
            switch (item.getItemId()){
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;

                case R.id.history:
                    replaceFragment(new HistoryFragment());
                    break;

                case R.id.profile:
                    replaceFragment(new ProfileFragment());
                    break;
            }

            return true;
        });
    }

    private void checkForUser() {
        localDataManager = new LocalDataManager();

        if(localDataManager.getSharedPreference(this, "email").isEmpty()
        && localDataManager.getSharedPreference(this, "password").isEmpty()){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        } else {
            dialog = new Dialog(MainActivity.this);
            dialog.ShowDialog("Een ogenblik gedult", "We controleren uw gegevens");
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
                localDataManager.getSharedPreference(this, "email"),
                localDataManager.getSharedPreference(this, "password")
        );

        loginCall.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                Login body = response.body();
                if (body.getError() == 0){
                    dialog.DismissDialog();
                    if (body.getIsAccepted().equals("false")){
                        startActivity(new Intent(MainActivity.this, WaitingForAdminActivity.class));
                        finish();
                    } else {
                        General.user = body;
                        initFragments();
                    }

                } else {
                    dialog.DismissDialog();
                    Toast.makeText(MainActivity.this, body.getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                dialog.DismissDialog();
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();

    }
}