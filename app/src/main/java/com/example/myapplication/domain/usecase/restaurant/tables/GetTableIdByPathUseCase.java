package com.example.myapplication.domain.usecase.restaurant.tables;

import static com.example.myapplication.di.DI.service;

public class GetTableIdByPathUseCase {
    public String invoke(String tablePath){
        return service.fireStore.document(tablePath).getId();
    }
}
