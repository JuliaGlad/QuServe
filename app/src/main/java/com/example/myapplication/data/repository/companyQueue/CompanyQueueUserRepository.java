package com.example.myapplication.data.repository.companyQueue;

import static com.example.myapplication.di.DI.service;
import static com.example.myapplication.presentation.utils.Utils.ACTIVE_QUEUES_COUNT;
import static com.example.myapplication.presentation.utils.Utils.ADMIN;
import static com.example.myapplication.presentation.utils.Utils.APPROVED;
import static com.example.myapplication.presentation.utils.Utils.COMPANIES;
import static com.example.myapplication.presentation.utils.Utils.COMPANIES_QUEUES;
import static com.example.myapplication.presentation.utils.Utils.COMPANY;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_EMAIL;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_EMPLOYEE;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_LIST;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_LOGO;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_NAME;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_OWNER;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_PHONE;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_SERVICE;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_STATE;
import static com.example.myapplication.presentation.utils.Utils.CREATED;
import static com.example.myapplication.presentation.utils.Utils.EMPLOYEES;
import static com.example.myapplication.presentation.utils.Utils.EMPLOYEE_NAME;
import static com.example.myapplication.presentation.utils.Utils.EMPLOYEE_ROLE;
import static com.example.myapplication.presentation.utils.Utils.NOT_PARTICIPATE_IN_QUEUE;
import static com.example.myapplication.presentation.utils.Utils.PARTICIPATE_IN_QUEUE;
import static com.example.myapplication.presentation.utils.Utils.PROFILE_IMAGES;
import static com.example.myapplication.presentation.utils.Utils.PROFILE_PHOTO;
import static com.example.myapplication.presentation.utils.Utils.PROFILE_UPDATED_AT;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_LIST;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_PARTICIPANTS_LIST;
import static com.example.myapplication.presentation.utils.Utils.URI;
import static com.example.myapplication.presentation.utils.Utils.USER_LIST;
import static com.example.myapplication.presentation.utils.Utils.WORKER;

import android.net.Uri;
import android.util.Log;

