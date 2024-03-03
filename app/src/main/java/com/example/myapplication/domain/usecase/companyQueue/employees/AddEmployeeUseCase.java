package com.example.myapplication.domain.usecase.companyQueue.employees;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.core.Completable;

public class AddEmployeeUseCase {
    public Completable invoke(String path, String email, String name) {
        return DI.companyQueueRepository.addEmployee(path, name, email);
    }
}
