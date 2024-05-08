package com.example.myapplication.domain.usecase.restaurant.employee;

import com.example.myapplication.di.restaurant.RestaurantEmployeeDI;

import io.reactivex.rxjava3.core.Completable;

public class UploadCookQrCodeUseCase {
    public Completable invoke(String locationId, byte[] bytes){
        return RestaurantEmployeeDI.restaurantEmployeesRepository.uploadCookQrCode(locationId, bytes);
    }
}
