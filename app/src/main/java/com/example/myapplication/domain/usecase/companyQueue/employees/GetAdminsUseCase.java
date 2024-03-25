package com.example.myapplication.domain.usecase.companyQueue.employees;

import static com.example.myapplication.presentation.utils.Utils.ADMIN;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.company.EmployeeMainModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class GetAdminsUseCase {
    public Single<List<EmployeeMainModel>> invoke(String companyId) {
        List<EmployeeMainModel> list = new ArrayList<>();
        return DI.companyUserRepository.getAdmins(companyId).map(employeeDtos -> {
            for (int i = 0; i < employeeDtos.size(); i++) {
                list.add(new EmployeeMainModel(employeeDtos.get(i).getName(), employeeDtos.get(i).getId(), ADMIN));
            }
            return list;
        });
    }
}
