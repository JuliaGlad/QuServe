package com.example.myapplication.presentation.companyQueue.queueDetails.editQueue.state;

import com.example.myapplication.presentation.companyQueue.queueDetails.editQueue.models.EditQueueModel;

public interface EditQueueState {

    class Success implements EditQueueState{
        public EditQueueModel data;

        public Success(EditQueueModel data) {
            this.data = data;
        }
    }

    class Loading implements EditQueueState{}

    class Error implements EditQueueState{}
}
