package com.example.myapplication.presentation.restaurantMenu.dishDetails.editRequiredChoice;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.restaurant.RestaurantMenuDI;
import com.example.myapplication.domain.model.restaurant.menu.RequiredChoiceModel;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.editRequiredChoice.model.EditRequireChoiceModel;
import com.example.myapplication.presentation.restaurantMenu.dishDetails.editRequiredChoice.state.EditRequiredChoiceState;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class EditRequiredChoiceViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _isUpdated = new MutableLiveData<>(false);
    LiveData<Boolean> isUpdated = _isUpdated;

    private final MutableLiveData<EditRequiredChoiceState> _state = new MutableLiveData<>(new EditRequiredChoiceState.Loading());
    LiveData<EditRequiredChoiceState> state = _state;

    private final MutableLiveData<Integer> _isChoiceVariantDelete = new MutableLiveData<>(null);
    LiveData<Integer> isChoiceVariantDelete = _isChoiceVariantDelete;

    public void updateRequiredChoiceUseCase(String restaurantId, String categoryId, String dishId, String choiceId, String name){
        RestaurantMenuDI.updateRequiredChoiceNameUseCase.invoke(restaurantId, categoryId, dishId, choiceId, name)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _isUpdated.postValue(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void updateVariant(String restaurantId, String categoryId, String dishId, String choiceId, String previousVariant, String newVariant){
        RestaurantMenuDI.updateRequiredChoiceVariantUseCase.invoke(restaurantId, categoryId, dishId, choiceId, previousVariant, newVariant)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void deleteChoiceVariant(String restaurantId, String categoryId, String dishId, String choiceId, String variant, int index){
        RestaurantMenuDI.deleteRequiredChoiceVariantUseCase.invoke(restaurantId, categoryId, dishId, choiceId, variant)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _isChoiceVariantDelete.postValue(index);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void getData(String restaurantId, String categoryId, String dishId, String choiceId) {
        RestaurantMenuDI.getSingleRequiredChoiceByIdUseCase.invoke(restaurantId, categoryId, dishId, choiceId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<RequiredChoiceModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull RequiredChoiceModel requiredChoiceModel) {
                        _state.postValue(new EditRequiredChoiceState.Success(
                               new EditRequireChoiceModel(
                                       requiredChoiceModel.getName(),
                                       requiredChoiceModel.getVariantsName()
                               )
                        ));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

}