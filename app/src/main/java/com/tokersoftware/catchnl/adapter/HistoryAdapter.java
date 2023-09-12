package com.tokersoftware.catchnl.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tokersoftware.catchnl.R;
import com.tokersoftware.catchnl.model.response.Complaint;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    ArrayList<Complaint> historyModelArrayList;

    public HistoryAdapter(ArrayList<Complaint> historyModelArrayList){
        this.historyModelArrayList = historyModelArrayList;
    }


    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_recyclerview_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.licencePlate.setText(historyModelArrayList.get(position).getIncident());
        holder.dateText.setText(historyModelArrayList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return historyModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView licencePlate, dateText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            licencePlate = itemView.findViewById(R.id.licencePlateText);
            dateText = itemView.findViewById(R.id.dateText);
        }
    }
}
