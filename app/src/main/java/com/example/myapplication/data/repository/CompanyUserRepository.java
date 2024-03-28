package com.example.myapplication.data.repository;

import static com.example.myapplication.DI.service;
import static com.example.myapplication.presentation.utils.Utils.ADMIN;
import static com.example.myapplication.presentation.utils.Utils.APPROVED;
import static com.example.myapplication.presentation.utils.Utils.COMPANIES;
import static com.example.myapplication.presentation.utils.Utils.COMPANY;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_EMAIL;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_LOGO;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_NAME;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_PHONE;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_SERVICE;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_STATE;
import static com.example.myapplication.presentation.utils.Utils.CREATED;
import static com.example.myapplication.presentation.utils.Utils.EMPLOYEES;
import static com.example.myapplication.presentation.utils.Utils.EMPLOYEE_EMAIL;
import static com.example.myapplication.presentation.utils.Utils.EMPLOYEE_NAME;
import static com.example.myapplication.presentation.utils.Utils.EMPLOYEE_ROLE;
import static com.example.myapplication.presentation.utils.Utils.PROFILE_IMAGES;
import static com.example.myapplication.presentation.utils.Utils.PROFILE_PHOTO;
import static com.example.myapplication.presentation.utils.Utils.PROFILE_UPDATED_AT;
import static com.example.myapplication.presentation.utils.Utils.URI;
import static com.example.myapplication.presentation.utils.Utils.USER_LIST;
import static com.example.myapplication.presentation.utils.Utils.WORKER;

import android.net.Uri;
import android.util.Log;

