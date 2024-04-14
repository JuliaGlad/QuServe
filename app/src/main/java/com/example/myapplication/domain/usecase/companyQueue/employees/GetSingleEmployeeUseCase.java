package com.example.myapplication.domain.usecase.companyQueue.employees;

import com.example.myapplication.di.DI;
import com.example.myapplication.domain.model.company.EmployeeMainModel;

import java.util.Objects;

import io.reactivex.rxjava3.core.Single;

public class GetSingleEmployeeUseCase {
    public Single<EmployeeMainModel> invoke(String companyId, String employeeId){
        return DI.companyQueueUserRepository.getEmployees(companyId).flatMap(employeeDtos ->
                Single.just(Objects.requireNonNull(employeeDtos.stream()
                        .filter(employeeDto -> employeeDto.getId().equals(employeeId))
                        .map(employeeDto -> new EmployeeMainModel(employeeDto.getName(), employeeDto.getId(), employeeDto.getRole(), employeeDto.getActiveQueuesCount()))
                        .findFirst()
                        .orElse(null))
                ));
    }
}
