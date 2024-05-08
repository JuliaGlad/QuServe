package com.example.myapplication.presentation.dialogFragments.ingredientsToRemoveOwner;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.restaurant.RestaurantMenuDI;
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

    private final MutableLiveData<String> _isAdded = new MutableLiveData<>(null);
    LiveData<String> isAdded = _isAdded;

    private final MutableLiveData<Integer> _isDeleted = new MutableLiveData<>(null);
    LiveData<Integer> isDeleted = _isDeleted;

    public void getIngredientsToRemove(String restaurantId, String categoryId, String dishId) {
        RestaurantMenuDI.getIngredientsToRemoveUseCase.invoke(restaurantId, categoryId, dishId)
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

    public void addItem(String restaurantId, String categoryId, String dishId, String name){
        RestaurantMenuDI.addIngredientToRemoveUseCase.invoke(restaurantId, categoryId, dishId, name)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _isAdded.postValue(name);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void updateIngredientToRemove(String restaurantId, String categoryId, String dishId, String previousName, String name) {
        RestaurantMenuDI.updateIngredientsToRemoveUseCase.invoke(restaurantId, categoryId, dishId, previousName, name)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d("Done", "done");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("Error", "error" + e.getMessage());
                    }
                });
    }

    public void deleteIngredient(String restaurantId, String categoryId, String dishId, String name, int index) {
        RestaurantMenuDI.deleteIngredientToRemoveUseCase.invoke(restaurantId, categoryId, dishId, name)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _isDeleted.postValue(index);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}
