package com.example.duongnvdssupperclock;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.example.duongnvdssupperclock.model.Alarm;

public class EditAlarm extends AppCompatActivity {

    private TimePicker timePicker;
    private TextInputEditText alarmEditTxt;
    private MaterialButton btnCancel, btnOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_alarm);

        timePicker = findViewById(R.id.timePicker);
        alarmEditTxt = findViewById(R.id.alarmEditTxt);
        btnCancel = findViewById(R.id.btnCancel);
        btnOK = findViewById(R.id.btnOK);

        btnCancel.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });

        btnOK.setOnClickListener(v -> {
            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();
            String label = alarmEditTxt.getText().toString();

            Alarm newAlarm = new Alarm(0, hour, minute, label, true);

            Intent resultIntent = new Intent();
            resultIntent.putExtra("NEW_ALARM", newAlarm);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }
}