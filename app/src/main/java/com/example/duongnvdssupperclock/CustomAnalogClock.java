package com.example.duongnvdssupperclock;



import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;

public class CustomAnalogClock extends View {
    private Paint paint;
    private RectF rectF;
    private float centerX, centerY, radius;

    public CustomAnalogClock(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true); // Làm mịn các cạnh của hình vẽ
        rectF = new RectF();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float margin = 60f; // Giữ nguyên khoảng cách để vạch ngoài dài hơn
        centerX = w / 2f;
        centerY = h / 2f;
        // Giảm kích thước vòng tròn để đồng hồ nhỏ lại
        radius = Math.min(w, h) / 2f - margin - 40; // Giảm radius thêm 40
        rectF.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Vẽ vòng tròn đồng hồ lớn hơn với màu #ffffff và thêm bóng đổ
        paint.setColor(Color.parseColor("#ffffff"));
        paint.setShadowLayer(50f, 0, 0, Color.argb(100, 0, 0, 0)); // Tăng độ mờ và lan ra ngoài nhiều hơn
        canvas.drawCircle(centerX, centerY, radius, paint);

        // Vẽ lại bóng đổ để xóa bóng cho các vạch phút
        paint.setShadowLayer(0, 0, 0, Color.TRANSPARENT);

        // Vẽ các vạch phút bên ngoài vòng tròn, với các vạch 5 phút to hơn và màu khác
        for (int i = 0; i < 60; i++) {
            float angle = (float) (i * 6 * Math.PI / 180);
            float startX, startY, stopX, stopY;

            if (i % 5 == 0) {
                // Vạch mỗi 5 phút - dài hơn và màu #32d2bd
                paint.setColor(Color.parseColor("#32d2bd"));
                paint.setStrokeWidth(6f); // Dày hơn
                startX = (float) (centerX + (radius + 20) * Math.sin(angle)); // Bắt đầu từ bên ngoài hơn
                startY = (float) (centerY - (radius + 20) * Math.cos(angle));
                stopX = (float) (centerX + (radius + 60) * Math.sin(angle)); // Dài hơn
                stopY = (float) (centerY - (radius + 60) * Math.cos(angle));
            } else {
                // Vạch mỗi phút - ngắn hơn và màu #35446b
                paint.setColor(Color.parseColor("#35446b"));
                paint.setStrokeWidth(4f); // Mỏng hơn
                startX = (float) (centerX + (radius + 20) * Math.sin(angle)); // Bắt đầu gần hơn
                startY = (float) (centerY - (radius + 20) * Math.cos(angle));
                stopX = (float) (centerX + (radius + 50) * Math.sin(angle)); // Ngắn hơn
                stopY = (float) (centerY - (radius + 50) * Math.cos(angle));
            }

            // Vẽ vạch phút
            canvas.drawLine(startX, startY, stopX, stopY, paint);
        }

        // Lấy thời gian hiện tại
        Calendar calendar = Calendar.getInstance();
        float hour = calendar.get(Calendar.HOUR_OF_DAY) % 12;
        float minute = calendar.get(Calendar.MINUTE);
        float second = calendar.get(Calendar.SECOND);

        // Vẽ kim giờ (độ dày 10f)
        drawHand(canvas, (hour + minute / 60) * 5f, radius * 0.6f, Color.BLACK, 10f);

        // Vẽ kim phút (độ dày 6f)
        drawHand(canvas, minute, radius * 0.7f, Color.BLACK, 6f);

        // Vẽ kim giây (độ dày 4f)
        drawHand(canvas, second, radius * 0.9f, Color.parseColor("#37d7b2"), 4f);

        // Invalidate để vẽ lại liên tục
        invalidate();
    }

    private void drawHand(Canvas canvas, float loc, float length, int color, float thickness) {
        float angle = (float) Math.PI * loc / 30 - (float) Math.PI / 2;
        paint.setColor(color);
        paint.setStrokeWidth(thickness); // Đặt độ dày cho kim
        canvas.drawLine(centerX, centerY,
                (float) (centerX + Math.cos(angle) * length),
                (float) (centerY + Math.sin(angle) * length),
                paint);
    }
}

