package com.tokersoftware.catchnl.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tokersoftware.catchnl.R;
import com.tokersoftware.catchnl.activity.InfoActivity;
import com.tokersoftware.catchnl.adapter.HistoryAdapter;
import com.tokersoftware.catchnl.databinding.FragmentHistoryBinding;
import com.tokersoftware.catchnl.general.General;
import com.tokersoftware.catchnl.model.response.Complaint;
import com.tokersoftware.catchnl.service.API;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HistoryFragment extends Fragment {

    FragmentHistoryBinding binding;
    HistoryAdapter adapter;
    ArrayList<Complaint> historyModelArrayList;
    ArrayList<Complaint> myComplantsArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        init();


        return binding.getRoot();
    }

    private void init() {
        historyModelArrayList = new ArrayList<>();
        myComplantsArrayList = new ArrayList<>();

        makeRequest();

        binding.actionbar.infoBtn.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), InfoActivity.class));
        });
        binding.sendLayout.setOnClickListener(v -> {
            binding.sendLayout.setBackgroundResource(R.drawable.history_left_active_button_background);
            binding.sendTxt.setTextColor(Color.WHITE);

            binding.receivedLayout.setBackgroundResource(R.drawable.history_right_hide_button_background);
            binding.receivedTxt.setTextColor(getResources().getColor(R.color.primary_red, getResources().newTheme()));

            //Setting adapter
            adapter = new HistoryAdapter(historyModelArrayList);
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            binding.recyclerView.setAdapter(adapter);
        });

        binding.receivedLayout.setOnClickListener(v -> {
            binding.sendLayout.setBackgroundResource(R.drawable.history_left_hide_button_background);
            binding.sendTxt.setTextColor(getResources().getColor(R.color.primary_red, getResources().newTheme()));

            binding.receivedLayout.setBackgroundResource(R.drawable.history_right_active_button_background);
            binding.receivedTxt.setTextColor(Color.WHITE);

            //Setting adapter
            adapter = new HistoryAdapter(myComplantsArrayList);
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            binding.recyclerView.setAdapter(adapter);
        });
    }

    private void makeRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API service = retrofit.create(API.class);
        Call<List<Complaint>> complaintCall = service.getComplaints();
        complaintCall.enqueue(new Callback<List<Complaint>>() {
            @Override
            public void onResponse(Call<List<Complaint>> call, Response<List<Complaint>> response) {
                try{
                    List<Complaint> body = response.body();
                    body.forEach(i -> {
                        if (General.user.getId().equals(i.getUserID())){
                            historyModelArrayList.add(new Complaint(
                                    i.getId(),
                                    i.getLicencePlate(),
                                    i.getDate(),
                                    i.getIncident(),
                                    i.getUserID(),
                                    i.getPlace()
                            ));
                        }
                        System.out.println(General.user.getLicencePlate());
                        System.out.println(i.getLicencePlate());
                        if (General.user.getLicencePlate().toLowerCase().equals(i.getLicencePlate().toLowerCase())) {
                            myComplantsArrayList.add(new Complaint(
                                    i.getId(),
                                    i.getLicencePlate(),
                                    i.getDate(),
                                    i.getIncident(),
                                    i.getUserID(),
                                    i.getPlace()
                            ));
                        }
                    });

                    //Setting adapter
                    adapter = new HistoryAdapter(historyModelArrayList);
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    binding.recyclerView.setAdapter(adapter);

                } catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<List<Complaint>> call, Throwable t) {

            }
        });
    }
}