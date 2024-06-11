package com.example.myapplication.domain.usecase.restaurant.tables;

import static com.example.myapplication.di.DI.service;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TABLE_LIST;

public class GetTableIdByOrderPathAndTableIdUseCase {
    public String invoke(String orderPath, String tableId){
        return service.fireStore.document(orderPath).getParent().getParent().collection(TABLE_LIST).document(tableId).getPath();
    }
}
