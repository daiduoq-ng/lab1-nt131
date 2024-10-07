package com.example.duongnvdssupperclock.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.duongnvdssupperclock.model.Alarm;
import java.util.ArrayList;
import java.util.List;

public class AlarmViewModel extends ViewModel {
    private final MutableLiveData<List<Alarm>> alarmListLiveData = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<Alarm>> getAlarmListLiveData() {
        return alarmListLiveData;
    }

    public void addAlarm(Alarm newAlarm) {
        List<Alarm> currentList = alarmListLiveData.getValue();
        if (currentList != null) {
            currentList.add(newAlarm);
            alarmListLiveData.setValue(currentList);
        }
    }
}