package com.example.myapplication.data.repository;

import static com.example.myapplication.DI.service;
import static com.example.myapplication.presentation.utils.Utils.ACTIVE_QUEUES_LIST;
import static com.example.myapplication.presentation.utils.Utils.ADMIN;
import static com.example.myapplication.presentation.utils.Utils.CITY_KEY;
import static com.example.myapplication.presentation.utils.Utils.COMPANIES_QUEUES;
import static com.example.myapplication.presentation.utils.Utils.COMPANY;
import static com.example.myapplication.presentation.utils.Utils.EMPLOYEE;
import static com.example.myapplication.presentation.utils.Utils.EMPLOYEE_NAME;
import static com.example.myapplication.presentation.utils.Utils.MID_TIME_WAITING;
import static com.example.myapplication.presentation.utils.Utils.PEOPLE_PASSED;
import static com.example.myapplication.presentation.utils.Utils.QR_CODES;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_IN_PROGRESS;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_LIFE_TIME_KEY;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_LIST;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_LOCATION_KEY;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_NAME_KEY;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_PARTICIPANTS_LIST;
import static com.example.myapplication.presentation.utils.Utils.USER_LIST;
import static com.example.myapplication.presentation.utils.Utils.WORKERS_COUNT;
import static com.example.myapplication.presentation.utils.Utils.WORKERS_LIST;

import android.service.autofill.SaveInfo;
import android.util.Log;

