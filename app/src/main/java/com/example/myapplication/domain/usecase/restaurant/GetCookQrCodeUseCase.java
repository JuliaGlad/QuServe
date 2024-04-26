package com.example.myapplication.domain.usecase.restaurant;

import android.net.Uri;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Single;

public class GetCookQrCodeUseCase {
    public Single<Uri> invoke(String locationId){
        return DI.restaurantRepository.getCookQrCode(locationId);
    }
}
