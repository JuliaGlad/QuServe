package com.example.myapplication.domain.usecase.queue.usecase;

import com.example.myapplication.di.DI;

import java.io.File;

import io.reactivex.rxjava3.core.Completable;

public class UploadFileToFireStorageUseCase {
    public Completable invoke(File file, String queueID){
        return DI.queueRepository.uploadFileToFireStorage(file, queueID);
    }
}
