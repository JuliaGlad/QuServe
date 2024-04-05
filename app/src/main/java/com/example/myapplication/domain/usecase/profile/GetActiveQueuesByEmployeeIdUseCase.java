package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.DI;
import com.example.myapplication.data.dto.ActiveEmployeeQueueDto;
import com.example.myapplication.domain.model.profile.ActiveQueueEmployeeModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class GetActiveQueuesByEmployeeIdUseCase {
    public Single<List<ActiveQueueEmployeeModel>> invoke(String companyId, String employeeId){
        List<ActiveQueueEmployeeModel> models = new ArrayList<>();
        return DI.profileRepository.getActiveQueuesByEmployeeId(companyId, employeeId).map(dtos -> {
            for (int i = 0; i < dtos.size(); i++) {
                ActiveEmployeeQueueDto current = dtos.get(i);
                models.add(new ActiveQueueEmployeeModel(
                        current.getQueueId(),
                        current.getQueueName(),
                        current.getLocation()
                ));
            }
            return models;
        });
    }
}
