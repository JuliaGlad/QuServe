package com.example.myapplication.domain.usecase.companyQueue.company;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.core.Completable;

public class UpdateRoleUseCase {
    public Completable invoke(String role, String employeeId, String companyId){
        return DI.companyUserRepository.updateRole(role, employeeId, companyId);
    }
}
