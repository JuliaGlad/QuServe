package com.example.myapplication.data.repository.companyQueue;

import static com.example.myapplication.di.DI.service;
import static com.example.myapplication.presentation.utils.Utils.ACTIVE_QUEUES_COUNT;
import static com.example.myapplication.presentation.utils.Utils.ACTIVE_QUEUES_LIST;
import static com.example.myapplication.presentation.utils.Utils.ADMIN;
import static com.example.myapplication.presentation.utils.Utils.CITY_KEY;
import static com.example.myapplication.presentation.utils.Utils.COMPANIES_QUEUES;
import static com.example.myapplication.presentation.utils.Utils.COMPANY;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_EMPLOYEE;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_LIST;
import static com.example.myapplication.presentation.utils.Utils.EMPLOYEES;
import static com.example.myapplication.presentation.utils.Utils.EMPLOYEE_NAME;
import static com.example.myapplication.presentation.utils.Utils.HOURS_DIVIDER;
import static com.example.myapplication.presentation.utils.Utils.MID_TIME_WAITING;
import static com.example.myapplication.presentation.utils.Utils.MINUTES_DIVIDER;
import static com.example.myapplication.presentation.utils.Utils.NOT_PARTICIPATE_IN_QUEUE;
import static com.example.myapplication.presentation.utils.Utils.PARTICIPATE_IN_QUEUE;
import static com.example.myapplication.presentation.utils.Utils.PAUSED;
import static com.example.myapplication.presentation.utils.Utils.PEOPLE_PASSED;
import static com.example.myapplication.presentation.utils.Utils.PEOPLE_PASSED_15;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_IN_PROGRESS;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_LIFE_TIME_KEY;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_LIST;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_LOCATION_KEY;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_NAME_KEY;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_PARTICIPANTS_LIST;
import static com.example.myapplication.presentation.utils.Utils.SECONDS_DIVIDER;
import static com.example.myapplication.presentation.utils.Utils.USER_LIST;
import static com.example.myapplication.presentation.utils.Utils.WORKERS_COUNT;
import static com.example.myapplication.presentation.utils.Utils.WORKERS_LIST;

