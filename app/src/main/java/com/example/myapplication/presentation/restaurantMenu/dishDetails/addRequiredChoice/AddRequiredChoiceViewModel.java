package com.example.myapplication.presentation.restaurantMenu.dishDetails.addRequiredChoice;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.RestaurantDI;

import java.util.Collections;
import java.util.Random;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddRequiredChoiceViewModel extends ViewModel {

    private final MutableLiveData<String> _isComplete = new MutableLiveData<>(null);
    LiveData<String> isComplete = _isComplete;

    public void addChoice(String restaurantId, String categoryId, String dishId){

        String choiceId = generateChoiceId();

        RestaurantDI.addRequiredChoicesUseCase.invoke(restaurantId, categoryId, dishId, choiceId, RequiredChoiceArguments.name, RequiredChoiceArguments.variants)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _isComplete.postValue(choiceId);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    private String generateChoiceId() {
        Random rand = new Random();
        int id = rand.nextInt(90000000) + 10000000;
        return "@" + id;
    }

    public void setArgumentsNull() {
        RequiredChoiceArguments.name = null;
        RequiredChoiceArguments.variants.clear();
    }
}