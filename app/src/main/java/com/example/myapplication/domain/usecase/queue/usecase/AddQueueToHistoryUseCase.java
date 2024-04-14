package com.example.myapplication.domain.usecase.queue.usecase;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class AddQueueToHistoryUseCase {

    public Completable invoke(String queueId, String name, String time, String date){
        return DI.queueRepository.addQueueToHistory(queueId, name, time, date);
    }

}
