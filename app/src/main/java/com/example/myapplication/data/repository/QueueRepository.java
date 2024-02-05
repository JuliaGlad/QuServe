package com.example.myapplication.data.repository;

import static com.example.myapplication.DI.service;
import static com.example.myapplication.presentation.utils.Utils.ANONYMOUS_ID;
import static com.example.myapplication.presentation.utils.Utils.JPG;
import static com.example.myapplication.presentation.utils.Utils.PDF;
import static com.example.myapplication.presentation.utils.Utils.QR_CODES;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_AUTHOR_KEY;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_LIFE_TIME_KEY;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_LIST;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_NAME_KEY;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_PARTICIPANTS_LIST;
import static com.example.myapplication.presentation.utils.Utils.storageReference;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplication.data.dto.JpgImageDto;
import com.example.myapplication.data.dto.QueueDto;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class QueueRepository {

    public Single<List<QueueDto>> getQueuesList() {
        return Single.create(emitter -> {
            service.fireStore.collection(QUEUE_LIST).get()
                    .addOnCompleteListener(task -> {
                        List<DocumentSnapshot> documents = task.getResult().getDocuments();
                        emitter.onSuccess(documents.stream().map(
                                document -> new QueueDto(
                                        document.getId(),
                                        document.getString(QUEUE_NAME_KEY),
                                        document.getString(QUEUE_LIFE_TIME_KEY),
                                        document.getString(QUEUE_AUTHOR_KEY))
                        ).collect(Collectors.toList()));
                    });
        });
    }

    public void createQrCodeDocument(String queueID, String queueName, String queueTime) {
//        DocumentReference docRef = fireStore.collection(QUEUE_LIST).document(queueID);
//
//        ArrayList<String> arrayList = new ArrayList<>();
//
//        Map<String, Object> userQueue = new HashMap<>();
//
//        userQueue.put(QUEUE_AUTHOR_KEY, userID);
//        userQueue.put(QUEUE_NAME_KEY, queueName);
//        userQueue.put(QUEUE_LIFE_TIME_KEY, queueLifeTime);
//        userQueue.put(QUEUE_PARTICIPANTS_LIST, arrayList);
//
//        docRef.set(userQueue);
        DocumentReference docRef = service.fireStore.collection(QUEUE_LIST).document(queueID);
        ArrayList<String> arrayList = new ArrayList<>();

        Map<String, Object> userQueue = new HashMap<>();

        userQueue.put(QUEUE_AUTHOR_KEY, service.auth.getCurrentUser().getUid());
        userQueue.put(QUEUE_NAME_KEY, queueName);
        userQueue.put(QUEUE_LIFE_TIME_KEY, queueTime);
        userQueue.put(QUEUE_PARTICIPANTS_LIST, arrayList);

        docRef.set(userQueue);
    }

    public Completable uploadBytesToFireStorage(String queueID, byte[] data) {
        StorageReference reference = storageReference.child(QR_CODES).child(queueID + "/" + queueID + JPG);
        return Completable.create(emitter -> {
            reference.putBytes(data).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onComplete();
                }
            });
        });
    }

    public Completable uploadFileToFireStorage(File file, String queueID) {
        return Completable.create(emitter -> {
            StorageReference reference = storageReference.child(QR_CODES).child(queueID + "/" + "QR-CODE.pdf");
            reference.putFile(Uri.fromFile(file)).addOnCompleteListener(task -> emitter.onComplete())
                    .addOnFailureListener(e -> emitter.onError(new Throwable(e.getMessage())));
        });
    }

    public Single<JpgImageDto> getQrCodeJpg(String queueID) {
        return Single.create(emitter -> {
            StorageReference local = storageReference.child(QR_CODES).child(queueID + "/" + queueID + ".jpg");
            local.getDownloadUrl().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onSuccess(new JpgImageDto(local.getDownloadUrl()));
                }
            });
        });
    }

    public Completable addToParticipantsList(String queueId) {
        DocumentReference docRef = service.fireStore.collection(QUEUE_LIST).document(queueId);
        return Completable.create(emitter -> {
            docRef.get().addOnSuccessListener(documentSnapshot -> {
                List<String> participants = new ArrayList<>(Arrays.asList(documentSnapshot.get(QUEUE_PARTICIPANTS_LIST).toString().split(",")));
                int queueLength;
                if (participants.get(0).equals("[]")){
                    queueLength = 0;
                } else {
                    queueLength = participants.size();
                }
                docRef.update(QUEUE_PARTICIPANTS_LIST, FieldValue.arrayUnion(queueLength + 1 + "_" + ANONYMOUS_ID))
                        .addOnCompleteListener(task -> {
                            emitter.onComplete();
                        });
            });
        });
    }
}

