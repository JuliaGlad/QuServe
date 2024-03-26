package com.example.myapplication.presentation.basicQueue.queueDetails;

import android.net.Uri;
import android.util.Log;

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
import com.example.myapplication.presentation.basicQueue.queueDetails.model.QueueDetailsModel;

import io.reactivex.rxjava3.functions.BiFunction;
import myapplication.android.ui.listeners.QueueDetailButtonItemListener;
import myapplication.android.ui.recycler.ui.items.items.queueDetailsButton.QueueDetailButtonModel;
import myapplication.android.ui.recycler.ui.items.items.queueDetailsButton.QueueDetailsButtonDelegateItem;

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
import myapplication.android.ui.recycler.ui.items.items.adviseBox.AdviseBoxDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.adviseBox.AdviseBoxModel;
import myapplication.android.ui.recycler.ui.items.items.imageView.ImageViewDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.imageView.ImageViewModel;

public class QueueDetailsViewModel extends ViewModel {

    private Uri imageUri;
    private String queueId;

    private final MutableLiveData<String> _name = new MutableLiveData<>(null);
    public LiveData<String> name = _name;

    private final MutableLiveData<Uri> _pdfUri = new MutableLiveData<>();
    public LiveData<Uri> pdfUri = _pdfUri;

    private final MutableLiveData<Boolean> _finishQueue = new MutableLiveData<>();
    public LiveData<Boolean> finishQueue = _finishQueue;

    private final MutableLiveData<Boolean> _pauseQueue = new MutableLiveData<>();
    public LiveData<Boolean> pauseQueue = _pauseQueue;

    private final MutableLiveData<List<DelegateItem>> _items = new MutableLiveData<>();
    public LiveData<List<DelegateItem>> items = _items;

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

    public Completable finishQueue() {
        DI.deleteQrCodeUseCase.invoke(queueId);
        return DI.finishQueueUseCase.invoke(queueId);
    }

    public Completable pauseQueue(String time) {
        return DI.pauseQueueUseCase.invoke(queueId, time);
    }

    public void getQueueRecycler(Fragment fragment) {
        DI.getQueueByAuthorUseCase.invoke()
                .flatMap(queueIdAndNameModel -> DI.getQrCodeImageUseCase.invoke(queueIdAndNameModel.getId()), new BiFunction<QueueIdAndNameModel, ImageModel, QueueDetailsModel>() {
                    @Override
                    public QueueDetailsModel apply(QueueIdAndNameModel queueIdAndNameModel, ImageModel imageModel) throws Throwable {
                        return new QueueDetailsModel(queueIdAndNameModel.getName(), queueIdAndNameModel.getId(), imageModel.getImageUri());
                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<QueueDetailsModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull QueueDetailsModel queueDetailsModel) {
                        queueId = queueDetailsModel.getId();
                        imageUri = queueDetailsModel.getUri();
                        _name.postValue(queueDetailsModel.getName());
                        initRecyclerView(fragment);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

    }

    private void initRecyclerView(Fragment fragment) {
        buildList(new DelegateItem[]{
                new ImageViewDelegateItem(new ImageViewModel(1, imageUri)),
                new AdviseBoxDelegateItem(new AdviseBoxModel(2, R.string.here_you_can_see_queue_details)),
                new QueueDetailsButtonDelegateItem(new QueueDetailButtonModel(3, R.string.download_pdf, R.string.dowload_pdf_description, R.drawable.ic_qrcode,
                        () -> {
                            getQrCodePdf(queueId);
                        })),
                new QueueDetailsButtonDelegateItem(new QueueDetailButtonModel(4, R.string.pause_queue, R.string.pause_queue_description, R.drawable.ic_time,
                        () -> {
                            _pauseQueue.postValue(true);
                        })),
                new QueueDetailsButtonDelegateItem(new QueueDetailButtonModel(5, R.string.participants_list, R.string.participants_list_description, R.drawable.ic_group, () -> {
                    NavHostFragment.findNavController(fragment)
                            .navigate(R.id.action_queueDetailsFragment_to_participantsListFragment);
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

    private void buildList(DelegateItem[] items) {
        List<DelegateItem> list = new ArrayList<>(Arrays.asList(items));
        _items.postValue(list);
    }

}