package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.DI;

import io.reactivex.rxjava3.core.Completable;

public class DeleteActiveQueueUseCase {
    public Completable invoke(String companyId, String queueId, String userId){
        return DI.profileRepository.deleteActiveQueue(companyId, queueId, userId);
    }
}
