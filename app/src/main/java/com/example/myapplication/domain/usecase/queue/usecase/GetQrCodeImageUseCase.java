package com.example.myapplication.domain.usecase.queue.usecase;

import com.example.myapplication.di.DI;
import com.example.myapplication.domain.model.common.ImageModel;

import io.reactivex.rxjava3.core.Single;

public class GetQrCodeImageUseCase {
    public Single<ImageModel> invoke(String queueID){
        return DI.queueRepository.getQrCodeJpg(queueID).flatMap(jpgImageDto ->
                Single.just(new ImageModel(jpgImageDto.getImageUri())));
    }
}
