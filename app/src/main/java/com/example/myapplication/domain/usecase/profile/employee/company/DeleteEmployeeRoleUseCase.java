package com.example.myapplication.domain.usecase.profile.employee.company;

import com.example.myapplication.di.DI;
import com.example.myapplication.di.profile.ProfileEmployeeDI;

import io.reactivex.rxjava3.core.Completable;

public class DeleteEmployeeRoleUseCase {
    public Completable invoke(String companyId, String userId){
        return ProfileEmployeeDI.companyEmployee.deleteEmployeeRole(companyId, userId);
    }
}
