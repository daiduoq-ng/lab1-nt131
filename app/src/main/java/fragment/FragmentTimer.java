package fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.duongnvdssupperclock.R;

import java.util.Locale;

public class FragmentTimer extends Fragment {
    private TextView timerTextView;
    private Button startButton, cancelButton;
    private NumberPicker hourPicker, minutePicker, secondPicker;
    private CountDownTimer countDownTimer;
    private boolean isTimerRunning = false;
    private long timeLeftInMillis = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        timerTextView = view.findViewById(R.id.timerTextView);
        startButton = view.findViewById(R.id.startButton);
        cancelButton = view.findViewById(R.id.cancelButton);
        hourPicker = view.findViewById(R.id.hourPicker);
        minutePicker = view.findViewById(R.id.minutePicker);
        secondPicker = view.findViewById(R.id.secondPicker);

        setupPickers();
        setupButtons();

        return view;
    }

    private void setupPickers() {
        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(23);
        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(59);
        secondPicker.setMinValue(0);
        secondPicker.setMaxValue(59);
    }

    private void setupButtons() {
        startButton.setOnClickListener(v -> {
            if (isTimerRunning) {
                pauseTimer();
            } else {
                startTimer();
            }
        });

        cancelButton.setOnClickListener(v -> cancelTimer());
    }

    private void startTimer() {
        if (!isTimerRunning) {
            long totalTimeInMillis = (hourPicker.getValue() * 3600000L) +
                    (minutePicker.getValue() * 60000L) +
                    (secondPicker.getValue() * 1000L);
            if (totalTimeInMillis > 0) {
                timeLeftInMillis = totalTimeInMillis;
                countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        timeLeftInMillis = millisUntilFinished;
                        updateTimerText();
                    }

                    @Override
                    public void onFinish() {
                        isTimerRunning = false;
                        updateButtons();
                        // Here you can add sound or vibration for timer completion
                    }
                }.start();

                isTimerRunning = true;
                updateButtons();
            }
        }
    }

    private void pauseTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        isTimerRunning = false;
        updateButtons();
    }

    private void cancelTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        timeLeftInMillis = 0;
        isTimerRunning = false;
        updateTimerText();
        updateButtons();
    }

    private void updateTimerText() {
        int hours = (int) (timeLeftInMillis / 3600000);
        int minutes = (int) ((timeLeftInMillis % 3600000) / 60000);
        int seconds = (int) ((timeLeftInMillis % 60000) / 1000);

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
        timerTextView.setText(timeLeftFormatted);
    }

    private void updateButtons() {
        if (isTimerRunning) {
            startButton.setText("PAUSE");
            cancelButton.setEnabled(true);
        } else {
            startButton.setText("START");
            if (timeLeftInMillis > 0) {
                cancelButton.setEnabled(true);
            } else {
                cancelButton.setEnabled(false);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}