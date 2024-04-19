package com.example.myapplication.presentation.restaurantMenu.dishDetails.addRequiredChoice;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.RestaurantDI;

import java.util.Collections;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddRequiredChoiceViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _isComplete = new MutableLiveData<>(false);
    LiveData<Boolean> isComplete = _isComplete;

    public void addChoice(String restaurantId, String categoryId, String dishId){
        RestaurantDI.addRequiredChoicesUseCase.invoke(restaurantId, categoryId, dishId, RequiredChoiceArguments.name, RequiredChoiceArguments.variants)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _isComplete.postValue(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void setArgumentsNull() {
        RequiredChoiceArguments.name = null;
        RequiredChoiceArguments.variants.clear();
    }
}