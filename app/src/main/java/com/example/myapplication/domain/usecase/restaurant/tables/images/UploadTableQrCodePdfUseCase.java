package com.example.myapplication.domain.usecase.restaurant.tables.images;

import com.example.myapplication.di.restaurant.RestaurantTableDI;

import java.io.File;

import io.reactivex.rxjava3.core.Completable;

public class UploadTableQrCodePdfUseCase {
    public Completable invoke(File file, String tableId){
        return RestaurantTableDI.tableImages.uploadTablePdfToFireStorage(file, tableId);
    }
}
