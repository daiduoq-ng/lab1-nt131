package fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import android.os.Handler;

import com.example.duongnvdssupperclock.R;

public class FragmentClock extends Fragment {
    private TextView digitalClock;
    private TextView dateView; // Thêm TextView cho ngày
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            updateDateTime(); // Đổi tên thành updateDateTime
            handler.postDelayed(this, 1000);
        }
    };

    public FragmentClock() {
    }

    public static FragmentClock newInstance() {
        return new FragmentClock();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clock, container, false);
        digitalClock = view.findViewById(R.id.digitalClock);
        dateView = view.findViewById(R.id.dateView); // Khởi tạo TextView cho ngày
        updateDateTime(); // Đổi tên thành updateDateTime

        handler.post(runnable);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(runnable);
    }

    private void updateDateTime() {
        Calendar calendar = Calendar.getInstance();

        // Format cho thời gian
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        String currentTime = timeFormat.format(calendar.getTime());

        // Format cho ngày
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());

        digitalClock.setText(currentTime);
        dateView.setText(currentDate);
    }
}