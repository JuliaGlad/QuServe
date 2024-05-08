package com.example.myapplication.domain.usecase.profile.employee.company;

import com.example.myapplication.di.DI;
import com.example.myapplication.data.dto.ActiveEmployeeQueueDto;
import com.example.myapplication.di.profile.ProfileDI;
import com.example.myapplication.di.profile.ProfileEmployeeDI;
import com.example.myapplication.domain.model.profile.ActiveQueueEmployeeModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public class GetActiveQueuesUseCase {
    public Single<List<ActiveQueueEmployeeModel>> invoke(String companyId) {
        List<ActiveQueueEmployeeModel> models = new ArrayList<>();
        return ProfileEmployeeDI.companyEmployee.getActiveQueues(companyId).map(dtos ->
                dtos.stream().map(dto -> new ActiveQueueEmployeeModel(
                        dto.getQueueId(),
                        dto.getQueueName(),
                        dto.getLocation()
                )).collect(Collectors.toList()));
    }
}
