package com.example.myapplication.domain.usecase.companyQueue.queues;

import com.example.myapplication.di.DI;
import com.example.myapplication.presentation.companyQueue.models.EmployeeModel;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class CreateCompanyQueueDocumentUseCase {
    public Single<String> invoke(String queueID, String city, String disableTime, String queueName, String location, String companyId, List<EmployeeModel> workers){
        return DI.companyQueueRepository.createCompanyQueueDocument(queueID, city, disableTime, queueName, location, companyId, workers);
    }
}
