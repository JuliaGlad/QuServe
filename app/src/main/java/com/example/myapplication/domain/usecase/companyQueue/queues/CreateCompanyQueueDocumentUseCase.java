package com.example.myapplication.domain.usecase.companyQueue.queues;

import com.example.myapplication.DI;
import com.example.myapplication.presentation.companyQueue.models.EmployeeModel;

import java.util.List;

public class CreateCompanyQueueDocumentUseCase {
    public void invoke(String queueID, String city, String disableTime, String queueName, String location, String companyId, List<EmployeeModel> workers){
        DI.companyQueueRepository.createCompanyQueueDocument(queueID, city, disableTime, queueName, location, companyId, workers);
    }
}
