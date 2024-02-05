package com.example.myapplication.presentation.queue.createQueue.finishedQueueCreation;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.JpgImageModel;
import com.google.android.gms.tasks.Task;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FinishedQueueCreationViewModel extends ViewModel {

    private final MutableLiveData<Task<Uri>> _image = new MutableLiveData<>();
    public LiveData<Task<Uri>> image = _image;

    public void getQrCode(String queueID) {
        DI.getQrCodeJpgUseCase.invoke(queueID)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<JpgImageModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull JpgImageModel jpgImageModel) {
                        _image.postValue(jpgImageModel.getImageUri());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

}