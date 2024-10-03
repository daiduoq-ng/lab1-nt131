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
    private Handler handler = new Handler(); // Handler để cập nhật thời gian
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            updateTime(); // Cập nhật thời gian
            handler.postDelayed(this, 1000); // Cập nhật mỗi giây
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
        digitalClock = view.findViewById(R.id.digitalClock); // Khởi tạo TextView từ View đã inflate
        updateTime();

        handler.post(runnable);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(runnable);
    }

    private void updateTime() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        String currentTime = sdf.format(calendar.getTime());


        digitalClock.setText(currentTime);
    }
}
