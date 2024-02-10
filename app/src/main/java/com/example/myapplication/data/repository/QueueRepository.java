package com.example.myapplication.data.repository;

import static com.example.myapplication.DI.service;
import static com.example.myapplication.presentation.utils.Utils.JPG;
import static com.example.myapplication.presentation.utils.Utils.QR_CODES;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_AUTHOR_KEY;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_LIFE_TIME_KEY;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_LIST;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_NAME_KEY;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_PARTICIPANTS_LIST;
import static com.example.myapplication.presentation.utils.Utils.storageReference;

import android.net.Uri;

import com.example.myapplication.data.dto.ImageDto;
import com.example.myapplication.data.dto.QueueDto;
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
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class QueueRepository {

    public Single<List<QueueDto>> getQueuesList() {
        return Single.create(emitter -> {
            service.fireStore.collection(QUEUE_LIST).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> documents = task.getResult().getDocuments();
                            emitter.onSuccess(documents.stream().map(
                                    document -> new QueueDto(
                                            Arrays.asList(document.get(QUEUE_PARTICIPANTS_LIST).toString().split(",")),
                                            document.getId(),
                                            document.getString(QUEUE_NAME_KEY),
                                            document.getString(QUEUE_LIFE_TIME_KEY),
                                            document.getString(QUEUE_AUTHOR_KEY))
                            ).collect(Collectors.toList()));
                        }
                    });
        });
    }

    public Single<QueueDto> getParticipantsList() {
        return Single.create(emitter -> {
            service.fireStore.collection(QUEUE_LIST)
                    .whereArrayContains(QUEUE_PARTICIPANTS_LIST, service.auth.getCurrentUser().getUid())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documents = task.getResult().getDocuments().get(0);
                            emitter.onSuccess(new QueueDto(new ArrayList<>(Arrays.asList(documents.get(QUEUE_PARTICIPANTS_LIST).toString())),
                                    documents.getId(),
                                    documents.getString(QUEUE_NAME_KEY),
                                    documents.getString(QUEUE_LIFE_TIME_KEY),
                                    documents.getString(QUEUE_AUTHOR_KEY)));
                        }
                    });
        });
    }

    public void removeUserFromParticipantList(String queueId, String name) {
        DocumentReference docRef = service.fireStore.collection(QUEUE_LIST).document(queueId);
        docRef.update(QUEUE_PARTICIPANTS_LIST, FieldValue.arrayRemove(name));

    }

    public Observable<Integer> addDocumentSnapshot(String queueId) {
        DocumentReference docRef = service.fireStore.collection(QUEUE_LIST).document(queueId);
        return Observable.create(emitter -> {
            docRef.addSnapshotListener((value, error) -> {
                List<String> newParticipants;
                if (value != null) {
                    newParticipants = new ArrayList<>(Arrays.asList(value.get(QUEUE_PARTICIPANTS_LIST).toString().split(",")));
                    if (newParticipants != null) {
                        emitter.onNext(newParticipants.size());
                    }
                }
            });
        });
    }

    public void createQrCodeDocument(String queueID, String queueName, String queueTime) {
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

    public Single<ImageDto> getQrCodeJpg(String queueID) {
        return Single.create(emitter -> {
            StorageReference local = storageReference.child(QR_CODES).child(queueID + "/" + queueID + ".jpg");
            local.getDownloadUrl().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onSuccess(new ImageDto(local.getDownloadUrl()));
                }
            });
        });
    }

    public Completable addToParticipantsList(String queueId) {
        DocumentReference docRef = service.fireStore.collection(QUEUE_LIST).document(queueId);
        return Completable.create(emitter -> {
            docRef.update(QUEUE_PARTICIPANTS_LIST, FieldValue.arrayUnion(service.auth.getCurrentUser().getUid()))
                    .addOnCompleteListener(task -> {
                        emitter.onComplete();
                    });
        });
    }

    public Single<ImageDto> getQrCodePdf(String queueId) {
        return Single.create(emitter -> {
            StorageReference local = storageReference.child(QR_CODES).child(queueId + "/" + "QR-CODE.pdf");
            local.getDownloadUrl().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onSuccess(new ImageDto(local.getDownloadUrl()));
                }
            });
        });
    }
}

