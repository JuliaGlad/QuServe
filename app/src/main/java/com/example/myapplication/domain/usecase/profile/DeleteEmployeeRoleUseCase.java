package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.core.Completable;

public class DeleteEmployeeRoleUseCase {
    public Completable invoke(String companyId, String userId){
        return DI.profileRepository.deleteEmployeeRole(companyId, userId);
    }
}
