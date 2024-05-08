package com.example.myapplication.di.restaurant;

import com.example.myapplication.data.repository.restaurant.RestaurantLocationRepository;
import com.example.myapplication.domain.usecase.restaurant.locations.CreateRestaurantLocationDocumentUseCase;
import com.example.myapplication.domain.usecase.restaurant.locations.GetRestaurantLocationsUseCase;

public class RestaurantLocationDI {
    public static RestaurantLocationRepository restaurantLocationRepository = new RestaurantLocationRepository();

    public static GetRestaurantLocationsUseCase getRestaurantLocationsUseCase = new GetRestaurantLocationsUseCase();
    public static CreateRestaurantLocationDocumentUseCase createRestaurantLocationDocumentUseCase = new CreateRestaurantLocationDocumentUseCase();
}
