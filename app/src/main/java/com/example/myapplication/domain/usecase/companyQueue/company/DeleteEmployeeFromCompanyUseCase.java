package com.example.myapplication.domain.usecase.companyQueue.company;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.core.Single;

public class DeleteEmployeeFromCompanyUseCase {
    public Single<String> invoke(String userId, String companyId){
        return DI.companyUserRepository.deleteEmployeeFromCompany(userId, companyId);
    }
}
