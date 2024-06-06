package com.example.myapplication.data.repository.restaurant;

import static com.example.myapplication.di.DI.service;
import static com.example.myapplication.presentation.utils.constants.Restaurant.NO_COOK;
import static com.example.myapplication.presentation.utils.constants.Restaurant.NO_WAITER;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TABLE_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TABLE_LIST;
import static com.example.myapplication.presentation.utils.constants.Utils.DATE_LEFT;
import static com.example.myapplication.presentation.utils.constants.Utils.HISTORY_KEY;
import static com.example.myapplication.presentation.utils.constants.Utils.NOT_RESTAURANT_VISITOR;
import static com.example.myapplication.presentation.utils.constants.Utils.NO_ORDER;
import static com.example.myapplication.presentation.utils.constants.Utils.PLACE_NAME;
import static com.example.myapplication.presentation.utils.constants.Utils.QUEUE_LIST;
import static com.example.myapplication.presentation.utils.constants.Utils.QUEUE_NAME_KEY;
import static com.example.myapplication.presentation.utils.constants.Utils.SERVICE;
import static com.example.myapplication.presentation.utils.constants.Utils.TIME;
import static com.example.myapplication.presentation.utils.constants.Utils.USER_LIST;
import static com.example.myapplication.presentation.utils.constants.Utils.USER_NAME_KEY;
import static com.example.myapplication.presentation.utils.constants.Restaurant.ACTIVE_ORDERS;
import static com.example.myapplication.presentation.utils.constants.Restaurant.ACTIVE_ORDERS_COUNT;
import static com.example.myapplication.presentation.utils.constants.Restaurant.COOK;
import static com.example.myapplication.presentation.utils.constants.Restaurant.COOKS;
import static com.example.myapplication.presentation.utils.constants.Restaurant.COUNT;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISHES;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISHES_COUNT;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISH_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISH_NAME;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISH_PRICE;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISH_WEIGHT_OR_COUNT;
import static com.example.myapplication.presentation.utils.constants.Restaurant.INGREDIENT_TO_REMOVE;
import static com.example.myapplication.presentation.utils.constants.Restaurant.IS_DONE;
import static com.example.myapplication.presentation.utils.constants.Restaurant.IS_TAKEN;
import static com.example.myapplication.presentation.utils.constants.Restaurant.IS_WORKING;
import static com.example.myapplication.presentation.utils.constants.Restaurant.NOT_TAKEN;
import static com.example.myapplication.presentation.utils.constants.Restaurant.ORDER_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.READY_DISHES;
import static com.example.myapplication.presentation.utils.constants.Restaurant.REQUIRED_CHOICES;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_LIST;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_LOCATION;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_NAME;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TABLE_NUMBER;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TOPPINGS;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TOTAL_PRICE;
import static com.example.myapplication.presentation.utils.constants.Restaurant.VISITOR;
import static com.example.myapplication.presentation.utils.constants.Restaurant.WAITER;
import static com.example.myapplication.presentation.utils.constants.Restaurant.WAITERS;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplication.data.dto.restaurant.ActiveOrderDishDto;
import com.example.myapplication.data.dto.restaurant.DishShortInfoDto;
import com.example.myapplication.data.dto.restaurant.OrderDto;
import com.example.myapplication.data.dto.restaurant.OrderShortDto;
import com.example.myapplication.data.dto.restaurant.OrderTableDetailsDto;
import com.example.myapplication.data.dto.restaurant.ReadyDishDto;
import com.example.myapplication.data.dto.user.AnonymousUserDto;
import com.example.myapplication.data.dto.user.UserDto;
import com.example.myapplication.data.providers.AnonymousUserProvider;
import com.example.myapplication.data.providers.CartProvider;
import com.example.myapplication.data.providers.UserDatabaseProvider;
import com.example.myapplication.presentation.restaurantOrder.VariantCartModel;
import com.example.myapplication.presentation.restaurantOrder.restaurantCart.model.OrderDishesModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableEmitter;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;

public class RestaurantOrderRepository {

