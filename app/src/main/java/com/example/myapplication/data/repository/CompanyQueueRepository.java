package com.example.myapplication.data.repository;

import static com.example.myapplication.DI.service;
import static com.example.myapplication.presentation.utils.Utils.CITY_KEY;
import static com.example.myapplication.presentation.utils.Utils.COMPANIES_QUEUES;
import static com.example.myapplication.presentation.utils.Utils.COMPANY;
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
import static com.example.myapplication.presentation.utils.Utils.WORKERS_COUNT;
import static com.example.myapplication.presentation.utils.Utils.WORKERS_LIST;

import android.service.autofill.SaveInfo;
import android.util.Log;

import com.example.myapplication.data.dto.CompanyQueueDto;
import com.example.myapplication.data.dto.EmployeeDto;
import com.example.myapplication.data.dto.WorkerDto;
import com.example.myapplication.presentation.companyQueue.models.EmployeeModel;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class CompanyQueueRepository {

    public void createCompanyQueueDocument(String queueID, String city, String disableTime, String queueName, String location, String companyId, List<EmployeeModel> workers) {
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

        docRef.set(companyQueue);

        for (int i = 0; i < workers.size(); i++) {
            DocumentReference workerDoc = docRef.collection(WORKERS_LIST).document(workers.get(i).getUserId());
            Map<String, Object> worker = new HashMap<>();
            worker.put(EMPLOYEE_NAME, workers.get(i).getName());
            workerDoc.set(worker);
        }
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

    public Completable finishQueue(String companyId, String queueId) {
        DocumentReference docRef = service.fireStore.collection(QUEUE_LIST).document(COMPANIES_QUEUES).collection(companyId).document(queueId);
        return Completable.create(emitter -> {
            docRef.delete().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onComplete();
                }
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

    public void updateInProgressUseCase(String queueId, String companyId, String name, int passed){
        DocumentReference docRef = service.fireStore.collection(QUEUE_LIST).document(COMPANIES_QUEUES).collection(companyId).document(queueId);
        docRef.update(QUEUE_IN_PROGRESS, name);
        docRef.update(PEOPLE_PASSED, String.valueOf(passed + 1));
    }

    public Single<List<WorkerDto>> getWorkersList(String companyId, String queueId) {
        return Single.create(emitter -> {
            service.fireStore.collection(QUEUE_LIST).document(COMPANIES_QUEUES).collection(companyId).document(queueId).collection(WORKERS_LIST)
                    .get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> documents = task.getResult().getDocuments();
                            if (documents.size() > 0) {
                                emitter.onSuccess(documents.stream().map(
                                        document -> new WorkerDto(
                                                document.getId(),
                                                document.getString(EMPLOYEE_NAME)
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

    public Completable deleteQueue(String companyId, String queueId){
        return Completable.create(emitter -> {
            service.fireStore.collection(QUEUE_LIST).document(COMPANIES_QUEUES).collection(companyId).document(queueId).delete()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            emitter.onComplete();
                        }
                    });
        });
    }
}
