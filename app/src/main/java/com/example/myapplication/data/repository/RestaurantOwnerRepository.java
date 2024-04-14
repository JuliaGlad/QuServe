package com.example.myapplication.data.repository;

import static com.example.myapplication.di.DI.service;
import static com.example.myapplication.presentation.utils.Utils.COMPANY;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_NAME;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_PHONE;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_SERVICE;
import static com.example.myapplication.presentation.utils.Utils.EMPLOYEES;
import static com.example.myapplication.presentation.utils.Utils.JPG;
import static com.example.myapplication.presentation.utils.Utils.PDF;
import static com.example.myapplication.presentation.utils.Utils.PROFILE_UPDATED_AT;
import static com.example.myapplication.presentation.utils.Utils.URI;
import static com.example.myapplication.presentation.utils.Utils.USER_LIST;
import static com.example.myapplication.presentation.utils.constants.Restaurant.CATEGORY_COUNT;
import static com.example.myapplication.presentation.utils.constants.Restaurant.CATEGORY_DISHES;
import static com.example.myapplication.presentation.utils.constants.Restaurant.CATEGORY_NAME;
import static com.example.myapplication.presentation.utils.constants.Restaurant.COOKS_COUNT;
import static com.example.myapplication.presentation.utils.constants.Restaurant.COOK_QR_CODE;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISH_ESTIMATED_TIME_COOKING;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISH_NAME;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISH_PRICE;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISH_WEIGHT_OR_COUNT;
import static com.example.myapplication.presentation.utils.constants.Restaurant.INGREDIENTS;
import static com.example.myapplication.presentation.utils.constants.Restaurant.INGREDIENT_TO_REMOVE;
import static com.example.myapplication.presentation.utils.constants.Restaurant.LOCATION;
import static com.example.myapplication.presentation.utils.constants.Restaurant.LOCATION_CITY;
import static com.example.myapplication.presentation.utils.constants.Restaurant.NECESSARY_CHOICES;
import static com.example.myapplication.presentation.utils.constants.Restaurant.NECESSARY_CHOICE_VARIANT;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_EMAIL;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_LIST;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_LOCATION;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_LOGO;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_MENU;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_MENUS_PATH;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_NAME;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_OWNER;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_PATH;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_PHONE;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TABLE_LIST;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TABLE_NUMBER;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TABLE_QR_CODES;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TOPPINGS;
import static com.example.myapplication.presentation.utils.constants.Restaurant.WAITERS_COUNT;
import static com.example.myapplication.presentation.utils.constants.Restaurant.WAITER_QR_CODE;

import android.net.Uri;
import android.util.Log;

import com.example.myapplication.data.dto.CategoryDto;
import com.example.myapplication.data.dto.DishDto;
import com.example.myapplication.data.dto.ImageDto;
import com.example.myapplication.data.dto.LocationDto;
import com.example.myapplication.data.dto.NecessaryChoiceDto;
import com.example.myapplication.data.dto.RestaurantDto;
import com.example.myapplication.data.dto.TableDto;
import com.example.myapplication.presentation.restaurantMenu.model.NecessaryChoiceModel;
import com.example.myapplication.presentation.restaurantMenu.model.VariantsModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.storage.StorageReference;

import java.io.File;
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

public class RestaurantOwnerRepository {

    public Single<List<Task<Uri>>> getNecessaryChoicesImages(String restaurantId, String dishId, List<String> names){

        List<Task<Uri>> tasks = new ArrayList<>();

        return Single.create(emitter -> {
            for (String name: names) {
                StorageReference reference = service.storageReference
                        .child(RESTAURANT_MENUS_PATH)
                        .child(restaurantId + "/")
                        .child(dishId + "/")
                        .child(NECESSARY_CHOICES + "/")
                        .child(name + JPG);
                tasks.add(reference.getDownloadUrl());
            }
            emitter.onSuccess(tasks);
        });
    }

    public Single<List<Task<Uri>>> getToppingsImages(String restaurantId, String dishId, List<String> names){
        List<Task<Uri>> tasks = new ArrayList<>();

        return Single.create(emitter -> {
            for (String name: names) {
                StorageReference reference = service.storageReference
                        .child(RESTAURANT_MENUS_PATH)
                        .child(restaurantId + "/")
                        .child(dishId + "/")
                        .child(TOPPINGS + "/")
                        .child(name + JPG);
                tasks.add(reference.getDownloadUrl());
            }
            emitter.onSuccess(tasks);
        });
    }

