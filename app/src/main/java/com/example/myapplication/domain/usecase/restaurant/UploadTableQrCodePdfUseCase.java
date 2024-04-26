package com.example.myapplication.domain.usecase.restaurant;

import com.example.myapplication.di.DI;

import java.io.File;

import io.reactivex.rxjava3.core.Completable;

public class UploadTableQrCodePdfUseCase {
    public Completable invoke(File file, String tableId){
        return DI.restaurantRepository.uploadTablePdfToFireStorage(file, tableId);
    }
}
