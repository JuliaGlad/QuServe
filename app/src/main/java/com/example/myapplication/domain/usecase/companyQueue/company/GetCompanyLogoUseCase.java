package com.example.myapplication.domain.usecase.companyQueue.company;

import android.util.Log;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.common.ImageModel;

import io.reactivex.rxjava3.core.Single;

public class GetCompanyLogoUseCase {

    public Single<ImageModel> invoke(String companyId){
        return DI.companyUserRepository.getCompanyLogo(companyId).flatMap(imageDto ->
            Single.just(new ImageModel(imageDto.getImageUri()))
        );
    }

}
