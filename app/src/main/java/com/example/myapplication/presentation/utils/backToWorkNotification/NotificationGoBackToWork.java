package com.example.myapplication.presentation.utils.backToWorkNotification;

import static com.example.myapplication.presentation.utils.constants.Utils.BASIC;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY;
import static com.example.myapplication.presentation.utils.constants.Utils.STATE;
import static com.example.myapplication.presentation.utils.constants.Utils.YOUR_TURN_CHANNEL_ID;
import static com.example.myapplication.presentation.utils.constants.Utils.YOUR_TURN_CHANNEL_NAME;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.myapplication.R;
import com.example.myapplication.presentation.basicQueue.queueDetails.QueueDetailsActivity;
import com.example.myapplication.presentation.companyQueue.queueDetails.workerDetails.WorkerQueueDetailsActivity;

public class NotificationGoBackToWork extends Service {

    String type = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        type = intent.getStringExtra(STATE);
        setupNotification();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void setupNotification() {

        Intent intent = null;
        switch (type) {
            case BASIC:
                intent = new Intent(this, QueueDetailsActivity.class);
                break;
            case COMPANY:
                intent = new Intent(this, WorkerQueueDetailsActivity.class);
                break;
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, YOUR_TURN_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle(getResources().getString(R.string.time_to_go_back_to_work))
                .setContentText(getResources().getString(R.string.rest_come_to_end))
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle())
                .setContentIntent(pendingIntent)
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
