package com.example.myapplication.presentation.utils.waitingNotification;

import static com.example.myapplication.presentation.utils.constants.Utils.NOTIFICATION_CHANNEL_ID;
import static com.example.myapplication.presentation.utils.constants.Utils.NOTIFICATION_CHANNEL_NAME;
import static com.example.myapplication.presentation.utils.constants.Utils.NOT_PARTICIPATE_IN_QUEUE;
import static com.example.myapplication.presentation.utils.constants.Utils.PARTICIPANT_QUEUE_PATH;

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

import com.example.myapplication.R;
import com.example.myapplication.di.QueueDI;
import com.example.myapplication.di.profile.ProfileDI;
import com.example.myapplication.domain.model.queue.QueueModel;
import com.example.myapplication.presentation.common.waitingInQueue.WaitingActivity;
import com.example.myapplication.presentation.utils.queuePausedNotification.NotificationQueuePaused;
import com.google.firebase.firestore.DocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NotificationForegroundService extends Service {
    private String name = null;
    private String time = null;
    private String queueId = null;

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

    private void addSnapshot(String path) {
        QueueDI.addSnapshotQueueUseCase.invoke(path)
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<DocumentSnapshot>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull DocumentSnapshot snapshot) {
                        if (!QueueDI.onPausedUseCase.invoke(snapshot)) {
                            Intent intent = new Intent(NotificationForegroundService.this, NotificationQueuePaused.class);
                            intent.putExtra(PARTICIPANT_QUEUE_PATH, path);
                            startService(intent);
                        } else if (QueueDI.onContainParticipantUseCase.invoke(snapshot)) {
                            updateParticipate();
                        } else if (QueueDI.onParticipantLeftUseCase.invoke(snapshot)) {
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

    private void updateParticipate() {
        String month = new SimpleDateFormat("MMM", Locale.getDefault()).format(new Date());
        String date = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
        String dateFull = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());
        String time = date.concat(" ").concat(month.concat(" ").concat(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date())));

        ProfileDI.updateParticipateInQueueUseCase.invoke(NOT_PARTICIPATE_IN_QUEUE)
                .concatWith(QueueDI.addQueueToHistoryUseCase.invoke(queueId, name, time, dateFull))
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                        stopForeground(true);
                        stopSelf();

                        Intent intent = new Intent(NotificationForegroundService.this, WaitingActivity.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(NotificationForegroundService.this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationForegroundService.this, NOTIFICATION_CHANNEL_ID)
                                .setSmallIcon(R.drawable.ic_notifications)
                                .setContentText(getString(R.string.you_can_now_go_to_service_point))
                                .setContentTitle(getString(R.string.it_s_your_turn_now))
                                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle())
                                .setContentIntent(pendingIntent)
                                .setPriority(NotificationCompat.PRIORITY_HIGH);

                        createBasicNotificationChannel(builder);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    private void createBasicNotificationChannel(NotificationCompat.Builder builder) {
        int importance = NotificationManager.IMPORTANCE_HIGH;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, importance);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            Notification notification = builder.build();
            notificationManager.notify(2, notification);
        }
    }

    private void getQueueByParticipantId() {
        ProfileDI.getParticipantQueuePathUseCase.invoke()
                .flatMap(path -> {
                    addSnapshot(path);
                    return QueueDI.getQueueByParticipantPathUseCase.invoke(path);
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<QueueModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull QueueModel queueModel) {
                        int before = 0;
                        List<String> participants = queueModel.getParticipants();
                        for (int i = 0; i < participants.size(); i++) {
                            if (checkParticipantsIndex(participants, i)) {
                                before = i + 1;
                                break;
                            }
                        }

                        name = queueModel.getName();
                        queueId = queueModel.getId();
                        time = String.valueOf(Integer.parseInt(queueModel.getMidTime()) * before);
                        setupNotification();

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public boolean checkParticipantsIndex(List<String> participants, int index) {
        return QueueDI.checkParticipantIndexUseCase.invoke(participants, index);
    }

    private void setupNotification() {

        Intent intent = new Intent(this, WaitingActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        String contentText;
        if (Integer.parseInt(time) != 0) {
            contentText = getString(R.string.estimated_waiting_time) + " " + time;
        } else {
            contentText = getResources().getString(R.string.no_estimated_time_yet);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentText(contentText)
                .setContentTitle(name)
//                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle())
                .setStyle(new NotificationCompat.BigTextStyle())
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
