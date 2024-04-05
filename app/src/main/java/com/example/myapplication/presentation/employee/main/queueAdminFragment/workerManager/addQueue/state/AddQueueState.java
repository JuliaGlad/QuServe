package com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.addQueue.state;
import com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.addQueue.model.AddQueueModel;

import java.util.List;

public interface AddQueueState {
    class Success implements AddQueueState{
       public List<AddQueueModel> data;

        public Success(List<AddQueueModel> data) {
            this.data = data;
        }
    }

    class Loading implements AddQueueState{}

    class Error implements AddQueueState{}
}
