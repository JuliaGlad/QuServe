package com.example.myapplication.domain.usecase.companyQueue.employees;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.common.ImageModel;

import io.reactivex.rxjava3.core.Single;

public class GetEmployeePhotoUseCase {
    public Single<ImageModel> invoke(String employeeId) {
        return DI.companyUserRepository.getSingleEmployeePhoto(employeeId).flatMap(uri ->
                Single.just(new ImageModel(uri.getImageUri())));
    }
}