import com.example.myapplication.data.dto.CompanyDto;
import com.example.myapplication.data.dto.EmployeeDto;
import com.example.myapplication.data.dto.ImageDto;
import com.example.myapplication.data.providers.CompanyUserProvider;
import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CompanyUserRepository {

    public Observable<DocumentSnapshot> addSnapshot(String companyId) {
        DocumentReference docRef = service.fireStore.document(getCompanyPath(companyId));
        return Observable.create(emitter -> {
            docRef.addSnapshotListener((value, error) -> {
                if (value != null) {
                    emitter.onNext(value);
                }
            });
        });
    }

    public Observable<DocumentSnapshot> addEmployeeSnapshot(String companyId, String employeeId) {
        DocumentReference docRef = service.fireStore.document(getCompanyPath(companyId) + "/" + EMPLOYEES + "/" + employeeId);
        return Observable.create(emitter -> {
            docRef.addSnapshotListener((value, error) -> {
                if (value != null) {
                    emitter.onNext(value);
                }
            });
        });
    }

    public Completable updateRole(String role, String employeeId, String companyId) {
        DocumentReference docRef = service.fireStore
                .collection(USER_LIST)
                .document(service.auth.getCurrentUser().getUid())
                .collection(COMPANY)
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
                }
            });
        });

    }

    public Single<CompanyDto> getCompanyByStringPath(String path) {
        return Single.create(emitter -> {
            service.fireStore.document(path).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    emitter.onSuccess(new CompanyDto(
                            document.getId(),
                            document.getString(URI),
                            document.getString(COMPANY_NAME),
                            document.getString(COMPANY_EMAIL),
                            document.getString(COMPANY_PHONE),
                            document.getString(COMPANY_SERVICE)
                    ));
                }
            });
        });
    }

    public Single<List<CompanyDto>> getCompany() {
        List<CompanyDto> local = CompanyUserProvider.getCompanies();
        if (local != null) {
            return Single.create(emitter -> {
                emitter.onSuccess(local);
            });
        } else {
            return
                    Single.create(emitter -> {
                        service.fireStore
                                .collection(USER_LIST)
                                .document(service.auth.getCurrentUser().getUid())
                                .collection(COMPANY)
                                .get().addOnCompleteListener(task -> {
                                    List<DocumentSnapshot> documents = task.getResult().getDocuments();
                                    List<CompanyDto> dtoList = documents.stream().map(
                                            document -> new CompanyDto(
                                                    document.getId(),
                                                    document.getString(URI),
                                                    document.getString(COMPANY_NAME),
                                                    document.getString(COMPANY_EMAIL),
                                                    document.getString(COMPANY_PHONE),
                                                    document.getString(COMPANY_SERVICE))
                                    ).collect(Collectors.toList());
                                    CompanyUserProvider.insertAllCompanies(dtoList);
                                    emitter.onSuccess(dtoList);
                                });
                    });
        }
    }

    public Maybe<List<CompanyDto>> getMaybeCompany() {
        List<CompanyDto> local = CompanyUserProvider.getCompanies();
        if (local != null) {
            return Maybe.create(emitter -> {
                emitter.onSuccess(local);
            });
        } else {
            return
                    Maybe.create(emitter -> {
                        service.fireStore
                                .collection(USER_LIST)
                                .document(service.auth.getCurrentUser().getUid())
                                .collection(COMPANY)
                                .get().addOnCompleteListener(task -> {
                                    List<DocumentSnapshot> documents = task.getResult().getDocuments();
                                    List<CompanyDto> dtoList = documents.stream().map(
                                            document -> new CompanyDto(
                                                    document.getId(),
                                                    document.getString(URI),
                                                    document.getString(COMPANY_NAME),
                                                    document.getString(COMPANY_EMAIL),
                                                    document.getString(COMPANY_PHONE),
                                                    document.getString(COMPANY_SERVICE))
                                    ).collect(Collectors.toList());
                                    CompanyUserProvider.insertAllCompanies(dtoList);
                                    emitter.onSuccess(dtoList);
                                });
                    });
        }
    }

    public Completable uploadCompanyBytesToFireStorage(String companyId, byte[] data) {
        StorageReference reference = service.storageReference
                .child(COMPANIES)
                .child(companyId + "/" + "EMPLOYEES_QR-CODE.jpg");

        return Completable.create(emitter -> {
            reference.putBytes(data).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onComplete();
                }
            });
        });
    }

    public void updateApproved(String companyId) {
        DocumentReference docRef = service.fireStore.document(getCompanyPath(companyId));
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                docRef.update(COMPANY_STATE, APPROVED);
            }
        });
    }

    public void createCompanyDocument(String companyID, String name, String email, String
            phone, String companyService) {
        DocumentReference docRef = service.fireStore
                .collection(USER_LIST)
                .document(service.auth.getCurrentUser().getUid())
                .collection(COMPANY)
                .document(companyID);

        Map<String, Object> company = new HashMap<>();

        company.put(COMPANY_NAME, name);
        company.put(COMPANY_EMAIL, email);
        company.put(COMPANY_PHONE, phone);
        company.put(URI, String.valueOf(Uri.EMPTY));
        company.put(COMPANY_SERVICE, companyService);
        company.put(COMPANY_STATE, CREATED);

        docRef.set(company);

        CompanyUserProvider.insertCompany(new CompanyDto(companyID, String.valueOf(Uri.EMPTY), name, email, phone, companyService));
    }

    public String getCompanyPath(String companyID) {
        return service.fireStore
                .collection(USER_LIST)
                .document(service.auth.getCurrentUser().getUid())
                .collection(COMPANY)
                .document(companyID)
                .getPath();
    }

    public Single<List<EmployeeDto>> getAdmins(String companyId) {
        return Single.create(emitter -> {
            service.fireStore
                    .collection(USER_LIST)
                    .document(service.auth.getCurrentUser().getUid())
                    .collection(COMPANY)
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
                                                    document.getString(EMPLOYEE_ROLE))
                                            ).collect(Collectors.toList())
                            );
                        }
                    });
        });
    }

    public Single<List<EmployeeDto>> getEmployees(String companyId) {
        return Single.create(emitter -> {
            service.fireStore
                    .collection(USER_LIST)
                    .document(service.auth.getCurrentUser().getUid())
                    .collection(COMPANY)
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
                                            document.getString(EMPLOYEE_ROLE))
                                    ).collect(Collectors.toList()));
                        }
                    });
        });
    }

    public Single<Boolean> checkCompany() {
        return Single.create(emitter -> {
            service.fireStore
                    .collection(USER_LIST)
                    .document(service.auth.getCurrentUser().getUid())
                    .collection(COMPANY)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onSuccess(task.getResult().size() > 0);
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
                }
            });
        });
    }

    public Completable addEmployee(String path, String name, String email) {
        return Completable.create(emitter -> {
            DocumentReference docRef = service.fireStore.document(path).collection(EMPLOYEES).document(service.auth.getCurrentUser().getUid());

            Map<String, Object> employee = new HashMap<>();

            employee.put(EMPLOYEE_NAME, name);
            employee.put(EMPLOYEE_ROLE, WORKER);
            employee.put(EMPLOYEE_EMAIL, email);

            docRef.set(employee).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onComplete();
                }
            });
        });

    }

    public Completable uploadCompanyLogoToFireStorage(Uri uri, String companyId) {
        return Completable.create(emitter -> {
            if (uri != Uri.EMPTY) {
                StorageReference reference = service.storageReference.child(COMPANIES).child(companyId + "/").child(COMPANY_LOGO);
                reference.putFile(uri)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                DocumentReference docRef = service.fireStore.document(getCompanyPath(companyId));
                                docRef.update(URI, String.valueOf(uri)).addOnCompleteListener(task1 -> {
                                    CompanyUserProvider.updateUri(companyId, String.valueOf(uri));
                                    emitter.onComplete();
                                });
                            }
                        });
            } else {
                emitter.onComplete();
            }
        });

    }

    public Single<ImageDto> getEmployeePhoto(String employeeId) {
        return Single.create(emitter -> {
            StorageReference local = service.storageReference.child(PROFILE_IMAGES).child(PROFILE_PHOTO + employeeId);
            try {
                local.getDownloadUrl().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Uri uri = task.getResult();
                        emitter.onSuccess(new ImageDto(uri));
                    } else {
                        Log.d("Error", task.getException().getMessage());
                        emitter.onSuccess(new ImageDto(Uri.EMPTY));
                    }
                });
            } catch (RuntimeExecutionException e) {
                Log.d("RuntimeExecutionException", e.getMessage());
                emitter.onSuccess(new ImageDto(Uri.EMPTY));
            }
        });

    }

    public Single<List<Task<Uri>>> getEmployeesPhotos(String companyId) {
        return Single.create(emitter -> {
            List<Task<Uri>> listTask = new ArrayList<>();
            getEmployees(companyId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new SingleObserver<List<EmployeeDto>>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onSuccess(@NonNull List<EmployeeDto> employeeDtos) {
                            for (int i = 0; i < employeeDtos.size(); i++) {
                                StorageReference local = service.storageReference
                                        .child(PROFILE_IMAGES)
                                        .child(PROFILE_PHOTO + employeeDtos.get(i).getId());
                                listTask.add(local.getDownloadUrl());
                            }
                            emitter.onSuccess(listTask);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }
                    });
        });
    }

    public Single<List<Task<Uri>>> getCompaniesLogos() {
        return Single.create(emitter -> {
            List<Task<Uri>> listTask = new ArrayList<>();
            getCompany()
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

                        }
                    });
        });
    }

    public Single<ImageDto> getCompanyLogo(String companyId) {
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
//        }

    }

    public Completable updateCompanyData(String companyId, String companyName, String phone) {
        return Completable.create(emitter -> {
            DocumentReference docRef = service.fireStore.document(getCompanyPath(companyId));
            FieldValue timestamp = FieldValue.serverTimestamp();
            docRef.update(
                    COMPANY_NAME, companyName,
                    COMPANY_PHONE, phone,
                    PROFILE_UPDATED_AT, timestamp
            ).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    CompanyUserProvider.updateCompany(companyId, companyName, phone);
                    emitter.onComplete();
                }
            });
        });
    }

    public Completable deleteCompany(String companyId) {
        return Completable.create(emitter -> {
            DocumentReference docRef = service.fireStore.document(getCompanyPath(companyId));
            docRef.delete().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    CompanyUserProvider.deleteCompany(companyId);
                    emitter.onComplete();
                }
            });
        });
    }

}
