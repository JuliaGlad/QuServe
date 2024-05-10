package com.example.myapplication.di.restaurant;

import com.example.myapplication.data.repository.restaurant.RestaurantEmployeesRepository;
import com.example.myapplication.domain.usecase.restaurant.UpdateIsWorkingUseCase;
import com.example.myapplication.domain.usecase.restaurant.employee.AddCookUseCase;
import com.example.myapplication.domain.usecase.restaurant.employee.AddWaiterUseCase;
import com.example.myapplication.domain.usecase.restaurant.employee.CheckIsWorkingUseCase;
import com.example.myapplication.domain.usecase.restaurant.employee.GetCookQrCodeByPathUseCase;
import com.example.myapplication.domain.usecase.restaurant.employee.GetCookQrCodeUseCase;
import com.example.myapplication.domain.usecase.restaurant.employee.GetWaiterQrCodeByPathUseCase;
import com.example.myapplication.domain.usecase.restaurant.employee.GetWaiterQrCodeUseCase;
import com.example.myapplication.domain.usecase.restaurant.employee.UploadCookQrCodeUseCase;
import com.example.myapplication.domain.usecase.restaurant.employee.UploadWaiterQrCodeUseCase;

public class RestaurantEmployeeDI {

    public static RestaurantEmployeesRepository restaurantEmployeesRepository = new RestaurantEmployeesRepository();

    public static CheckIsWorkingUseCase checkIsWorkingUseCase = new CheckIsWorkingUseCase();
    public static GetWaiterQrCodeByPathUseCase getWaiterQrCodeByPathUseCase = new GetWaiterQrCodeByPathUseCase();
    public static AddWaiterUseCase addWaiterUseCase = new AddWaiterUseCase();
    public static UpdateIsWorkingUseCase updateIsWorkingUseCase = new UpdateIsWorkingUseCase();
    public static AddCookUseCase addCookUseCase = new AddCookUseCase();
    public static GetCookQrCodeUseCase getCookQrCodeUseCase = new GetCookQrCodeUseCase();
    public static GetWaiterQrCodeUseCase getWaiterQrCodeUseCase = new GetWaiterQrCodeUseCase();
    public static UploadCookQrCodeUseCase uploadCookQrCodeUseCase = new UploadCookQrCodeUseCase();
    public static UploadWaiterQrCodeUseCase uploadWaiterQrCodeUseCase = new UploadWaiterQrCodeUseCase();
    public static GetCookQrCodeByPathUseCase getCookQrCodeByPathUseCase = new GetCookQrCodeByPathUseCase();
}
