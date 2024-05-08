package com.example.myapplication.presentation.dialogFragments.deleteRequiredChoice;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.restaurant.RestaurantMenuDI;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DeleteRequiredChoiceViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _isChoiceDelete = new MutableLiveData<>(false);
    LiveData<Boolean> isChoiceDelete = _isChoiceDelete;

    public void deleteChoice(String restaurantId, String categoryId, String dishId, String choiceId){
        RestaurantMenuDI.deleteRequiredChoiceByIdUseCase.invoke(restaurantId, categoryId, dishId, choiceId)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _isChoiceDelete.postValue(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}