    public Completable addOrderToHistory(String orderId, String name, String timeLeft, String date) {
        DocumentReference docRef = service.fireStore
                .collection(USER_LIST)
                .document(service.auth.getCurrentUser().getUid())
                .collection(HISTORY_KEY)
                .document(orderId);

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put(PLACE_NAME, name);
        hashMap.put(SERVICE, RESTAURANT);
        hashMap.put(TIME, timeLeft);
        hashMap.put(DATE_LEFT, date);

        return Completable.create(emitter -> {
            docRef.set(hashMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onComplete();
                } else {
                    emitter.onError(new Throwable(task.getException()));
                }
            });
        });
    }

    public Completable dishServed(String restaurantId, String locationId, String readyDishDocId) {
        return Completable.create(emitter -> {
            service.fireStore
                    .collection(RESTAURANT_LIST)
                    .document(restaurantId)
                    .collection(RESTAURANT_LOCATION)
                    .document(locationId)
                    .collection(READY_DISHES)
                    .document(readyDishDocId)
                    .delete().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onComplete();
                        } else {
                            emitter.onError(new Throwable(task.getException()));
                        }
                    });
        });
    }

    public Observable<ReadyDishDto> addReadyDishesSnapshot(String restaurantId, String locationId, List<String> readyDishDocIds) {
        return Observable.create(emitter -> {
            service.fireStore
                    .collection(RESTAURANT_LIST)
                    .document(restaurantId)
                    .collection(RESTAURANT_LOCATION)
                    .document(locationId)
                    .collection(READY_DISHES)
                    .addSnapshotListener((value, error) -> {
                        if (value != null) {
                            for (QueryDocumentSnapshot query : value) {
                                if (query.get(WAITER).equals(service.auth.getCurrentUser().getUid()) && !readyDishDocIds.contains(query.getId())) {
                                    emitter.onNext(new ReadyDishDto(
                                            query.getString(TABLE_NUMBER),
                                            query.getString(COUNT),
                                            query.getString(DISH_NAME),
                                            query.getId()
                                    ));
                                }
                            }
                        } else {
                            emitter.onError(new Throwable("Value is null"));
                        }
                    });
        });
    }

    public Single<List<ReadyDishDto>> getReadyDishesToWaiter(String restaurantId, String locationId) {
        return Single.create(emitter -> {
            service.fireStore
                    .collection(RESTAURANT_LIST)
                    .document(restaurantId)
                    .collection(RESTAURANT_LOCATION)
                    .document(locationId)
                    .collection(READY_DISHES)
                    .get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> snapshots = task.getResult().getDocuments();
                            List<ReadyDishDto> dishDtos = new ArrayList<>();
                            for (DocumentSnapshot current : snapshots) {
                                if (current.getString(WAITER).equals(service.auth.getCurrentUser().getUid())) {
                                    dishDtos.add(new ReadyDishDto(
                                            current.getString(TABLE_NUMBER),
                                            current.getString(COUNT),
                                            current.getString(DISH_NAME),
                                            current.getId()
                                    ));
                                }
                            }
                            emitter.onSuccess(dishDtos);
                        } else {
                            emitter.onError(new Throwable(task.getException()));
                        }
                    });
        });
    }

    public Observable<DocumentSnapshot> addTableSnapshot(String tablePath) {
        return Observable.create(emitter -> {
            service.fireStore
                    .document(tablePath)
                    .addSnapshotListener((value, error) -> {
                        if (value != null) {
                            emitter.onNext(value);
                        } else {
                            emitter.onError(new Throwable("Value is null"));
                        }
                    });
        });
    }

    public Completable finishOrder(String orderPath, String tableId, boolean isCook) {
        return Completable.create(emitter -> {
            String orderId = service.fireStore.document(orderPath).getId();
            DocumentReference tableDoc = service.fireStore.document(orderPath).getParent().getParent().collection(TABLE_LIST).document(tableId);
            service.fireStore.document(orderPath).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    String waiterId = task.getResult().getString(WAITER);
                    CollectionReference collRef = service.fireStore.document(orderPath).getParent().getParent().collection(WAITERS);
                    removeOrderFromWaiter(collRef, waiterId, orderId).addOnCompleteListener(taskRemove -> {
                        if (taskRemove.isSuccessful()) {
                            deleteOrderFromTable(tableDoc).addOnCompleteListener(taskUpdate -> {
                                if (taskUpdate.isSuccessful()) {
                                    deleteOrderDocumentByPath(orderPath, emitter, isCook);
                                }
                            });
                        } else {
                            emitter.onError(new Throwable(taskRemove.getException()));
                        }
                    });
                } else {
                    emitter.onError(new Throwable(task.getException()));
                }
            });
        });
    }

    private Task<Void> deleteOrderFromTable(DocumentReference tableDoc) {
        return tableDoc.update(ORDER_ID, NO_ORDER);
    }

    public Single<Integer> addToReadyDishes(String orderDishId, String tableNumber, String dishName, String count, String orderPath) {
        return Single.create(emitter -> {
            DocumentReference locationRef = service.fireStore.document(orderPath).getParent().getParent();
            CollectionReference collRef = service.fireStore.document(orderPath).collection(DISHES);
            String orderId = service.fireStore.document(orderPath).getId();
            assert locationRef != null;
            DocumentReference docRef = locationRef
                    .collection(READY_DISHES)
                    .document(orderId + "_" + orderDishId);

            HashMap<String, String> dish = new HashMap<>();
            service.fireStore.document(orderPath).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    dish.put(WAITER, task.getResult().getString(WAITER));
                    dish.put(COUNT, count);
                    dish.put(TABLE_NUMBER, tableNumber);
                    dish.put(DISH_NAME, dishName);
                    setReadyDish(orderDishId, emitter, docRef, dish, collRef);
                }
            });
        });
    }

    public Completable takeOrder(String restaurantId, String locationId, String orderId) {
        return Completable.create(emitter -> {
            service.fireStore
                    .collection(RESTAURANT_LIST)
                    .document(restaurantId)
                    .collection(RESTAURANT_LOCATION)
                    .document(locationId)
                    .collection(ACTIVE_ORDERS)
                    .document(orderId)
                    .update(IS_TAKEN, service.auth.getCurrentUser().getUid())
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onComplete();
                        }
                    });
        });
    }

    public Single<List<OrderShortDto>> getShortDetailsRestaurantOrders(String restaurantId, String locationId) {
        return Single.create(emitter -> {
            CollectionReference collRef = service.fireStore
                    .collection(RESTAURANT_LIST)
                    .document(restaurantId)
                    .collection(RESTAURANT_LOCATION)
                    .document(locationId)
                    .collection(ACTIVE_ORDERS);

            collRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    List<OrderShortDto> dtos = new ArrayList<>();
                    List<DocumentSnapshot> snapshots = task.getResult().getDocuments();
                    for (DocumentSnapshot current : snapshots) {
                        String path = collRef.document(current.getId()).getPath();
                        dtos.add(new OrderShortDto(
                                current.getId(),
                                path,
                                current.getString(TABLE_NUMBER),
                                current.getString(DISHES_COUNT),
                                current.getString(IS_TAKEN)
                        ));
                    }
                    emitter.onSuccess(dtos);
                }
            });
        });
    }

    public Single<OrderTableDetailsDto> getOrderByIds(String restaurantId, String locationId, String orderId) {
        return Single.create(emitter -> {
            DocumentReference docLocation = service.fireStore
                    .collection(RESTAURANT_LIST)
                    .document(restaurantId)
                    .collection(RESTAURANT_LOCATION)
                    .document(locationId);

            DocumentReference orderDoc = docLocation.collection(ACTIVE_ORDERS).document(orderId);

            orderDoc.get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot snapshot = task.getResult();
                            List<DishShortInfoDto> dtos = new ArrayList<>();
                            String cook = getCook(snapshot.getString(COOK), docLocation);
                            String waiter = getWaiter(snapshot.getString(WAITER), docLocation);
                            if (waiter != null) {
                                getDishesShortInfo(dtos, orderDoc);
                                emitter.onSuccess(new OrderTableDetailsDto(waiter, cook, dtos));
                            } else {
                                emitter.onSuccess(new OrderTableDetailsDto(NO_WAITER, NO_COOK, Collections.emptyList()));
                            }
                        }
                    });
        });
    }

    public Single<OrderDto> getOrderByPath(String path) {
        return Single.create(emitter -> {
            DocumentReference docRef = service.fireStore.document(path);
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult();
                    DocumentReference parentDoc =
                            snapshot.getReference()
                                    .getParent()
                                    .getParent()
                                    .getParent()
                                    .getParent();

                    CollectionReference dishes = docRef.collection(DISHES);
                    getDishes(path, emitter, dishes, parentDoc, snapshot);
                }
            });


        });
    }

    private void deleteOrderDocumentByPath(String orderPath, CompletableEmitter emitter, boolean isCook) {
        DocumentReference locationRef = service.fireStore.document(orderPath).getParent().getParent();
        assert locationRef != null;
        locationRef.get().addOnCompleteListener(taskGet -> {
            if (taskGet.isSuccessful()) {
                int currentCount = Integer.parseInt(taskGet.getResult().getString(ACTIVE_ORDERS));
                locationRef.update(ACTIVE_ORDERS, String.valueOf(currentCount - 1)).addOnCompleteListener(taskUpdate -> {
                    if (taskUpdate.isSuccessful()) {
                        service.fireStore.document(orderPath).delete().addOnCompleteListener(taskDocument -> {
                            if (taskDocument.isSuccessful()) {
                                if (!isCook) {
                                    UserDto localUser = UserDatabaseProvider.getUser();
                                    AnonymousUserDto localAnonymous = AnonymousUserProvider.getUser();
                                    if (localUser != null) {
                                        UserDatabaseProvider.updateRestaurantVisitor(NOT_RESTAURANT_VISITOR);
                                    } else if (localAnonymous != null) {
                                        AnonymousUserProvider.updateRestaurantVisitor(NOT_RESTAURANT_VISITOR);
                                    }
                                }
                                emitter.onComplete();
                            } else {
                                emitter.onError(new Throwable(taskDocument.getException()));
                            }
                        });
                    } else {
                        emitter.onError(new Throwable(taskUpdate.getException()));
                    }
                });
            } else {
                emitter.onError(new Throwable(taskGet.getException()));
            }
        });

    }

    private void getDishes(String path, SingleEmitter<OrderDto> emitter, CollectionReference dishes, DocumentReference parentDoc, DocumentSnapshot snapshot) {
        dishes.get().addOnCompleteListener(taskDishes -> {
            List<ActiveOrderDishDto> dtos = new ArrayList<>();
            List<DocumentSnapshot> dishesShapshot = taskDishes.getResult().getDocuments();

            addDishesDtos(dishesShapshot, dtos);
            assert parentDoc != null;
            getParentDocument(path, emitter, parentDoc, snapshot, dtos);
        });
    }

    private void getParentDocument(String path, SingleEmitter<OrderDto> emitter, DocumentReference parentDoc, DocumentSnapshot snapshot, List<ActiveOrderDishDto> dtos) {
        parentDoc.get().addOnCompleteListener(taskName -> {
            if (taskName.isSuccessful()) {
                DocumentSnapshot snapshotParent = taskName.getResult();
                String name = snapshotParent.getString(RESTAURANT_NAME);
                String restaurantId = snapshotParent.getId();

                emitter.onSuccess(new OrderDto(
                        snapshot.getId(),
                        snapshot.getString(TABLE_ID),
                        path,
                        snapshot.getString(TABLE_NUMBER),
                        restaurantId,
                        name,
                        snapshot.getString(TOTAL_PRICE),
                        snapshot.getString(IS_TAKEN),
                        dtos
                ));
            } else {
                emitter.onError(new Throwable(taskName.getException()));
            }
        });
    }

    private void addDishesDtos(List<DocumentSnapshot> dishesShapshot, List<ActiveOrderDishDto> dtos) {
        for (DocumentSnapshot current : dishesShapshot) {
            List<String> toppings = (List<String>) current.get(TOPPINGS);
            List<String> toRemove = (List<String>) current.get(INGREDIENT_TO_REMOVE);
            List<String> requiredChoice = (List<String>) current.get(REQUIRED_CHOICES);

            String price = current.getString(DISH_PRICE);
            String documentDishId = current.getId();
            String name = current.getString(DISH_NAME);
            String weight = current.getString(DISH_WEIGHT_OR_COUNT);
            String dishId = current.getString(DISH_ID);
            boolean isDone = Boolean.TRUE.equals(current.getBoolean(IS_DONE));
            dtos.add(new ActiveOrderDishDto(
                    documentDishId, dishId, current.getString(COUNT),
                    name, weight, price, isDone,
                    toppings, requiredChoice, toRemove
            ));
        }
    }

    public Completable addToTableListOrder(String path, String orderId) {
        return Completable.create(emitter -> {
            DocumentReference docRef = service.fireStore.document(path);
            docRef.update(ORDER_ID, orderId).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    CartProvider.deleteCart();
                    emitter.onComplete();
                } else {
                    emitter.onError(new Throwable(task.getException()));
                }
            });
        });
    }

    public Single<String> addToActiveOrders(String restaurantId, String tableId, String path, String orderId, String totalPrice, List<OrderDishesModel> models) {
        return Single.create(emitter -> {

            DocumentReference pathDoc = service.fireStore.document(path);

            pathDoc.get().addOnCompleteListener(task -> {
                String tableNumber;
                if (task.isSuccessful()) {
                    tableNumber = task.getResult().getString(TABLE_NUMBER);
                } else {
                    tableNumber = null;
                }

                String locationId = pathDoc.getParent().getParent().getId();

                DocumentReference locationDoc = service.fireStore.collection(RESTAURANT_LIST)
                        .document(restaurantId)
                        .collection(RESTAURANT_LOCATION)
                        .document(locationId);

                DocumentReference docRef =
                        locationDoc
                                .collection(ACTIVE_ORDERS)
                                .document(orderId);

                String orderPath = docRef.getPath();
                CollectionReference collRef = docRef.collection(DISHES);

                CollectionReference collRefLocationWaiters = service.fireStore.collection(RESTAURANT_LIST)
                        .document(restaurantId)
                        .collection(RESTAURANT_LOCATION)
                        .document(locationId)
                        .collection(WAITERS);

                setOrder(restaurantId, orderId, tableId, totalPrice, models, emitter, locationId, collRefLocationWaiters, tableNumber, docRef, collRef, locationDoc, orderPath);
            });
        });
    }

    private void setOrder(String restaurantId, String orderId, String tableId, String totalPrice, List<OrderDishesModel> models, SingleEmitter<String> emitter, String locationId, CollectionReference collRefLocationWaiters, String tableNumber, DocumentReference docRef, CollectionReference collRef, DocumentReference locationDoc, String orderPath) {
        final String[] minId = {null};
        CollectionReference collRefWaiter = service.fireStore
                .collection(RESTAURANT_LIST)
                .document(restaurantId)
                .collection(RESTAURANT_LOCATION)
                .document(locationId)
                .collection(WAITERS);

        collRefWaiter.get().addOnCompleteListener(taskGet -> {
            if (taskGet.isSuccessful()) {
                List<DocumentSnapshot> snapshots = taskGet.getResult().getDocuments();
                minId[0] = getWaiterId(snapshots);
                String waiterId = minId[0];

                addOrderToWaiterDocument(collRefLocationWaiters, waiterId, orderId).addOnCompleteListener(taskAdding -> {
                    if (taskAdding.isSuccessful()) {
                        HashMap<String, Object> order = new HashMap<>();
                        order.put(TABLE_NUMBER, tableNumber);
                        order.put(TABLE_ID, tableId);
                        order.put(VISITOR, service.auth.getCurrentUser().getUid());
                        order.put(TOTAL_PRICE, totalPrice);
                        order.put(IS_TAKEN, NOT_TAKEN);
                        order.put(WAITER, waiterId);
                        order.put(DISHES_COUNT, String.valueOf(models.size()));

                        setOrder(models, emitter, docRef, order, collRef, locationDoc, orderPath);
                    }
                });
            }
        });
    }

    private void setOrder(List<OrderDishesModel> models, SingleEmitter<String> emitter, DocumentReference docRef, HashMap<String, Object> order, CollectionReference collRef, DocumentReference locationDoc, String orderPath) {
        docRef.set(order).addOnCompleteListener(taskOrder -> {
            if (taskOrder.isSuccessful()) {
                setOrderDishesDocument(models, collRef);

                locationDoc.get().addOnCompleteListener(taskGet -> {
                    if (taskGet.isSuccessful()) {
                        int currentCount = Integer.parseInt(taskGet.getResult().getString(ACTIVE_ORDERS));
                        locationDoc.update(ACTIVE_ORDERS, String.valueOf(currentCount + 1)).addOnCompleteListener(taskUpdate -> {
                            if (taskUpdate.isSuccessful()) {
                                emitter.onSuccess(orderPath);
                            } else {
                                emitter.onError(new Throwable(taskUpdate.getException()));
                            }
                        });
                    } else {
                        emitter.onError(new Throwable(taskGet.getException()));
                    }
                });
            } else {
                emitter.onError(new Throwable(taskOrder.getException()));
            }
        });
    }

    private void setReadyDish(String orderDishId, SingleEmitter<Integer> emitter, DocumentReference docRef, HashMap<String, String> dish, CollectionReference collRef) {
        docRef.set(dish).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                updateIsDone(orderDishId, emitter, collRef);
            }
        });
    }

    private void updateIsDone(String orderDishId, SingleEmitter<Integer> emitter, CollectionReference collRef) {
        collRef.document(orderDishId).update(IS_DONE, true)
                .addOnCompleteListener(taskUpdate -> {
                    if (taskUpdate.isSuccessful()) {
                        getDishes(emitter, collRef);
                    } else {
                        emitter.onError(new Throwable(taskUpdate.getException()));
                    }
                });
    }

    private void getDishes(SingleEmitter<Integer> emitter, CollectionReference collRef) {
        collRef.get().addOnCompleteListener(taskDocument -> {
            if (taskDocument.isSuccessful()) {
                List<DocumentSnapshot> documentSnapshots = taskDocument.getResult().getDocuments();
                int size = 0;
                for (DocumentSnapshot snapshot : documentSnapshots) {
                    boolean isDone = Boolean.TRUE.equals(snapshot.getBoolean(IS_DONE));
                    if (!isDone) {
                        size++;
                    }
                }
                Log.i("Size", size + "");
                emitter.onSuccess(size);
            } else {
                emitter.onError(new Throwable(taskDocument.getException()));
            }
        });
    }

    private Task<Void> addOrderToWaiterDocument(CollectionReference collRef, String waiterId, String orderId) {
        return collRef.document(waiterId).update(ACTIVE_ORDERS_COUNT, FieldValue.arrayUnion(orderId));
    }

    private Task<Void> removeOrderFromWaiter(CollectionReference collRef, String waiterId, String orderId) {
        return collRef.document(waiterId).update(ACTIVE_ORDERS_COUNT, FieldValue.arrayRemove(orderId));
    }

    private String getWaiterId(List<DocumentSnapshot> snapshots) {
        int minOrders = -1;
        String minId = null;
        for (DocumentSnapshot current : snapshots) {
            if (Boolean.TRUE.equals(current.getBoolean(IS_WORKING))) {
                List<String> orders = ((List<String>) current.get(ACTIVE_ORDERS_COUNT));
                assert orders != null;
                int currentOrders = orders.size();
                if (minOrders == -1 || currentOrders < minOrders) {
                    minOrders = currentOrders;
                    minId = current.getId();
                }
            }
        }
        return minId;
    }

    private void setOrderDishesDocument(List<OrderDishesModel> models, CollectionReference collRef) {
        for (int i = 0; i < models.size(); i++) {
            OrderDishesModel current = models.get(i);
            DocumentReference docDish = collRef.document("#" + (i + 1));
            List<String> names = new ArrayList<>();
            List<String> prices = new ArrayList<>();

            for (VariantCartModel model : current.getToppings()) {
                names.add(model.getName());
                prices.add(model.getPrice());
            }

            int totalPrice = Integer.parseInt(current.getPrice());

            for (String currentPrice : prices) {
                totalPrice += Integer.parseInt(currentPrice);
            }

            HashMap<String, Object> dishDocument = new HashMap<>();
            dishDocument.put(DISH_WEIGHT_OR_COUNT, current.getAmount());
            dishDocument.put(DISH_NAME, current.getName());
            dishDocument.put(IS_DONE, false);
            dishDocument.put(DISH_ID, current.getDishId());
            dishDocument.put(COUNT, current.getAmount());
            dishDocument.put(TOPPINGS, names);
            dishDocument.put(INGREDIENT_TO_REMOVE, current.getToRemove());
            dishDocument.put(REQUIRED_CHOICES, current.getRequireChoices());
            dishDocument.put(DISH_PRICE, String.valueOf(totalPrice));

            docDish.set(dishDocument);
        }
    }

    private String getWaiter(String waiterId, DocumentReference locationDoc) {
        final String[] name = new String[1];
        try {
            locationDoc.collection(WAITERS).document(waiterId).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    name[0] = task.getResult().getString(USER_NAME_KEY);
                }
            });
        } catch (NullPointerException e){
            name[0] = null;
        }
        return name[0];
    }

    private String getCook(String cookId, DocumentReference locationDoc) {
        final String[] name = new String[1];
        try {
            locationDoc.collection(COOKS).document(cookId).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    name[0] = task.getResult().getString(USER_NAME_KEY);
                }
            });
        } catch (NullPointerException e){
            name[0] = NO_COOK;
        }
        return name[0];
    }

    private void getDishesShortInfo(List<DishShortInfoDto> dtos, DocumentReference docRef) {
        docRef.collection(DISHES).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<DocumentSnapshot> snapshots = task.getResult().getDocuments();
                for (DocumentSnapshot current : snapshots) {
                    dtos.add(new DishShortInfoDto(current.getString(DISH_NAME), current.getString(COUNT)));
                }
            }
        });
    }
}
