package com.example.myapplication.domain.usecase.queue.usecase;

import static com.example.myapplication.presentation.utils.Utils.OWN_QUEUE;

import com.google.firebase.firestore.DocumentSnapshot;

public class GetOwnQueueData {
    public boolean invoke(DocumentSnapshot value){
        return value.getBoolean(OWN_QUEUE);
    }
}
