package com.example.myapplication.presentation.queue.queueDetails;

import static com.example.myapplication.presentation.utils.Utils.storageReference;

import android.net.Uri;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;
import com.example.myapplication.R;
import com.example.myapplication.domain.model.QueueIdAndNameModel;
import com.example.myapplication.presentation.queue.queueDetails.finishQueueButton.FinishQueueButtonDelegateItem;
import com.example.myapplication.presentation.queue.queueDetails.finishQueueButton.FinishQueueButtonModel;
import com.example.myapplication.presentation.queue.queueDetails.queueDetailsButton.QueueDetailButtonModel;
import com.example.myapplication.presentation.queue.queueDetails.queueDetailsButton.QueueDetailsButtonDelegateItem;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import myapplication.android.ui.listeners.VoidListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.ui.items.items.imageView.ImageViewDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.imageView.ImageViewModel;
import myapplication.android.ui.recycler.ui.items.items.stringTextView.StringTextViewDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.stringTextView.StringTextViewModel;

public class QueueDetailsViewModel extends ViewModel {

    private Uri imageUri;

    private final MutableLiveData<Task<Uri>> _pdfUri = new MutableLiveData<>();
    public LiveData<Task<Uri>> pdfUri = _pdfUri;

    private final MutableLiveData<List<DelegateItem>> _items = new MutableLiveData<>();
    public LiveData<List<DelegateItem>> items = _items;

    private final MutableLiveData<Boolean> _showDialog = new MutableLiveData<>();
    public LiveData<Boolean> showDialog = _showDialog;


    public void getQueueRecycler(VoidListener onButtonListener) {
        DI.getQueueByAuthorUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<QueueIdAndNameModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull QueueIdAndNameModel queueIdAndNameModel) {
                        getQrCodeImage(queueIdAndNameModel.getId()).addOnCompleteListener(task1 -> {
                            initRecyclerView(queueIdAndNameModel.getId(), queueIdAndNameModel.getName(), onButtonListener);
                        });
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("Error", "error");
                    }
                });
    }

//    new SingleSubscriber<QueueIdAndNameModel>() {
//        @Override
//        public void onSuccess(QueueIdAndNameModel queueModel) {
//            getQrCodeImage(queueModel.getId()).addOnCompleteListener(task1 -> {
//                initRecyclerView(queueModel.getId(), queueModel.getName(), onButtonListener);
//            });
//        }
//
//        @Override
//        public void onError(Throwable error) {
//        }
//    }

    private Task<Uri> getQrCodeImage(String queueId) {
        StorageReference local = storageReference.child("qr-codes/").child(queueId + "/" + queueId + ".jpg");
        return (local.getDownloadUrl().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                imageUri = task.getResult();
            }
        }));
    }

    private void initRecyclerView(String queueId, String queueName, VoidListener onButtonListener) {
        buildList(new DelegateItem[]{
                new StringTextViewDelegateItem(new StringTextViewModel(1, queueName, 32, View.TEXT_ALIGNMENT_CENTER)),
                new ImageViewDelegateItem(new ImageViewModel(2, imageUri)),
                new QueueDetailsButtonDelegateItem(new QueueDetailButtonModel(3, R.string.download_pdf, R.string.dowload_pdf_description, () -> {
                    getQrCodePdf(queueId);
                })),
                new QueueDetailsButtonDelegateItem(new QueueDetailButtonModel(4, R.string.pause_queue, R.string.pause_queue_description, () -> {

                })),
                new QueueDetailsButtonDelegateItem(new QueueDetailButtonModel(5, R.string.participants_list, R.string.participants_list_description, onButtonListener::run)),
                new FinishQueueButtonDelegateItem(new FinishQueueButtonModel(6, R.string.finish_queue, () -> {

                }))
        });
    }

    private void getQrCodePdf(String queueId) {
        StorageReference local = storageReference.child("qr-codes/").child(queueId + "/" + "QR-CODE.pdf");
        _pdfUri.postValue(local.getDownloadUrl());
    }

    private void buildList(DelegateItem[] items) {
        List<DelegateItem> list = new ArrayList<>(Arrays.asList(items));
        _items.postValue(list);
    }

}