package com.example.myapplication.presentation.companyQueue.queueDetails.workerDetails.state;

import com.example.myapplication.presentation.companyQueue.queueDetails.workerDetails.model.WorkerQueueDetailsModel;

public interface WorkerQueueDetailsState {
    class Success implements WorkerQueueDetailsState{
        public WorkerQueueDetailsModel data;

        public Success(WorkerQueueDetailsModel data) {
            this.data = data;
        }
    }
    class Loading implements WorkerQueueDetailsState{}

    class Error implements WorkerQueueDetailsState{}
}
