package com.example.myapplication.presentation.companyQueue.queueDetails.workerDetails.pauseQueue;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PauseWorkerQueueViewModel extends ViewModel {

    private final MutableLiveData<String> _queue = new MutableLiveData<>(null);
    LiveData<String> queue = _queue;

    public void continueQueue(String queueId, String companyId) {
    }
}