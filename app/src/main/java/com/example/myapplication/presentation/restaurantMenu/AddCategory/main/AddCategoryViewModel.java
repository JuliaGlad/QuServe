package com.example.myapplication.presentation.restaurantMenu.AddCategory.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.RestaurantDI;

import java.io.ByteArrayOutputStream;
import java.util.Random;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddCategoryViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _onComplete = new MutableLiveData<>(false);
    public LiveData<Boolean> onComplete = _onComplete;

    public void initCategoryData(String restaurantId, View view) {
        String categoryId = generateId();
        if (ArgumentsCategory.chosenImage instanceof Integer) {
            Bitmap image = BitmapFactory.decodeResource(view.getResources(), (Integer) ArgumentsCategory.chosenImage);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);

            byte[] bytes = baos.toByteArray();
            initDataWithBytes(bytes, restaurantId, categoryId);

        } else if (ArgumentsCategory.chosenImage instanceof Uri) {
            initDataWithUri((Uri) ArgumentsCategory.chosenImage, restaurantId, categoryId);
        }

    }

    private void initDataWithUri(Uri uri, String restaurantId, String categoryId) {
        RestaurantDI.addMenuCategoryUseCase.invoke(restaurantId, categoryId, ArgumentsCategory.name)
                    .concatWith(RestaurantDI.uploadCategoryUriImageUseCase.invoke(uri, restaurantId, ArgumentsCategory.name))
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _onComplete.postValue(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    private void initDataWithBytes(byte[] bytes, String restaurantId, String categoryId) {
        RestaurantDI.addMenuCategoryUseCase.invoke(restaurantId, categoryId, ArgumentsCategory.name)
                .concatWith(RestaurantDI.uploadCategoryBytesImageUseCase.invoke(bytes, restaurantId, ArgumentsCategory.name))
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _onComplete.postValue(true);
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