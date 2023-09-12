package com.tokersoftware.catchnl.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.tokersoftware.catchnl.MainActivity;
import com.tokersoftware.catchnl.R;
import com.tokersoftware.catchnl.databinding.ActivityRegisterBinding;
import com.tokersoftware.catchnl.helper.Dialog;
import com.tokersoftware.catchnl.helper.LocalDataManager;
import com.tokersoftware.catchnl.model.response.Register;
import com.tokersoftware.catchnl.service.API;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    Dialog dialog;

    Bitmap bitmap;
    Uri selectedImageUri;
    int SELECT_PICTURE = 200;
    int permissionRequestCode = 201;
    String encodedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.loginBtn.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });

        binding.registerBtn.setOnClickListener(v -> {
            dialog = new Dialog(this);
            dialog.ShowDialog("Een ogenblik gedult", "We zijn bezig met het aanmaken van uw account");
            checkForInputs();
        });
        
        binding.licenceFileTXT.setOnClickListener( v -> {
            checkForPermission();
        });
    }

    private void checkForInputs() {
        if (binding.licencePlate.getText().toString().isEmpty()){
            Toast.makeText(this, "Vul aub het kenteken in", Toast.LENGTH_SHORT).show();
            dialog.DismissDialog();
        } else if (binding.name.getText().toString().isEmpty()){
            Toast.makeText(this, "Vul aub uw naam in", Toast.LENGTH_SHORT).show();
            dialog.DismissDialog();
        } else if (binding.email.getText().toString().isEmpty()) {
            Toast.makeText(this, "Vul aub uw email in", Toast.LENGTH_SHORT).show();
            dialog.DismissDialog();

        } else if (binding.password.getText().toString().isEmpty()){
            dialog.DismissDialog();

            Toast.makeText(this, "Vul aub uw wachtwoord in", Toast.LENGTH_SHORT).show();
        } else if (binding.licenceFilePreview.getDrawable() == null) {
            Toast.makeText(this, "Selecteer een afbeelding door op de Een foto van uw KENTEKENBEWIJS te klikken.", Toast.LENGTH_LONG).show();
            dialog.DismissDialog();

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
        Call<Register> registerCall = service.sendRegisterRequest(
                binding.name.getText().toString(),
                binding.email.getText().toString(),
                binding.password.getText().toString(),
                binding.licencePlate.getText().toString(),
                encodedImage
        );

        registerCall.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                System.out.println(response.body());
                Register body = response.body();
                if (body.getError() == 0){
                    dialog.DismissDialog();
                    Toast.makeText(RegisterActivity.this, "Je account is succesvol aangemaakt", Toast.LENGTH_SHORT).show();

                    LocalDataManager localDataManager = new LocalDataManager();
                    localDataManager.setSharedPreference(RegisterActivity.this, "email", binding.email.getText().toString());
                    localDataManager.setSharedPreference(RegisterActivity.this, "password", binding.password.getText().toString());

                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    finish();
                } else {
                    dialog.DismissDialog();
                    Toast.makeText(RegisterActivity.this, body.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                System.out.println(t.getMessage());
                dialog.DismissDialog();

            }
        });
    }

    private void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    private void checkForPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, permissionRequestCode);
        }else{
            imageChooser();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
                Uri selectedImageUriFromGallery = data.getData();
                // update the preview image in the layout
                selectedImageUri = selectedImageUriFromGallery;
                binding.licenceFilePreview.setImageURI(selectedImageUriFromGallery);
                binding.licenceFilePreview.setVisibility(View.VISIBLE);
                binding.licenceFileTXT.setVisibility(View.GONE);



            try {
                InputStream inputStream = getContentResolver().openInputStream(selectedImageUriFromGallery);
                bitmap = BitmapFactory.decodeStream(inputStream);

                imageStore(bitmap);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }else{
            Toast.makeText(this, "foo2", Toast.LENGTH_SHORT).show();
        }
    }

    private void imageStore(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);

        byte[] imageBytes = stream.toByteArray();

        encodedImage = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);


    }


    // This function is called when user accept or decline the permission.
// Request Code is used to check which permission called this function.
// This request code is provided when user is prompt for permission.
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == permissionRequestCode) {

            // Checking whether user granted the permission or not.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                imageChooser();
            }
            else {
                Toast.makeText(RegisterActivity.this, "Voor het uploaden van uw rijbewijs hebben wij uw toestemming nodig", Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == permissionRequestCode) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                imageChooser();
            }
            else {
                Toast.makeText(RegisterActivity.this, "Voor het uploaden van uw rijbewijs hebben wij uw toestemming nodig", Toast.LENGTH_SHORT).show();
            }
        }
    }
}