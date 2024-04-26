package com.example.myapplication.domain.usecase.restaurant;

import com.example.myapplication.di.DI;
import com.google.firebase.firestore.DocumentSnapshot;

import io.reactivex.rxjava3.core.Observable;

public class AddRestaurantSnapshotUseCase {
    public Observable<DocumentSnapshot> invoke(String restaurantId){
        return DI.restaurantRepository.addSnapshot(restaurantId);
    }
}
