package com.example.myapplication.domain.usecase.companyQueue.queues;

import com.example.myapplication.DI;
import com.example.myapplication.presentation.employee.main.ActiveQueueModel;
import com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.addQueue.model.AddQueueModel;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

public class AddEmployeeToListQueues {
    public Completable invoke(
            List<ActiveQueueModel> queues,
            String companyId,
            String employeeId,
            String employeeName){

        return DI.companyQueueRepository.addEmployeeToListQueues(queues, companyId, employeeId, employeeName);
    }
}
