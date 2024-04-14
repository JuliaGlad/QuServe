package com.example.myapplication.presentation.service.finishedQueueCreation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.CompanyQueueDI;
import com.example.myapplication.di.DI;
import com.example.myapplication.di.QueueDI;
import com.example.myapplication.domain.model.common.ImageModel;
import com.example.myapplication.domain.model.queue.QueueTimeModel;
import com.example.myapplication.presentation.service.finishedQueueCreation.state.FinishedQueueState;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FinishedQueueCreationViewModel extends ViewModel {

    private final MutableLiveData<String> _queue = new MutableLiveData<>(null);
    LiveData<String> queue = _queue;

    private final MutableLiveData<FinishedQueueState> _state = new MutableLiveData<>(new FinishedQueueState.Loading());
    LiveData<FinishedQueueState> state = _state;

    public void delayBasicQueueFinish() {
        QueueDI.getQueueTimeModelUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<QueueTimeModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull QueueTimeModel queueTimeModel) {
                        String time = queueTimeModel.getTime();
                        _queue.postValue(time);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void getQrCode(String queueID) {
        QueueDI.getQrCodeImageUseCase.invoke(queueID)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<ImageModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull ImageModel imageModel) {
                        _state.postValue(new FinishedQueueState.Success(imageModel.getImageUri()));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void delayCompanyQueueFinish(String queueId, String companyId) {
        CompanyQueueDI.getCompanyQueueTimeModelUseCase.invoke(queueId, companyId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull String time) {
                        _queue.postValue(time);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

}