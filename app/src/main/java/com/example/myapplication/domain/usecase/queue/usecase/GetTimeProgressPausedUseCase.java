package com.example.myapplication.domain.usecase.queue.usecase;

import static com.example.myapplication.presentation.utils.Utils.QUEUE_IN_PROGRESS;

import com.google.firebase.firestore.DocumentSnapshot;

public class GetTimeProgressPausedUseCase {
    public String invoke(DocumentSnapshot value){
        String progress = value.getString(QUEUE_IN_PROGRESS);
        int index = progress.indexOf("_");
        return progress.substring(0, index);
    }
}
