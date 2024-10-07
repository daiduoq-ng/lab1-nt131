package com.example.duongnvdssupperclock.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.duongnvdssupperclock.R;
import com.example.duongnvdssupperclock.EditAlarm;
import com.example.duongnvdssupperclock.adapter.AlarmAdapter;
import com.example.duongnvdssupperclock.model.Alarm;
import com.example.duongnvdssupperclock.viewmodel.AlarmViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FragmentAlarm extends Fragment {
    private static final String TAG = "FragmentAlarm";
    private AlarmViewModel alarmViewModel;
    private AlarmAdapter alarmAdapter;

    private final ActivityResultLauncher<Intent> editAlarmLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == getActivity().RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        Alarm newAlarm = data.getParcelableExtra("NEW_ALARM");
                        if (newAlarm != null) {
                            Log.d(TAG, "New alarm received: " + newAlarm.getHour() + ":" + newAlarm.getMinute());
                            alarmViewModel.addAlarm(requireContext(), newAlarm);  // Thêm báo thức vào ViewModel và lên lịch
                        } else {
                            Log.e(TAG, "Received null alarm from EditAlarm");
                        }
                    } else {
                        Log.e(TAG, "Received null intent data from EditAlarm");
                    }
                } else {
                    Log.d(TAG, "EditAlarm cancelled or failed");
                }
            }
    );

    public FragmentAlarm() {
        // Required empty public constructor
    }

    public static FragmentAlarm newInstance() {
        return new FragmentAlarm();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alarmViewModel = new ViewModelProvider(this).get(AlarmViewModel.class);  // Tạo ViewModel
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        alarmAdapter = new AlarmAdapter(new ArrayList<>());
        recyclerView.setAdapter(alarmAdapter);

        // Quan sát thay đổi dữ liệu từ ViewModel và cập nhật RecyclerView
        alarmViewModel.getAlarmListLiveData().observe(getViewLifecycleOwner(), alarms -> {
            alarmAdapter.setAlarmList(alarms);  // Cập nhật danh sách báo thức trong adapter
        });

        FloatingActionButton floatingBtn = view.findViewById(R.id.floatingBtn);
        floatingBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), EditAlarm.class);
            editAlarmLauncher.launch(intent);
        });

        return view;
    }
}