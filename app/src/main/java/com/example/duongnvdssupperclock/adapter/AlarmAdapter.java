package com.example.duongnvdssupperclock.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duongnvdssupperclock.R;
import com.example.duongnvdssupperclock.model.Alarm;

import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolder> {
    private List<Alarm> alarmList;

    public AlarmAdapter(List<Alarm> alarmList) {
        this.alarmList = alarmList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_alarm, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Alarm alarm = alarmList.get(position);
        holder.alarmHour.setText(String.format("%02d", alarm.getHour()));
        holder.alarmMinute.setText(String.format("%02d", alarm.getMinute()));
        holder.alarmLabel.setText(alarm.getLabel());
        holder.alarmSwitch.setChecked(alarm.isEnabled());

        holder.alarmSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            alarm.setEnabled(isChecked);
            // Cập nhật alarm trong cơ sở dữ liệu nếu cần
        });
    }

    @Override
    public int getItemCount() {
        return alarmList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView alarmHour, alarmMinute, alarmLabel;
        Switch alarmSwitch;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            alarmHour = itemView.findViewById(R.id.alarmHour);
            alarmMinute = itemView.findViewById(R.id.alarmMinute);
            alarmLabel = itemView.findViewById(R.id.alarmLabel);
            alarmSwitch = itemView.findViewById(R.id.alarmSwitch);
        }
    }
}
