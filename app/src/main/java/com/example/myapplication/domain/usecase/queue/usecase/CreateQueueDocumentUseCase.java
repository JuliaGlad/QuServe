package com.example.myapplication.domain.usecase.queue.usecase;

import com.example.myapplication.DI;

public class CreateQueueDocumentUseCase {
    public void invoke(String queueID, String queueName, String queueTime){
        DI.queueRepository.createQrCodeDocument(queueID, queueName, queueTime);
    }
}
