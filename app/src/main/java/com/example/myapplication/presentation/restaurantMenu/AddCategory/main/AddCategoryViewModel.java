package com.example.myapplication.presentation.restaurantMenu.AddCategory.main;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.restaurant.RestaurantMenuDI;
import com.example.myapplication.presentation.restaurantMenu.CategoryAddedModel;

import java.util.Random;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddCategoryViewModel extends ViewModel {

    private final MutableLiveData<CategoryAddedModel> _onComplete = new MutableLiveData<>(null);
    public LiveData<CategoryAddedModel> onComplete = _onComplete;

    public void initCategoryData(String restaurantId) {
        String categoryId = generateId();
        if (ArgumentsCategory.chosenImage instanceof String) {
            initDataWithStringDrawable((String)ArgumentsCategory.chosenImage, restaurantId, categoryId);
        } else if (ArgumentsCategory.chosenImage instanceof Uri) {
            initDataWithUri((Uri) ArgumentsCategory.chosenImage, restaurantId, categoryId);
        }
    }

    private void initDataWithStringDrawable(String chosenImage, String restaurantId, String categoryId) {
        RestaurantMenuDI.addMenuCategoryWithDrawableUseCase.invoke(restaurantId, categoryId, ArgumentsCategory.name, chosenImage)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _onComplete.postValue(new CategoryAddedModel(categoryId, chosenImage, null, ArgumentsCategory.name));
                        setArgumentsNull();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    private void initDataWithUri(Uri uri, String restaurantId, String categoryId) {
        RestaurantMenuDI.addMenuCategoryUseCase.invoke(restaurantId, categoryId, ArgumentsCategory.name)
                    .concatWith(RestaurantMenuDI.uploadCategoryUriImageUseCase.invoke(uri, restaurantId, ArgumentsCategory.name))
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _onComplete.postValue(new CategoryAddedModel(categoryId, null, uri, ArgumentsCategory.name));
                        setArgumentsNull();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }


    private String generateId() {
        Random rand = new Random();
        int id = rand.nextInt(90000000) + 10000000;
        return "@" + id;
    }

    public void setArgumentsNull() {
        ArgumentsCategory.name = null;
        ArgumentsCategory.chosenImage = null;
    }
}