package com.example.myapplication.presentation.basicQueue.queueDetails.state;

import com.example.myapplication.presentation.basicQueue.queueDetails.model.QueueDetailsModel;

public interface QueueDetailsState {

    class Success implements QueueDetailsState{
        public QueueDetailsModel data;

        public Success(QueueDetailsModel data) {
            this.data = data;
        }
    }

    class Loading implements QueueDetailsState{}

    class Error implements QueueDetailsState{}
}
