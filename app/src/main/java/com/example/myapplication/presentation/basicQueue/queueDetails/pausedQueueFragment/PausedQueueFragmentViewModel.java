package com.example.myapplication.presentation.basicQueue.queueDetails.pausedQueueFragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.queue.QueueIdAndNameModel;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PausedQueueFragmentViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _isContinued = new MutableLiveData<>(false);
    LiveData<Boolean> isContinued = _isContinued;

    private final MutableLiveData<String> _queueId = new MutableLiveData<>(null);
    LiveData<String> queueId = _queueId;

    public void getQueue(){
        DI.getQueueByAuthorUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<QueueIdAndNameModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull QueueIdAndNameModel queueIdAndNameModel) {
                        _queueId.postValue(queueIdAndNameModel.getId());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void continueQueue(String queueId){
        DI.continueQueueUseCase.invoke(queueId)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _isContinued.postValue(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

}
