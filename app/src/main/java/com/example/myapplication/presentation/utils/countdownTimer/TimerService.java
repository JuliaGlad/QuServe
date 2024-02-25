package com.example.myapplication.presentation.utils.countdownTimer;

import static com.example.myapplication.presentation.utils.Utils.CURRENT_TIMER_TIME;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.work.Constraints;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.myapplication.DI;
import com.example.myapplication.R;
import com.example.myapplication.presentation.queue.queueDetails.QueueDetailsActivity;
import com.example.myapplication.presentation.utils.backToWorkNotification.HideNotificationWorker;
import com.example.myapplication.presentation.utils.waitingNotification.NotificationForegroundService;
import com.example.myapplication.presentation.utils.workers.PauseAvailableWorker;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TimerService extends Service {

    private String timeLeft, queueId;
    private long timeMillis;
    private CountDownTimer timer;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        timeLeft = intent.getStringExtra("TIME_LEFT");
        timeMillis = intent.getLongExtra("TIME_MILLIS", 0);
        queueId = intent.getStringExtra("QUEUE_ID");
        setupNotification();
        startCountDown();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Notification setupNotification() {
        Intent intent = new Intent(this, QueueDetailsActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "TIMER_NOTIFICATION")
                .setSmallIcon(R.drawable.baseline_hourglass_bottom_24)
                .setContentTitle("Timer")
                .setContentText(timeLeft)
                .setOnlyAlertOnce(true)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle())
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        createNotificationChannel(builder);
        return builder.build();
    }

    private void createNotificationChannel(NotificationCompat.Builder builder) {
        int importance = NotificationManager.IMPORTANCE_HIGH;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("TIMER_NOTIFICATION", "TIMER_NOTIFICATION_NAME", importance);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            startForeground(5, builder.build());
        }
    }

    private void startCountDown() {
        timer = new CountDownTimer(timeMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeMillis = millisUntilFinished;
                updateTimer();
                updateNotification();
            }

            @Override
            public void onFinish() {
                stopForeground(true);
                stopSelf();

                DI.continueQueueUseCase.invoke(queueId)
                        .subscribeOn(Schedulers.io())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onComplete() {
                                initNotificationWorker();
                                initPauseAvailableWorker();
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }
                        });
            }

        }.start();
    }

    private void updateTimer() {
        int minutes = (int) (timeMillis / 1000) / 60;
        int seconds = (int) (timeMillis / 1000) % 60;
        timeLeft = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        CURRENT_TIMER_TIME = timeMillis;
    }

    private void updateNotification() {
        Notification notification = setupNotification();
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(5, notification);
    }

    private void initNotificationWorker() {
        Constraints constraints = new Constraints.Builder()
                .setRequiresDeviceIdle(false)
                .build();

        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(HideNotificationWorker.class)
                .setConstraints(constraints)
                .setInitialDelay(5, TimeUnit.MINUTES)
                .addTag("StopPause")
                .build();

        WorkManager.getInstance(getApplicationContext()).enqueue(request);
    }

    private void initPauseAvailableWorker() {

        Constraints constraints = new Constraints.Builder()
                .setRequiresDeviceIdle(false)
                .build();

        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(PauseAvailableWorker.class)
                .setConstraints(constraints)
                .setInitialDelay(2, TimeUnit.HOURS)
                .addTag("PauseAvailable")
                .build();

        WorkManager.getInstance(getApplicationContext()).enqueue(request);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        stopForeground(true);
        stopSelf();
    }
}