import com.example.myapplication.data.dto.company.CompanyQueueDto;
import com.example.myapplication.data.dto.company.WorkerDto;
import com.example.myapplication.presentation.companyQueue.models.EmployeeModel;
import com.example.myapplication.presentation.companyQueue.queueDetails.editQueue.addWorkersFragment.model.AddWorkerModel;
import com.example.myapplication.presentation.employee.main.ActiveQueueModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableEmitter;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class CompanyQueueRepository {

    public Completable continueCompanyQueue(String queueId, String companyId) {
        DocumentReference docRef = service.fireStore
                .collection(QUEUE_LIST)
                .document(COMPANIES_QUEUES)
                .collection(companyId)
                .document(queueId);

        return Completable.create(emitter -> {
            docRef.update(QUEUE_IN_PROGRESS, "").addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onComplete();
                } else {
                    emitter.onError(new Throwable(task.getException()));
                }
            });
        });
    }

    public Completable pauseQueue(String queueId, String companyId, int hours, int minutes, int seconds) {
        DocumentReference docRef = service.fireStore
                .collection(QUEUE_LIST)
                .document(COMPANIES_QUEUES)
                .collection(companyId)
                .document(queueId);

        return Completable.create(emitter -> {
            docRef.update(QUEUE_IN_PROGRESS, hours + HOURS_DIVIDER + minutes + MINUTES_DIVIDER + seconds + SECONDS_DIVIDER + "_" + PAUSED).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onComplete();
                } else {
                    emitter.onError(new Throwable(task.getException()));
                }
            });
        });
    }

    public Completable addEmployeeToListQueues(List<ActiveQueueModel> queues, String companyId, String employeeId, String employeeName) {
        return Completable.create(emitter -> {

            CollectionReference collRef = service.fireStore
                    .collection(QUEUE_LIST)
                    .document(COMPANIES_QUEUES)
                    .collection(companyId);

            for (int i = 0; i < queues.size(); i++) {
                ActiveQueueModel current = queues.get(i);
                DocumentReference docRef = collRef.document(current.getId())
                        .collection(WORKERS_LIST)
                        .document(employeeId);

                Map<String, Object> employee = new HashMap<>();
                employee.put(EMPLOYEE_NAME, employeeName);

                docRef.set(employee);

                DocumentReference userDoc =
                        service.fireStore
                                .collection(USER_LIST)
                                .document(employeeId)
                                .collection(COMPANY_EMPLOYEE)
                                .document(current.getId());

                Map<String, Object> userRole = new HashMap<>();
                userRole.put(QUEUE_LOCATION_KEY, current.getLocation());
                userRole.put(QUEUE_NAME_KEY, current.getName());

                userDoc.set(userRole);

                DocumentReference companyEmployeeName = service.fireStore
                        .collection(COMPANY_LIST)
                        .document(companyId)
                        .collection(EMPLOYEES)
                        .document(employeeId);

                companyEmployeeName.get().addOnCompleteListener(taskGetEmployee -> {
                    if (taskGetEmployee.isSuccessful()) {
                        int activeQueues = Integer.parseInt(taskGetEmployee.getResult().getString(ACTIVE_QUEUES_COUNT));
                        companyEmployeeName.update(ACTIVE_QUEUES_COUNT, String.valueOf(activeQueues + 1));
                    } else {
                        emitter.onError(new Throwable(taskGetEmployee.getException()));
                    }
                });
            }
            emitter.onComplete();
        });
    }

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
                    } else {
                        emitter.onError(new Throwable(task.getException()));
                    }
                });
            });
        } else {
            return Completable.create(CompletableEmitter::onComplete);
        }
    }

    public Completable addListEmployeesToQueue(List<AddWorkerModel> employee, String companyId, String queueId, String queueName, String location) {
        DocumentReference docRef = service.fireStore
                .collection(QUEUE_LIST)
                .document(COMPANIES_QUEUES)
                .collection(companyId)
                .document(queueId);

        return Completable.create(emitter -> {
            for (int i = 0; i < employee.size(); i++) {

                AddWorkerModel current = employee.get(i);

                setWorkerToQueue(docRef, current);
                setActiveQueueToEmployee(companyId, queueId, queueName, location, current);

                DocumentReference companyEmployeeName = service.fireStore
                        .collection(COMPANY_LIST)
                        .document(companyId)
                        .collection(EMPLOYEES)
                        .document(current.getId());

                companyEmployeeName.get().addOnCompleteListener(taskGetEmployee -> {
                    if (taskGetEmployee.isSuccessful()) {
                        int activeQueues = Integer.parseInt(taskGetEmployee.getResult().getString(ACTIVE_QUEUES_COUNT));
                        companyEmployeeName.update(ACTIVE_QUEUES_COUNT, String.valueOf(activeQueues + 1)).addOnCompleteListener(taskActiveUpdate -> {
                            emitter.onComplete();
                        });
                    } else {
                        emitter.onError(new Throwable(taskGetEmployee.getException()));
                    }
                });
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
                                getEmployeeData(companyId, employeeId, emitter, taskGet, docRef);
                            });
                        } else {
                            emitter.onError(new Throwable(task.getException()));
                        }
                    });
        });
    }

    public Single<String> createCompanyQueueDocument(String queueID, String city, String disableTime, String queueName, String location, String companyId, List<EmployeeModel> workers) {
        DocumentReference docRef = service.fireStore.collection(QUEUE_LIST).document(COMPANIES_QUEUES).collection(companyId).document(queueID);
        String path = docRef.getPath();
        ArrayList<String> arrayList = new ArrayList<>();

        Map<String, Object> companyQueue = new HashMap<>();

        initCompanyQueue(city, disableTime, queueName, location, companyId, workers, companyQueue, arrayList);

        return Single.create(emitter -> {
            docRef.set(companyQueue).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (int i = 0; i < workers.size(); i++) {
                        setWorkerQueueCreation(workers, docRef, i);
                        setWorkerActiveQueue(queueID, queueName, location, companyId, workers, i);
                    }
                    emitter.onSuccess(path);
                } else {
                    emitter.onError(new Throwable(task.getException()));
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
                                            document.getString(PEOPLE_PASSED_15),
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
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            emitter.onSuccess(new CompanyQueueDto(
                                    (List<Object>) document.get(QUEUE_PARTICIPANTS_LIST),
                                    document.getId(),
                                    document.getString(QUEUE_NAME_KEY),
                                    document.getString(QUEUE_LIFE_TIME_KEY),
                                    document.getString(COMPANY),
                                    document.getString(QUEUE_IN_PROGRESS),
                                    document.getString(PEOPLE_PASSED),
                                    document.getString(PEOPLE_PASSED_15),
                                    document.getString(MID_TIME_WAITING),
                                    document.getString(QUEUE_LOCATION_KEY),
                                    document.getString(CITY_KEY),
                                    document.getString(WORKERS_COUNT))
                            );
                        } else {
                            emitter.onError(new Throwable(task.getException()));
                        }
                    }
            );

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
                } else {
                    emitter.onError(new Throwable("Value is null"));
                }
            });
        });
    }

    public Completable nextCompanyQueueParticipantUpdateList(String queueId, String companyId, String name, int passed) {
        DocumentReference docRef = service.fireStore.collection(QUEUE_LIST).document(COMPANIES_QUEUES).collection(companyId).document(queueId);
        return Completable.create(emitter -> {
            docRef.update(QUEUE_PARTICIPANTS_LIST, FieldValue.arrayRemove(name))
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            updateInProgressUseCase(queueId, companyId, name, passed).addOnCompleteListener(taskUpdate -> {
                                if (taskUpdate.isSuccessful()){
                                    emitter.onComplete();
                                } else {
                                    emitter.onError(new Throwable(taskUpdate.getException()));
                                }
                            });
                        } else {
                            emitter.onError(new Throwable(task.getException()));
                        }
                    });
        });
    }

    public Completable updateMidTime(String queueId, String companyId, int previous, int passed15) {
        return Completable.create(emitter -> {
            if (passed15 != 0) {
                DocumentReference docRef = service.fireStore
                        .collection(QUEUE_LIST)
                        .document(COMPANIES_QUEUES)
                        .collection(companyId)
                        .document(queueId);

                int midTimeLast15Minutes = 15 / passed15;
                int newMid;
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
                                emitter.onError(new Throwable(task.getException()));
                            }
                        });
            } else {
                emitter.onComplete();
            }
        });

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
                } else {
                    emitter.onError(new Throwable(task.getException()));
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
                            if (!documents.isEmpty()) {
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
                } else {
                    emitter.onError(new Throwable(task.getException()));
                }
            });
        });
    }

    public Completable deleteQueue(String companyId, String queueId) {
        return Completable.create(emitter -> {
            DocumentReference docRef = service.fireStore
                    .collection(QUEUE_LIST)
                    .document(COMPANIES_QUEUES)
                    .collection(companyId)
                    .document(queueId);

            deleteQueueFromWorkersList(companyId, queueId, emitter, docRef);
            updateParticipantsState(emitter, docRef);
            deleteQueueDocument(emitter, docRef);
        });
    }

    private void deleteQueueDocument(CompletableEmitter emitter, DocumentReference docRef) {
        docRef.delete().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                emitter.onComplete();
            } else {
                emitter.onError(new Throwable(task.getException()));
            }
        });
    }

    private void updateParticipantsState(CompletableEmitter emitter, DocumentReference docRef) {
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<String> participants = (List<String>) task.getResult().get(QUEUE_PARTICIPANTS_LIST);
                assert participants != null;
                for (String participant : participants) {
                    service.fireStore
                            .collection(USER_LIST)
                            .document(participant)
                            .update(PARTICIPATE_IN_QUEUE, NOT_PARTICIPATE_IN_QUEUE);
                }
            } else {
                emitter.onError(new Throwable(task.getException()));
            }
        });
    }

    private void deleteQueueFromWorkersList(String companyId, String queueId, CompletableEmitter emitter, DocumentReference docRef) {
        docRef.collection(WORKERS_LIST).get().addOnCompleteListener(taskWorkers -> {
            if (taskWorkers.isSuccessful()) {
                List<DocumentSnapshot> documentSnapshots = taskWorkers.getResult().getDocuments();
                for (int i = 0; i < documentSnapshots.size(); i++) {
                    service.fireStore
                            .collection(USER_LIST)
                            .document(documentSnapshots.get(i).getId())
                            .collection(EMPLOYEES)
                            .document(companyId)
                            .collection(ACTIVE_QUEUES_LIST)
                            .document(queueId)
                            .delete();
                }
            } else {
                emitter.onError(new Throwable(taskWorkers.getException()));
            }
        });
    }

    private void getEmployeeData(String companyId, String employeeId, CompletableEmitter emitter, Task<DocumentSnapshot> taskGet, DocumentReference docRef) {
        if (taskGet.isSuccessful()) {
            int count = Integer.parseInt(Objects.requireNonNull(taskGet.getResult().getString(WORKERS_COUNT)));
            updateWorkersCount(companyId, employeeId, emitter, docRef, count);
        } else {
            emitter.onError(new Throwable(taskGet.getException()));
        }
    }

    private void updateWorkersCount(String companyId, String employeeId, CompletableEmitter emitter, DocumentReference docRef, int count) {
        docRef.update(WORKERS_COUNT, String.valueOf(count - 1)).addOnCompleteListener(taskUpdate -> {
            if (taskUpdate.isSuccessful()) {
                updateActiveQueuesCount(companyId, employeeId, emitter);

            } else {
                emitter.onError(new Throwable(taskUpdate.getException()));
            }
        });
    }

    private void updateActiveQueuesCount(String companyId, String employeeId, CompletableEmitter emitter) {
        DocumentReference companyEmployeeName = service.fireStore
                .collection(COMPANY_LIST)
                .document(companyId)
                .collection(EMPLOYEES)
                .document(employeeId);

        companyEmployeeName.get().addOnCompleteListener(taskGetEmployee -> {
            if (taskGetEmployee.isSuccessful()) {
                int activeQueues = Integer.parseInt(taskGetEmployee.getResult().getString(ACTIVE_QUEUES_COUNT));
                companyEmployeeName.update(ACTIVE_QUEUES_COUNT, String.valueOf(activeQueues - 1))
                        .addOnCompleteListener(taskActiveUpdate -> {
                            if (taskActiveUpdate.isSuccessful()) {
                                emitter.onComplete();
                            } else {
                                emitter.onError(new Throwable(taskActiveUpdate.getException()));
                            }
                        });
            }
        });
    }

    private void setActiveQueueToEmployee(String companyId, String queueId, String queueName, String location, AddWorkerModel current) {
        DocumentReference userEmployeeDoc =
                service.fireStore
                        .collection(USER_LIST)
                        .document(current.getId())
                        .collection(COMPANY_EMPLOYEE)
                        .document(companyId)
                        .collection(ACTIVE_QUEUES_LIST)
                        .document(queueId);

        Map<String, Object> activeQueue = new HashMap<>();
        activeQueue.put(QUEUE_NAME_KEY, queueName);
        activeQueue.put(QUEUE_LOCATION_KEY, location);
        userEmployeeDoc.set(activeQueue);
    }

    private void setWorkerToQueue(DocumentReference docRef, AddWorkerModel current) {
        DocumentReference workerDoc = docRef
                .collection(WORKERS_LIST)
                .document(current.getId());
        Map<String, Object> worker = new HashMap<>();
        worker.put(EMPLOYEE_NAME, current.getName());
        workerDoc.set(worker);
    }

    private void setWorkerActiveQueue(String queueID, String queueName, String location, String companyId, List<EmployeeModel> workers, int i) {
        DocumentReference userEmployeeDoc =
                service.fireStore
                        .collection(USER_LIST)
                        .document(workers.get(i).getUserId())
                        .collection(COMPANY_EMPLOYEE)
                        .document(companyId)
                        .collection(ACTIVE_QUEUES_LIST)
                        .document(queueID);

        Map<String, Object> activeQueue = new HashMap<>();
        activeQueue.put(QUEUE_NAME_KEY, queueName);
        activeQueue.put(QUEUE_LOCATION_KEY, location);
        userEmployeeDoc.set(activeQueue);
    }

    private void setWorkerQueueCreation(List<EmployeeModel> workers, DocumentReference docRef, int i) {
        DocumentReference workerDoc =
                docRef.collection(WORKERS_LIST).document(workers.get(i).getUserId());

        Map<String, Object> worker = new HashMap<>();
        worker.put(EMPLOYEE_NAME, workers.get(i).getName());
        workerDoc.set(worker);
    }

    private void initCompanyQueue(String city, String disableTime, String queueName, String location, String companyId, List<EmployeeModel> workers, Map<String, Object> companyQueue, ArrayList<String> arrayList) {
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
    }

    private Task<Void> updateInProgressUseCase(String queueId, String companyId, String name, int passed) {
        DocumentReference docRef = service.fireStore.collection(QUEUE_LIST).document(COMPANIES_QUEUES).collection(companyId).document(queueId);
        return docRef.update(QUEUE_IN_PROGRESS, name).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                docRef.update(PEOPLE_PASSED, String.valueOf(passed + 1));
            }
        });
    }
}
