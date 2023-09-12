package com.tokersoftware.catchnl.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.tokersoftware.catchnl.MainActivity;
import com.tokersoftware.catchnl.R;
import com.tokersoftware.catchnl.helper.InternetManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        checkInternet();

    }
    private void checkInternet(){
        if (InternetManager.InternetExists(SplashActivity.this)){
            goToMainScreen();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
            builder.setTitle("internetverbindingsfout");
            builder.setIcon(R.drawable.no_wifi_icon);
            builder.setMessage("Controleer uw internetverbinding");
            builder.setCancelable(false);
            builder.setPositiveButton("Opnieuw proberen", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    checkInternet();
                }
            });
            builder.show();
        }
    }

    private void goToMainScreen(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 2000);
    }
}