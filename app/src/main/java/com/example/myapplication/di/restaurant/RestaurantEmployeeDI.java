package com.example.myapplication.di.restaurant;

import com.example.myapplication.data.repository.restaurant.RestaurantEmployeesRepository;
import com.example.myapplication.domain.usecase.profile.employee.restaurant.DeleteRestaurantEmployeeRoleUseCase;
import com.example.myapplication.domain.usecase.restaurant.UpdateIsWorkingUseCase;
import com.example.myapplication.domain.usecase.restaurant.employee.AddCookUseCase;
import com.example.myapplication.domain.usecase.restaurant.employee.AddWaiterUseCase;
import com.example.myapplication.domain.usecase.restaurant.employee.CheckActiveEmployeeOrders;
import com.example.myapplication.domain.usecase.restaurant.employee.CheckIsWorkingUseCase;
import com.example.myapplication.domain.usecase.restaurant.employee.DeleteEmployeeUseCase;
import com.example.myapplication.domain.usecase.restaurant.employee.GetCookQrCodeByPathUseCase;
import com.example.myapplication.domain.usecase.restaurant.employee.GetCookQrCodeUseCase;
import com.example.myapplication.domain.usecase.restaurant.employee.GetEmployeesUseCase;
import com.example.myapplication.domain.usecase.restaurant.employee.GetWaiterQrCodeByPathUseCase;
import com.example.myapplication.domain.usecase.restaurant.employee.GetWaiterQrCodeUseCase;
import com.example.myapplication.domain.usecase.restaurant.employee.OnHaveWorkingEmployeesUseCase;
import com.example.myapplication.domain.usecase.restaurant.employee.UploadCookQrCodeUseCase;
import com.example.myapplication.domain.usecase.restaurant.employee.UploadWaiterQrCodeUseCase;

public class RestaurantEmployeeDI {

    public static RestaurantEmployeesRepository restaurantEmployeesRepository = new RestaurantEmployeesRepository();

    public static OnHaveWorkingEmployeesUseCase onHaveWorkingEmployeesUseCase = new OnHaveWorkingEmployeesUseCase();
    public static CheckActiveEmployeeOrders checkActiveEmployeeOrders = new CheckActiveEmployeeOrders();
    public static DeleteEmployeeUseCase deleteEmployeeUseCase = new DeleteEmployeeUseCase();
    public static GetEmployeesUseCase getEmployeesUseCase = new GetEmployeesUseCase();
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
