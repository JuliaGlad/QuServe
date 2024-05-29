package com.example.myapplication.data.repository.profile;

import static com.example.myapplication.di.DI.service;
import static com.example.myapplication.presentation.utils.Utils.ACTIVE_QUEUES_LIST;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_EMPLOYEE;
import static com.example.myapplication.presentation.utils.Utils.EMPLOYEE_ROLE;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_LOCATION_KEY;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_NAME_KEY;
import static com.example.myapplication.presentation.utils.Utils.USER_LIST;
import static com.example.myapplication.presentation.utils.Utils.WORKER;
import static com.example.myapplication.presentation.utils.constants.Restaurant.COOK;
import static com.example.myapplication.presentation.utils.constants.Restaurant.LOCATION_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_EMPLOYEE;
import static com.example.myapplication.presentation.utils.constants.Restaurant.WAITER;

import android.util.Log;

import com.example.myapplication.data.dto.company.ActiveEmployeeQueueDto;
import com.example.myapplication.data.dto.restaurant.RestaurantEmployeeDto;
import com.example.myapplication.data.dto.user.UserEmployeeRoleDto;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class ProfileEmployeeRepository {
    public Single<Boolean> isEmployee() {
        return Single.create(emitter -> {
            DocumentReference userDoc = service.fireStore
                    .collection(USER_LIST)
                    .document(service.auth.getCurrentUser().getUid());

            userDoc.collection(COMPANY_EMPLOYEE).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d("Task employee", task.getResult().size() + "");
                    boolean size = !task.getResult().isEmpty();
                    Log.d("Task employee", size + "");
                    if (size) {
                        emitter.onSuccess(!task.getResult().isEmpty());
                    }
                } else {
                    emitter.onError(new Throwable(task.getException()));
                }
            });

            userDoc.collection(RESTAURANT_EMPLOYEE).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onSuccess(!task.getResult().isEmpty());
                } else {
                    emitter.onError(new Throwable(task.getException()));
                }
            });
        });
    }

    public static class RestaurantEmployee {

        public Completable addWaiterEmployeeRole(String waiterPath) {
            return Completable.create(emitter -> {
                String locationId = service.fireStore.collection(waiterPath).getParent().getId();
                String restaurantId = service.fireStore.collection(waiterPath).getParent().getParent().getParent().getId();

                DocumentReference docRef = service.fireStore
                        .collection(USER_LIST)
                        .document(service.auth.getCurrentUser().getUid())
                        .collection(RESTAURANT_EMPLOYEE)
                        .document(restaurantId);

                HashMap<String, String> employee = new HashMap<>();
                employee.put(EMPLOYEE_ROLE, WAITER);
                employee.put(LOCATION_ID, locationId);

                docRef.set(employee).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        emitter.onComplete();
                    } else {
                        emitter.onError(new Throwable(task.getException()));
                    }
                });
            });
        }

        public Single<List<RestaurantEmployeeDto>> getRestaurantEmployeeRoles() {
            return Single.create(emitter -> {
                service.fireStore
                        .collection(USER_LIST)
                        .document(service.auth.getCurrentUser().getUid())
                        .collection(RESTAURANT_EMPLOYEE)
                        .get().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                List<DocumentSnapshot> snapshots = task.getResult().getDocuments();
                                if (!snapshots.isEmpty()) {
                                    List<RestaurantEmployeeDto> dtos = new ArrayList<>();
                                    for (DocumentSnapshot current : snapshots) {
                                        dtos.add(new RestaurantEmployeeDto(
                                                current.getId(),
                                                current.getString(LOCATION_ID),
                                                current.getString(EMPLOYEE_ROLE)
                                        ));
                                    }
                                    emitter.onSuccess(dtos);
                                } else {
                                    emitter.onSuccess(Collections.emptyList());
                                }
                            } else {
                                emitter.onError(new Throwable(task.getException()));
                            }
                        });
            });
        }

        public Completable addCookEmployeeRole(String path) {
            String restaurantId = service.fireStore.collection(path).getParent().getParent().getParent().getId();
            String locationId = service.fireStore.collection(path).getParent().getId();
            return Completable.create(emitter -> {
                DocumentReference docRef = service.fireStore
                        .collection(USER_LIST)
                        .document(service.auth.getCurrentUser().getUid())
                        .collection(RESTAURANT_EMPLOYEE)
                        .document(restaurantId);

                HashMap<String, String> employee = new HashMap<>();
                employee.put(EMPLOYEE_ROLE, COOK);
                employee.put(LOCATION_ID, locationId);

                docRef.set(employee).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        emitter.onComplete();
                    } else {
                        emitter.onError(new Throwable(task.getException()));
                    }
                });
            });
        }

    }

    public static class CompanyEmployee {

        public Completable deleteActiveQueue(String companyId, String queueId, String userId) {
            return Completable.create(emitter -> {
                service.fireStore
                        .collection(USER_LIST)
                        .document(userId)
                        .collection(COMPANY_EMPLOYEE)
                        .document(companyId)
                        .collection(ACTIVE_QUEUES_LIST)
                        .document(queueId)
                        .delete().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                emitter.onComplete();
                            } else {
                                emitter.onError(new Throwable(task.getException()));
                            }
                        });
            });
        }

        public Single<List<ActiveEmployeeQueueDto>> getActiveQueuesByEmployeeId(String companyId, String employeeId) {
            return Single.create(emitter -> {
                List<ActiveEmployeeQueueDto> dtos = new ArrayList<>();
                service.fireStore
                        .collection(USER_LIST)
                        .document(employeeId)
                        .collection(COMPANY_EMPLOYEE)
                        .document(companyId)
                        .collection(ACTIVE_QUEUES_LIST)
                        .get().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                List<DocumentSnapshot> value = task.getResult().getDocuments();
                                for (int i = 0; i < value.size(); i++) {
                                    DocumentSnapshot current = value.get(i);
                                    dtos.add(new ActiveEmployeeQueueDto(
                                            current.getId(),
                                            current.getString(QUEUE_NAME_KEY),
                                            current.getString(QUEUE_LOCATION_KEY)
                                    ));
                                }
                                emitter.onSuccess(dtos);
                            } else {
                                emitter.onError(new Throwable(task.getException()));
                            }
                        });
            });
        }

        public Single<List<ActiveEmployeeQueueDto>> getActiveQueues(String companyId) {
            return Single.create(emitter -> {
                List<ActiveEmployeeQueueDto> dtos = new ArrayList<>();
                service.fireStore
                        .collection(USER_LIST)
                        .document(service.auth.getCurrentUser().getUid())
                        .collection(COMPANY_EMPLOYEE)
                        .document(companyId)
                        .collection(ACTIVE_QUEUES_LIST)
                        .get().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                List<DocumentSnapshot> value = task.getResult().getDocuments();
                                for (int i = 0; i < value.size(); i++) {
                                    DocumentSnapshot current = value.get(i);
                                    dtos.add(new ActiveEmployeeQueueDto(
                                            current.getId(),
                                            current.getString(QUEUE_NAME_KEY),
                                            current.getString(QUEUE_LOCATION_KEY)
                                    ));
                                }
                                emitter.onSuccess(dtos);
                            }else {
                                emitter.onError(new Throwable(task.getException()));
                            }
                        });
            });
        }

        public Completable addEmployeeRole(String companyId) {
            DocumentReference docRef = service.fireStore
                    .collection(USER_LIST)
                    .document(service.auth.getCurrentUser().getUid())
                    .collection(COMPANY_EMPLOYEE)
                    .document(companyId);

            Map<String, Object> employee = new HashMap<>();
            employee.put(EMPLOYEE_ROLE, WORKER);

            return Completable.create(emitter -> {
                docRef.set(employee).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        emitter.onComplete();
                    }else {
                        emitter.onError(new Throwable(task.getException()));
                    }
                });
            });
        }

        public Single<List<UserEmployeeRoleDto>> getEmployeeRoles() {
            return Single.create(emitter -> {
                service.fireStore
                        .collection(USER_LIST)
                        .document(service.auth.getCurrentUser().getUid())
                        .collection(COMPANY_EMPLOYEE)
                        .get().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                List<DocumentSnapshot> documentSnapshots = task.getResult().getDocuments();
                                if (!documentSnapshots.isEmpty()) {
                                    List<UserEmployeeRoleDto> dtos = new ArrayList<>();
                                    for (int i = 0; i < documentSnapshots.size(); i++) {
                                        DocumentSnapshot current = documentSnapshots.get(i);
                                        dtos.add(new UserEmployeeRoleDto(
                                                current.getString(EMPLOYEE_ROLE),
                                                current.getId()
                                        ));
                                    }
                                    emitter.onSuccess(dtos);
                                } else {
                                    emitter.onSuccess(Collections.emptyList());
                                }
                            }else {
                                emitter.onSuccess(Collections.emptyList());
                            }
                        });
            });
        }

        public Completable updateRole(String companyId, String userId, String role) {
            return Completable.create(emitter -> {
                service.fireStore
                        .collection(USER_LIST)
                        .document(userId)
                        .collection(COMPANY_EMPLOYEE)
                        .document(companyId)
                        .update(EMPLOYEE_ROLE, role).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                emitter.onComplete();
                            } else {
                                emitter.onError(new Throwable(task.getException()));
                            }
                        });
            });
        }

        public Completable deleteEmployeeRole(String companyId, String userId) {
            return Completable.create(emitter -> {
                service.fireStore
                        .collection(USER_LIST)
                        .document(userId)
                        .collection(COMPANY_EMPLOYEE)
                        .document(companyId).delete().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                emitter.onComplete();
                            } else {
                                emitter.onError(new Throwable(task.getException()));
                            }
                        });
            });
        }
    }
}
