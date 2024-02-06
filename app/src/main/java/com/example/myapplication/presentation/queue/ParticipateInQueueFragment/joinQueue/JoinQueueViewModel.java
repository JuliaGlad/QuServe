package com.example.myapplication.presentation.queue.ParticipateInQueueFragment.joinQueue;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.ImageModel;
import com.example.myapplication.domain.model.QueueNameModel;
import com.google.android.gms.tasks.Task;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class JoinQueueViewModel extends ViewModel {

    private final MutableLiveData<String> _name = new MutableLiveData<>();
    public LiveData<String> name = _name;

    private final MutableLiveData<Task<Uri>> _image = new MutableLiveData<>();
    public LiveData<Task<Uri>> image = _image;

    public boolean checkUserID() {
        return DI.checkUserIdUseCase.invoke();

    }

    public Completable signInAnonymously() {
        return DI.signInAnonymouslyUseCase.invoke();
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

    public void getQueueData(String queueID) {
        DI.getQueueByQueueIdUseCase.invoke(queueID)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<QueueNameModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull QueueNameModel queueNameModel) {
                        _name.postValue(queueNameModel.getName());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

    }

    public Completable addToParticipantsList(String queueID) {
        return DI.addToParticipantsListUseCase.invoke(queueID);
    }

}