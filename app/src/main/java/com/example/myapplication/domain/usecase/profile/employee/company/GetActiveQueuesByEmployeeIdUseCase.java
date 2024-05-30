package com.example.myapplication.domain.usecase.profile.employee.company;

import com.example.myapplication.di.profile.ProfileEmployeeDI;
import com.example.myapplication.domain.model.profile.ActiveQueueEmployeeModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public class GetActiveQueuesByEmployeeIdUseCase {
    public Single<List<ActiveQueueEmployeeModel>> invoke(String companyId, String employeeId) {
        return ProfileEmployeeDI.companyEmployee.getActiveQueuesByEmployeeId(companyId, employeeId).map(dtos ->
                dtos.stream().map(dto -> new ActiveQueueEmployeeModel(
                        dto.getQueueId(),
                        dto.getQueueName(),
                        dto.getLocation()
                )).collect(Collectors.toList()));
    }
}
