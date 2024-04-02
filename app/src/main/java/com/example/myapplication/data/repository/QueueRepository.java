package com.example.myapplication.data.repository;

import static com.example.myapplication.DI.service;
import static com.example.myapplication.presentation.utils.Utils.DATE_LEFT;
import static com.example.myapplication.presentation.utils.Utils.HISTORY_KEY;
import static com.example.myapplication.presentation.utils.Utils.HOURS_DIVIDER;
import static com.example.myapplication.presentation.utils.Utils.JPG;
import static com.example.myapplication.presentation.utils.Utils.MID_TIME_WAITING;
import static com.example.myapplication.presentation.utils.Utils.MINUTES_DIVIDER;
import static com.example.myapplication.presentation.utils.Utils.PAUSED;
import static com.example.myapplication.presentation.utils.Utils.PEOPLE_PASSED;
import static com.example.myapplication.presentation.utils.Utils.QR_CODES;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_AUTHOR_KEY;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_IN_PROGRESS;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_LIFE_TIME_KEY;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_LIST;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_NAME_KEY;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_PARTICIPANTS_LIST;
import static com.example.myapplication.presentation.utils.Utils.SECONDS_DIVIDER;
import static com.example.myapplication.presentation.utils.Utils.TIME;
import static com.example.myapplication.presentation.utils.Utils.USER_LIST;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.data.dto.ImageDto;
import com.example.myapplication.data.dto.QueueDto;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class QueueRepository {

    public Completable addQueueToHistory(String queueId, String name, String timeLeft, String date) {
        DocumentReference docRef = service.fireStore
                .collection(USER_LIST)
                .document(service.auth.getCurrentUser().getUid())
                .collection(HISTORY_KEY)
                .document(date)
                .collection(QUEUE_LIST)
                .document(queueId);

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put(QUEUE_NAME_KEY, name);
        hashMap.put(TIME, timeLeft);
        hashMap.put(DATE_LEFT, date);

        return Completable.create(emitter -> {
            docRef.set(hashMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onComplete();
                }
            });
        });
    }

    public void updateTimePassed(String queueId, int previous, int passed) {
        if (passed != 0) {
            DocumentReference docRef = service.fireStore.collection(QUEUE_LIST).document(queueId);
            docRef.update(MID_TIME_WAITING, String.valueOf((previous + 30) / passed));
        }
    }

    public Single<List<QueueDto>> getQueuesList() {
        return Single.create(emitter -> {
            service.fireStore.collection(QUEUE_LIST).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> documents = task.getResult().getDocuments();
                            emitter.onSuccess(documents.stream().map(
                                    document -> new QueueDto(
                                            (List<Object>) document.get(QUEUE_PARTICIPANTS_LIST),
                                            document.getId(),
                                            document.getString(QUEUE_NAME_KEY),
                                            document.getString(QUEUE_LIFE_TIME_KEY),
                                            document.getString(QUEUE_AUTHOR_KEY),
                                            document.getString(QUEUE_IN_PROGRESS),
                                            document.getString(PEOPLE_PASSED),
                                            document.getString(MID_TIME_WAITING))
                            ).collect(Collectors.toList()));
                        } else {
                            emitter.onSuccess(null);
                        }
                    });
        });
    }

    public Completable nextParticipantUpdateList(String queueId, String name, int passed) {
        DocumentReference docRef = service.fireStore.collection(QUEUE_LIST).document(queueId);
        return Completable.create(emitter -> {
            docRef.update(QUEUE_PARTICIPANTS_LIST, FieldValue.arrayRemove(name))
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            updateInProgress(queueId, name, passed);
                            emitter.onComplete();
                        } else {
                            emitter.onError(new Throwable("Failed" + task.getException()));
                        }
                    });
        });
    }

    public Observable<DocumentSnapshot> addSnapshotListener(String queueId) {
        return Observable.create(emitter -> {
            service.fireStore.collection(QUEUE_LIST).document(queueId).addSnapshotListener((value, error) -> {
                if (value != null) {
                    emitter.onNext(value);
                }
            });
        });
    }

    public Completable pauseQueue(String queueId, int hours, int minutes, int seconds) {
        DocumentReference docRef = service.fireStore.collection(QUEUE_LIST).document(queueId);
        return Completable.create(emitter -> {
            docRef.update(QUEUE_IN_PROGRESS, hours + HOURS_DIVIDER + minutes + MINUTES_DIVIDER + seconds + SECONDS_DIVIDER + "_" + PAUSED).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onComplete();
                }
            });
        });
    }

    public Completable continueQueue(String queueId) {
        return Completable.create(emitter -> {
//          /
        });
    }

    public void updateInProgress(String queueId, String name, int peoplePassed) {
        DocumentReference docRef = service.fireStore.collection(QUEUE_LIST).document(queueId);
        docRef.update(QUEUE_IN_PROGRESS, name);
        docRef.update(PEOPLE_PASSED, String.valueOf(peoplePassed + 1));
    }

    public Completable removeParticipantById(String queueId) {
        return Completable.create(emitter -> {
            DocumentReference docRef = service.fireStore.collection(QUEUE_LIST).document(queueId);
            docRef.update(QUEUE_PARTICIPANTS_LIST, FieldValue.arrayRemove(service.auth.getCurrentUser().getUid()))
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            service.auth.getCurrentUser().delete().addOnCompleteListener(task1 -> emitter.onComplete());
                        }
                    });
        });
    }

    public Completable finishQueue(String queueId) {
        DocumentReference docRef = service.fireStore.collection(QUEUE_LIST).document(queueId);
        return Completable.create(emitter -> {
            docRef.delete().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onComplete();
                }
            });
        });
    }

    public Completable deleteQrCode(String queueId) {
        return Completable.create(emitter -> {
            service.storageReference.child(QR_CODES).child(queueId + "/" + queueId + ".jpg")
                    .delete().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onComplete();
                        }
                    });
        });
    }

    public Completable onParticipantServed(String queueId) {
        String userId = service.auth.getCurrentUser().getUid();
        DocumentReference docRef = service.fireStore.collection(QUEUE_LIST).document(queueId);
        return Completable.create(emitter -> {
            docRef.addSnapshotListener((value, error) -> {
                if (value != null && !value.get(QUEUE_PARTICIPANTS_LIST).toString().contains(userId) && !value.getString(QUEUE_IN_PROGRESS).contains(userId)) {
                    emitter.onComplete();
                }
            });
        });
    }

    public Observable<Integer> addParticipantsSizeDocumentSnapshot(String queueId) {
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

    public Observable<Integer> addPeopleBeforeDocumentSnapshot(String queueId, int previousSize) {
        DocumentReference docRef = service.fireStore.collection(QUEUE_LIST).document(queueId);
        return Observable.create(emitter -> {
            docRef.addSnapshotListener((value, error) -> {
                List<String> peopleBeforeYou;
                int size = 0;
                if (value != null) {
                    peopleBeforeYou = (List<String>) value.get(QUEUE_PARTICIPANTS_LIST);
                    for (int i = 0; i < peopleBeforeYou.size(); i++) {
                        if (peopleBeforeYou.get(i).equals(service.auth.getCurrentUser().getUid())) {
                            size = i;
                            break;
                        }
                    }
                    if (size < previousSize) {
                        emitter.onNext(size);
                    }
                }
            });
        });
    }

    public Completable createQueueDocument(String queueID, String queueName, String queueTime) {
        DocumentReference docRef = service.fireStore.collection(QUEUE_LIST).document(queueID);
        ArrayList<String> arrayList = new ArrayList<>();

        Map<String, Object> userQueue = new HashMap<>();

        userQueue.put(QUEUE_AUTHOR_KEY, service.auth.getCurrentUser().getUid());
        userQueue.put(QUEUE_NAME_KEY, queueName);
        userQueue.put(QUEUE_LIFE_TIME_KEY, queueTime);
        userQueue.put(QUEUE_PARTICIPANTS_LIST, arrayList);
        userQueue.put(QUEUE_IN_PROGRESS, "No one");
        userQueue.put(PEOPLE_PASSED, "0");
        userQueue.put(MID_TIME_WAITING, "0");

        return Completable.create(emitter -> {
            docRef.set(userQueue).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onComplete();
                }
            });
        });
    }

    public Completable uploadBytesToFireStorage(String queueID, byte[] data) {
        StorageReference reference = service.storageReference.child(QR_CODES).child(queueID + "/" + queueID + JPG);
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
            StorageReference reference = service.storageReference.child(QR_CODES).child(queueID + "/" + "QR-CODE.pdf");
            reference.putFile(Uri.fromFile(file)).addOnCompleteListener(task -> emitter.onComplete())
                    .addOnFailureListener(e -> emitter.onError(new Throwable(e.getMessage())));
        });
    }

    public Single<ImageDto> getQrCodeJpg(String queueID) {
        return Single.create(emitter -> {
            StorageReference local = service.storageReference.child(QR_CODES).child(queueID + "/" + queueID + ".jpg");
            local.getDownloadUrl().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onSuccess(new ImageDto(task.getResult()));
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
            StorageReference local = service.storageReference.child(QR_CODES).child(queueId + "/" + "QR-CODE.pdf");
            local.getDownloadUrl().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onSuccess(new ImageDto(task.getResult()));
                }
            });
        });
    }
}

