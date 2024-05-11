package com.example.myapplication.domain.usecase.profile.employee.restaurant;

import com.example.myapplication.di.profile.ProfileDI;

import io.reactivex.rxjava3.core.Completable;

public class DeleteRestaurantEmployeeRoleUseCase {
    public Completable invoke(String restaurantId, String userId){
        return ProfileDI.profileRepository.deleteRestaurantEmployeeRole(restaurantId, userId);
    }
}
