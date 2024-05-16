package com.example.myapplication.presentation.profile.loggedProfile.companyUser;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.company.CompanyQueueUserDI;
import com.example.myapplication.di.restaurant.RestaurantUserDI;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.model.CompanyUserModel;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.state.CompanyUserState;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CompanyUserViewModel extends ViewModel {

    private final MutableLiveData<CompanyUserState> _state = new MutableLiveData<>(new CompanyUserState.Loading());
    LiveData<CompanyUserState> state = _state;

    private final MutableLiveData<Boolean> _isExist = new MutableLiveData<>(true);
    LiveData<Boolean> isExist = _isExist;

    public void getRestaurant(String restaurantId){
        RestaurantUserDI.getSingleRestaurantUseCase.invoke(restaurantId)
                .zipWith(RestaurantUserDI.getSingleRestaurantLogoUseCase.invoke(restaurantId), (restaurantEmailNameModel, imageModel) -> new CompanyUserModel(
                        restaurantEmailNameModel.getName(),
                        restaurantEmailNameModel.getEmail(),
                        imageModel.getImageUri()
                ))
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<CompanyUserModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull CompanyUserModel companyUserModel) {
                        _state.postValue(new CompanyUserState.Success(companyUserModel));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _state.postValue(new CompanyUserState.Error());
                    }
                });
    }

    public void getCompany(String companyId) {
        CompanyQueueUserDI.getSingleCompanyUseCase.invoke(companyId)
                .zipWith(CompanyQueueUserDI.getCompanyLogoUseCase.invoke(companyId), (companyNameAndEmailModel, imageModel) ->
                        new CompanyUserModel(companyNameAndEmailModel.getName(), companyNameAndEmailModel.getEmail(), imageModel.getImageUri()))
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<CompanyUserModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull CompanyUserModel companyUserModel) {
                        _state.postValue(new CompanyUserState.Success(companyUserModel));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _state.postValue(new CompanyUserState.Error());
                    }
                });
    }
}