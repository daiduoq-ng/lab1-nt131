package com.example.duongnvdssupperclock;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.example.duongnvdssupperclock.model.Alarm;
import java.util.Calendar;

public class EditAlarm extends AppCompatActivity {
    private TimePicker timePicker;
    private TextInputEditText alarmEditTxt;
    private MaterialButton btnCancel, btnOK;
    private TextView datePickerText; // TextView to select date
    private int selectedYear, selectedMonth, selectedDay; // Variables to store selected date

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_alarm);

        timePicker = findViewById(R.id.timePicker);
        alarmEditTxt = findViewById(R.id.alarmEditTxt);
        btnCancel = findViewById(R.id.btnCancel);
        btnOK = findViewById(R.id.btnOK);
        datePickerText = findViewById(R.id.datePickerText); // Initialize date picker TextView

        // Set default date to today
        final Calendar calendar = Calendar.getInstance();
        selectedYear = calendar.get(Calendar.YEAR);
        selectedMonth = calendar.get(Calendar.MONTH);
        selectedDay = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerText.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear); // Set default date text

        // Set click listener for date picker TextView
        datePickerText.setOnClickListener(v -> {
            // Create DatePickerDialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(EditAlarm.this,
                    (view, year, month, dayOfMonth) -> {
                        selectedYear = year;
                        selectedMonth = month;
                        selectedDay = dayOfMonth;
                        // Update TextView with the selected date
                        datePickerText.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }, selectedYear, selectedMonth, selectedDay);
            datePickerDialog.show();
        });

        btnCancel.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });

        btnOK.setOnClickListener(v -> {
            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();
            String label = alarmEditTxt.getText().toString();
            // Create Alarm object with the selected date
            Alarm newAlarm = new Alarm(0, hour, minute, label, true, selectedYear, selectedMonth, selectedDay);

            Intent resultIntent = new Intent();
            resultIntent.putExtra("NEW_ALARM", newAlarm);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}