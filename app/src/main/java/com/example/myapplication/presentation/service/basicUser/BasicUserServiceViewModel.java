package com.example.myapplication.presentation.service.basicUser;

import static com.example.myapplication.presentation.utils.Utils.NOT_RESTAURANT_VISITOR;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.profile.ProfileDI;
import com.example.myapplication.domain.model.profile.UserActionsDataModel;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BasicUserServiceViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _isVisitor = new MutableLiveData<>(null);
    LiveData<Boolean> isVisitor = _isVisitor;

    public void getActions(){
        ProfileDI.getUserActionsDataUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<UserActionsDataModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull UserActionsDataModel userActionsDataModel) {
                        if (userActionsDataModel.getRestaurantVisitor().equals(NOT_RESTAURANT_VISITOR)){
                            _isVisitor.postValue(false);
                        } else {
                            _isVisitor.postValue(true);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

}
