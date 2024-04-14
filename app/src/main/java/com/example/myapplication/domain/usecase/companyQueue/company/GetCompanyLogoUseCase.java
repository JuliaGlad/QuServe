package com.example.myapplication.domain.usecase.companyQueue.company;

import com.example.myapplication.di.DI;
import com.example.myapplication.domain.model.common.ImageModel;

import io.reactivex.rxjava3.core.Single;

public class GetCompanyLogoUseCase {

    public Single<ImageModel> invoke(String companyId){
        return DI.companyQueueUserRepository.getQueueCompanyLogo(companyId).flatMap(imageDto ->
            Single.just(new ImageModel(imageDto.getImageUri()))
        );
    }

}
