package com.example.myapplication.data.repository.restaurant;

import static com.example.myapplication.di.DI.service;
import static com.example.myapplication.presentation.utils.constants.Restaurant.ACTIVE_ORDERS;
import static com.example.myapplication.presentation.utils.constants.Restaurant.ACTIVE_ORDERS_COUNT;
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

import com.example.myapplication.data.dto.restaurant.ActiveOrderDishDto;
import com.example.myapplication.data.dto.restaurant.OrderDto;
import com.example.myapplication.data.dto.restaurant.OrderShortDto;
import com.example.myapplication.data.dto.restaurant.ReadyDishDto;
import com.example.myapplication.data.providers.CartProvider;
import com.example.myapplication.presentation.restaurantOrder.VariantCartModel;
import com.example.myapplication.presentation.restaurantOrder.restaurantCart.model.OrderDishesModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class RestaurantOrderRepository {

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
                        }
                    });
        });
    }

    public Completable finishOrder(String orderPath) {
        return Completable.create(emitter -> {
            String orderId = service.fireStore.document(orderPath).getId();

            service.fireStore.document(orderPath).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    String waiterId = task.getResult().getString(WAITER);
                    CollectionReference collRef = service.fireStore.document(orderPath).getParent().getParent().collection(WAITERS);
                    removeOrderFromWaiter(collRef, waiterId, orderId).addOnCompleteListener(taskRemove -> {
                        if (task.isSuccessful()) {
                            service.fireStore.document(orderPath).delete().addOnCompleteListener(taskDocument -> {
                                if (taskDocument.isSuccessful()) {
                                    emitter.onComplete();
                                }
                            });
                        }
                    });
                }
            });
        });
    }

    public Single<Integer> addToReadyDishes(String orderDishId, String tableNumber, String dishName, String orderPath) {
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
                    dish.put(COUNT, task.getResult().getString(COUNT));
                }
            });
            dish.put(TABLE_NUMBER, tableNumber);
            dish.put(DISH_NAME, dishName);

            docRef.set(dish).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    collRef.document(orderDishId).update(IS_DONE, true)
                            .addOnCompleteListener(taskUpdate -> {
                                if (taskUpdate.isSuccessful()) {
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
                                            emitter.onSuccess(size);
                                        }
                                    });
                                }
                            });
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
                    dishes.get().addOnCompleteListener(taskDishes -> {
                        List<ActiveOrderDishDto> dtos = new ArrayList<>();
                        List<DocumentSnapshot> dishesShapshot = taskDishes.getResult().getDocuments();

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

                        assert parentDoc != null;

                        parentDoc.get().addOnCompleteListener(taskName -> {
                            if (taskName.isSuccessful()) {
                                DocumentSnapshot snapshotParent = taskName.getResult();
                                String name = snapshotParent.getString(RESTAURANT_NAME);
                                String restaurantId = snapshotParent.getId();

                                emitter.onSuccess(new OrderDto(
                                        snapshot.getId(),
                                        path,
                                        snapshot.getString(TABLE_NUMBER),
                                        restaurantId,
                                        name,
                                        snapshot.getString(TOTAL_PRICE),
                                        snapshot.getString(IS_TAKEN),
                                        dtos
                                ));
                            }
                        });


                    });
                }
            });


        });
    }

    public Completable addToTableListOrder(String path, String orderId) {
        return Completable.create(emitter -> {
            DocumentReference docRef = service.fireStore.document(path);

            HashMap<String, String> order = new HashMap<>();
            order.put(ORDER_ID, orderId);
            docRef.set(order).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    CartProvider.deleteCart();
                    emitter.onComplete();
                }
            });
        });
    }

    public Single<String> addToActiveOrders(String restaurantId, String path, String orderId, String totalPrice, List<OrderDishesModel> models) {
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

                DocumentReference docRef = service.fireStore.collection(RESTAURANT_LIST)
                        .document(restaurantId)
                        .collection(RESTAURANT_LOCATION)
                        .document(locationId)
                        .collection(ACTIVE_ORDERS)
                        .document(orderId);

                String orderPath = docRef.getPath();
                CollectionReference collRef = docRef.collection(DISHES);
                CollectionReference collRefLocationWaiters = service.fireStore.collection(RESTAURANT_LIST)
                        .document(restaurantId)
                        .collection(RESTAURANT_LOCATION)
                        .document(locationId)
                        .collection(WAITERS);

                String waiterId = getWaiter(restaurantId, locationId);
                addOrderToWaiterDocument(collRefLocationWaiters, waiterId, orderId).addOnCompleteListener(taskAdding -> {
                    if (taskAdding.isSuccessful()) {
                        HashMap<String, Object> order = new HashMap<>();
                        order.put(TABLE_NUMBER, tableNumber);
                        order.put(VISITOR, service.auth.getCurrentUser().getUid());
                        order.put(TOTAL_PRICE, totalPrice);
                        order.put(IS_TAKEN, NOT_TAKEN);
                        order.put(WAITER, waiterId);
                        order.put(DISHES_COUNT, String.valueOf(models.size()));

                        docRef.set(order).addOnCompleteListener(taskOrder -> {
                            if (taskOrder.isSuccessful()) {
                                setOrderDishesDocument(models, collRef);
                                emitter.onSuccess(orderPath);
                            }
                        });
                    }
                });
            });
        });
    }

    private String getWaiter(String restaurantId, String locationId) {
        final String[] minId = {null};
        CollectionReference collRef = service.fireStore
                .collection(RESTAURANT_LIST)
                .document(restaurantId)
                .collection(RESTAURANT_LOCATION)
                .document(locationId)
                .collection(WAITERS);

        collRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<DocumentSnapshot> snapshots = task.getResult().getDocuments();
                minId[0] = getWaiterId(snapshots);
            }
        });
        String waiterId = minId[0];
        return waiterId;
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
}
