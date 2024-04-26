package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Single;

public class GetActiveRestaurantOrderUseCase {
    public Single<String> invoke(){
        return DI.profileRepository.getActiveOrder();
    }
}
