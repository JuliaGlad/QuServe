package com.example.myapplication.data.repository.restaurant;

import static com.example.myapplication.di.DI.service;
import static com.example.myapplication.presentation.utils.Utils.EMPLOYEES;
import static com.example.myapplication.presentation.utils.Utils.EMPLOYEE_ROLE;
import static com.example.myapplication.presentation.utils.Utils.JPG;
import static com.example.myapplication.presentation.utils.Utils.USER_LIST;
import static com.example.myapplication.presentation.utils.Utils.USER_NAME_KEY;
import static com.example.myapplication.presentation.utils.constants.Restaurant.ACTIVE_ORDERS_COUNT;
import static com.example.myapplication.presentation.utils.constants.Restaurant.COOK;
import static com.example.myapplication.presentation.utils.constants.Restaurant.COOKS;
import static com.example.myapplication.presentation.utils.constants.Restaurant.COOK_QR_CODE;
import static com.example.myapplication.presentation.utils.constants.Restaurant.IS_WORKING;
import static com.example.myapplication.presentation.utils.constants.Restaurant.LOCATION;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_EMPLOYEE;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_LIST;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_LOCATION;
import static com.example.myapplication.presentation.utils.constants.Restaurant.WAITER;
import static com.example.myapplication.presentation.utils.constants.Restaurant.WAITERS;
import static com.example.myapplication.presentation.utils.constants.Restaurant.WAITER_QR_CODE;

import android.net.Uri;

import com.example.myapplication.data.dto.common.EmployeeDto;
import com.example.myapplication.data.dto.restaurant.EmployeeRestaurantDto;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class RestaurantEmployeesRepository {

    public Completable deleteRestaurantEmployee(String restaurantId, String locationId, String userId, String role){
        return Completable.create(emitter -> {
            String document;
            if (role.equals(WAITER)){
                document = WAITERS;
            } else {
                document = COOKS;
            }
            service.fireStore
                    .collection(RESTAURANT_LIST)
                    .document(restaurantId)
                    .collection(RESTAURANT_LOCATION)
                    .document(locationId)
                    .collection(document)
                    .document(userId)
                    .delete().addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            emitter.onComplete();
                        }
                    });
        });
    }

    public Single<List<EmployeeRestaurantDto>> getEmployees(String restaurantId, String locationId) {
        return Single.create(emitter -> {
            DocumentReference docRef =
                    service.fireStore
                            .collection(RESTAURANT_LIST)
                            .document(restaurantId)
                            .collection(RESTAURANT_LOCATION)
                            .document(locationId);
            List<EmployeeRestaurantDto> employees = new ArrayList<>();
            getCooks(employees, docRef);
            getWaiters(employees, docRef);
        });
    }

    public Single<Boolean> checkIsWorking(String restaurantId, String locationId) {
        return Single.create(emitter -> {
            service.fireStore
                    .collection(RESTAURANT_LIST)
                    .document(restaurantId)
                    .collection(RESTAURANT_LOCATION)
                    .document(locationId)
                    .collection(WAITERS)
                    .document(service.auth.getCurrentUser().getUid())
                    .get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onSuccess(task.getResult().getBoolean(IS_WORKING));
                        }
                    });
        });
    }

    public Completable updateIsWorking(String restaurantId, String locationId, boolean isWorking) {
        return Completable.create(emitter -> {
            service.fireStore
                    .collection(RESTAURANT_LIST)
                    .document(restaurantId)
                    .collection(RESTAURANT_LOCATION)
                    .document(locationId)
                    .collection(WAITERS)
                    .document(service.auth.getCurrentUser().getUid())
                    .update(IS_WORKING, isWorking)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onComplete();
                        }
                    });
        });
    }

    public Completable addWaiter(String waiterPath) {
        return Completable.create(emitter -> {
            String userId = service.auth.getCurrentUser().getUid();
            DocumentReference docRef = service.fireStore
                    .collection(waiterPath)
                    .document(userId);

            service.fireStore.collection(USER_LIST).document(userId)
                    .get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            String name = task.getResult().getString(USER_NAME_KEY);
                            HashMap<String, Object> waiter = new HashMap<>();
                            waiter.put(USER_NAME_KEY, name);
                            waiter.put(IS_WORKING, false);
                            waiter.put(ACTIVE_ORDERS_COUNT, Collections.emptyList());

                            docRef.set(waiter).addOnCompleteListener(taskWaiter -> {
                                if (taskWaiter.isSuccessful()) {
                                    emitter.onComplete();
                                }
                            });
                        }
                    });
        });
    }

    public Completable addCook(String path) {
        return Completable.create(emitter -> {
            DocumentReference docRef = service.fireStore
                    .collection(path)
                    .document(service.auth.getCurrentUser().getUid());

            service.fireStore.collection(USER_LIST).document(service.auth.getCurrentUser().getUid())
                    .get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
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
                    .child(WAITER_QR_CODE + JPG)
                    .getDownloadUrl().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onSuccess(task.getResult());
                        }
                    });
        });
    }

    public Single<Uri> getWaiterQrCodeByPath(String waiterPath) {
        return Single.create(emitter -> {
            String locationId = service.fireStore.collection(waiterPath).getParent().getId();
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
                    .child(COOK_QR_CODE + JPG)
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
                    .child(COOK_QR_CODE + JPG)
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

    private void getWaiters(List<EmployeeRestaurantDto> employees, DocumentReference docRef) {
        docRef.collection(WAITERS).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                List<DocumentSnapshot> snapshots = task.getResult().getDocuments();
                for (DocumentSnapshot current : snapshots) {
                    employees.add(new EmployeeRestaurantDto(
                            current.getId(),
                            current.getString(USER_NAME_KEY),
                            WAITER
                    ));
                }
            }
        });
    }

    private void getCooks(List<EmployeeRestaurantDto> employees, DocumentReference docRef) {
        docRef.collection(COOKS).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<DocumentSnapshot> snapshots = task.getResult().getDocuments();
                for (DocumentSnapshot current : snapshots) {
                    employees.add(new EmployeeRestaurantDto(
                            current.getId(),
                            current.getString(USER_NAME_KEY),
                            COOK
                    ));
                }
            }
        });
    }

}
