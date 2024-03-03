package com.example.myapplication.domain.usecase.companyQueue.company;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.core.Single;

public class CheckCompanyExistUseCase {
    public Single<Boolean> invoke(){
        return DI.companyQueueRepository.checkCompany();
    }
}
