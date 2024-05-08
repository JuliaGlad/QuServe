package com.example.myapplication.domain.usecase.profile.employee.company;

import com.example.myapplication.di.DI;
import com.example.myapplication.di.profile.ProfileEmployeeDI;

import io.reactivex.rxjava3.core.Completable;

public class AddEmployeeRoleUseCase {
    public Completable invoke(String companyId){
        return ProfileEmployeeDI.companyEmployee.addEmployeeRole(companyId);
    }
}
