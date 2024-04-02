package com.example.myapplication.domain.usecase.companyQueue.employees;

import com.example.myapplication.DI;
import com.example.myapplication.presentation.companyQueue.queueDetails.editQueue.addWorkersFragment.model.AddWorkerModel;

import java.io.DataInput;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;

public class AddListEmployeesToQueue {
    public Completable invoke(List<AddWorkerModel> list, String companyId, String queueId){
        return DI.companyQueueRepository.addListEmployeesToQueue(list, companyId, queueId);
    }
}
