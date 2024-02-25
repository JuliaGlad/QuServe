package com.example.myapplication.domain.usecase.profile;

import static com.example.myapplication.presentation.utils.Utils.USER_NAME_KEY;

import com.google.firebase.firestore.DocumentSnapshot;

public class GetNameUseCase {
    public String invoke(DocumentSnapshot value){
        return value.getString(USER_NAME_KEY);
    }
}
