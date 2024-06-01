package com.example.myapplication.data.repository.restaurant;

import static com.example.myapplication.di.DI.service;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_NAME;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_PHONE;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_SERVICE;
import static com.example.myapplication.presentation.utils.constants.Utils.PROFILE_UPDATED_AT;
import static com.example.myapplication.presentation.utils.constants.Utils.URI;
import static com.example.myapplication.presentation.utils.constants.Utils.USER_LIST;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_EMAIL;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_LIST;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_LOGO;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_NAME;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_OWNER;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_PATH;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_PHONE;

import android.net.Uri;

import com.example.myapplication.data.dto.common.ImageDto;
import com.example.myapplication.data.dto.restaurant.RestaurantDto;
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
import java.util.stream.Collectors;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RestaurantUserRepository {

    public Observable<DocumentSnapshot> addSnapshot(String restaurantId) {
        return Observable.create(emitter -> {
            service.fireStore
                    .collection(RESTAURANT_LIST)
                    .document(restaurantId)
                    .addSnapshotListener((value, error) -> {
                        if (value != null) {
                            emitter.onNext(value);
                        } else {
                            emitter.onError(new Throwable("Value is null"));
                        }
                    });
        });
    }

    public Single<RestaurantDto> getRestaurantByIds(String restaurantId){
        return Single.create(emitter -> {
            service.fireStore
                    .collection(RESTAURANT_LIST)
                    .document(restaurantId)
                    .get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot snapshot = task.getResult();
                            emitter.onSuccess(new RestaurantDto(
                                    snapshot.getId(),
                                    snapshot.getString(RESTAURANT_NAME),
                                    snapshot.getString(RESTAURANT_EMAIL),
                                    snapshot.getString(RESTAURANT_PHONE),
                                    snapshot.getString(RESTAURANT_OWNER)
                            ));
                        }else {
                            emitter.onError(new Throwable(task.getException()));
                        }
                    });
        });
    }

    public Single<RestaurantDto> getRestaurantByPath(String path) {
        return Single.create(emitter -> {
            service.fireStore.collection(path).getParent().getParent().getParent()
                    .get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot snapshot = task.getResult();
                            emitter.onSuccess(new RestaurantDto(
                                    snapshot.getId(),
                                    snapshot.getString(RESTAURANT_NAME),
                                    snapshot.getString(RESTAURANT_EMAIL),
                                    snapshot.getString(RESTAURANT_PHONE),
                                    snapshot.getString(RESTAURANT_OWNER)
                            ));
                        } else {
                            emitter.onError(new Throwable(task.getException()));
                        }
                    });
        });
    }

    public Completable deleteRestaurant(String restaurantId) {
        DocumentReference docRef = service.fireStore.collection(RESTAURANT_LIST).document(restaurantId);
        return Completable.create(emitter -> {
            docRef.delete().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onComplete();
                }else {
                    emitter.onError(new Throwable(task.getException()));
                }
            });
        });
    }

    public Completable setRestaurantUser(String restaurantId, String restaurantName) {
        DocumentReference userCompany = service.fireStore
                .collection(USER_LIST)
                .document(service.auth.getCurrentUser().getUid())
                .collection(COMPANY)
                .document(restaurantId);

        Map<String, Object> restaurantUser = new HashMap<>();
        restaurantUser.put(COMPANY_NAME, restaurantName);
        restaurantUser.put(COMPANY_SERVICE, service);

        return Completable.create(emitter -> {
            userCompany.set(restaurantUser).addOnCompleteListener(taskUser -> {
                if (taskUser.isSuccessful()) {
                    emitter.onComplete();
                }else {
                    emitter.onError(new Throwable(taskUser.getException()));
                }
            });
        });
    }

    public Completable createRestaurantDocument(String restaurantId, String restaurantName, String email, String phone) {
        return Completable.create(emitter -> {

            String ownerId = service.auth.getCurrentUser().getUid();

            DocumentReference docRef = service.fireStore.collection(RESTAURANT_LIST).document(restaurantId);

            Map<String, Object> restaurant = new HashMap<>();
            restaurant.put(RESTAURANT_NAME, restaurantName);
            restaurant.put(RESTAURANT_EMAIL, email);
            restaurant.put(RESTAURANT_PHONE, phone);
            restaurant.put(RESTAURANT_OWNER, ownerId);

            docRef.set(restaurant).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    RestaurantDto restaurantDto = new RestaurantDto(restaurantId, restaurantName, email, phone, ownerId);
                    emitter.onComplete();
                }else {
                    emitter.onError(new Throwable(task.getException()));
                }
            });

        });
    }

    public static Single<List<RestaurantDto>> getRestaurants() {
        return Single.create(emitter -> {
            service.fireStore
                    .collection(RESTAURANT_LIST)
                    .get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> documents = task.getResult().getDocuments();
                            List<RestaurantDto> dtoList = documents.stream()
                                    .map(document -> new RestaurantDto(
                                            document.getId(),
                                            document.getString(RESTAURANT_NAME),
                                            document.getString(RESTAURANT_EMAIL),
                                            document.getString(RESTAURANT_PHONE),
                                            document.getString(RESTAURANT_OWNER)
                                    )).collect(Collectors.toList());
                            emitter.onSuccess(dtoList);
                        } else {
                            emitter.onSuccess(Collections.emptyList());
                        }
                    });
        });
    }

    public Completable updateRestaurantData(String restaurantId, String email, String restaurantName, String phone) {
        return Completable.create(emitter -> {
            DocumentReference docRef = service.fireStore
                    .collection(RESTAURANT_LIST)
                    .document(restaurantId);

            FieldValue timestamp = FieldValue.serverTimestamp();
            docRef.update(
                    RESTAURANT_NAME, restaurantName,
                    RESTAURANT_EMAIL, email,
                    COMPANY_PHONE, phone,
                    PROFILE_UPDATED_AT, timestamp
            ).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onComplete();
                }else {
                    emitter.onError(new Throwable(task.getException()));
                }
            });
        });
    }

    public static class RestaurantImages{
        public Completable uploadRestaurantLogoToFireStorage(Uri uri, String restaurantId) {
            return Completable.create(emitter -> {
                if (uri != Uri.EMPTY) {

                    StorageReference reference = service.storageReference
                            .child(RESTAURANT_PATH)
                            .child(restaurantId + "/")
                            .child(RESTAURANT_LOGO);

                    reference.putFile(uri)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    DocumentReference docRef = service.fireStore
                                            .collection(RESTAURANT_LIST)
                                            .document(restaurantId);

                                    docRef.update(URI, String.valueOf(uri)).addOnCompleteListener(task1 -> {
                                        emitter.onComplete();
                                    });
                                }else {
                                    emitter.onError(new Throwable(task.getException()));
                                }
                            });
                } else {
                    emitter.onComplete();
                }
            });

        }

        public Single<ImageDto> getSingleRestaurantLogo(String restaurantId) {
            return Single.create(emitter -> {
                StorageReference reference = service.storageReference
                        .child(RESTAURANT_PATH)
                        .child(restaurantId + "/")
                        .child(RESTAURANT_LOGO);

                reference.getDownloadUrl().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        emitter.onSuccess(new ImageDto(task.getResult()));
                    } else {
                        emitter.onSuccess(new ImageDto(Uri.EMPTY));
                    }
                });

            });

        }

        public Single<List<Task<Uri>>> getRestaurantLogos() {
            return Single.create(emitter -> {
                List<Task<Uri>> listTask = new ArrayList<>();
                getRestaurants()
                        .subscribeOn(Schedulers.io())
                        .subscribe(new SingleObserver<List<RestaurantDto>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onSuccess(@NonNull List<RestaurantDto> companyDtos) {
                                for (int i = 0; i < companyDtos.size(); i++) {
                                    StorageReference reference = service.storageReference
                                            .child(RESTAURANT_PATH)
                                            .child(companyDtos.get(i).getRestaurantId() + "/")
                                            .child(RESTAURANT_LOGO);

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
    }

}
