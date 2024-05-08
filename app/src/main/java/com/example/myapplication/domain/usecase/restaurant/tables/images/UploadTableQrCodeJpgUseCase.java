package com.example.myapplication.domain.usecase.restaurant.tables.images;

import com.example.myapplication.di.DI;
import com.example.myapplication.di.restaurant.RestaurantTableDI;

import io.reactivex.rxjava3.core.Completable;

public class UploadTableQrCodeJpgUseCase {
    public Completable invoke(String tableId, byte[] data){
        return RestaurantTableDI.tableImages.uploadTableQrCodeFireStorage(tableId, data);
    }
}