import com.example.myapplication.data.dto.CompanyQueueDto;
import com.example.myapplication.data.dto.EmployeeDto;
import com.example.myapplication.data.dto.WorkerDto;
import com.example.myapplication.presentation.companyQueue.models.EmployeeModel;
import com.example.myapplication.presentation.companyQueue.queueDetails.editQueue.addWorkersFragment.model.AddWorkerModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableEmitter;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class CompanyQueueRepository {

    public Completable removeAdminFromAllQueuesAsWorker(String companyId, String adminId, String role) {
        if (role.equals(ADMIN)) {
            CollectionReference collRef = service.fireStore
                    .collection(QUEUE_LIST)
                    .document(COMPANIES_QUEUES)
                    .collection(companyId);

            return Completable.create(emitter -> {
                collRef.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<DocumentSnapshot> snapshots = task.getResult().getDocuments();
                        for (int i = 0; i < snapshots.size(); i++) {
                            DocumentSnapshot current = snapshots.get(i);
                            collRef.document(current.getId()).collection(WORKERS_LIST).document(adminId).delete();

                        }
                        emitter.onComplete();
                    }
                });
            });
        } else {
           return Completable.create(CompletableEmitter::onComplete);
        }
    }

    public Completable addListEmployeesToQueue(List<AddWorkerModel> list, String companyId, String queueId) {
        DocumentReference docRef = service.fireStore
                .collection(QUEUE_LIST)
                .document(COMPANIES_QUEUES)
                .collection(companyId)
                .document(queueId);

        return Completable.create(emitter -> {
            for (int i = 0; i < list.size(); i++) {
                DocumentReference workerDoc = docRef
                        .collection(WORKERS_LIST)
                        .document(list.get(i).getId());
                Map<String, Object> worker = new HashMap<>();
                worker.put(EMPLOYEE_NAME, list.get(i).getName());
                workerDoc.set(worker);

                DocumentReference userEmployeeDoc =
                        service.fireStore
                                .collection(USER_LIST)
                                .document(list.get(i).getId())
                                .collection(EMPLOYEE)
                                .document(companyId)
                                .collection(ACTIVE_QUEUES_LIST)
                                .document(queueId);

                Map<String, Object> activeQueue = new HashMap<>();
                activeQueue.put(QUEUE_NAME_KEY, "Test");
                activeQueue.put(QUEUE_LOCATION_KEY, "Test");
                userEmployeeDoc.set(activeQueue);
            }
            emitter.onComplete();
        });
    }

    public Completable deleteEmployeeFromQueue(String companyId, String queueId, String employeeId) {
        return Completable.create(emitter -> {
            DocumentReference docRef = service.fireStore
                    .collection(QUEUE_LIST)
                    .document(COMPANIES_QUEUES)
                    .collection(companyId)
                    .document(queueId);

            docRef.collection(WORKERS_LIST)
                    .document(employeeId)
                    .delete().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            docRef.get().addOnCompleteListener(taskGet -> {
                                if (taskGet.isSuccessful()) {
                                    int count = Integer.parseInt(Objects.requireNonNull(taskGet.getResult().getString(WORKERS_COUNT)));
                                    docRef.update(WORKERS_COUNT, String.valueOf(count - 1)).addOnCompleteListener(taskUpdate -> {
                                        if (taskUpdate.isSuccessful()) {
                                            emitter.onComplete();
                                        }
                                    });
                                }
                            });
                        }
                    });
        });
    }

    public Completable addEmployeeToQueue(String companyId, String queueId) {

        DocumentReference docRef = service.fireStore
                .collection(QUEUE_LIST)
                .document(COMPANIES_QUEUES)
                .collection(companyId)
                .document(queueId);

        return Completable.create(emitter -> {
            DocumentReference workerDoc = docRef
                    .collection(WORKERS_LIST)
                    .document(service.auth.getCurrentUser().getUid());
            Map<String, Object> worker = new HashMap<>();
            worker.put(EMPLOYEE_NAME, service.auth.getCurrentUser().getDisplayName());
            workerDoc.set(worker).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    docRef.get().addOnCompleteListener(taskGet -> {
                        if (taskGet.isSuccessful()) {
                            int count = Integer.parseInt(Objects.requireNonNull(taskGet.getResult().getString(WORKERS_COUNT)));
                            docRef.update(WORKERS_COUNT, String.valueOf(count + 1)).addOnCompleteListener(taskUpdate -> {
                                if (taskUpdate.isSuccessful()) {
                                    emitter.onComplete();
                                }
                            });
                        }
                    });
                }
            });
        });
    }

    public Completable createCompanyQueueDocument(String queueID, String city, String disableTime, String queueName, String location, String companyId, List<EmployeeModel> workers) {
        DocumentReference docRef = service.fireStore.collection(QUEUE_LIST).document(COMPANIES_QUEUES).collection(companyId).document(queueID);

        ArrayList<String> arrayList = new ArrayList<>();

        Map<String, Object> companyQueue = new HashMap<>();

        companyQueue.put(COMPANY, companyId);
        companyQueue.put(QUEUE_NAME_KEY, queueName);
        companyQueue.put(QUEUE_LIFE_TIME_KEY, disableTime);
        companyQueue.put(QUEUE_PARTICIPANTS_LIST, arrayList);
        companyQueue.put(QUEUE_IN_PROGRESS, "No one");
        companyQueue.put(PEOPLE_PASSED, "0");
        companyQueue.put(MID_TIME_WAITING, "0");
        companyQueue.put(CITY_KEY, city);
        companyQueue.put(QUEUE_LOCATION_KEY, location);
        companyQueue.put(WORKERS_COUNT, String.valueOf(workers.size()));

        return Completable.create(emitter -> {
            docRef.set(companyQueue).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (int i = 0; i < workers.size(); i++) {
                        DocumentReference workerDoc =
                                docRef.collection(WORKERS_LIST).document(workers.get(i).getUserId());

                        Map<String, Object> worker = new HashMap<>();
                        worker.put(EMPLOYEE_NAME, workers.get(i).getName());
                        workerDoc.set(worker);

                        DocumentReference userEmployeeDoc =
                                service.fireStore
                                        .collection(USER_LIST)
                                        .document(workers.get(i).getUserId())
                                        .collection(EMPLOYEE)
                                        .document(companyId)
                                        .collection(ACTIVE_QUEUES_LIST)
                                        .document(queueID);

                        Map<String, Object> activeQueue = new HashMap<>();
                        activeQueue.put(QUEUE_NAME_KEY, queueName);
                        activeQueue.put(QUEUE_LOCATION_KEY, location);
                        userEmployeeDoc.set(activeQueue);
                    }
                    emitter.onComplete();
                }
            });
        });
    }

    public Single<List<CompanyQueueDto>> getCompaniesQueues(String companyId) {
        return Single.create(emitter -> {
            service.fireStore.collection(QUEUE_LIST).document(COMPANIES_QUEUES).collection(companyId)
                    .get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> documents = task.getResult().getDocuments();
                            emitter.onSuccess(documents.stream().map(
                                    document -> new CompanyQueueDto(
                                            (List<Object>) document.get(QUEUE_PARTICIPANTS_LIST),
                                            document.getId(),
                                            document.getString(QUEUE_NAME_KEY),
                                            document.getString(QUEUE_LIFE_TIME_KEY),
                                            document.getString(COMPANY),
                                            document.getString(QUEUE_IN_PROGRESS),
                                            document.getString(PEOPLE_PASSED),
                                            document.getString(MID_TIME_WAITING),
                                            document.getString(QUEUE_LOCATION_KEY),
                                            document.getString(CITY_KEY),
                                            document.getString(WORKERS_COUNT))
                            ).collect(Collectors.toList()));
                        } else {
                            emitter.onSuccess(Collections.emptyList());
                        }
                    });
        });
    }

    public Single<CompanyQueueDto> getSingleCompanyQueue(String companyId, String queueId) {
        return Single.create(emitter -> {
            DocumentReference docRef = service.fireStore.collection(QUEUE_LIST).document(COMPANIES_QUEUES).collection(companyId).document(queueId);
            docRef.get().addOnCompleteListener(task -> {
                DocumentSnapshot document = task.getResult();
                emitter.onSuccess(new CompanyQueueDto(
                        (List<Object>) document.get(QUEUE_PARTICIPANTS_LIST),
                        document.getId(),
                        document.getString(QUEUE_NAME_KEY),
                        document.getString(QUEUE_LIFE_TIME_KEY),
                        document.getString(COMPANY),
                        document.getString(QUEUE_IN_PROGRESS),
                        document.getString(PEOPLE_PASSED),
                        document.getString(MID_TIME_WAITING),
                        document.getString(QUEUE_LOCATION_KEY),
                        document.getString(CITY_KEY),
                        document.getString(WORKERS_COUNT))
                );
            });

        });
    }

    public Observable<Integer> addParticipantsSizeDocumentSnapshot(String companyId, String queueId) {
        DocumentReference docRef = service.fireStore.collection(QUEUE_LIST).document(COMPANIES_QUEUES).collection(companyId).document(queueId);
        return Observable.create(emitter -> {
            docRef.addSnapshotListener((value, error) -> {
                List<String> newParticipants;
                if (value != null) {
                    newParticipants = Arrays.asList(value.get(QUEUE_PARTICIPANTS_LIST).toString().split(","));
                    if (newParticipants != null) {
                        emitter.onNext(newParticipants.size());
                    }
                }
            });
        });
    }

    public Completable nextCompanyQueueParticipantUpdateList(String queueId, String companyId, String name, int passed) {
        DocumentReference docRef = service.fireStore.collection(QUEUE_LIST).document(COMPANIES_QUEUES).collection(companyId).document(queueId);
        return Completable.create(emitter -> {
            docRef.update(QUEUE_PARTICIPANTS_LIST, FieldValue.arrayRemove(name))
                    .addOnCompleteListener(task -> {
                        updateInProgressUseCase(queueId, companyId, name, passed);
                        emitter.onComplete();
                    });
        });
    }

    public void updateInProgressUseCase(String queueId, String companyId, String name, int passed) {
        DocumentReference docRef = service.fireStore.collection(QUEUE_LIST).document(COMPANIES_QUEUES).collection(companyId).document(queueId);
        docRef.update(QUEUE_IN_PROGRESS, name);
        docRef.update(PEOPLE_PASSED, String.valueOf(passed + 1));
    }

    public Completable deleteEmployeeFromAllQueues(String companyId, String employeeId) {
        CollectionReference collRef = service.fireStore.collection(QUEUE_LIST).document(COMPANIES_QUEUES).collection(companyId);
        return Completable.create(emitter -> {
            collRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    List<DocumentSnapshot> documents = task.getResult().getDocuments();
                    for (int i = 0; i < documents.size(); i++) {
                        DocumentSnapshot current = documents.get(i);
                        collRef.document(current.getId())
                                .collection(WORKERS_LIST)
                                .document(employeeId).delete();
                    }
                    emitter.onComplete();
                }
            });
        });
    }

    public Single<List<WorkerDto>> getWorkersList(String companyId, String queueId) {
        return Single.create(emitter -> {
            service.fireStore
                    .collection(QUEUE_LIST)
                    .document(COMPANIES_QUEUES)
                    .collection(companyId)
                    .document(queueId)
                    .collection(WORKERS_LIST)
                    .get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> documents = task.getResult().getDocuments();
                            if (documents.size() > 0) {
                                emitter.onSuccess(documents.stream().map(
                                        document -> new WorkerDto(
                                                document.getString(EMPLOYEE_NAME),
                                                document.getId()
                                        )
                                ).collect(Collectors.toList()));
                            } else {
                                emitter.onSuccess(new ArrayList<>());
                            }
                        }
                    });
        });
    }

    public Completable updateQueueData(String companyId, String queueId, String name, String location) {
        return Completable.create(emitter -> {
            DocumentReference docRef = service.fireStore.collection(QUEUE_LIST).document(COMPANIES_QUEUES).collection(companyId).document(queueId);
            docRef.update(
                    QUEUE_NAME_KEY, name,
                    QUEUE_LOCATION_KEY, location
            ).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onComplete();
                }
            });
        });
    }

    public Completable deleteQueue(String companyId, String queueId) {
        return Completable.create(emitter -> {
            service.fireStore.collection(QUEUE_LIST).document(COMPANIES_QUEUES).collection(companyId).document(queueId).delete()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onComplete();
                        }
                    });
        });
    }
}
