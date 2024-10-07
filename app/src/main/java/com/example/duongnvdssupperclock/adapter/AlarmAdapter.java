package com.example.duongnvdssupperclock.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

    public void setAlarmList(List<Alarm> alarmList) {
        this.alarmList = alarmList;
        notifyDataSetChanged();  // Notify data change
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

        // Get hour and minute
        int hour = alarm.getHour();
        int minute = alarm.getMinute();

        // Convert hour from 24-hour format to 12-hour format
        String amPm;
        if (hour >= 12) {
            amPm = "PM";
            if (hour > 12) {
                hour -= 12; // Convert hour greater than 12 to 12-hour format
            }
        } else {
            amPm = "AM";
            if (hour == 0) {
                hour = 12; // Set 0 hour to 12 AM
            }
        }

        holder.alarmHour.setText(String.format("%02d", hour)); // Display hour
        holder.alarmMinute.setText(String.format("%02d", minute)); // Display minute
        holder.alarmLabel.setText(alarm.getLabel());
        holder.alarmSwitch.setChecked(alarm.isEnabled());

        // Display formatted date
        holder.alarmDate.setText(alarm.getFormattedDate());

        // Change icon based on alarm time
        if (alarm.getHour() >= 6 && alarm.getHour() < 18) {
            holder.alarmIcon.setImageResource(R.drawable.ic_sun);
            holder.alarmIcon.setColorFilter(holder.itemView.getContext().getResources().getColor(R.color.sun_color));
        } else {
            holder.alarmIcon.setImageResource(R.drawable.ic_moon);
            holder.alarmIcon.setColorFilter(holder.itemView.getContext().getResources().getColor(R.color.moon_color));
        }

        // Display AM/PM
        holder.alarmAmPm.setText(amPm);

        holder.alarmSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            alarm.setEnabled(isChecked);
            // Update alarm state if needed
        });
    }

    @Override
    public int getItemCount() {
        return alarmList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView alarmHour, alarmMinute, alarmLabel, alarmAmPm, alarmDate; // Add alarmDate
        Switch alarmSwitch;
        ImageView alarmIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            alarmHour = itemView.findViewById(R.id.alarmHour);
            alarmMinute = itemView.findViewById(R.id.alarmMinute);
            alarmLabel = itemView.findViewById(R.id.alarmLabel);
            alarmSwitch = itemView.findViewById(R.id.alarmSwitch);
            alarmIcon = itemView.findViewById(R.id.alarmIcon);
            alarmAmPm = itemView.findViewById(R.id.alarmAmPm);
            alarmDate = itemView.findViewById(R.id.alarmDate); // Initialize alarmDate
        }
    }
}