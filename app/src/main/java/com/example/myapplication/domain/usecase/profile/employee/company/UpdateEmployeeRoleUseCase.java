package com.example.myapplication.domain.usecase.profile.employee.company;

import com.example.myapplication.di.DI;
import com.example.myapplication.di.profile.ProfileDI;
import com.example.myapplication.di.profile.ProfileEmployeeDI;

import io.reactivex.rxjava3.core.Completable;

public class UpdateEmployeeRoleUseCase {
    public Completable invoke(String companyId, String userId, String role){
        return ProfileEmployeeDI.companyEmployee.updateRole(companyId, userId, role);
    }
}
