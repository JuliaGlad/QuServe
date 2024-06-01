package com.example.myapplication.domain.usecase.queue.usecase;

import static com.example.myapplication.presentation.utils.constants.Utils.PAUSED;
import static com.example.myapplication.presentation.utils.constants.Utils.QUEUE_IN_PROGRESS;

import com.google.firebase.firestore.DocumentSnapshot;

public class OnPausedUseCase {
    public boolean invoke(DocumentSnapshot value){
        return !value.getString(QUEUE_IN_PROGRESS).contains(PAUSED);
    }
}
