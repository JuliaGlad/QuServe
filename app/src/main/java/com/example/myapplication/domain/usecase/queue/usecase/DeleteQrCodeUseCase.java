package com.example.myapplication.domain.usecase.queue.usecase;

import com.example.myapplication.DI;

public class DeleteQrCodeUseCase {
    public void invoke(String queueId){
        DI.queueRepository.deleteQrCode(queueId);
    }
}