    public Single<List<Task<Uri>>> getIngredientsToRemoveImages(String restaurantId, String dishId, List<String> names){
        List<Task<Uri>> tasks = new ArrayList<>();

        return Single.create(emitter -> {
            for (String name: names) {
                StorageReference reference = service.storageReference
                        .child(RESTAURANT_MENUS_PATH)
                        .child(restaurantId + "/")
                        .child(dishId + "/")
                        .child(INGREDIENT_TO_REMOVE + "/")
                        .child(name + JPG);
                tasks.add(reference.getDownloadUrl());
            }
            emitter.onSuccess(tasks);
        });
    }

    public Single<List<Task<Uri>>> getDishesImages(String restaurantId, String dishId, List<String> dishNames){
        List<Task<Uri>> tasks = new ArrayList<>();

        return Single.create(emitter -> {
            for (String name : dishNames) {
                StorageReference reference = service.storageReference
                        .child(RESTAURANT_MENUS_PATH)
                        .child(restaurantId + "/")
                        .child(dishId + "/")
                        .child(name + JPG);

                tasks.add(reference.getDownloadUrl());
            }
            emitter.onSuccess(tasks);
        });
    }

    public Single<List<Task<Uri>>> getCategoriesImages(String restaurantId, List<String> categoriesNames){

        List<Task<Uri>> tasks = new ArrayList<>();

        return Single.create(emitter -> {
            for (String category : categoriesNames) {
                StorageReference reference = service.storageReference
                        .child(RESTAURANT_MENUS_PATH)
                        .child(restaurantId + "/")
                        .child(category + JPG);
                tasks.add(reference.getDownloadUrl());
            }
            emitter.onSuccess(tasks);
        });
    }

