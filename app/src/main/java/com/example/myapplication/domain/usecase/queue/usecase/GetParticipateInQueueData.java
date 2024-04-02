package com.example.myapplication.domain.usecase.queue.usecase;

import static com.example.myapplication.presentation.utils.Utils.PARTICIPATE_IN_QUEUE;

import com.google.firebase.firestore.DocumentSnapshot;

public class GetParticipateInQueueData {
    public boolean invoke(DocumentSnapshot value){
        return Boolean.TRUE.equals(value.getBoolean(PARTICIPATE_IN_QUEUE));
    }
}
