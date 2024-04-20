package com.example.myapplication.presentation.dialogFragments.addNewVariant;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.RestaurantDI;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddNewVariantViewModel extends ViewModel {
    private final MutableLiveData<String> _isAdded = new MutableLiveData<>(null);
    LiveData<String> isAdded = _isAdded;

    public void addVariant(String restaurantId, String categoryId, String dishId, String choiceId, String newVariant){
        RestaurantDI.addNewRequireChoiceVariantUseCase.invoke(restaurantId, categoryId, dishId, choiceId, newVariant)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _isAdded.postValue(newVariant);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}
