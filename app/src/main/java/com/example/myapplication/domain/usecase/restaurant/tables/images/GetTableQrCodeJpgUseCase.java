package com.example.myapplication.domain.usecase.restaurant.tables.images;

import android.net.Uri;

import com.example.myapplication.di.DI;
import com.example.myapplication.di.restaurant.RestaurantTableDI;

import io.reactivex.rxjava3.core.Single;

public class GetTableQrCodeJpgUseCase {
    public Single<Uri> invoke(String tableId){
        return RestaurantTableDI.tableImages.getTableQrCodeJpg(tableId);
    }
}
