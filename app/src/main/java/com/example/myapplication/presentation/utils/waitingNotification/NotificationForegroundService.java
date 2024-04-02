package com.example.myapplication.presentation.utils.waitingNotification;

import static com.example.myapplication.presentation.utils.Utils.INTENT_TIME_PAUSED;
import static com.example.myapplication.presentation.utils.Utils.NOTIFICATION_CHANNEL_ID;
import static com.example.myapplication.presentation.utils.Utils.NOTIFICATION_CHANNEL_NAME;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_ID;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.myapplication.DI;
import com.example.myapplication.R;
import com.example.myapplication.domain.model.queue.QueueModel;
import com.example.myapplication.presentation.queue.waitingFragment.fragment.WaitingActivity;
import com.example.myapplication.presentation.utils.queuePausedNotification.NotificationQueuePaused;
import com.example.myapplication.presentation.utils.youtTurnNotification.YourTurnForegroundService;
import com.google.firebase.firestore.DocumentSnapshot;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NotificationForegroundService extends Service {

    private String name = null;
    private String time = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        getQueueByParticipantId();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void addSnapshot(String queueId) {
        DI.addSnapshotQueueUseCase.invoke(queueId)
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<DocumentSnapshot>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull DocumentSnapshot snapshot) {
                        if (!DI.onPausedUseCase.invoke(snapshot)) {
                            Intent intent = new Intent(NotificationForegroundService.this, NotificationQueuePaused.class);
                            String timePaused = DI.getTimeProgressPausedUseCase.invoke(snapshot);
                            intent.putExtra(QUEUE_ID, queueId);
                            intent.putExtra(INTENT_TIME_PAUSED, timePaused);
                            startService(intent);
                        } else if (DI.onContainParticipantUseCase.invoke(snapshot)) {
                            stopForeground(true);
                            stopSelf();
                            Intent intent = new Intent(NotificationForegroundService.this, YourTurnForegroundService.class);
                            intent.putExtra(QUEUE_ID, queueId);
                            startService(intent);
                        } else if (DI.onParticipantLeftUseCase.invoke(snapshot)){
                            stopForeground(true);
                            stopSelf();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void getQueueByParticipantId() {
        DI.getQueueByParticipantIdUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<QueueModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull QueueModel queueModel) {
                        name = queueModel.getName();
                        time = queueModel.getMidTime();
                        setupNotification();
                        addSnapshot(queueModel.getId());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    private void setupNotification() {

        Intent intent = new Intent(this, WaitingActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentText("Estimated time waiting:" + " " + time)
                .setContentTitle(name)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle())
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        createNotificationChannel(builder);
    }

    private void createNotificationChannel(NotificationCompat.Builder builder) {
        int importance = NotificationManager.IMPORTANCE_HIGH;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, importance);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            Notification notification = builder.build();
            startForeground(1, notification);
        }
    }

}
