package com.example.myapplication.data.repository.restaurant;

import static com.example.myapplication.di.DI.service;
import static com.example.myapplication.presentation.utils.constants.Restaurant.ACTIVE_ORDERS;
import static com.example.myapplication.presentation.utils.constants.Restaurant.COOKS;
import static com.example.myapplication.presentation.utils.constants.Restaurant.COOKS_COUNT;
import static com.example.myapplication.presentation.utils.constants.Restaurant.LOCATION;
import static com.example.myapplication.presentation.utils.constants.Restaurant.LOCATION_CITY;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_LIST;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_LOCATION;
import static com.example.myapplication.presentation.utils.constants.Restaurant.WAITERS;
import static com.example.myapplication.presentation.utils.constants.Restaurant.WAITERS_COUNT;

import com.example.myapplication.data.dto.restaurant.LocationDto;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;

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
                    addLocationDtos(snapshots, locationDtos, emitter);
                    emitter.onSuccess(locationDtos);
                } else {
                    emitter.onError(new Throwable(task.getException()));
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
                } else {
                    emitter.onError(new Throwable(task.getException()));
                }
            });
        });
    }

    private void addLocationDtos(List<DocumentSnapshot> snapshots, List<LocationDto> locationDtos, SingleEmitter<List<LocationDto>> emitter) {
        for (int i = 0; i < snapshots.size(); i++) {
            DocumentSnapshot current = snapshots.get(i);
            locationDtos.add(new LocationDto(
                    current.getId(),
                    current.getString(LOCATION),
                    current.getString(LOCATION_CITY),
                    current.getString(COOKS_COUNT),
                    current.getString(WAITERS_COUNT),
                    current.getString(ACTIVE_ORDERS)
            ));
        }
    }

}
