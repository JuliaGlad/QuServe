package com.example.myapplication.domain.usecase.restaurant.tables;

import com.example.myapplication.di.restaurant.RestaurantOrderDI;
import com.google.firebase.firestore.DocumentSnapshot;

import io.reactivex.rxjava3.core.Observable;

public class AddTableSnapshotUseCase {
    public Observable<DocumentSnapshot> invoke(String tablePath){
        return RestaurantOrderDI.restaurantOrderRepository.addTableSnapshot(tablePath);
    }
}
