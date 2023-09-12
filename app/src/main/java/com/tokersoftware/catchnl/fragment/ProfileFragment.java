package com.tokersoftware.catchnl.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.tokersoftware.catchnl.MainActivity;
import com.tokersoftware.catchnl.R;
import com.tokersoftware.catchnl.activity.InfoActivity;
import com.tokersoftware.catchnl.databinding.FragmentProfileBinding;
import com.tokersoftware.catchnl.general.General;
import com.tokersoftware.catchnl.helper.Dialog;
import com.tokersoftware.catchnl.helper.LocalDataManager;
import com.tokersoftware.catchnl.model.response.ErrorMessage;
import com.tokersoftware.catchnl.service.API;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;
    Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding =  FragmentProfileBinding.inflate(inflater, container, false);

        init();

        return binding.getRoot();
    }
    private void init(){
        binding.email.setText(General.user.getEmail());
        binding.name.setText(General.user.getName());
        binding.licencePlate.setText(General.user.getLicencePlate());

        binding.licencePlate.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                System.out.println("before");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("on");
            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println("after");
            }
        });

        binding.actionbar.infoBtn.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), InfoActivity.class));
        });

        binding.logoutBtn.setOnClickListener(v -> {
            LocalDataManager localDataManager = new LocalDataManager();
            localDataManager.removeSharedPreference(getActivity(), "email");
            localDataManager.removeSharedPreference(getActivity(), "password");

            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        });

        binding.updateBtn.setOnClickListener(v -> {
            checkInputs();
        });
    }

    private void checkInputs() {
        if (binding.name.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "Vul het naam gedeelte in", Toast.LENGTH_SHORT).show();
        } else if (binding.email.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Vul het email gedeelte in", Toast.LENGTH_SHORT).show();
        } else if (binding.licencePlate.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "Vul het kenteken gedeelte in", Toast.LENGTH_SHORT).show();
        } else {
            dialog = new Dialog(getActivity());
            dialog.ShowDialog("Een ogenblik gedult", "We zijn bezig met het aanmaken van uw account");
            makeRequest();
        }
    }

    private void makeRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API service = retrofit.create(API.class);
        Call<ErrorMessage> errorMessageCall = service.updateUser(
                binding.email.getText().toString(),
                binding.name.getText().toString(),
                binding.licencePlate.getText().toString(),
                General.user.getId()
        );

        errorMessageCall.enqueue(new Callback<ErrorMessage>() {
            @Override
            public void onResponse(Call<ErrorMessage> call, Response<ErrorMessage> response) {
                ErrorMessage body = response.body();
                if (body.getError() == 0){
                    dialog.DismissDialog();
                    Toast.makeText(getActivity(), "Uw gegevens zijn bijgewerkt", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(getActivity(), MainActivity.class));
                    getActivity().finish();
                } else {
                    dialog.DismissDialog();
                    Toast.makeText(getActivity(), body.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ErrorMessage> call, Throwable t) {
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}