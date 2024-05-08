package com.example.myapplication.domain.usecase.profile.employee.restaurant;

import com.example.myapplication.di.DI;
import com.example.myapplication.di.profile.ProfileEmployeeDI;

import io.reactivex.rxjava3.core.Completable;

public class AddCookEmployeeRoleUseCase {
    public Completable invoke(String path){
        return ProfileEmployeeDI.restaurantEmployee.addCookEmployeeRole(path);
    }
}
