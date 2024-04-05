package com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.workerDetails.state;

import com.example.myapplication.presentation.employee.main.ActiveQueueModel;
import com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.workerDetails.model.WorkerDetailsModel;

import java.util.List;

public interface WorkerDetailsState {

    class Success implements WorkerDetailsState{
        public List<ActiveQueueModel> data;

        public Success(List<ActiveQueueModel> data) {
            this.data = data;
        }
    }

    class Loading implements WorkerDetailsState{}

    class Error implements WorkerDetailsState{}

}
