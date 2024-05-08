package com.example.myapplication.domain.usecase.restaurant.tables.images;

import com.example.myapplication.di.DI;
import com.example.myapplication.di.restaurant.RestaurantTableDI;

import io.reactivex.rxjava3.core.Completable;

public class DeleteTableQrCodePdfUseCase {
    public Completable invoke(String tableId){
        return RestaurantTableDI.tableImages.deleteQrCodePdf(tableId);
    }
}
