package com.example.myapplication.domain.usecase.queue.usecase;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.ImageModel;

import io.reactivex.rxjava3.core.Single;

public class GetQrCodePdfUseCase {
    public Single<ImageModel> invoke(String queueID){
        return DI.queueRepository.getQrCodePdf(queueID).flatMap(pdfImageDto ->
                Single.just(new ImageModel(pdfImageDto.getImageUri())));
    }
}