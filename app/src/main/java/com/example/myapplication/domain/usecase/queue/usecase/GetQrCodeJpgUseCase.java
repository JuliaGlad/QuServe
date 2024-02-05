package com.example.myapplication.domain.usecase.queue.usecase;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.JpgImageModel;

import io.reactivex.rxjava3.core.Single;

public class GetQrCodeJpgUseCase {
    public Single<JpgImageModel> invoke(String queueID){
        return DI.queueRepository.getQrCodeJpg(queueID).flatMap(jpgImageDto ->
                Single.just(new JpgImageModel(jpgImageDto.getImageUri())));
    }
}
