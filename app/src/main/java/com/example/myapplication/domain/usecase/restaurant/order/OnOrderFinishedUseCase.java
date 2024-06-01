package com.example.myapplication.domain.usecase.restaurant.order;

import static com.example.myapplication.presentation.utils.constants.Restaurant.ORDER_ID;
import static com.example.myapplication.presentation.utils.constants.Utils.NO_ORDER;

import com.google.firebase.firestore.DocumentSnapshot;

public class OnOrderFinishedUseCase {
    public boolean invoke(DocumentSnapshot value){
        return value.getString(ORDER_ID).equals(NO_ORDER);
    }
}
