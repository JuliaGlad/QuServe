package com.example.myapplication.domain.usecase.companyQueue.employees;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.common.ImageModel;
import com.google.android.gms.tasks.Task;

import io.reactivex.rxjava3.core.Single;

public class GetEmployeeQrCodeUseCase {
    public Single<ImageModel> invoke(String id){
        return DI.companyQueueRepository.getEmployeeQrCode(id)
                .map(imageDto -> new ImageModel(imageDto.getImageUri()));
    }
}
