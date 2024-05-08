package com.example.myapplication.domain.usecase.restaurant.employee;

import android.net.Uri;

import com.example.myapplication.di.restaurant.RestaurantEmployeeDI;

import io.reactivex.rxjava3.core.Single;

public class GetCookQrCodeByPathUseCase {
    public Single<Uri> invoke(String path){
        return RestaurantEmployeeDI.restaurantEmployeesRepository.getCookQrCodeByPath(path);
    }
}
