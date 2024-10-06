package com.example.duongnvdssupperclock.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import com.example.duongnvdssupperclock.R;
import com.example.duongnvdssupperclock.EditAlarm;
import com.example.duongnvdssupperclock.adapter.AlarmAdapter;
import com.example.duongnvdssupperclock.model.Alarm;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FragmentAlarm extends Fragment {

    private static final String TAG = "FragmentAlarm";
    private RecyclerView recyclerView;
    private AlarmAdapter alarmAdapter;
    private List<Alarm> alarmList;

    private final ActivityResultLauncher<Intent> editAlarmLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == getActivity().RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        Alarm newAlarm = data.getParcelableExtra("NEW_ALARM");
                        if (newAlarm != null) {
                            Log.d(TAG, "New alarm received: " + newAlarm.getHour() + ":" + newAlarm.getMinute());
                            alarmList.add(newAlarm);
                            alarmAdapter.notifyDataSetChanged();
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
        alarmList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        alarmAdapter = new AlarmAdapter(alarmList);
        recyclerView.setAdapter(alarmAdapter);

        FloatingActionButton floatingBtn = view.findViewById(R.id.floatingBtn);
        floatingBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), EditAlarm.class);
            editAlarmLauncher.launch(intent);
        });

        return view;
    }
}