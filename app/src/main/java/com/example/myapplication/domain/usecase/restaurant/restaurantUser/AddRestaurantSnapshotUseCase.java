package com.example.myapplication.domain.usecase.restaurant.restaurantUser;

import com.example.myapplication.di.restaurant.RestaurantUserDI;
import com.google.firebase.firestore.DocumentSnapshot;

import io.reactivex.rxjava3.core.Observable;

public class AddRestaurantSnapshotUseCase {
    public Observable<DocumentSnapshot> invoke(String restaurantId){
        return RestaurantUserDI.addRestaurantSnapshotUseCase.invoke(restaurantId);
    }
}
