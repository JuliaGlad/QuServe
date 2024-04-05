package com.example.myapplication.presentation.employee.main.queueWorkerFragment.state;

import com.example.myapplication.presentation.employee.main.queueWorkerFragment.model.QueueWorkerStateModel;

public interface QueueWorkerState {
    class Success implements QueueWorkerState{
        public QueueWorkerStateModel data;

        public Success(QueueWorkerStateModel data) {
            this.data = data;
        }
    }

    class Loading implements QueueWorkerState{}

    class Error implements QueueWorkerState{}
}
