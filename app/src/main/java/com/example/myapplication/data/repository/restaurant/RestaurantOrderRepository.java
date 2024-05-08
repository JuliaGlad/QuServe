package com.example.myapplication.data.repository.restaurant;

import static com.example.myapplication.di.DI.service;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.Utils.NOT_RESTAURANT_VISITOR;
import static com.example.myapplication.presentation.utils.Utils.USER_LIST;
import static com.example.myapplication.presentation.utils.constants.Restaurant.ACTIVE_ORDERS;
import static com.example.myapplication.presentation.utils.constants.Restaurant.COUNT;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISHES;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISHES_COUNT;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISH_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISH_NAME;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISH_PRICE;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISH_WEIGHT_OR_COUNT;
import static com.example.myapplication.presentation.utils.constants.Restaurant.INGREDIENT_TO_REMOVE;
import static com.example.myapplication.presentation.utils.constants.Restaurant.IS_DONE;
import static com.example.myapplication.presentation.utils.constants.Restaurant.IS_RESTAURANT_VISITOR;
import static com.example.myapplication.presentation.utils.constants.Restaurant.IS_TAKEN;
import static com.example.myapplication.presentation.utils.constants.Restaurant.NOT_TAKEN;
import static com.example.myapplication.presentation.utils.constants.Restaurant.ORDER_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.PATH;
import static com.example.myapplication.presentation.utils.constants.Restaurant.READY_DISHES;
import static com.example.myapplication.presentation.utils.constants.Restaurant.REQUIRED_CHOICES;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_LIST;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_LOCATION;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_MENU;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_NAME;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TABLE_NUMBER;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TOPPINGS;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TOPPING_NAME;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TOPPING_PRICE;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TOTAL_PRICE;
import static com.example.myapplication.presentation.utils.constants.Restaurant.VISITOR;

import android.util.Log;

import androidx.annotation.Nullable;

import com.example.myapplication.data.dto.ActiveOrderDishDto;
import com.example.myapplication.data.dto.OrderDto;
import com.example.myapplication.data.dto.OrderShortDto;
import com.example.myapplication.data.providers.CartProvider;
import com.example.myapplication.presentation.restaurantOrder.VariantCartModel;
import com.example.myapplication.presentation.restaurantOrder.restaurantCart.model.OrderDishesModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RestaurantOrderRepository {

    public Completable finishOrder(String orderPath) {
        return Completable.create(emitter -> {
            service.fireStore.document(orderPath).delete().addOnCompleteListener(taskDocument -> {
                if (taskDocument.isSuccessful()) {
                    emitter.onComplete();
                }
            });
        });
    }

    public Single<Integer> addToReadyDishes(String orderDishId, String tableNumber, String dishName, String orderPath) {
        return Single.create(emitter -> {
            DocumentReference locationRef = service.fireStore.document(orderPath).getParent().getParent();
            CollectionReference collRef = service.fireStore.document(orderPath).collection(DISHES);

            assert locationRef != null;
            DocumentReference docRef = locationRef
                    .collection(READY_DISHES)
                    .document(orderDishId);

            HashMap<String, String> dish = new HashMap<>();
            dish.put(TABLE_NUMBER, tableNumber);
            dish.put(DISH_NAME, dishName);

            docRef.set(dish).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    collRef.document(orderDishId).update(IS_DONE, true)
                            .addOnCompleteListener(taskUpdate -> {
                                if (taskUpdate.isSuccessful()) {

                                }
                            });
                    collRef.get().addOnCompleteListener(taskDocument -> {
                        if (taskDocument.isSuccessful()) {
                            List<DocumentSnapshot> documentSnapshots = taskDocument.getResult().getDocuments();
                            int size = 0;
                            for (DocumentSnapshot snapshot : documentSnapshots) {
                                boolean isDone = Boolean.TRUE.equals(snapshot.getBoolean(IS_DONE));
                                if (!isDone){
                                    size++;
                                }
                            }
                            emitter.onSuccess(size);
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
                String tableNumber = null;
                if (task.isSuccessful()) {
                    tableNumber = task.getResult().getString(TABLE_NUMBER);
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

                HashMap<String, Object> order = new HashMap<>();
                order.put(TABLE_NUMBER, tableNumber);
                order.put(VISITOR, service.auth.getCurrentUser().getUid());
                order.put(TOTAL_PRICE, totalPrice);
                order.put(IS_TAKEN, NOT_TAKEN);
                order.put(DISHES_COUNT, String.valueOf(models.size()));

                docRef.set(order).addOnCompleteListener(taskOrder -> {
                    if (taskOrder.isSuccessful()) {
                        setOrderDishesDocument(models, collRef);
                        emitter.onSuccess(orderPath);
                    }
                });
            });
        });
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
