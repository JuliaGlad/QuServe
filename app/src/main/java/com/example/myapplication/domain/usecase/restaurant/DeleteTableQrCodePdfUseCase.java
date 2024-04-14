package com.example.myapplication.domain.usecase.restaurant;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class DeleteTableQrCodePdfUseCase {
    public Completable invoke(String tableId){
        return DI.restaurantOwnerRepository.deleteQrCodePdf(tableId);
    }
}