package com.example.myapplication.presentation.utils.backToWorkNotification;

import static com.example.myapplication.presentation.utils.Utils.YOUR_TURN_CHANNEL_ID;
import static com.example.myapplication.presentation.utils.Utils.YOUR_TURN_CHANNEL_NAME;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.myapplication.R;

public class NotificationGoBackToWork extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        setupNotification();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void setupNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), YOUR_TURN_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle("Time to go back to work!")
                .setContentText("Your rest has come to end! It`s time to work!")
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle())
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        createNotificationChannel(builder);
    }

    private void createNotificationChannel(NotificationCompat.Builder builder) {
        int importance = NotificationManager.IMPORTANCE_HIGH;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(YOUR_TURN_CHANNEL_ID, YOUR_TURN_CHANNEL_NAME, importance);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            startForeground(1, builder.build());
        }
    }

}
