package com.example.myapplication.presentation.dialogFragments.ingredientsToRemoveOwner;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.RestaurantDI;
import com.example.myapplication.presentation.dialogFragments.ingredientsToRemoveOwner.state.IngredientToRemoveDialogState;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class IngredientsToRemoveViewModel extends ViewModel {

    private final MutableLiveData<IngredientToRemoveDialogState> _state = new MutableLiveData<>(new IngredientToRemoveDialogState.Loading());
    LiveData<IngredientToRemoveDialogState> state = _state;

    private final MutableLiveData<Boolean> _isSaved = new MutableLiveData<>(false);
    LiveData<Boolean> isSaved = _isSaved;

    public void getIngredientsToRemove(String restaurantId, String categoryId, String dishId) {
        RestaurantDI.getIngredientsToRemoveUseCase.invoke(restaurantId, categoryId, dishId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<String>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<String> list) {
                        _state.postValue(new IngredientToRemoveDialogState.Success(list));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void addItems(String restaurantId, String categoryId, String dishId, List<String> names){
        RestaurantDI.addIngredientsToRemoveUseCase.invoke(restaurantId, categoryId, dishId, names)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _isSaved.postValue(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}
