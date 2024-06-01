package com.example.myapplication.presentation.home.anonymousUser;

import static com.example.myapplication.presentation.utils.constants.Utils.NOT_PARTICIPATE_IN_QUEUE;
import static com.example.myapplication.presentation.utils.constants.Utils.NOT_RESTAURANT_VISITOR;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.QueueDI;
import com.example.myapplication.di.profile.ProfileDI;
import com.example.myapplication.di.restaurant.RestaurantOrderDI;
import com.example.myapplication.domain.model.profile.AnonymousUserModel;
import com.example.myapplication.domain.model.queue.QueueNameModel;
import com.example.myapplication.domain.model.restaurant.order.OrderDetailsModel;
import com.example.myapplication.presentation.home.anonymousUser.models.AnonymousUserActionsHomeModel;
import com.example.myapplication.presentation.home.anonymousUser.state.AnonymousUserState;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AnonymousUserViewModel extends ViewModel {

    private final MutableLiveData<AnonymousUserState> _state = new MutableLiveData<>(new AnonymousUserState.Loading());
    LiveData<AnonymousUserState> state = _state;

    public void getActions() {
        ProfileDI.getAnonymousUserUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<AnonymousUserModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull AnonymousUserModel anonymous) {
                        _state.postValue(new AnonymousUserState.ActionsGot(new AnonymousUserActionsHomeModel(
                                anonymous.getRestaurantVisitor(),
                                anonymous.getQueueParticipant()
                        )));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _state.postValue(new AnonymousUserState.Error());
                    }
                });

    }

    public void getQueueByPath(String path) {
        if (!path.equals(NOT_PARTICIPATE_IN_QUEUE)) {
            QueueDI.getQueueByPathUseCase.invoke(path)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new SingleObserver<QueueNameModel>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onSuccess(@NonNull QueueNameModel queueNameModel) {
                            _state.postValue(new AnonymousUserState.QueueDataGot(queueNameModel.getName()));
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            _state.postValue(new AnonymousUserState.Error());
                        }
                    });
        } else {
            _state.postValue(new AnonymousUserState.QueueDataGot(path));
        }
    }

    public void getOrderByPath(String path) {
        if (!path.equals(NOT_RESTAURANT_VISITOR)) {
            RestaurantOrderDI.getOrderByPathUseCase.invoke(path)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new SingleObserver<OrderDetailsModel>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onSuccess(@NonNull OrderDetailsModel orderDetailsModel) {
                            _state.postValue(new AnonymousUserState.RestaurantDataGot(orderDetailsModel.getName()));
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            _state.postValue(new AnonymousUserState.Error());
                        }
                    });
        } else {
            _state.postValue(new AnonymousUserState.RestaurantDataGot(path));
        }
    }
}