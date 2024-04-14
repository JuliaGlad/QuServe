package com.example.myapplication.presentation.service.JoinQueueFragment.joinQueue;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.DI;
import com.example.myapplication.di.ProfileDI;
import com.example.myapplication.di.QueueDI;
import com.example.myapplication.presentation.service.JoinQueueFragment.joinQueue.model.JoinQueueModel;
import com.example.myapplication.presentation.service.JoinQueueFragment.joinQueue.state.JoinQueueState;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class JoinQueueViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _isUpdated = new MutableLiveData<>(false);
    LiveData<Boolean> isUpdated = _isUpdated;

    private final MutableLiveData<JoinQueueState> _state = new MutableLiveData<>();
    LiveData<JoinQueueState> state = _state;

    private final MutableLiveData<Boolean> _isSignIn = new MutableLiveData<>(false);
    LiveData<Boolean> isSignIn = _isSignIn;

    public boolean checkUserID() {
        return QueueDI.checkUserIdUseCase.invoke();
    }

    public void signInAnonymously() {
        QueueDI.signInAnonymouslyUseCase.invoke().subscribeOn(Schedulers.io())
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

    public void getQueueData(String path) {
        QueueDI.getQueueByPathUseCase.invoke(path)
                .flatMap(model -> QueueDI.getQrCodeImageUseCase.invoke(model.getQueueId()),
                        (queueNameModel, imageModel) -> new JoinQueueModel(queueNameModel.getName(), imageModel.getImageUri()))
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<JoinQueueModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull JoinQueueModel joinQueueModel) {
                        Log.d("Join queueModel", joinQueueModel.getName());
                        _state.postValue(new JoinQueueState.Success(joinQueueModel));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

    }

    public void addToParticipantsList(String path) {
        QueueDI.addToParticipantsListUseCase.invoke(path)
                .andThen(ProfileDI.updateParticipateInQueueUseCase.invoke(path,true))
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