    public Single<List<DishDto>> getDishes(String restaurantId, String categoryId) {
        CollectionReference collRef = service.fireStore
                .collection(RESTAURANT_LIST)
                .document(restaurantId)
                .collection(RESTAURANT_MENU)
                .document(categoryId)
                .collection(CATEGORY_DISHES);
        return Single.create(emitter -> {
            collRef
                    .get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> snapshots = task.getResult().getDocuments();
                            List<DishDto> dtos = new ArrayList<>();
                            List<NecessaryChoiceDto> choices = new ArrayList<>();
                            for (DocumentSnapshot current : snapshots) {

                                collRef.document(current.getId()).collection(NECESSARY_CHOICES)
                                        .get().addOnCompleteListener(taskChoices -> {
                                            if (taskChoices.isSuccessful()) {
                                                List<DocumentSnapshot> snapshotChoices = taskChoices.getResult().getDocuments();
                                                for (int i = 0; i < snapshotChoices.size(); i++) {
                                                    DocumentSnapshot currentChoice = snapshotChoices.get(i);
                                                    choices.add(new NecessaryChoiceDto(
                                                            currentChoice.getId(),
                                                            (List<String>) currentChoice.get(NECESSARY_CHOICE_VARIANT)
                                                    ));
                                                }
                                            }
                                        });

                                dtos.add(new DishDto(
                                        current.getId(),
                                        current.getString(DISH_NAME),
                                        (List<String>) current.get(INGREDIENTS),
                                        current.getString(DISH_WEIGHT_OR_COUNT),
                                        current.getString(DISH_PRICE),
                                        current.getString(DISH_ESTIMATED_TIME_COOKING),
                                        choices,
                                        (List<String>) current.get(INGREDIENT_TO_REMOVE),
                                        (List<String>) current.get(TOPPINGS)
                                ));
                            }
                            emitter.onSuccess(dtos);
                        } else {
                            emitter.onSuccess(Collections.emptyList());
                        }
                    });
        });
    }

    public Single<List<CategoryDto>> getCategories(String restaurantId) {
        return Single.create(emitter -> {
            service.fireStore
                    .collection(RESTAURANT_LIST)
                    .document(restaurantId)
                    .collection(RESTAURANT_MENU)
                    .get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> snapshots = task.getResult().getDocuments();
                            List<CategoryDto> categoryDtos = new ArrayList<>();

                            for (DocumentSnapshot current : snapshots) {
                                categoryDtos.add(new CategoryDto(
                                        current.getId(),
                                        current.getString(CATEGORY_NAME)
                                ));
                            }

                            emitter.onSuccess(categoryDtos);

                        } else {
                            emitter.onSuccess(Collections.emptyList());
                        }
                    });
        });
    }

    public Completable uploadDishImage(String restaurantId, String dishId, String dishName, Uri uri) {
        StorageReference reference = service.storageReference
                .child(RESTAURANT_MENUS_PATH)
                .child(restaurantId + "/")
                .child(dishId + "/")
                .child(dishName + JPG);

        return Completable.create(emitter -> {
            reference.putFile(uri)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onComplete();
                        }
                    });
        });
    }

    public Completable addCompanyDish(
            String restaurantId,
            String categoryName,
            String dishId,
            String name,
            List<String> ingredients,
            String weightCount,
            String price,
            String estimatedTimeCooking,
            List<NecessaryChoiceModel> necessaryChoices,
            List<VariantsModel> toRemove,
            List<VariantsModel> toppings) {

        DocumentReference menu = service.fireStore
                .collection(RESTAURANT_LIST)
                .document(restaurantId)
                .collection(RESTAURANT_MENU)
                .document(categoryName)
                .collection(CATEGORY_DISHES)
                .document(dishId);

        HashMap<String, Object> dish = new HashMap<>();
        dish.put(DISH_NAME, name);
        dish.put(INGREDIENTS, ingredients.toString().split(","));
        dish.put(DISH_WEIGHT_OR_COUNT, weightCount);
        dish.put(DISH_PRICE, price);
        dish.put(DISH_ESTIMATED_TIME_COOKING, estimatedTimeCooking);
        if (toppings.size() != 0) {
            ArrayList<String> names = new ArrayList<>();
            for (VariantsModel current : toppings) {
                names.add(current.getName());
            }
            dish.put(TOPPINGS, names);
        }
        if (toRemove.size() != 0) {
            ArrayList<String> names = new ArrayList<>();
            for (VariantsModel current : toRemove) {
                names.add(current.getName());
            }
            dish.put(INGREDIENT_TO_REMOVE, names);
        }

        return Completable.create(emitter -> {
            menu.set(dish).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    if (necessaryChoices.size() > 0) {
                        CollectionReference collRef = menu.collection(NECESSARY_CHOICES);
                        for (NecessaryChoiceModel current : necessaryChoices) {

                            DocumentReference docRef = collRef.document(current.getName());
                            List<VariantsModel> variantsModels = current.getVariants();
                            List<String> variantNames = new ArrayList<>();

                            for (VariantsModel currentModel : variantsModels) {
                                variantNames.add(currentModel.getName());
                            }

                            HashMap<String, List<String>> variants = new HashMap<>();
                            variants.put(NECESSARY_CHOICE_VARIANT, variantNames);

                            docRef.set(variants);
                        }
                    }
                    emitter.onComplete();
                }
            });
        });
    }

    public Completable uploadToRemoveImages(List<VariantsModel> toRemove, String restaurantId, String dishId) {
        StorageReference basic = service.storageReference
                .child(RESTAURANT_MENUS_PATH)
                .child(restaurantId + "/")
                .child(dishId + "/")
                .child(INGREDIENT_TO_REMOVE + "/");

        return Completable.create(emitter -> {
            for (VariantsModel models : toRemove) {
                basic.child(models.getName() + JPG).putFile(models.getUri());
            }
            emitter.onComplete();
        });
    }

    public Completable uploadToppingImages(List<VariantsModel> toppings, String restaurantId, String dishId) {
        StorageReference basic = service.storageReference
                .child(RESTAURANT_MENUS_PATH)
                .child(restaurantId + "/")
                .child(dishId + "/")
                .child(TOPPINGS + "/");

        return Completable.create(emitter -> {
            for (VariantsModel models : toppings) {
                basic.child(models.getName() + JPG).putFile(models.getUri());
            }
            emitter.onComplete();
        });
    }

    public Completable uploadNecessaryChoiceImages(List<NecessaryChoiceModel> necessaryChoices, String restaurantId, String dishId) {

        StorageReference basic = service.storageReference
                .child(RESTAURANT_MENUS_PATH)
                .child(restaurantId + "/")
                .child(dishId + "/")
                .child(NECESSARY_CHOICES + "/");

        return Completable.create(emitter -> {
            for (NecessaryChoiceModel current : necessaryChoices) {

                String choiceName = current.getName();
                List<VariantsModel> models = current.getVariants();

                for (VariantsModel model : models) {
                    basic.child(choiceName + "/")
                            .child(model.getName() + JPG)
                            .putFile(model.getUri());
                }
            }
            emitter.onComplete();
        });

    }

    public Completable uploadCategoryImage(Uri uri, String restaurantId, String categoryName) {
        return Completable.create(emitter -> {
            StorageReference reference = service.storageReference
                    .child(RESTAURANT_MENUS_PATH)
                    .child(restaurantId + "/")
                    .child(categoryName + JPG);

            reference.putFile(uri)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onComplete();
                        }
                    });
        });
    }

    public Completable addMenuCategory(String restaurantId, String categoryId, String categoryName) {
        DocumentReference menu = service.fireStore
                .collection(RESTAURANT_LIST)
                .document(restaurantId)
                .collection(RESTAURANT_MENU)
                .document(categoryId);

        HashMap<String, String> category = new HashMap<>();
        category.put(CATEGORY_NAME, categoryName);

        return Completable.create(emitter -> {
            menu.set(category).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    menu.get().addOnCompleteListener(taskGet -> {
                        if (taskGet.isSuccessful()) {
                            try {
                                String count = taskGet.getResult().getString(CATEGORY_COUNT);
                                menu.update(CATEGORY_COUNT, String.valueOf(Integer.parseInt(count) + 1));
                            } catch (NullPointerException e) {
                                menu.update(CATEGORY_COUNT, "1");
                            }
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

    public Observable<DocumentSnapshot> addSnapshot(String restaurantId) {
        DocumentReference docRef = service.fireStore
                .collection(RESTAURANT_LIST)
                .document(restaurantId);

        return Observable.create(emitter -> {
            docRef.addSnapshotListener((value, error) -> {
                if (value != null) {
                    emitter.onNext(value);
                }
            });
        });
    }

    public Completable deleteTable(String restaurantId, String locationId, String tableId) {
        return Completable.create(emitter -> {
            service.fireStore
                    .collection(RESTAURANT_LIST)
                    .document(restaurantId)
                    .collection(RESTAURANT_LOCATION)
                    .document(locationId)
                    .collection(TABLE_LIST)
                    .document(tableId).delete().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onComplete();
                        }
                    });
        });
    }

    public Completable deleteQrCodeJpg(String tableId) {
        return Completable.create(emitter -> {
            service.storageReference
                    .child(TABLE_QR_CODES)
                    .child(tableId + "/" + tableId + JPG)
                    .delete().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onComplete();
                        }
                    });
        });
    }

    public Completable deleteQrCodePdf(String tableId) {
        return Completable.create(emitter -> {
            service.storageReference
                    .child(TABLE_QR_CODES)
                    .child(tableId + "/" + tableId + PDF)
                    .delete().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onComplete();
                        }
                    });
        });
    }

    public Single<String> getSingleTableById(String restaurantId, String locationId, String tableId) {
        return Single.create(emitter -> {
            service.fireStore
                    .collection(RESTAURANT_LIST)
                    .document(restaurantId)
                    .collection(RESTAURANT_LOCATION)
                    .document(locationId)
                    .collection(TABLE_LIST)
                    .document(tableId).get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onSuccess(task.getResult().getString(TABLE_NUMBER));
                        }
                    });
        });
    }

    public Single<Uri> getTableQrCodePdf(String tableId) {
        return Single.create(emitter -> {
            service.storageReference
                    .child(TABLE_QR_CODES)
                    .child(tableId + "/" + tableId + PDF)
                    .getDownloadUrl().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onSuccess(task.getResult());
                        }
                    });
        });
    }

    public Single<Uri> getTableQrCodeJpg(String tableId) {
        return Single.create(emitter -> {
            service.storageReference
                    .child(TABLE_QR_CODES)
                    .child(tableId + "/" + tableId + JPG)
                    .getDownloadUrl().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onSuccess(task.getResult());
                        }
                    });
        });
    }

    public Completable uploadTableQrCodeFireStorage(String tableId, byte[] data) {
        StorageReference reference =
                service.storageReference
                        .child(TABLE_QR_CODES)
                        .child(tableId + "/" + tableId + JPG);

        return Completable.create(emitter -> {
            reference.putBytes(data).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onComplete();
                }
            });
        });
    }

    public Completable uploadTablePdfToFireStorage(File file, String tableId) {
        return Completable.create(emitter -> {
            StorageReference reference =
                    service.storageReference
                            .child(TABLE_QR_CODES)
                            .child(tableId + "/" + tableId + PDF);

            reference.putFile(Uri.fromFile(file)).addOnCompleteListener(task -> emitter.onComplete())
                    .addOnFailureListener(e -> emitter.onError(new Throwable(e.getMessage())));
        });
    }

    public Single<String> addTable(String locationId, String restaurantId, String tableId, String tableNumber) {
        Log.i("DATA", restaurantId + locationId + tableId);
        DocumentReference docRef =
                service.fireStore
                        .collection(RESTAURANT_LIST)
                        .document(restaurantId)
                        .collection(RESTAURANT_LOCATION)
                        .document(locationId)
                        .collection(TABLE_LIST)
                        .document(tableId);

        String path = docRef.getPath();

        HashMap<String, Object> table = new HashMap<>();
        table.put(TABLE_NUMBER, tableNumber);

        return Single.create(emitter -> {
            docRef.set(table).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onSuccess(path);
                }
            });
        });
    }

    public Single<List<TableDto>> getTables(String restaurantId, String locationId) {
        return Single.create(emitter -> {
            service.fireStore
                    .collection(RESTAURANT_LIST)
                    .document(restaurantId)
                    .collection(RESTAURANT_LOCATION)
                    .document(locationId)
                    .collection(TABLE_LIST)
                    .get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> snapshots = task.getResult().getDocuments();
                            List<TableDto> tables = new ArrayList<>();
                            for (int i = 0; i < snapshots.size(); i++) {
                                DocumentSnapshot current = snapshots.get(i);
                                tables.add(new TableDto(
                                        current.getId(),
                                        current.getString(TABLE_NUMBER)
                                ));
                            }
                            emitter.onSuccess(tables);
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

        Log.d("Path", userCompany.getPath());

        Map<String, Object> restaurantUser = new HashMap<>();
        restaurantUser.put(COMPANY_NAME, restaurantName);
        restaurantUser.put(COMPANY_SERVICE, service);

        return Completable.create(emitter -> {
            userCompany.set(restaurantUser).addOnCompleteListener(taskUser -> {
                if (taskUser.isSuccessful()) {
                    emitter.onComplete();
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
                }
            });

        });
    }

    public Single<List<RestaurantDto>> getRestaurants() {
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

    public Completable updateRestaurantData(String restaurantId, String restaurantName, String phone) {
        return Completable.create(emitter -> {
            DocumentReference docRef = service.fireStore
                    .collection(RESTAURANT_LIST)
                    .document(restaurantId);

            FieldValue timestamp = FieldValue.serverTimestamp();
            docRef.update(
                    RESTAURANT_NAME, restaurantName,
                    COMPANY_PHONE, phone,
                    PROFILE_UPDATED_AT, timestamp
            ).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onComplete();
                }
            });
        });
    }

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
                            }
                        });
            } else {
                emitter.onComplete();
            }
        });

    }

    public Single<List<LocationDto>> getRestaurantLocations(String restaurantId) {
        return Single.create(emitter -> {
            service.fireStore
                    .collection(RESTAURANT_LIST)
                    .document(restaurantId)
                    .collection(RESTAURANT_LOCATION)
                    .get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> snapshots = task.getResult().getDocuments();
                            List<LocationDto> locationDtos = new ArrayList<>();
                            for (int i = 0; i < snapshots.size(); i++) {
                                DocumentSnapshot current = snapshots.get(i);
                                locationDtos.add(new LocationDto(
                                        current.getId(),
                                        current.getString(LOCATION),
                                        current.getString(LOCATION_CITY),
                                        current.getString(COOKS_COUNT),
                                        current.getString(WAITERS_COUNT)
                                ));
                            }
                            emitter.onSuccess(locationDtos);
                        }
                    });
        });
    }

    public Single<String> createRestaurantLocationDocument(String restaurantId, String locationId, String location, String city) {

        DocumentReference docRef =
                service.fireStore
                        .collection(RESTAURANT_LIST)
                        .document(restaurantId)
                        .collection(RESTAURANT_LOCATION)
                        .document(locationId);

        String path = docRef.getPath();

        Map<String, Object> locationMap = new HashMap<>();
        locationMap.put(LOCATION_CITY, city);
        locationMap.put(LOCATION, location);
        locationMap.put(COOKS_COUNT, "0");
        locationMap.put(WAITERS_COUNT, "0");

        return Single.create(emitter -> {
            docRef.set(locationMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onSuccess(path);
                }
            });
        });
    }

}
