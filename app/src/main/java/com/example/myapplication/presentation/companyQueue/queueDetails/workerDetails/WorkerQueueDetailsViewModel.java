package com.example.myapplication.presentation.companyQueue.queueDetails.workerDetails;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.company.CompanyQueueDI;
import com.example.myapplication.di.QueueDI;
import com.example.myapplication.domain.model.common.ImageModel;
import com.example.myapplication.domain.model.company.CompanyQueueNameModel;
import com.example.myapplication.domain.model.queue.QueueInProgressModel;
import com.example.myapplication.presentation.companyQueue.queueDetails.workerDetails.model.WorkerQueueDetailsModel;
import com.example.myapplication.presentation.companyQueue.queueDetails.workerDetails.state.WorkerQueueDetailsState;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function3;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class WorkerQueueDetailsViewModel extends ViewModel {

    private final MutableLiveData<Uri> _pdfUri = new MutableLiveData<>(null);
    LiveData<Uri> pdfUri = _pdfUri;

    private final MutableLiveData<WorkerQueueDetailsState> _state = new MutableLiveData<>(new WorkerQueueDetailsState.Loading());
    LiveData<WorkerQueueDetailsState> state = _state;

    public void getCompanyQueueById(String companyId, String queueId) {
        Single.zip(CompanyQueueDI.getQueueByIdUseCase.invoke(companyId, queueId),
                        QueueDI.getQrCodeImageUseCase.invoke(queueId),
                        CompanyQueueDI.getCompanyQueueInProgressUseCase.invoke(companyId, queueId), (companyQueueNameModel, imageModel, queueInProgressModel) -> {
                            boolean isPaused = queueInProgressModel.getInProgress().contains("Paused");
                            return new WorkerQueueDetailsModel(
                                    companyQueueNameModel.getName(),
                                    imageModel.getImageUri(),
                                    isPaused
                            );
                        })
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<WorkerQueueDetailsModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull WorkerQueueDetailsModel workerQueueDetailsModel) {
                        _state.postValue(new WorkerQueueDetailsState.Success(workerQueueDetailsModel));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("Error worker details", e.getMessage());
                        _state.postValue(new WorkerQueueDetailsState.Error());
                    }
                });
    }

    public void getQrCodePdf(String queueId) {
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