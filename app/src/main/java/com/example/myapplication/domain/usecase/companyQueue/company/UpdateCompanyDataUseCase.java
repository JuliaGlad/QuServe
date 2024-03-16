package com.example.myapplication.domain.usecase.companyQueue.company;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.core.Completable;

public class UpdateCompanyDataUseCase {

    public Completable invoke(String companyId, String companyName, String phone){
        return DI.companyUserRepository.updateCompanyData(companyId, companyName, phone);
    }

}
