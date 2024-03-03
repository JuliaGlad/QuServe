package com.example.myapplication.presentation.utils.youtTurnNotification;

import static com.example.myapplication.presentation.utils.Utils.YOUR_TURN_CHANNEL_ID;
import static com.example.myapplication.presentation.utils.Utils.YOUR_TURN_CHANNEL_NAME;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.work.Constraints;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.myapplication.DI;
import com.example.myapplication.R;
import com.example.myapplication.domain.model.queue.QueueModel;
import com.example.myapplication.presentation.queue.waitingFragment.fragment.WaitingActivity;
import com.example.myapplication.presentation.utils.backToWorkNotification.HideNotificationWorker;
import com.example.myapplication.presentation.utils.workers.PauseAvailableWorker;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class YourTurnForegroundService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        setupNotification();
        addDocumentSnapshot();
        return super.onStartCommand(intent, flags, startId);
    }

    private void addDocumentSnapshot() {
        DI.getQueueByParticipantIdUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<QueueModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull QueueModel queueModel) {
                        DI.addSnapshotQueueUseCase.invoke(queueModel.getId())
                                .subscribeOn(Schedulers.io())
                                .subscribe(new Observer<DocumentSnapshot>() {
                                    @Override
                                    public void onSubscribe(@NonNull Disposable d) {

                                    }

                                    @Override
                                    public void onNext(@NonNull DocumentSnapshot snapshot) {
                                        DI.onParticipantServedUseCase.invoke(snapshot)
                                                .subscribeOn(Schedulers.io())
                                                .subscribe(new CompletableObserver() {
                                                    @Override
                                                    public void onSubscribe(@NonNull Disposable d) {

                                                    }

                                                    @Override
                                                    public void onComplete() {
                                                        stopForeground(true);
                                                        stopSelf();
                                                    }

                                                    @Override
                                                    public void onError(@NonNull Throwable e) {

                                                    }
                                                });
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

    }

    private void setupNotification() {

        Intent intent = new Intent(this, WaitingActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, YOUR_TURN_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle("It`s your turn now")
                .setContentText("Come to the service point")
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

}
