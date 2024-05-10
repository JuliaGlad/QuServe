package com.example.myapplication.data.repository.restaurant;

import static com.example.myapplication.di.DI.service;
import static com.example.myapplication.presentation.utils.constants.Restaurant.ACTIVE_ORDERS;
import static com.example.myapplication.presentation.utils.constants.Restaurant.COOKS;
import static com.example.myapplication.presentation.utils.constants.Restaurant.LOCATION;
import static com.example.myapplication.presentation.utils.constants.Restaurant.LOCATION_CITY;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_LIST;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_LOCATION;
import static com.example.myapplication.presentation.utils.constants.Restaurant.WAITERS;

import com.example.myapplication.data.dto.restaurant.LocationDto;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Single;

public class RestaurantLocationRepository {

    public Single<List<LocationDto>> getRestaurantLocations(String restaurantId) {
        return Single.create(emitter -> {
            CollectionReference collRef = service.fireStore
                    .collection(RESTAURANT_LIST)
                    .document(restaurantId)
                    .collection(RESTAURANT_LOCATION);

            collRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    List<DocumentSnapshot> snapshots = task.getResult().getDocuments();
                    List<LocationDto> locationDtos = new ArrayList<>();
                    addLocationDtos(collRef, snapshots, locationDtos);
                    emitter.onSuccess(locationDtos);
                }
            });
        });
    }

    public Single<String> createRestaurantLocationDocument(String restaurantId, String locationId, String location, String city) {

        DocumentReference docRef =
                service.fireStore
                        .collection(RESTAURANT_LIST)
                        .document(restaurantId)
                        .collection(RESTAURANT_LOCATION)
                        .document(locationId);

        String path = docRef.getPath();

        Map<String, Object> locationMap = new HashMap<>();
        locationMap.put(LOCATION_CITY, city);
        locationMap.put(LOCATION, location);

        return Single.create(emitter -> {
            docRef.set(locationMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onSuccess(path);
                }
            });
        });
    }

    private void addLocationDtos(CollectionReference collRef, List<DocumentSnapshot> snapshots, List<LocationDto> locationDtos) {
        for (int i = 0; i < snapshots.size(); i++) {
            DocumentSnapshot current = snapshots.get(i);
            DocumentReference locationRef = collRef.document(current.getId());

            String cooksCount = getLocationCooks(locationRef);
            String waitersCount = getLocationWaiters(locationRef);
            String ordersCount = getLocationOrders(locationRef);

            locationDtos.add(new LocationDto(
                    current.getId(),
                    current.getString(LOCATION),
                    current.getString(LOCATION_CITY),
                    cooksCount,
                    waitersCount,
                    ordersCount
            ));
        }
    }

    private String getLocationOrders(DocumentReference locationRef) {
        List<DocumentSnapshot> orders = new ArrayList<>();
        locationRef.collection(ACTIVE_ORDERS).get().addOnCompleteListener(taskCooks -> {
            if (taskCooks.isSuccessful()) {
                orders.addAll(taskCooks.getResult().getDocuments());
            }
        });
        return String.valueOf(orders.size());
    }

    private String getLocationWaiters(DocumentReference locationRef) {
        List<DocumentSnapshot> waiters = new ArrayList<>();
        locationRef.collection(WAITERS).get().addOnCompleteListener(taskCooks -> {
            if (taskCooks.isSuccessful()) {
                waiters.addAll(taskCooks.getResult().getDocuments());
            }
        });
        return String.valueOf(waiters.size());
    }

    private String getLocationCooks(DocumentReference locationRef) {
        List<DocumentSnapshot> cooks = new ArrayList<>();
        locationRef.collection(COOKS).get().addOnCompleteListener(taskCooks -> {
            if (taskCooks.isSuccessful()) {
                cooks.addAll(taskCooks.getResult().getDocuments());
            }
        });
        return String.valueOf(cooks.size());
    }

}
