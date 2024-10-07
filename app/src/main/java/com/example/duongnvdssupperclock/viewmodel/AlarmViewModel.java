package com.example.duongnvdssupperclock.viewmodel;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.duongnvdssupperclock.model.Alarm;
import com.example.duongnvdssupperclock.AlarmReceiver;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AlarmViewModel extends ViewModel {
    private final MutableLiveData<List<Alarm>> alarmListLiveData = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<Alarm>> getAlarmListLiveData() {
        return alarmListLiveData;
    }

    public void addAlarm(Context context, Alarm newAlarm) {
        List<Alarm> currentList = alarmListLiveData.getValue();
        if (currentList != null) {
            currentList.add(newAlarm);
            alarmListLiveData.setValue(currentList);
            scheduleAlarm(context, newAlarm);
        }
    }

    private void scheduleAlarm(Context context, Alarm alarm) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarm.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(alarm.getYear(), alarm.getMonth(), alarm.getDay(), alarm.getHour(), alarm.getMinute(), 0);

        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }
}