package com.example.myapplication.presentation.basicQueue.queueDetails;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.DI;
import com.example.myapplication.di.QueueDI;
import com.example.myapplication.domain.model.common.ImageModel;
import com.example.myapplication.domain.model.queue.QueueInProgressModel;
import com.example.myapplication.presentation.basicQueue.queueDetails.model.QueueDetailsModel;
import com.example.myapplication.presentation.basicQueue.queueDetails.state.QueueDetailsState;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QueueDetailsViewModel extends ViewModel {

    private String queueId;

    private final MutableLiveData<QueueDetailsState> _state = new MutableLiveData<>(new QueueDetailsState.Loading());
    LiveData<QueueDetailsState> state = _state;

    private final MutableLiveData<Boolean> _isPaused = new MutableLiveData<>(false);
    LiveData<Boolean> isPaused = _isPaused;

    private final MutableLiveData<Uri> _pdfUri = new MutableLiveData<>();
    public LiveData<Uri> pdfUri = _pdfUri;

    public void getQueue() {
        QueueDI.getQueueInProgressModelUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<QueueInProgressModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull QueueInProgressModel queueInProgressModel) {
                        if (queueInProgressModel.getInProgress().contains("Paused")) {
                            _isPaused.postValue(true);
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void getQueueRecycler() {
        QueueDI.getQueueByAuthorUseCase.invoke()
                .flatMap(queueIdAndNameModel -> QueueDI.getQrCodeImageUseCase.invoke(queueIdAndNameModel.getId()),
                        (queueIdAndNameModel, imageModel) ->
                                new QueueDetailsModel(
                                        queueIdAndNameModel.getName(),
                                        queueIdAndNameModel.getId(),
                                        imageModel.getImageUri())
                )
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<QueueDetailsModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull QueueDetailsModel queueDetailsModel) {
                        queueId = queueDetailsModel.getId();
                        _state.postValue(new QueueDetailsState.Success(queueDetailsModel));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _state.postValue(new QueueDetailsState.Error());
                    }
                });

    }

    public void getQrCodePdf() {
        QueueDI.getQrCodePdfUseCase.invoke(queueId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<ImageModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull ImageModel imageModel) {
                        _pdfUri.postValue(imageModel.getImageUri());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

}