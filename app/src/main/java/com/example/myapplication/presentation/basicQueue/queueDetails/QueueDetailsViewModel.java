package com.example.myapplication.presentation.basicQueue.queueDetails;

import android.net.Uri;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.DI;
import com.example.myapplication.R;
import com.example.myapplication.domain.model.common.ImageModel;
import com.example.myapplication.domain.model.queue.QueueInProgressModel;
import com.example.myapplication.presentation.basicQueue.queueDetails.model.QueueDetailsModel;
import com.example.myapplication.presentation.basicQueue.queueDetails.state.QueueDetailsState;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QueueDetailsViewModel extends ViewModel {

    private String queueId;

    private final MutableLiveData<QueueDetailsState> _state = new MutableLiveData<>(new QueueDetailsState.Loading());
    LiveData<QueueDetailsState> state = _state;

    private final MutableLiveData<Uri> _pdfUri = new MutableLiveData<>();
    public LiveData<Uri> pdfUri = _pdfUri;

    public void getQueue(Fragment fragment) {
        DI.getQueueInProgressModelUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<QueueInProgressModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull QueueInProgressModel queueInProgressModel) {
                        if (queueInProgressModel.getInProgress().contains("Paused")) {
                            NavHostFragment.findNavController(fragment).navigate(R.id.action_queueDetailsFragment_to_pausedQueueFragment);
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void getQueueRecycler() {
        DI.getQueueByAuthorUseCase.invoke()
                .flatMap(queueIdAndNameModel -> DI.getQrCodeImageUseCase.invoke(queueIdAndNameModel.getId()),
                        (queueIdAndNameModel, imageModel) -> new QueueDetailsModel(queueIdAndNameModel.getName(), queueIdAndNameModel.getId(), imageModel.getImageUri()))
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

                    }
                });

    }

    public void getQrCodePdf() {
        DI.getQrCodePdfUseCase.invoke(queueId)
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