package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.core.Completable;

public class UpdateEmployeeRoleUseCase {
    public Completable invoke(String companyId, String userId, String role){
        return DI.profileRepository.updateRole(companyId, userId, role);
    }
}
