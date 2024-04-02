package com.example.myapplication.presentation.companyQueue.queueDetails.editQueue.addWorkersFragment.state;

import com.example.myapplication.presentation.companyQueue.queueDetails.editQueue.addWorkersFragment.model.AddWorkerModel;

import java.util.List;

public interface AddWorkersState {
    class Success implements AddWorkersState{
        public List<AddWorkerModel> data;

        public Success(List<AddWorkerModel> data) {
            this.data = data;
        }
    }

    class Loading implements AddWorkersState{

    }

    class Error implements AddWorkersState{

    }
}
