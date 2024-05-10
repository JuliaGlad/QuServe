package com.example.myapplication.di.profile;

import com.example.myapplication.data.repository.profile.ProfileEmployeeRepository;
import com.example.myapplication.domain.usecase.profile.employee.restaurant.AddCookEmployeeRoleUseCase;
import com.example.myapplication.domain.usecase.profile.employee.company.AddEmployeeRoleUseCase;
import com.example.myapplication.domain.usecase.profile.employee.company.DeleteActiveQueueUseCase;
import com.example.myapplication.domain.usecase.profile.employee.company.DeleteEmployeeRoleUseCase;
import com.example.myapplication.domain.usecase.profile.employee.company.GetActiveQueuesByEmployeeIdUseCase;
import com.example.myapplication.domain.usecase.profile.employee.company.GetActiveQueuesUseCase;
import com.example.myapplication.domain.usecase.profile.GetEmployeeRolesUseCase;
import com.example.myapplication.domain.usecase.profile.employee.restaurant.AddWaiterEmployeeRoleUseCase;
import com.example.myapplication.domain.usecase.profile.employee.restaurant.GetRestaurantEmployeeRolesUseCase;
import com.example.myapplication.domain.usecase.profile.employee.IsEmployeeUseCase;
import com.example.myapplication.domain.usecase.profile.employee.company.UpdateEmployeeRoleUseCase;

public class ProfileEmployeeDI {

    public static ProfileEmployeeRepository profileEmployeeRepository = new ProfileEmployeeRepository();
    public static ProfileEmployeeRepository.CompanyEmployee companyEmployee = new ProfileEmployeeRepository.CompanyEmployee();
    public static ProfileEmployeeRepository.RestaurantEmployee restaurantEmployee = new ProfileEmployeeRepository.RestaurantEmployee();

    public static AddWaiterEmployeeRoleUseCase addWaiterEmployeeRoleUseCase = new AddWaiterEmployeeRoleUseCase();
    public static GetRestaurantEmployeeRolesUseCase getRestaurantEmployeeRolesUseCase = new GetRestaurantEmployeeRolesUseCase();
    public static AddCookEmployeeRoleUseCase addCookEmployeeRoleUseCase = new AddCookEmployeeRoleUseCase();
    public static DeleteEmployeeRoleUseCase deleteEmployeeRoleUseCase = new DeleteEmployeeRoleUseCase();
    public static UpdateEmployeeRoleUseCase updateEmployeeRoleUseCase = new UpdateEmployeeRoleUseCase();
    public static GetActiveQueuesByEmployeeIdUseCase getActiveQueuesByEmployeeIdUseCase = new GetActiveQueuesByEmployeeIdUseCase();
    public static DeleteActiveQueueUseCase deleteActiveQueueUseCase = new DeleteActiveQueueUseCase();
    public static GetActiveQueuesUseCase getActiveQueuesUseCase = new GetActiveQueuesUseCase();
    public static GetEmployeeRolesUseCase getEmployeeRolesUseCase = new GetEmployeeRolesUseCase();
    public static IsEmployeeUseCase isEmployeeUseCase = new IsEmployeeUseCase();
    public static AddEmployeeRoleUseCase addEmployeeRoleUseCase = new AddEmployeeRoleUseCase();
}
