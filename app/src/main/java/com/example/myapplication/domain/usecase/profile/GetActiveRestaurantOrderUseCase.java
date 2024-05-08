package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.di.DI;
import com.example.myapplication.di.profile.ProfileDI;
import com.example.myapplication.di.profile.ProfileEmployeeDI;

import io.reactivex.rxjava3.core.Single;

public class GetActiveRestaurantOrderUseCase {
    public Single<String> invoke(){
        return ProfileDI.profileRepository.getActiveOrder();
    }
}
