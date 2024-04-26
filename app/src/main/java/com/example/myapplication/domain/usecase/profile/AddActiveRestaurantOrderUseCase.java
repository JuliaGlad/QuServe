package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class AddActiveRestaurantOrderUseCase {
    public Completable invoke(String path){
        return DI.profileRepository.addActiveOrder(path);
    }
}
