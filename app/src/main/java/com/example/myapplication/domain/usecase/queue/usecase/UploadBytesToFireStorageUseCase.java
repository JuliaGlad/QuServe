package com.example.myapplication.domain.usecase.queue.usecase;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class UploadBytesToFireStorageUseCase {
    public Completable invoke(String queueID, byte[] data){
        return DI.queueRepository.uploadBytesToFireStorage(queueID, data);
    }
}
