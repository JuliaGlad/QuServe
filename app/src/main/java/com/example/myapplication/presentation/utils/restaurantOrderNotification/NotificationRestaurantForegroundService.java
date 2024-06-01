package com.example.myapplication.presentation.utils.restaurantOrderNotification;

import static com.example.myapplication.presentation.utils.constants.Restaurant.PATH;
import static com.example.myapplication.presentation.utils.constants.Utils.NOTIFICATION_CHANNEL_ID;
import static com.example.myapplication.presentation.utils.constants.Utils.NOTIFICATION_CHANNEL_NAME;
import static com.example.myapplication.presentation.utils.constants.Utils.NOT_PARTICIPATE_IN_QUEUE;
import static com.example.myapplication.presentation.utils.constants.Utils.NOT_RESTAURANT_VISITOR;
import static com.example.myapplication.presentation.utils.constants.Utils.PARTICIPANT_QUEUE_PATH;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.myapplication.R;
import com.example.myapplication.di.QueueDI;
import com.example.myapplication.di.profile.ProfileDI;
import com.example.myapplication.di.restaurant.RestaurantOrderDI;
import com.example.myapplication.domain.model.queue.QueueModel;
import com.example.myapplication.presentation.common.waitingInQueue.WaitingActivity;
import com.example.myapplication.presentation.utils.queuePausedNotification.NotificationQueuePaused;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NotificationRestaurantForegroundService extends Service {

    String tablePath;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        setupNotification();
        tablePath = intent.getStringExtra(PATH);
        addSnapshot(tablePath);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void addSnapshot(String path) {
        RestaurantOrderDI.addTableSnapshotUseCase.invoke(path)
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<DocumentSnapshot>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull DocumentSnapshot documentSnapshot) {
                        if (RestaurantOrderDI.onOrderFinishedUseCase.invoke(documentSnapshot)) {
                            updateVisitor();
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

    private void updateVisitor() {
        ProfileDI.updateRestaurantVisitorUseCase.invoke(NOT_RESTAURANT_VISITOR)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                        stopForeground(true);
                        stopSelf();

                        Intent intent = new Intent(NotificationRestaurantForegroundService.this, WaitingActivity.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(NotificationRestaurantForegroundService.this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationRestaurantForegroundService.this, NOTIFICATION_CHANNEL_ID)
                                .setSmallIcon(R.drawable.ic_notifications)
                                .setContentTitle(getString(R.string.order_is_finished))
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

    private void setupNotification() {

        Intent intent = new Intent(this, WaitingActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle(getString(R.string.order_is_cooking))
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
