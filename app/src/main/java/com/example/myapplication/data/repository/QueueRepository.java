package com.example.myapplication.data.repository;

import static com.example.myapplication.di.DI.service;
import static com.example.myapplication.presentation.utils.constants.Utils.DATE_LEFT;
import static com.example.myapplication.presentation.utils.constants.Utils.HISTORY_KEY;
import static com.example.myapplication.presentation.utils.constants.Utils.HOURS_DIVIDER;
import static com.example.myapplication.presentation.utils.constants.Utils.JPG;
import static com.example.myapplication.presentation.utils.constants.Utils.MID_TIME_WAITING;
import static com.example.myapplication.presentation.utils.constants.Utils.MINUTES_DIVIDER;
import static com.example.myapplication.presentation.utils.constants.Utils.NOT_PARTICIPATE_IN_QUEUE;
import static com.example.myapplication.presentation.utils.constants.Utils.PARTICIPATE_IN_QUEUE;
import static com.example.myapplication.presentation.utils.constants.Utils.PAUSED;
import static com.example.myapplication.presentation.utils.constants.Utils.PEOPLE_PASSED;
import static com.example.myapplication.presentation.utils.constants.Utils.PEOPLE_PASSED_15;
import static com.example.myapplication.presentation.utils.constants.Utils.QR_CODES;
import static com.example.myapplication.presentation.utils.constants.Utils.QUEUE_AUTHOR_KEY;
import static com.example.myapplication.presentation.utils.constants.Utils.QUEUE_IN_PROGRESS;
import static com.example.myapplication.presentation.utils.constants.Utils.QUEUE_LIFE_TIME_KEY;
import static com.example.myapplication.presentation.utils.constants.Utils.QUEUE_LIST;
import static com.example.myapplication.presentation.utils.constants.Utils.QUEUE_NAME_KEY;
import static com.example.myapplication.presentation.utils.constants.Utils.QUEUE_PARTICIPANTS_LIST;
import static com.example.myapplication.presentation.utils.constants.Utils.SECONDS_DIVIDER;
import static com.example.myapplication.presentation.utils.constants.Utils.TIME;
import static com.example.myapplication.presentation.utils.constants.Utils.USER_LIST;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.myapplication.data.dto.common.ImageDto;
import com.example.myapplication.data.dto.queues.QueueDto;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
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
                }else {
                    emitter.onError(new Throwable(task.getException()));
                }
            });
        });
    }

    public Completable updateMidTime(String queueId, int previous, int passed15) {
        return Completable.create(emitter -> {
            if (passed15 != 0) {
                DocumentReference docRef = service.fireStore.collection(QUEUE_LIST).document(queueId);
                int midTimeLast15Minutes = 15 / passed15;
                int newMid = 0;
                if (previous != 0) {
                    newMid = (previous + midTimeLast15Minutes) / 2;
                } else {
                    newMid = midTimeLast15Minutes;
                }
                docRef.update(MID_TIME_WAITING, String.valueOf((newMid)),
                                PEOPLE_PASSED_15, "0")
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                emitter.onComplete();
                            } else {
                                emitter.onError(new Throwable("Error", task.getException()));
                            }
                        });
            } else {
                emitter.onComplete();
            }
        });

    }

    public Single<QueueDto> getQueueByParticipantPath(String path) {
        return Single.create(emitter -> {
            try {
                DocumentReference docRef = service.fireStore.document(path);
                docRef.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        emitter.onSuccess(new QueueDto(
                                (List<Object>) document.get(QUEUE_PARTICIPANTS_LIST),
                                document.getId(),
                                document.getString(QUEUE_NAME_KEY),
                                document.getString(QUEUE_LIFE_TIME_KEY),
                                document.getString(QUEUE_AUTHOR_KEY),
                                document.getString(QUEUE_IN_PROGRESS),
                                document.getString(PEOPLE_PASSED),
                                document.getString(PEOPLE_PASSED_15),
                                document.getString(MID_TIME_WAITING))
                        );
                    } else {
                        emitter.onError(new Throwable(task.getException()));
                    }
                });
            } catch (IllegalArgumentException e){
                emitter.onError(new Throwable(e.getMessage()));
            }
        });
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
                                            document.getString(PEOPLE_PASSED_15),
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
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    docRef.update(QUEUE_PARTICIPANTS_LIST, FieldValue.arrayRemove(name),
                                    PEOPLE_PASSED, String.valueOf(Integer.parseInt(task.getResult().getString(PEOPLE_PASSED)) + 1),
                                    PEOPLE_PASSED_15, String.valueOf(passed + 1))
                            .addOnCompleteListener(taskUpdate -> {
                                if (taskUpdate.isSuccessful()) {
                                    updateInProgress(queueId, name);
                                    emitter.onComplete();
                                } else {
                                    emitter.onError(new Throwable("Failed " + taskUpdate.getException()));
                                }
                            });
                }
            });
        });
    }

    public Observable<DocumentSnapshot> addSnapshotListener(String path) {
        return Observable.create(emitter -> {
            service.fireStore.document(path).addSnapshotListener((value, error) -> {
                if (value != null) {
                    emitter.onNext(value);
                } else {
                    emitter.onError(new Throwable("Value is null"));
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
            DocumentReference docRef = service.fireStore.collection(QUEUE_LIST).document(queueId);
            docRef.update(QUEUE_IN_PROGRESS, "").addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onComplete();
                }else {
                    emitter.onError(new Throwable(task.getException()));
                }
            });
        });
    }

    public void updateInProgress(String queueId, String name) {
        DocumentReference docRef = service.fireStore.collection(QUEUE_LIST).document(queueId);
        docRef.update(QUEUE_IN_PROGRESS, name);
    }

    public Completable removeParticipantById(String path) {
        return Completable.create(emitter -> {
            DocumentReference docRef = service.fireStore.document(path);
            docRef.update(QUEUE_PARTICIPANTS_LIST, FieldValue.arrayRemove(service.auth.getCurrentUser().getUid()))
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            service.auth.getCurrentUser().delete().addOnCompleteListener(task1 -> emitter.onComplete());
                        }else {
                            emitter.onError(new Throwable(task.getException()));
                        }
                    });
        });
    }

    public Completable finishQueue(String queueId) {
        DocumentReference docRef = service.fireStore.collection(QUEUE_LIST).document(queueId);
        return Completable.create(emitter -> {
            docRef.get().addOnCompleteListener(taskGet -> {
                if (taskGet.isSuccessful()) {
                    List<String> participants = (List<String>) taskGet.getResult().get(QUEUE_PARTICIPANTS_LIST);
                    if (participants != null) {
                        for (String currentId : participants) {
                            service.fireStore
                                    .collection(USER_LIST)
                                    .document(currentId)
                                    .update(PARTICIPATE_IN_QUEUE, NOT_PARTICIPATE_IN_QUEUE);
                        }
                    }
                    docRef.delete().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onComplete();
                        }
                    });
                }else {
                    emitter.onError(new Throwable(taskGet.getException()));
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
                        }else {
                            emitter.onError(new Throwable(task.getException()));
                        }
                    });
        });
    }

    public Completable onParticipantServed(String path) {
        String userId = service.auth.getCurrentUser().getUid();
        DocumentReference docRef = service.fireStore.document(path);
        return Completable.create(emitter -> {
            docRef.addSnapshotListener((value, error) -> {
                if (value != null && !value.get(QUEUE_PARTICIPANTS_LIST).toString().contains(userId) && !value.getString(QUEUE_IN_PROGRESS).contains(userId)) {
                    emitter.onComplete();
                } else if (value == null){
                    emitter.onError(new Throwable("Value is null"));
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
                    newParticipants = (List<String>) value.get(QUEUE_PARTICIPANTS_LIST);
                    assert newParticipants != null;
                    emitter.onNext(newParticipants.size());
                } else {
                    emitter.onError(new Throwable("Value is null"));
                }
            });
        });
    }

    public Observable<Integer> addPeopleBeforeDocumentSnapshot(String path, int previousSize) {
        DocumentReference docRef = service.fireStore.document(path);
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
                } else {
                    emitter.onError(new Throwable("Value is null"));
                }
            });
        });
    }

    public Single<String> createQueueDocument(String queueID, String queueName, String queueTime) {
        DocumentReference docRef = service.fireStore.collection(QUEUE_LIST).document(queueID);
        String path = docRef.getPath();
        Map<String, Object> userQueue = geUserQueueMap(queueName, queueTime);

        return Single.create(emitter -> {
            docRef.set(userQueue).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onSuccess(path);
                }else {
                    emitter.onError(new Throwable(task.getException()));
                }
            });
        });
    }

    @NonNull
    private Map<String, Object> geUserQueueMap(String queueName, String queueTime) {
        ArrayList<String> arrayList = new ArrayList<>();

        Map<String, Object> userQueue = new HashMap<>();

        userQueue.put(QUEUE_AUTHOR_KEY, service.auth.getCurrentUser().getUid());
        userQueue.put(QUEUE_NAME_KEY, queueName);
        userQueue.put(QUEUE_LIFE_TIME_KEY, queueTime);
        userQueue.put(QUEUE_PARTICIPANTS_LIST, arrayList);
        userQueue.put(QUEUE_IN_PROGRESS, "No one");
        userQueue.put(PEOPLE_PASSED, "0");
        userQueue.put(PEOPLE_PASSED_15, "0");
        userQueue.put(MID_TIME_WAITING, "0");
        return userQueue;
    }

    public Completable uploadBytesToFireStorage(String queueID, byte[] data) {
        StorageReference reference = service.storageReference
                .child(QR_CODES)
                .child(queueID + "/" + queueID + JPG);
        return Completable.create(emitter -> {
            reference.putBytes(data).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onComplete();
                }else {
                    emitter.onError(new Throwable(task.getException()));
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
            StorageReference local = service.storageReference
                    .child(QR_CODES)
                    .child(queueID + "/" + queueID + ".jpg");
            local.getDownloadUrl().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onSuccess(new ImageDto(task.getResult()));
                }else {
                    emitter.onSuccess(new ImageDto(Uri.EMPTY));
                }
            });
        });
    }

    public Completable addToParticipantsList(String path) {
        DocumentReference docRef = service.fireStore.document(path);
        return Completable.create(emitter -> {
            docRef.update(QUEUE_PARTICIPANTS_LIST, FieldValue.arrayUnion(service.auth.getCurrentUser().getUid()))
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onComplete();
                        } else {
                            emitter.onError(new Throwable(task.getException()));
                        }
                    });
        });
    }

    public Single<ImageDto> getQrCodePdf(String queueId) {
        return Single.create(emitter -> {
            StorageReference local = service.storageReference.child(QR_CODES).child(queueId + "/" + "QR-CODE.pdf");
            local.getDownloadUrl().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onSuccess(new ImageDto(task.getResult()));
                } else {
                    emitter.onSuccess(new ImageDto(Uri.EMPTY));
                }
            });
        });
    }
}

