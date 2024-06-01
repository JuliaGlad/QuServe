package com.example.myapplication.presentation.common.JoinQueueFragment.joinQueue;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.QueueDI;
import com.example.myapplication.di.profile.ProfileDI;
import com.example.myapplication.presentation.common.JoinQueueFragment.joinQueue.model.JoinQueueModel;
import com.example.myapplication.presentation.common.JoinQueueFragment.joinQueue.state.JoinQueueState;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class JoinQueueViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _isUpdated = new MutableLiveData<>(false);
    LiveData<Boolean> isUpdated = _isUpdated;

    private final MutableLiveData<JoinQueueState> _state = new MutableLiveData<>(new JoinQueueState.Loading());
    LiveData<JoinQueueState> state = _state;

    private final MutableLiveData<Boolean> _isSignIn = new MutableLiveData<>(false);
    LiveData<Boolean> isSignIn = _isSignIn;

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
                        if (joinQueueModel.getName() != null) {
                            _state.postValue(new JoinQueueState.Success(joinQueueModel));
                        } else {
                            _state.postValue(new JoinQueueState.Error());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _state.postValue(new JoinQueueState.Error());
                    }
                });

    }

    public void addToParticipantsList(String path) {
        QueueDI.addToParticipantsListUseCase.invoke(path)
                .andThen(ProfileDI.updateParticipateInQueueUseCase.invoke(path))
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