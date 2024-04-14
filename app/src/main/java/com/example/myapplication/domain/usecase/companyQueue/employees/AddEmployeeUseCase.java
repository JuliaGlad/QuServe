package com.example.myapplication.domain.usecase.companyQueue.employees;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class AddEmployeeUseCase {
    public Completable invoke(String path, String name) {
        return DI.companyQueueUserRepository.addEmployee(path, name);
    }
}
