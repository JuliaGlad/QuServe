package com.example.myapplication.presentation.queue.queueDetails;

import android.net.Uri;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.DI;
import com.example.myapplication.R;
import com.example.myapplication.domain.model.common.ImageModel;
import com.example.myapplication.domain.model.queue.QueueIdAndNameModel;
import com.example.myapplication.domain.model.queue.QueueInProgressModel;
import com.example.myapplication.presentation.queue.queueDetails.finishQueueButton.FinishQueueButtonDelegateItem;
import com.example.myapplication.presentation.queue.queueDetails.finishQueueButton.FinishQueueButtonModel;
import com.example.myapplication.presentation.queue.queueDetails.queueDetailsButton.QueueDetailButtonModel;
import com.example.myapplication.presentation.queue.queueDetails.queueDetailsButton.QueueDetailsButtonDelegateItem;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import myapplication.android.ui.listeners.VoidListener;
import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.ui.items.items.imageView.ImageViewDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.imageView.ImageViewModel;
import myapplication.android.ui.recycler.ui.items.items.stringTextView.StringTextViewDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.stringTextView.StringTextViewModel;

public class QueueDetailsViewModel extends ViewModel {

    private Uri imageUri;
    private String queueId;

    private final MutableLiveData<Task<Uri>> _pdfUri = new MutableLiveData<>();
    public LiveData<Task<Uri>> pdfUri = _pdfUri;

    private final MutableLiveData<Boolean> _finishQueue = new MutableLiveData<>();
    public LiveData<Boolean> finishQueue = _finishQueue;

    private final MutableLiveData<Boolean> _pauseQueue = new MutableLiveData<>();
    public LiveData<Boolean> pauseQueue = _pauseQueue;

    private final MutableLiveData<List<DelegateItem>> _items = new MutableLiveData<>();
    public LiveData<List<DelegateItem>> items = _items;

    public void getQueue(Fragment fragment){
        DI.getQueueInProgressModelUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<QueueInProgressModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull QueueInProgressModel queueInProgressModel) {
                        if (queueInProgressModel.getInProgress().contains("Paused")){
                           NavHostFragment.findNavController(fragment).navigate(R.id.action_queueDetailsFragment_to_pausedQueueFragment);
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public Completable finishQueue(){
        DI.deleteQrCodeUseCase.invoke(queueId);
        return DI.finishQueueUseCase.invoke(queueId);
    }

    public Completable pauseQueue(String time){
        return DI.pauseQueueUseCase.invoke(queueId, time);
    }

    public void getQueueRecycler(VoidListener onButtonListener) {
        DI.getQueueByAuthorUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<QueueIdAndNameModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull QueueIdAndNameModel queueIdAndNameModel) {
                        queueId = queueIdAndNameModel.getId();
                        getQrCodeImage( queueIdAndNameModel.getName(), onButtonListener);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("Error", "Problem with network");
                    }
                });
    }

    private void getQrCodeImage(String name, VoidListener voidListener) {
       DI.getQrCodeImageUseCase.invoke(queueId)
               .subscribeOn(Schedulers.io())
               .subscribe(new SingleObserver<ImageModel>() {
                   @Override
                   public void onSubscribe(@NonNull Disposable d) {

                   }

                   @Override
                   public void onSuccess(@NonNull ImageModel imageModel) {
//                      imageModel.getImageUri().addOnCompleteListener(task -> {
//                          imageUri = task.getResult();
//                          initRecyclerView(name, voidListener);
//                      });
                   }

                   @Override
                   public void onError(@NonNull Throwable e) {
                       Log.e("Error", "Problem with network");
                   }
               });
    }

    private void initRecyclerView(String queueName, VoidListener onButtonListener) {
        buildList(new DelegateItem[]{
                new StringTextViewDelegateItem(new StringTextViewModel(1, queueName, 32, View.TEXT_ALIGNMENT_CENTER)),
                new ImageViewDelegateItem(new ImageViewModel(2, imageUri)),
                new QueueDetailsButtonDelegateItem(new QueueDetailButtonModel(3, R.string.download_pdf, R.string.dowload_pdf_description, () -> {
                    getQrCodePdf(queueId);
                })),
                new QueueDetailsButtonDelegateItem(new QueueDetailButtonModel(4, R.string.pause_queue, R.string.pause_queue_description, () -> {
                    _pauseQueue.postValue(true);
                })),
                new QueueDetailsButtonDelegateItem(new QueueDetailButtonModel(5, R.string.participants_list, R.string.participants_list_description, onButtonListener::run)),
                new FinishQueueButtonDelegateItem(new FinishQueueButtonModel(6, R.string.finish_queue, () -> {
                    _finishQueue.postValue(true);
                }))
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
//                        _pdfUri.postValue(imageModel.getImageUri());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    private void buildList(DelegateItem[] items) {
        List<DelegateItem> list = new ArrayList<>(Arrays.asList(items));
        _items.postValue(list);
    }

}