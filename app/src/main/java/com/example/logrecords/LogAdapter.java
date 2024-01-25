package com.example.logrecords;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.LogViewHolder> {

    private List<LogData> logDataList;

    public LogAdapter(List<LogData> logDataList) {
        this.logDataList = logDataList;
    }

    @NonNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_log, parent, false);
        return new LogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
        LogData logData = logDataList.get(position);

        // Bind data to the views in the ViewHolder
        holder.contactTextView.setText(logData.getContact());
        holder.detailTextView.setText(logData.getDetail());
        holder.dateTextView.setText(logData.getDate());
    }

    @Override
    public int getItemCount() {
        return logDataList.size();
    }

    static class LogViewHolder extends RecyclerView.ViewHolder {

        TextView contactTextView;
        TextView detailTextView;
        TextView dateTextView;

        LogViewHolder(@NonNull View itemView) {
            super(itemView);
            contactTextView = itemView.findViewById(R.id.contactTextView);
            detailTextView = itemView.findViewById(R.id.detailTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
        }
    }
}

