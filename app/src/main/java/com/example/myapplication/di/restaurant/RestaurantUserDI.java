package com.example.myapplication.di.restaurant;

import com.example.myapplication.data.repository.restaurant.RestaurantUserRepository;
import com.example.myapplication.domain.usecase.restaurant.order.GetNonTakenOrdersByRestaurantIdUseCase;
import com.example.myapplication.domain.usecase.restaurant.restaurantUser.restaurantImages.GetSingleRestaurantLogoUseCase;
import com.example.myapplication.domain.usecase.restaurant.restaurantUser.restaurantImages.UploadRestaurantLogoUseCase;
import com.example.myapplication.domain.usecase.restaurant.restaurantUser.AddRestaurantSnapshotUseCase;
import com.example.myapplication.domain.usecase.restaurant.restaurantUser.CreateRestaurantDocumentUseCase;
import com.example.myapplication.domain.usecase.restaurant.restaurantUser.DeleteRestaurantUseCase;
import com.example.myapplication.domain.usecase.restaurant.restaurantUser.GetRestaurantEditModel;
import com.example.myapplication.domain.usecase.restaurant.restaurantUser.GetRestaurantNameByLocationPathUseCase;
import com.example.myapplication.domain.usecase.restaurant.restaurantUser.GetSingleRestaurantUseCase;
import com.example.myapplication.domain.usecase.restaurant.restaurantUser.UpdateRestaurantDataUseCase;

public class RestaurantUserDI {
    public static RestaurantUserRepository restaurantUserRepository = new RestaurantUserRepository();
    public static RestaurantUserRepository.RestaurantImages restaurantImages = new RestaurantUserRepository.RestaurantImages();

    public static GetRestaurantNameByLocationPathUseCase getRestaurantNameByLocationPathUseCase = new GetRestaurantNameByLocationPathUseCase();
    public static GetNonTakenOrdersByRestaurantIdUseCase getNonTakenOrdersByRestaurantIdUseCase = new GetNonTakenOrdersByRestaurantIdUseCase();
    public static AddRestaurantSnapshotUseCase addRestaurantSnapshotUseCase = new AddRestaurantSnapshotUseCase();
    public static CreateRestaurantDocumentUseCase createRestaurantDocumentUseCase = new CreateRestaurantDocumentUseCase();
    public static GetSingleRestaurantUseCase getSingleRestaurantUseCase = new GetSingleRestaurantUseCase();
    public static GetSingleRestaurantLogoUseCase getSingleRestaurantLogoUseCase = new GetSingleRestaurantLogoUseCase();
    public static GetRestaurantEditModel getRestaurantEditModel = new GetRestaurantEditModel();
    public static UpdateRestaurantDataUseCase updateRestaurantDataUseCase = new UpdateRestaurantDataUseCase();
    public static UploadRestaurantLogoUseCase uploadRestaurantLogoUseCase = new UploadRestaurantLogoUseCase();
    public static DeleteRestaurantUseCase deleteRestaurantUseCase = new DeleteRestaurantUseCase();
}
