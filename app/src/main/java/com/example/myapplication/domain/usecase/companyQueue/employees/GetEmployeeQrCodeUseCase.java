package com.example.myapplication.domain.usecase.companyQueue.employees;

import com.example.myapplication.di.DI;
import com.example.myapplication.domain.model.common.ImageModel;

import io.reactivex.rxjava3.core.Single;

public class GetEmployeeQrCodeUseCase {
    public Single<ImageModel> invoke(String id){
        return DI.companyQueueUserRepository.getEmployeeQrCode(id)
                .map(imageDto -> new ImageModel(imageDto.getImageUri()));
    }
}
