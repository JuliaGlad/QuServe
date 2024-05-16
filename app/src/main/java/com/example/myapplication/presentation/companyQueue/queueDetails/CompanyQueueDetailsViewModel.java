package com.example.myapplication.presentation.companyQueue.queueDetails;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.company.CompanyQueueDI;
import com.example.myapplication.di.QueueDI;
import com.example.myapplication.domain.model.common.ImageModel;
import com.example.myapplication.presentation.companyQueue.queueDetails.model.CompanyQueueDetailModel;
import com.example.myapplication.presentation.companyQueue.queueDetails.state.CompanyQueueDetailsState;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CompanyQueueDetailsViewModel extends ViewModel {

    private final MutableLiveData<CompanyQueueDetailsState> _state = new MutableLiveData<>();
    LiveData<CompanyQueueDetailsState> state = _state;

    private final MutableLiveData<Uri> _pdfUri = new MutableLiveData<>();
    public LiveData<Uri> pdfUri = _pdfUri;

    public void getQueueRecycler(String queueId, String companyId) {
        CompanyQueueDI.getQueueByIdUseCase.invoke(companyId, queueId)
                .zipWith(QueueDI.getQrCodeImageUseCase.invoke(queueId), (companyQueueNameModel, imageModel) -> new CompanyQueueDetailModel(
                        companyQueueNameModel.getQueueId(),
                        companyQueueNameModel.getName(),
                        imageModel.getImageUri()
                ))
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<CompanyQueueDetailModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull CompanyQueueDetailModel companyQueueDetailModel) {
                        _state.postValue(new CompanyQueueDetailsState.Success(companyQueueDetailModel));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _state.postValue(new CompanyQueueDetailsState.Error());
                    }
                });
    }

    void getQrCodePdf(String queueId) {
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