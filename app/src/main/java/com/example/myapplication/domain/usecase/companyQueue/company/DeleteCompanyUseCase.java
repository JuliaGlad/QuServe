package com.example.myapplication.domain.usecase.companyQueue.company;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.core.Completable;

public class DeleteCompanyUseCase {
    public Completable invoke(String companyId){
        return DI.companyUserRepository.deleteCompany(companyId);
    }
}
