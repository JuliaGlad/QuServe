package com.example.myapplication.presentation.queue.createQueue.finishedQueueCreation;

import static com.example.myapplication.presentation.utils.Utils.QUEUE_ID;
import static com.example.myapplication.presentation.utils.Utils.stringsArray;

import android.net.Uri;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.ImageModel;
import com.example.myapplication.domain.model.QueueTimeModel;
import com.example.myapplication.presentation.utils.workers.PauseAvailableWorker;
import com.example.myapplication.presentation.utils.workers.QueueTimeWorker;
import com.example.myapplication.presentation.utils.workers.MidTimeWorker;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FinishedQueueCreationViewModel extends ViewModel {

    private final MutableLiveData<Task<Uri>> _image = new MutableLiveData<>();
    public LiveData<Task<Uri>> image = _image;

    public void addTimeCounterWorker(View view){
        Constraints constraints = new Constraints.Builder()
                .setRequiresDeviceIdle(false)
                .build();

        PeriodicWorkRequest request = new PeriodicWorkRequest.Builder(MidTimeWorker.class, 15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance(view.getContext()).enqueue(request);
    }

    public void delayQueueFinish(View view){
        DI.getQueueTimeModelUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<QueueTimeModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull QueueTimeModel queueTimeModel) {

                        String time = queueTimeModel.getTime();
                        if (!time.equals(stringsArray[0])) {
                            List<String> list = Arrays.asList(time.split(" "));
                            int number = Integer.parseInt(list.get(0));
                            TimeUnit timeUnit = TimeUnit.valueOf(list.get(1));

                            Data data = new Data.Builder()
                                    .putString(QUEUE_ID, queueTimeModel.getId())
                                    .build();

                            Constraints constraints = new Constraints.Builder()
                                    .setRequiresDeviceIdle(false)
                                    .build();

                            OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(QueueTimeWorker.class)
                                    .setInputData(data)
                                    .setConstraints(constraints)
                                    .setInitialDelay(number, timeUnit)
                                    .addTag("FinishQueueScheduler")
                                    .build();

                            WorkManager.getInstance(view.getContext()).enqueue(request);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void delayPauseAvailable(View view){
        Constraints constraints = new Constraints.Builder()
                .setRequiresDeviceIdle(false)
                .build();

        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(PauseAvailableWorker.class)
                .setConstraints(constraints)
                .setInitialDelay(2, TimeUnit.HOURS)
                .addTag("FinishQueueScheduler")
                .build();

        WorkManager.getInstance(view.getContext()).enqueue(request);
    }

    public void getQrCode(String queueID) {
        DI.getQrCodeImageUseCase.invoke(queueID)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<ImageModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull ImageModel imageModel) {
                        _image.postValue(imageModel.getImageUri());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

}