import com.example.myapplication.data.dto.company.CompanyDto;
import com.example.myapplication.data.dto.common.EmployeeDto;
import com.example.myapplication.data.dto.common.ImageDto;
import com.example.myapplication.data.dto.user.UserDto;
import com.example.myapplication.data.providers.CompanyUserProvider;
import com.example.myapplication.data.providers.UserDatabaseProvider;
import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableEmitter;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CompanyQueueUserRepository {

    public Observable<DocumentSnapshot> addSnapshot(String companyId) {
        DocumentReference docRef = service.fireStore
                .collection(COMPANY_LIST)
                .document(companyId);

        return Observable.create(emitter -> {
            docRef.addSnapshotListener((value, error) -> {
                if (value != null) {
                    emitter.onNext(value);
                } else {
                    emitter.onError(new Throwable("Value is null"));
                }
            });
        });
    }

    public Observable<DocumentSnapshot> addEmployeeSnapshot(String companyId, String employeeId) {
        DocumentReference docRef = service.fireStore
                .collection(COMPANY_LIST)
                .document(companyId)
                .collection(EMPLOYEES)
                .document(employeeId);

        return Observable.create(emitter -> {
            docRef.addSnapshotListener((value, error) -> {
                if (value != null) {
                    emitter.onNext(value);
                } else {
                    emitter.onError(new Throwable("Value is null"));
                }
            });
        });
    }

    public Completable updateRole(String role, String employeeId, String companyId) {
        DocumentReference docRef = service.fireStore
                .collection(COMPANY_LIST)
                .document(companyId)
                .collection(EMPLOYEES)
                .document(employeeId);

        return Completable.create(emitter -> {
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    String previous = task.getResult().getString(EMPLOYEE_ROLE);
                    if (!Objects.equals(role, previous)) {
                        docRef.update(EMPLOYEE_ROLE, role)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        emitter.onComplete();
                                    }
                                });
                    }
                } else {
                    emitter.onError(new Throwable(task.getException()));
                }
            });
        });

    }

    public Single<List<CompanyDto>> getCompanyQueue() {
        List<CompanyDto> local = CompanyUserProvider.getCompanies();
        if (local != null) {
            return Single.create(emitter -> {
                emitter.onSuccess(local);
            });
        } else {
            return
                    Single.create(emitter -> {
                        service.fireStore
                                .collection(COMPANY_LIST)
                                .get().addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        List<DocumentSnapshot> documents = task.getResult().getDocuments();
                                        List<CompanyDto> dtoList = documents.stream()
                                                .map(document -> new CompanyDto(
                                                        document.getId(),
                                                        document.getString(COMPANY_OWNER),
                                                        document.getString(URI),
                                                        document.getString(COMPANY_NAME),
                                                        document.getString(COMPANY_EMAIL),
                                                        document.getString(COMPANY_PHONE),
                                                        document.getString(COMPANY_SERVICE))
                                                ).collect(Collectors.toList());
                                        CompanyUserProvider.insertAllCompanies(dtoList);
                                        emitter.onSuccess(dtoList);
                                    } else {
                                        emitter.onSuccess(Collections.emptyList());
                                    }
                                });
                    });
        }
    }

    public Completable uploadCompanyQueueBytesToFireStorage(String companyId, byte[] data) {
        StorageReference reference = service.storageReference
                .child(COMPANIES)
                .child(companyId + "/" + "EMPLOYEES_QR-CODE.jpg");

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

    public void updateApproved(String companyId) {
        DocumentReference docRef = service.fireStore
                .collection(COMPANY_LIST)
                .document(companyId);

        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                docRef.update(COMPANY_STATE, APPROVED);
            }
        });
    }

    public Completable createCompanyQueueDocument(String companyID, String name, String email, String
            phone, String companyService) {
        DocumentReference docRef = service.fireStore
                .collection(COMPANY_LIST)
                .document(companyID);

        Map<String, Object> company = new HashMap<>();

        initCompany(name, email, phone, companyService, company);

        return Completable.create(emitter -> {
            docRef.set(company).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    seCompanyUser(companyID, name, email, phone, companyService, emitter, task);
                } else {
                    emitter.onError(new Throwable(task.getException()));
                }
            });
        });
    }

    public Single<List<EmployeeDto>> getAdmins(String companyId) {
        return Single.create(emitter -> {
            service.fireStore
                    .collection(COMPANY_LIST)
                    .document(companyId)
                    .collection(EMPLOYEES)
                    .get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> documents = task.getResult().getDocuments();
                            emitter.onSuccess(
                                    documents.stream()
                                            .filter(documentSnapshot -> documentSnapshot.getString(EMPLOYEE_ROLE).equals(ADMIN))
                                            .map(document -> new EmployeeDto(
                                                    document.getString(EMPLOYEE_NAME),
                                                    document.getId(),
                                                    document.getString(EMPLOYEE_ROLE),
                                                    document.getString(ACTIVE_QUEUES_COUNT)
                                            )).collect(Collectors.toList())
                            );
                        } else {
                            emitter.onError(new Throwable(task.getException()));
                        }
                    });
        });
    }

    public Single<List<EmployeeDto>> getEmployees(String companyId) {
        return Single.create(emitter -> {
            service.fireStore
                    .collection(COMPANY_LIST)
                    .document(companyId)
                    .collection(EMPLOYEES)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> documents = task.getResult().getDocuments();
                            emitter.onSuccess(
                                    documents.stream().map(document -> new EmployeeDto(
                                            document.getString(EMPLOYEE_NAME),
                                            document.getId(),
                                            document.getString(EMPLOYEE_ROLE),
                                            document.getString(ACTIVE_QUEUES_COUNT)
                                    )).collect(Collectors.toList()));
                        } else {
                            emitter.onError(new Throwable(task.getException()));
                        }
                    });
        });
    }

    public Single<Boolean> checkCompanyQueue() {
        return Single.create(emitter -> {
            service.fireStore
                    .collection(COMPANY_LIST)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> snapshots = task.getResult().getDocuments();
                            for (int i = 0; i < snapshots.size(); i++) {
                                if (Objects.equals(snapshots.get(i).getString(COMPANY_OWNER), service.auth.getCurrentUser().getUid())) {
                                    emitter.onSuccess(true);
                                }
                            }
                            emitter.onSuccess(false);
                        } else {
                            emitter.onError(new Throwable(task.getException()));
                        }
                    });
        });
    }

    public Single<ImageDto> getEmployeeQrCode(String companyID) {
        return Single.create(emitter -> {
            StorageReference local = service.storageReference.child(COMPANIES).child(companyID + "/").child("EMPLOYEES_QR-CODE.jpg");
            local.getDownloadUrl().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onSuccess(new ImageDto(task.getResult()));
                } else {
                    emitter.onError(new Throwable(task.getException()));
                }
            });
        });
    }

    public Completable addEmployee(String companyId, String name) {
        return Completable.create(emitter -> {
            DocumentReference docRef = service.fireStore
                    .collection(COMPANY_LIST)
                    .document(companyId);

            DocumentReference employeeRef = docRef
                    .collection(EMPLOYEES)
                    .document(service.auth.getCurrentUser().getUid());

            Map<String, Object> employee = new HashMap<>();

            employee.put(EMPLOYEE_NAME, name);
            employee.put(EMPLOYEE_ROLE, WORKER);
            employee.put(ACTIVE_QUEUES_COUNT, "0");

            employeeRef.set(employee).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onComplete();
                } else {
                    emitter.onError(new Throwable(task.getException()));
                }
            });
        });

    }

    public Single<String> deleteEmployeeFromQueueCompany(String userId, String companyId) {
        return Single.create(emitter -> {
            DocumentReference employeeRef = service.fireStore
                    .collection(COMPANY_LIST)
                    .document(companyId)
                    .collection(EMPLOYEES)
                    .document(userId);

            employeeRef.get().addOnCompleteListener(task -> {
                String role;
                if (task.isSuccessful()) {
                    role = task.getResult().getString(EMPLOYEE_ROLE);
                    employeeRef.delete().addOnCompleteListener(taskDelete -> {
                        emitter.onSuccess(role);
                    });
                } else {
                    emitter.onError(new Throwable(task.getException()));
                }
            });
        });
    }

    public Completable uploadQueueCompanyLogoToFireStorage(Uri uri, String companyId) {
        return Completable.create(emitter -> {
            if (uri != Uri.EMPTY) {
                StorageReference reference = service.storageReference.child(COMPANIES).child(companyId + "/").child(COMPANY_LOGO);
                reference.putFile(uri)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                DocumentReference docRef = service.fireStore
                                        .collection(COMPANY_LIST)
                                        .document(companyId);

                                docRef.update(URI, String.valueOf(uri)).addOnCompleteListener(task1 -> {
                                    CompanyUserProvider.updateUri(companyId, String.valueOf(uri));
                                    emitter.onComplete();
                                });
                            } else {
                                emitter.onError(new Throwable(task.getException()));
                            }
                        });
            } else {
                emitter.onComplete();
            }
        });

    }

    public Single<ImageDto> getSingleEmployeePhoto(String employeeId) {
        return Single.create(emitter -> {
            StorageReference local = service.storageReference.child(PROFILE_IMAGES).child(PROFILE_PHOTO + employeeId);
            try {
                local.getDownloadUrl().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Uri uri = task.getResult();
                        emitter.onSuccess(new ImageDto(uri));
                    } else {
                        emitter.onSuccess(new ImageDto(Uri.EMPTY));
                    }
                });
            } catch (RuntimeExecutionException e) {
                emitter.onSuccess(new ImageDto(Uri.EMPTY));
            }
        });

    }

    public Single<List<Task<Uri>>> getQueueCompaniesLogos() {
        return Single.create(emitter -> {
            List<Task<Uri>> listTask = new ArrayList<>();
            getCompanyQueue()
                    .subscribeOn(Schedulers.io())
                    .subscribe(new SingleObserver<List<CompanyDto>>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onSuccess(@NonNull List<CompanyDto> companyDtos) {
                            for (int i = 0; i < companyDtos.size(); i++) {
                                StorageReference reference = service.storageReference.child(COMPANIES).child(companyDtos.get(i).getId() + "/").child(COMPANY_LOGO);
                                listTask.add(reference.getDownloadUrl());
                            }
                            emitter.onSuccess(listTask);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            emitter.onError(new Throwable(e.getMessage()));
                        }
                    });
        });
    }

    public Single<ImageDto> getQueueCompanyLogo(String companyId) {
        return Single.create(emitter -> {

            StorageReference reference = service.storageReference.child(COMPANIES).child(companyId + "/").child(COMPANY_LOGO);
            try {
                reference.getDownloadUrl().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        emitter.onSuccess(new ImageDto(task.getResult()));
                    } else {
                        emitter.onSuccess(new ImageDto(Uri.EMPTY));
                    }
                });
            } catch (RuntimeExecutionException e) {
                emitter.onSuccess(new ImageDto(Uri.EMPTY));
            }
        });
    }

    public Completable updateQueueCompanyData(String companyId, String companyName, String phone) {
        return Completable.create(emitter -> {
            DocumentReference docRef = service.fireStore
                    .collection(COMPANY_LIST)
                    .document(companyId);

            FieldValue timestamp = FieldValue.serverTimestamp();
            docRef.update(
                    COMPANY_NAME, companyName,
                    COMPANY_PHONE, phone,
                    PROFILE_UPDATED_AT, timestamp
            ).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    CompanyUserProvider.updateCompany(companyId, companyName, phone);
                    emitter.onComplete();
                } else {
                    emitter.onError(new Throwable(task.getException()));
                }
            });
        });
    }

    public Completable deleteQueueCompany(String companyId) {
        return Completable.create(emitter -> {
            DocumentReference docRef = service.fireStore
                    .collection(COMPANY_LIST)
                    .document(companyId);

            docRef.collection(EMPLOYEES).get().addOnCompleteListener(taskGet -> {
                if (taskGet.isSuccessful()) {
                    List<DocumentSnapshot> snapshots = taskGet.getResult().getDocuments();
                    for (DocumentSnapshot current : snapshots) {
                        String currentId = current.getId();
                        service.fireStore
                                .collection(USER_LIST)
                                .document(currentId)
                                .collection(COMPANY_EMPLOYEE)
                                .document(companyId)
                                .delete();
                    }

                } else {
                    emitter.onError(new Throwable(taskGet.getException()));
                }
            });

            service.fireStore.collection(QUEUE_LIST).document(COMPANIES_QUEUES).collection(companyId)
                    .get().addOnCompleteListener(taskQueues -> {
                        if (taskQueues.isSuccessful()) {
                            List<DocumentSnapshot> snapshots = taskQueues.getResult().getDocuments();
                            for (DocumentSnapshot current : snapshots) {
                                List<String> participants = (List<String>) current.get(QUEUE_PARTICIPANTS_LIST);
                                assert participants != null;
                                for (String participant : participants) {
                                    service.fireStore
                                            .collection(USER_LIST)
                                            .document(participant)
                                            .update(PARTICIPATE_IN_QUEUE, NOT_PARTICIPATE_IN_QUEUE);
                                }
                            }
                        }
                    });

            docRef.delete().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    CompanyUserProvider.deleteCompany(companyId);
                    emitter.onComplete();
                }
            });
        });
    }

    private void seCompanyUser(String companyID, String name, String email, String phone, String companyService, CompletableEmitter emitter, Task<Void> task) {
        DocumentReference userCompany = service.fireStore
                .collection(USER_LIST)
                .document(service.auth.getCurrentUser().getUid())
                .collection(COMPANY)
                .document(companyID);

        Map<String, Object> companyUser = new HashMap<>();
        companyUser.put(COMPANY_SERVICE, companyService);
        companyUser.put(COMPANY_NAME, name);

        userCompany.set(companyUser).addOnCompleteListener(taskUser -> {
            if (taskUser.isSuccessful()) {
                CompanyUserProvider.insertCompany(new CompanyDto(
                        companyID,
                        service.auth.getCurrentUser().getUid(),
                        String.valueOf(Uri.EMPTY),
                        name, email,
                        phone, companyService)
                );
                emitter.onComplete();
            } else {
                emitter.onError(new Throwable(task.getException()));
            }
        });
    }

    private void initCompany(String name, String email, String phone, String companyService, Map<String, Object> company) {
        company.put(COMPANY_OWNER, service.auth.getCurrentUser().getUid());
        company.put(COMPANY_NAME, name);
        company.put(COMPANY_EMAIL, email);
        company.put(COMPANY_PHONE, phone);
        company.put(URI, String.valueOf(Uri.EMPTY));
        company.put(COMPANY_SERVICE, companyService);
        company.put(COMPANY_STATE, CREATED);
    }
}
