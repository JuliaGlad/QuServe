package com.example.myapplication.presentation.dialogFragments.alreadyParticipateInQueue;

import static com.example.myapplication.presentation.utils.constants.Utils.NOT_PARTICIPATE_IN_QUEUE;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.profile.ProfileDI;
import com.example.myapplication.di.QueueDI;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AlreadyParticipateInQueueDialogViewModel extends ViewModel {
    private final MutableLiveData<Boolean> _isLeft = new MutableLiveData<>();
    LiveData<Boolean> isLeft = _isLeft;

    public void leaveQueue() {
        ProfileDI.getParticipantQueuePathUseCase.invoke()
                .flatMapCompletable(path -> QueueDI.removeParticipantById.invoke(path))
                .andThen(ProfileDI.updateParticipateInQueueUseCase.invoke( NOT_PARTICIPATE_IN_QUEUE))
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _isLeft.postValue(true);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
    }

}