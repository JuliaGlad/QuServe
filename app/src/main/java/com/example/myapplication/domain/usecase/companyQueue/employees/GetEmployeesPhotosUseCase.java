package com.example.myapplication.domain.usecase.companyQueue.employees;

import android.util.Log;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.common.ImageTaskModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class GetEmployeesPhotosUseCase {
    public Single<List<ImageTaskModel>> invoke(String companyId){
        List<ImageTaskModel> list = new ArrayList<>();
        return DI.companyUserRepository.getEmployeesPhotos(companyId).map(tasks -> {
            for (int i = 0; i < tasks.size(); i++) {
                list.add(new ImageTaskModel(tasks.get(i)));

            }
            Log.d("Task", String.valueOf(list.size()));
            return list;
        });
    }
}
