package com.example.myapplication.domain.usecase.profile.employee.restaurant;

import com.example.myapplication.di.profile.ProfileEmployeeDI;

import io.reactivex.rxjava3.core.Completable;

public class AddWaiterEmployeeRoleUseCase {
    public Completable invoke(String waiterPath){
        return ProfileEmployeeDI.restaurantEmployee.addWaiterEmployeeRole(waiterPath);
    }
}
