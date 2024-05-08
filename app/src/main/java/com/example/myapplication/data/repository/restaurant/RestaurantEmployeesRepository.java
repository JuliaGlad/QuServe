package com.example.myapplication.data.repository.restaurant;

import static com.example.myapplication.di.DI.service;
import static com.example.myapplication.presentation.utils.Utils.EMPLOYEES;
import static com.example.myapplication.presentation.utils.Utils.JPG;
import static com.example.myapplication.presentation.utils.Utils.USER_LIST;
import static com.example.myapplication.presentation.utils.Utils.USER_NAME_KEY;
import static com.example.myapplication.presentation.utils.constants.Restaurant.COOK_QR_CODE;
import static com.example.myapplication.presentation.utils.constants.Restaurant.LOCATION;
import static com.example.myapplication.presentation.utils.constants.Restaurant.WAITER_QR_CODE;

import android.net.Uri;

import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class RestaurantEmployeesRepository {

    public static RestaurantEmployeesRepository restaurantEmployeesRepository = new RestaurantEmployeesRepository();

    public Completable addCook(String path) {
        return Completable.create(emitter -> {
            DocumentReference docRef = service.fireStore
                    .collection(path)
                    .document(service.auth.getCurrentUser().getUid());

            service.fireStore.collection(USER_LIST).document(service.auth.getCurrentUser().getUid())
                    .get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()){

                            String name = task.getResult().getString(USER_NAME_KEY);
                            HashMap<String, String> cook = new HashMap<>();
                            cook.put(USER_NAME_KEY, name);

                            docRef.set(cook).addOnCompleteListener(taskAdd -> {
                                if (taskAdd.isSuccessful()) {
                                    emitter.onComplete();
                                }
                            });
                        }
                    });


        });
    }

    public Single<Uri> getWaiterQrCode(String locationId) {
        return Single.create(emitter -> {
            service.storageReference
                    .child(LOCATION + "/")
                    .child(locationId + "/")
                    .child(EMPLOYEES + "/")
                    .child(COOK_QR_CODE + JPG)
                    .getDownloadUrl().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onSuccess(task.getResult());
                        }
                    });
        });
    }

    public Single<Uri> getCookQrCodeByPath(String path) {
        return Single.create(emitter -> {
            String locationId = service.fireStore.collection(path).getParent().getId();
            service.storageReference
                    .child(LOCATION + "/")
                    .child(locationId + "/")
                    .child(EMPLOYEES + "/")
                    .child(WAITER_QR_CODE + JPG)
                    .getDownloadUrl().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onSuccess(task.getResult());
                        }
                    });
        });
    }

    public Single<Uri> getCookQrCode(String locationId) {
        return Single.create(emitter -> {
            service.storageReference
                    .child(LOCATION + "/")
                    .child(locationId + "/")
                    .child(EMPLOYEES + "/")
                    .child(WAITER_QR_CODE + JPG)
                    .getDownloadUrl().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onSuccess(task.getResult());
                        }
                    });
        });
    }

    public Completable uploadCookQrCode(String locationId, byte[] bytes) {
        return Completable.create(emitter -> {
            service.storageReference
                    .child(LOCATION + "/")
                    .child(locationId + "/")
                    .child(EMPLOYEES + "/")
                    .child(COOK_QR_CODE + JPG)
                    .putBytes(bytes).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onComplete();
                        }
                    });
        });
    }

    public Completable uploadWaiterQrCode(String locationId, byte[] bytes) {
        return Completable.create(emitter -> {
            service.storageReference
                    .child(LOCATION + "/")
                    .child(locationId + "/")
                    .child(EMPLOYEES + "/")
                    .child(WAITER_QR_CODE + JPG)
                    .putBytes(bytes).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onComplete();
                        }
                    });
        });
    }

}
