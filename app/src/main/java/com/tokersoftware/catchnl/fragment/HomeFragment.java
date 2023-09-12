package com.tokersoftware.catchnl.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.tokersoftware.catchnl.R;
import com.tokersoftware.catchnl.activity.InfoActivity;
import com.tokersoftware.catchnl.databinding.FragmentHomeBinding;
import com.tokersoftware.catchnl.general.General;
import com.tokersoftware.catchnl.helper.Dialog;
import com.tokersoftware.catchnl.model.response.ErrorMessage;
import com.tokersoftware.catchnl.service.API;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    String incidentInput = "";
    Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);


        String[] incidents = {
                "Wat is er gebeurd?",
                "Hard rijden in een woonwijk",
                "Geen richting aangeven bij het wisselen van rijbaan",
                "Bumperkleven bij hoge snelheid",
                "Agressief rijgedrag",
                "Door rood licht rijden",
                "Inhalen op een onveilige plek",
                "Geen voorrang verlenen bij een gelijkwaardige kruising of zebrapad",
                "Onveilig parkeren",
                "Onnodig veel geluid maken, zoals hard gas geven of toeteren",
                "anders"
        };

        if (General.user != null){
            binding.monthLeftText.setText("Je kan deze maand nog " +  General.user.getComplaintPerMonthLeft() + " auto's opgeven");
        }

        ArrayAdapter<String> adapter =  new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.incident_spinner_item, incidents);

        binding.incidentSPINNER.setAdapter(adapter);
        binding.incidentSPINNER.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(parent.getItemAtPosition(position).toString());
                if (parent.getItemAtPosition(position).toString().equals("anders")){
                    binding.incidentMessage.setVisibility(View.VISIBLE);
                    incidentInput = "anders";
                } else {
                    binding.incidentMessage.setVisibility(View.GONE);
                    incidentInput = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                System.out.println("nothing selected");
            }
        });

        binding.actionbar.infoBtn.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), InfoActivity.class));
        });

        binding.uploadBtn.setOnClickListener(v -> {
            dialog = new Dialog(getActivity());
            checkInputs();
            //startActivity(new Intent(getActivity(), SuccessComplaintActivity.class));
        });
        return binding.getRoot();
    }

    private void checkInputs() {
        if (binding.licencePlate.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "Vul het kenteken gedeelte in", Toast.LENGTH_SHORT).show();
        } else if (binding.placeEditText.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Vul het plaats gedeelte in", Toast.LENGTH_SHORT).show();
        } else if(incidentInput.equals("") || incidentInput.equals("Wat is er gebeurd?")){
            System.out.println(incidentInput);
            Toast.makeText(getActivity(), "selecteer alstublieft een incident", Toast.LENGTH_SHORT).show();
        } else if(incidentInput.equals("anders") && binding.incidentMessage.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "Vul het anders gedeelte in", Toast.LENGTH_SHORT).show();
        } else if(binding.dateEditText.getText().toString().isEmpty() || !binding.dateEditText.getText().toString().contains("-")){
            Toast.makeText(getActivity(), "Vul het datum gedeelte in", Toast.LENGTH_SHORT).show();
        } else {
            dialog.ShowDialog("Een ogenblik gedult", "We zijn bezig met het aanmaken van uw account");
            makeRequest();
        }
    }

    private void makeRequest() {
        //TODO: incidents doesnt work true
        if (incidentInput.equals("anders")){
            incidentInput = binding.incidentMessage.getText().toString();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API service = retrofit.create(API.class);
        Call<ErrorMessage> loginCall = service.sendComplaint(
                binding.licencePlate.getText().toString().toUpperCase(),
                incidentInput,
                General.user.getId(),
                binding.placeEditText.getText().toString(),
                binding.dateEditText.getText().toString()
        );

        loginCall.enqueue(new Callback<ErrorMessage>() {
            @Override
            public void onResponse(Call<ErrorMessage> call, Response<ErrorMessage> response) {
                ErrorMessage body = response.body();
                if (body.getError() == 0){
                    System.out.println(body.getMessage());
                    binding.licencePlate.setText("");
                    binding.placeEditText.setText("");
                    dialog.DismissDialog();

                    Toast.makeText(getActivity(), "Bedankt voor je melding", Toast.LENGTH_SHORT).show();

                } else {
                    dialog.DismissDialog();
                    Toast.makeText(getActivity(), body.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ErrorMessage> call, Throwable t) {
                dialog.DismissDialog();
                System.out.println(t.getLocalizedMessage());
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}