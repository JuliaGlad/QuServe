package com.example.myapplication.presentation.common.finishedQueueCreation.state;

import android.net.Uri;

public interface FinishedQueueState {
    class Success implements FinishedQueueState{
        public Uri data;

        public Success(Uri data) {
            this.data = data;
        }
    }

    class Loading implements FinishedQueueState{}

    class Error implements FinishedQueueState{}
}
