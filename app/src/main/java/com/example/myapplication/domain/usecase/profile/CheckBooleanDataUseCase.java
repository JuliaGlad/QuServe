package com.example.myapplication.domain.usecase.profile;

import static com.example.myapplication.presentation.utils.Utils.OWN_QUEUE;
import static com.example.myapplication.presentation.utils.Utils.PARTICIPATE_IN_QUEUE;

import com.google.firebase.firestore.DocumentSnapshot;

public class CheckBooleanDataUseCase {
    public boolean invoke(DocumentSnapshot value, boolean isOwnQueue, boolean isParticipateInQueue){
        return isOwnQueue == (value.getBoolean(OWN_QUEUE)) || isParticipateInQueue ==(value.getBoolean(PARTICIPATE_IN_QUEUE));
    }
}
