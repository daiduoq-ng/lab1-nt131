package com.example.duongnvdssupperclock;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import android.net.Uri;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "ALARM_NOTIFICATION_CHANNEL";
    private static final int NOTIFICATION_ID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (isAppInForeground(context)) {

            openAlarmRingingActivity(context);
        } else {

            createNotificationChannel(context);
            showNotification(context);
        }
    }


    private boolean isAppInForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager == null) return false;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (ActivityManager.AppTask task : activityManager.getAppTasks()) {
                if (task.getTaskInfo().topActivity != null &&
                        task.getTaskInfo().topActivity.getPackageName().equals(context.getPackageName())) {
                    return true; // Ứng dụng đang ở foreground
                }
            }
        }
        return false; // Ứng dụng không ở foreground
    }


    private void openAlarmRingingActivity(Context context) {
        Intent intent = new Intent(context, AlarmRingingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Alarm Notification";
            String description = "Channel for Alarm notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    // Hiển thị thông báo
    private void showNotification(Context context) {

        Uri alarmSound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.playing_god);

        // Tạo intent để mở lại ứng dụng khi nhấn vào thông báo
        Intent intent = new Intent(context, AlarmRingingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.alarm_icon)
                .setContentTitle("Alarm!")
                .setContentText("Your alarm is ringing.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setTimeoutAfter(60000)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setSound(alarmSound);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
