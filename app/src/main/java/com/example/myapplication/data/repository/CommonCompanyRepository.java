package com.example.myapplication.data.repository;

import static com.example.myapplication.di.DI.service;
import static com.example.myapplication.presentation.utils.Utils.COMPANIES;
import static com.example.myapplication.presentation.utils.Utils.COMPANY;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_LOGO;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_NAME;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_QUEUE;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_SERVICE;
import static com.example.myapplication.presentation.utils.Utils.USER_LIST;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_LOGO;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_PATH;

import android.net.Uri;
import android.util.Log;

import com.example.myapplication.data.dto.CommonCompanyDto;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CommonCompanyRepository {

    public Completable setCompanyUser(String companyId, String name, String companyService){
        DocumentReference userCompany = service.fireStore
                .collection(USER_LIST)
                .document(service.auth.getCurrentUser().getUid())
                .collection(COMPANY)
                .document(companyId);

        Map<String, Object> companyUser = new HashMap<>();
        companyUser.put(COMPANY_SERVICE, companyService);
        companyUser.put(COMPANY_NAME, name);

        return Completable.create(emitter -> {
            userCompany.set(companyUser).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    emitter.onComplete();
                }
            });
        });

    }

    public Single<Boolean> checkCompanyExist() {
        return Single.create(emitter -> {
            service.fireStore
                    .collection(USER_LIST)
                    .document(service.auth.getCurrentUser().getUid())
                    .collection(COMPANY)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> snapshots = task.getResult().getDocuments();
                            if (snapshots.size() > 0) {
                                emitter.onSuccess(true);
                            } else {
                                emitter.onSuccess(false);
                            }
                        }
                    });
        });
    }

    public Single<List<Task<Uri>>> getAllCompaniesLogos() {
        return Single.create(emitter -> {
            List<Task<Uri>> listTask = new ArrayList<>();

            getAllServiceCompanies()
                    .subscribeOn(Schedulers.io())
                    .subscribe(new SingleObserver<List<CommonCompanyDto>>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onSuccess(@NonNull List<CommonCompanyDto> companyDtos) {
                            for (CommonCompanyDto current : companyDtos) {
                                String companyService = current.getService();
                                switch (companyService) {
                                    case COMPANY_QUEUE:
                                        StorageReference referenceQueue = service.storageReference
                                                .child(COMPANIES)
                                                .child(current.getCompanyId() + "/")
                                                .child(COMPANY_LOGO);
                                        listTask.add(referenceQueue.getDownloadUrl());
                                        break;
                                    case RESTAURANT:
                                        StorageReference referenceRestaurant = service.storageReference
                                                .child(RESTAURANT_PATH)
                                                .child(current.getCompanyId() + "/")
                                                .child(RESTAURANT_LOGO);
                                        listTask.add(referenceRestaurant.getDownloadUrl());
                                        break;
                                }

                            }
                            emitter.onSuccess(listTask);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }
                    });



        });
    }

    public Single<List<CommonCompanyDto>> getAllServiceCompanies() {
        return Single.create(emitter -> {
            service.fireStore
                    .collection(USER_LIST)
                    .document(service.auth.getCurrentUser().getUid())
                    .collection(COMPANY)
                    .get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> snapshots = task.getResult().getDocuments();
                            if (snapshots.size() > 0) {
                                List<CommonCompanyDto> companyDtos = new ArrayList<>();
                                for (DocumentSnapshot snapshot : snapshots) {
                                    companyDtos.add(new CommonCompanyDto(
                                            snapshot.getId(),
                                            snapshot.getString(COMPANY_NAME),
                                            snapshot.getString(COMPANY_SERVICE)
                                    ));
                                }
                                emitter.onSuccess(companyDtos);
                            }
                        }
                    });

        });
    }

}
