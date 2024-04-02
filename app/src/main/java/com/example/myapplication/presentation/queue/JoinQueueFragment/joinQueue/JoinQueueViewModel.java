package com.example.myapplication.presentation.queue.JoinQueueFragment.joinQueue;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.common.ImageModel;
import com.example.myapplication.domain.model.queue.QueueNameModel;
import com.example.myapplication.presentation.queue.JoinQueueFragment.joinQueue.model.JoinQueueModel;
import com.example.myapplication.presentation.queue.JoinQueueFragment.joinQueue.state.JoinQueueState;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class JoinQueueViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _isUpdated = new MutableLiveData<>(false);
    LiveData<Boolean> isUpdated = _isUpdated;

    private final MutableLiveData<JoinQueueState> _state = new MutableLiveData<>();
    LiveData<JoinQueueState> state = _state;

    private final MutableLiveData<Boolean> _isSignIn = new MutableLiveData<>(false);
    LiveData<Boolean> isSignIn = _isSignIn;

    public boolean checkUserID() {
        return DI.checkUserIdUseCase.invoke();

    }

    public void signInAnonymously() {
        DI.signInAnonymouslyUseCase.invoke().subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _isSignIn.postValue(true);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
    }

    public void getQueueData(String queueID) {
        DI.getQueueByQueueIdUseCase.invoke(queueID)
                .zipWith(DI.getQrCodeImageUseCase.invoke(queueID), new BiFunction<QueueNameModel, ImageModel, JoinQueueModel>() {
                    @Override
                    public JoinQueueModel apply(QueueNameModel queueNameModel, ImageModel imageModel) throws Throwable {
                        return new JoinQueueModel(queueNameModel.getName(), imageModel.getImageUri());
                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<JoinQueueModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull JoinQueueModel joinQueueModel) {
                        _state.postValue(new JoinQueueState.Success(joinQueueModel));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

    }

    public void addToParticipantsList(String queueID) {
        DI.addToParticipantsListUseCase.invoke(queueID)
                .andThen(DI.updateParticipateInQueueUseCase.invoke(true))
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {


                    }

                    @Override
                    public void onComplete() {
                        _isUpdated.postValue(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

}