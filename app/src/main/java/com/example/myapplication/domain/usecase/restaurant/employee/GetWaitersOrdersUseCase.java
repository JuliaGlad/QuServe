package com.example.myapplication.domain.usecase.restaurant.employee;

import com.example.myapplication.di.restaurant.RestaurantEmployeeDI;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class GetWaitersOrdersUseCase {
    public Single<List<String>> invoke(String restaurantId){
      return null;
    }
}
