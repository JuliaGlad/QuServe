package com.example.myapplication.presentation.utils.countdownTimer;

import static com.example.myapplication.presentation.utils.Utils.BASIC;
import static com.example.myapplication.presentation.utils.Utils.COMPANY;
import static com.example.myapplication.presentation.utils.Utils.CURRENT_TIMER_TIME;
import static com.example.myapplication.presentation.utils.Utils.PROGRESS;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_ID;
import static com.example.myapplication.presentation.utils.Utils.STATE;
import static com.example.myapplication.presentation.utils.Utils.TIME_LEFT;
import static com.example.myapplication.presentation.utils.Utils.TIME_MILLIS;

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

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.work.Constraints;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.myapplication.di.DI;
import com.example.myapplication.R;
import com.example.myapplication.di.QueueDI;
import com.example.myapplication.presentation.basicQueue.queueDetails.QueueDetailsActivity;
import com.example.myapplication.presentation.companyQueue.queueDetails.workerDetails.WorkerQueueDetailsActivity;
import com.example.myapplication.presentation.utils.backToWorkNotification.HideNotificationWorker;
import com.example.myapplication.presentation.utils.backToWorkNotification.NotificationGoBackToWork;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TimerService extends Service {

    private String timeLeft, queueId, type;
    private long timeMillis;
    private CountDownTimer timer;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        timeLeft = intent.getStringExtra(TIME_LEFT);
        timeMillis = intent.getLongExtra(TIME_MILLIS, 0);
        queueId = intent.getStringExtra(QUEUE_ID);
        type = intent.getStringExtra(STATE);
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
        Intent intent = null;
        switch (type){
            case BASIC:
                intent = new Intent(this, QueueDetailsActivity.class);
                break;
            case COMPANY:
                intent = new Intent(this, WorkerQueueDetailsActivity.class);
                break;
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

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
                PROGRESS = 0;
                stopForeground(true);
                stopSelf();

                QueueDI.continueQueueUseCase.invoke(queueId)
                        .subscribeOn(Schedulers.io())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onComplete() {
                                Intent intent = new Intent(TimerService.this, NotificationGoBackToWork.class);
                                intent.putExtra(STATE, type);
                                startService(intent);

                                initNotificationWorker();
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }
                        });
            }

        }.start();
    }

    private void updateTimer() {
        long hours = (timeMillis / 1000) / 3600;
        long minutes = ((timeMillis / 1000) % 3600) / 60;
        long seconds = (timeMillis / 1000) % 60;

        timeLeft = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
        CURRENT_TIMER_TIME = timeMillis;
    }

    private void updateNotification() {
        Notification notification = setupNotification();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(5, notification);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        stopForeground(true);
        stopSelf();
    }
}
