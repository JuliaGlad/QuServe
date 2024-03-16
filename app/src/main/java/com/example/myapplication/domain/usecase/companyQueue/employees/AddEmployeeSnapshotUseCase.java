package com.example.myapplication.domain.usecase.companyQueue.employees;

import com.example.myapplication.DI;
import com.google.firebase.firestore.DocumentSnapshot;

import io.reactivex.rxjava3.core.Observable;

public class AddEmployeeSnapshotUseCase {
    public Observable<DocumentSnapshot> invoke(String companyId, String employeeId) {
        return DI.companyUserRepository.addEmployeeSnapshot(companyId, employeeId);
    }
}
