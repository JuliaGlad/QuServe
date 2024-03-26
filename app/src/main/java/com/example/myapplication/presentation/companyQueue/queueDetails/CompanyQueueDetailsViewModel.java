package com.example.myapplication.presentation.companyQueue.queueDetails;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;
import com.example.myapplication.R;
import com.example.myapplication.domain.model.common.ImageModel;
import com.example.myapplication.domain.model.company.CompanyQueueNameModel;

import myapplication.android.ui.recycler.ui.items.items.queueDetailsButton.QueueDetailButtonModel;
import myapplication.android.ui.recycler.ui.items.items.queueDetailsButton.QueueDetailsButtonDelegateItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.ui.items.items.adviseBox.AdviseBoxDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.adviseBox.AdviseBoxModel;
import myapplication.android.ui.recycler.ui.items.items.imageView.ImageViewDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.imageView.ImageViewModel;

public class CompanyQueueDetailsViewModel extends ViewModel {

    private Uri imageUri;

    private final MutableLiveData<String> _name = new MutableLiveData<>();
    public LiveData<String> name = _name;

    private final MutableLiveData<Uri> _pdfUri = new MutableLiveData<>();
    public LiveData<Uri> pdfUri = _pdfUri;

    private final MutableLiveData<Boolean> _openEditQueue = new MutableLiveData<>(false);
    public LiveData<Boolean> openEditQueue = _openEditQueue;

    private final MutableLiveData<Boolean> _openParticipants = new MutableLiveData<>(false);
    public LiveData<Boolean> openParticipants = _openParticipants;

    private final MutableLiveData<List<DelegateItem>> _items = new MutableLiveData<>();
    public LiveData<List<DelegateItem>> items = _items;

    public void setDataNull() {
        _openEditQueue.postValue(false);
        _openParticipants.postValue(false);
        _items.postValue(Collections.emptyList());
    }

    public Completable finishQueue(String queueId, String companyId) {
        DI.deleteQrCodeUseCase.invoke(queueId);
        return DI.finishCompanyQueueUseCase.invoke(companyId, queueId);
    }

    public void getQueueRecycler(String queueId, String companyId) {
        DI.getQueueByIdUseCase.invoke(companyId, queueId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<CompanyQueueNameModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull CompanyQueueNameModel companyQueueNameModel) {
                        _name.postValue(companyQueueNameModel.getName());
                        getQrCodeImage(queueId);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    private void initRecyclerView(String queueId) {
        buildList(new DelegateItem[]{
                new ImageViewDelegateItem(new ImageViewModel(1, imageUri)),
                new AdviseBoxDelegateItem(new AdviseBoxModel(2, R.string.here_you_can_see_queue_details)),
                new QueueDetailsButtonDelegateItem(new QueueDetailButtonModel(3, R.string.download_pdf, R.string.dowload_pdf_description, R.drawable.ic_qrcode,
                        () -> {
                            getQrCodePdf(queueId);
                        })),
                new QueueDetailsButtonDelegateItem(new QueueDetailButtonModel(4, R.string.edit_queue, R.string.edit_queue_description, R.drawable.ic_edit,
                        () -> {
                            _openEditQueue.postValue(true);
                        })),
                new QueueDetailsButtonDelegateItem(new QueueDetailButtonModel(5, R.string.participants_list, R.string.participants_list_description, R.drawable.ic_group, () -> {
                    _openParticipants.postValue(true);
                })),
        });
    }

    private void getQrCodePdf(String queueId) {
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

    private void getQrCodeImage(String queueId) {
        DI.getQrCodeImageUseCase.invoke(queueId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<ImageModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull ImageModel imageModel) {
                        imageUri = imageModel.getImageUri();
                        initRecyclerView(queueId);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("Error", "Problem with network");
                    }
                });
    }

    private void buildList(DelegateItem[] items) {
        List<DelegateItem> list = new ArrayList<>(Arrays.asList(items));
        _items.postValue(list);
    }


}