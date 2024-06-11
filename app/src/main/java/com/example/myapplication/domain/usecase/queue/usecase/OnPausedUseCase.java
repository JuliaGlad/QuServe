package com.example.myapplication.domain.usecase.queue.usecase;

import static com.example.myapplication.presentation.utils.constants.Utils.PAUSED;
import static com.example.myapplication.presentation.utils.constants.Utils.QUEUE_IN_PROGRESS;

import com.google.firebase.firestore.DocumentSnapshot;

public class OnPausedUseCase {
    public boolean invoke(DocumentSnapshot value){
        if (value != null){
            String inProgress = value.getString(QUEUE_IN_PROGRESS);
            if (inProgress != null) {
                return !inProgress.contains(PAUSED);
            } else {
                return true;
            }
        } else {
            return true;
        }

    }
}
