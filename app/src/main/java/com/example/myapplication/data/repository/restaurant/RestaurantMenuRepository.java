package com.example.myapplication.data.repository.restaurant;

import static com.example.myapplication.di.DI.service;
import static com.example.myapplication.presentation.utils.Utils.JPG;
import static com.example.myapplication.presentation.utils.constants.Restaurant.CATEGORY_NAME;
import static com.example.myapplication.presentation.utils.constants.Restaurant.CHOICE_NAME;
import static com.example.myapplication.presentation.utils.constants.Restaurant.CHOICE_VARIANT;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISHES;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISH_ESTIMATED_TIME_COOKING;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISH_NAME;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISH_PRICE;
import static com.example.myapplication.presentation.utils.constants.Restaurant.DISH_WEIGHT_OR_COUNT;
import static com.example.myapplication.presentation.utils.constants.Restaurant.INGREDIENTS;
import static com.example.myapplication.presentation.utils.constants.Restaurant.INGREDIENT_TO_REMOVE;
import static com.example.myapplication.presentation.utils.constants.Restaurant.REQUIRED_CHOICES;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_LIST;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_MENU;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_MENUS_PATH;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TOPPINGS;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TOPPING_PRICE;

import android.net.Uri;

import com.example.myapplication.data.dto.restaurant.CategoryDto;
import com.example.myapplication.data.dto.restaurant.DishDto;
import com.example.myapplication.data.dto.common.ImageDto;
import com.example.myapplication.data.dto.common.ImageTaskNameDto;
import com.example.myapplication.data.dto.restaurant.RequiredChoiceDto;
import com.example.myapplication.data.dto.restaurant.ToppingDto;
import com.example.myapplication.domain.model.restaurant.menu.DishMenuOwnerModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableEmitter;
import io.reactivex.rxjava3.core.Single;

public class RestaurantMenuRepository {

    public Completable deleteCategory(String restaurantId, String categoryId) {
        return Completable.create(emitter -> {
            service.fireStore
                    .collection(RESTAURANT_LIST)
                    .document(restaurantId)
                    .collection(RESTAURANT_MENU)
                    .document(categoryId)
                    .delete().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onComplete();
                        }
                    });
        });
    }

    public Completable deleteDish(String restaurantId, String categoryId, String dishId) {
        return Completable.create(emitter -> {
            service.fireStore
                    .collection(RESTAURANT_LIST)
                    .document(restaurantId)
                    .collection(RESTAURANT_MENU)
                    .document(categoryId)
                    .collection(DISHES)
                    .document(dishId)
                    .delete().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onComplete();
                        }
                    });
        });
    }

    public Completable updateDishData(String restaurantId, String categoryId, String dishId, String name, String ingredients, String price, String weightOrCount) {
        return Completable.create(emitter -> {
            service.fireStore
                    .collection(RESTAURANT_LIST)
                    .document(restaurantId)
                    .collection(RESTAURANT_MENU)
                    .document(categoryId)
                    .collection(DISHES)
                    .document(dishId)
                    .update(DISH_NAME, name,
                            INGREDIENTS, ingredients,
                            DISH_PRICE, price,
                            DISH_WEIGHT_OR_COUNT, weightOrCount)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onComplete();
                        } else {
                            emitter.onError(new Throwable(task.getException()));
                        }
                    });
        });
    }

    public Single<List<DishDto>> getDishes(String restaurantId, String categoryId) {
        CollectionReference collRef = service.fireStore
                .collection(RESTAURANT_LIST)
                .document(restaurantId)
                .collection(RESTAURANT_MENU)
                .document(categoryId)
                .collection(DISHES);

        return Single.create(emitter -> {
            collRef
                    .get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> snapshots = task.getResult().getDocuments();
                            List<DishDto> dtos = new ArrayList<>();
                            for (DocumentSnapshot current : snapshots) {
                                dtos.add(new DishDto(
                                        current.getId(),
                                        current.getString(DISH_NAME),
                                        current.getString(INGREDIENTS),
                                        current.getString(DISH_WEIGHT_OR_COUNT),
                                        current.getString(DISH_PRICE),
                                        current.getString(DISH_ESTIMATED_TIME_COOKING),
                                        (List<String>) current.get(INGREDIENT_TO_REMOVE)
                                ));
                            }
                            emitter.onSuccess(dtos);
                        } else {
                            emitter.onSuccess(Collections.emptyList());
                        }
                    });
        });
    }

    public Single<DishDto> getSingleDishById(String restaurantId, String categoryId, String dishId) {
        return Single.create(emitter -> {
            service.fireStore
                    .collection(RESTAURANT_LIST)
                    .document(restaurantId)
                    .collection(RESTAURANT_MENU)
                    .document(categoryId)
                    .collection(DISHES)
                    .document(dishId)
                    .get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            emitter.onSuccess(new DishDto(
                                    dishId,
                                    document.getString(DISH_NAME),
                                    document.getString(INGREDIENTS),
                                    document.getString(DISH_WEIGHT_OR_COUNT),
                                    document.getString(DISH_PRICE),
                                    document.getString(DISH_ESTIMATED_TIME_COOKING),
                                    (List<String>) document.get(INGREDIENT_TO_REMOVE)
                            ));
                        } else {
                            emitter.onError(new Throwable(task.getException()));
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

    public Completable addCompanyDish(
            String restaurantId,
            String categoryId,
            String dishId,
            String name,
            String ingredients,
            String weightCount,
            String price,
            String estimatedTimeCooking) {

        DocumentReference menu = service.fireStore
                .collection(RESTAURANT_LIST)
                .document(restaurantId)
                .collection(RESTAURANT_MENU)
                .document(categoryId)
                .collection(DISHES)
                .document(dishId);

        return Completable.create(emitter -> {

            HashMap<String, Object> dish = new HashMap<>();
            dish.put(DISH_NAME, name);
            dish.put(INGREDIENTS, ingredients);
            dish.put(DISH_WEIGHT_OR_COUNT, weightCount);
            dish.put(DISH_PRICE, price);
            dish.put(DISH_ESTIMATED_TIME_COOKING, estimatedTimeCooking);
            dish.put(TOPPINGS, Collections.emptyList());
            dish.put(INGREDIENT_TO_REMOVE, Collections.emptyList());

            menu.set(dish).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onComplete();
                } else {
                    emitter.onError(new Throwable(task.getException()));
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
                            emitter.onComplete();
                        }
                    });
                } else {
                    emitter.onError(new Throwable(task.getException()));
                }
            });
        });
    }

    public static class MenuImages {

        public Completable uploadUriCategoryImage(Uri uri, String restaurantId, String categoryName) {
            return Completable.create(emitter -> {
                service.storageReference
                        .child(RESTAURANT_MENUS_PATH)
                        .child(restaurantId + "/")
                        .child(categoryName + JPG)
                        .putFile(uri).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                emitter.onComplete();
                            } else {
                                emitter.onError(new Throwable(task.getException()));
                            }
                        });
            });
        }

        public Completable uploadDishImage(String restaurantId, String dishId, Uri uri) {
            if (uri != Uri.EMPTY) {
                StorageReference reference = service.storageReference
                        .child(RESTAURANT_MENUS_PATH)
                        .child(restaurantId + "/")
                        .child(dishId + "/")
                        .child(dishId + JPG);

                return Completable.create(emitter -> {
                    reference.putFile(uri)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    emitter.onComplete();
                                } else {
                                    emitter.onError(new Throwable(task.getException()));
                                }
                            });
                });
            } else {
                return Completable.create(CompletableEmitter::onComplete);
            }
        }

        public Single<List<ImageTaskNameDto>> getDishesImages(String restaurantId, List<DishMenuOwnerModel> dishes) {
            List<ImageTaskNameDto> tasks = new ArrayList<>();

            return Single.create(emitter -> {
                for (DishMenuOwnerModel current : dishes) {
                    String dishId = current.getDishId();
                    StorageReference reference = service.storageReference
                            .child(RESTAURANT_MENUS_PATH)
                            .child(restaurantId + "/")
                            .child(current.getDishId() + "/")
                            .child(dishId + JPG);

                    tasks.add(new ImageTaskNameDto(reference.getDownloadUrl(), dishId));
                }
                emitter.onSuccess(tasks);
            });
        }

        public Single<List<ImageTaskNameDto>> getCategoriesImages(String restaurantId, List<String> categoriesNames) {
            List<ImageTaskNameDto> tasks = new ArrayList<>();
            return Single.create(emitter -> {
                for (String category : categoriesNames) {
                    StorageReference reference = service.storageReference
                            .child(RESTAURANT_MENUS_PATH)
                            .child(restaurantId + "/")
                            .child(category + JPG);
                    tasks.add(new ImageTaskNameDto(reference.getDownloadUrl(), category));
                }
                emitter.onSuccess(tasks);
            });
        }

        public Single<ImageDto> getSingleDishImage(String restaurantId, String dishId) {
            return Single.create(emitter -> {
                service.storageReference
                        .child(RESTAURANT_MENUS_PATH)
                        .child(restaurantId + "/")
                        .child(dishId + "/")
                        .child(dishId + JPG)
                        .getDownloadUrl().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                emitter.onSuccess(new ImageDto(task.getResult()));
                            } else {
                                emitter.onError(new Throwable(task.getException()));
                            }
                        });
            });
        }

        public Single<List<ImageTaskNameDto>> getDishesImagesByIds(String restaurantId, List<String> ids) {
            return Single.create(emitter -> {
                List<ImageTaskNameDto> tasks = new ArrayList<>();
                for (String id : ids) {
                    StorageReference reference = service.storageReference
                            .child(RESTAURANT_MENUS_PATH)
                            .child(restaurantId + "/")
                            .child(id + "/")
                            .child(id + JPG);
                    tasks.add(new ImageTaskNameDto(
                            reference.getDownloadUrl(),
                            id
                    ));
                }
                emitter.onSuccess(tasks);
            });
        }
    }

    public static class Toppings {

        public Single<List<ToppingDto>> getToppings(String restaurantId, String categoryId, String dishId) {
            return Single.create(emitter -> {
                CollectionReference collRef =
                        service.fireStore
                                .collection(RESTAURANT_LIST)
                                .document(restaurantId)
                                .collection(RESTAURANT_MENU)
                                .document(categoryId)
                                .collection(DISHES)
                                .document(dishId)
                                .collection(TOPPINGS);

                collRef.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<ToppingDto> dtos = new ArrayList<>();
                        List<DocumentSnapshot> snapshots = task.getResult().getDocuments();
                        for (DocumentSnapshot snapshot : snapshots) {
                            dtos.add(new ToppingDto(snapshot.getId(), snapshot.getString(TOPPING_PRICE)));
                        }
                        emitter.onSuccess(dtos);
                    } else {
                        emitter.onSuccess(Collections.emptyList());
                    }
                });

            });
        }

        public Completable addTopping(String restaurantId, String categoryId, String dishId, String name, String price) {
            return Completable.create(emitter -> {
                DocumentReference docRef = service.fireStore
                        .collection(RESTAURANT_LIST)
                        .document(restaurantId)
                        .collection(RESTAURANT_MENU)
                        .document(categoryId)
                        .collection(DISHES)
                        .document(dishId)
                        .collection(TOPPINGS)
                        .document(name);

                HashMap<String, Object> topping = new HashMap<>();
                topping.put(TOPPING_PRICE, price);

                docRef.set(topping).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        emitter.onComplete();
                    } else {
                        emitter.onError(new Throwable(task.getException()));
                    }
                });
            });
        }

        public Single<List<ImageTaskNameDto>> getToppingsImages(String restaurantId, String dishId, List<String> names) {
            List<ImageTaskNameDto> tasks = new ArrayList<>();
            return Single.create(emitter -> {
                if (names != null && !names.isEmpty()) {
                    for (String name : names) {
                        StorageReference reference = service.storageReference
                                .child(RESTAURANT_MENUS_PATH)
                                .child(restaurantId + "/")
                                .child(dishId + "/")
                                .child(TOPPINGS + "/")
                                .child(name + JPG);
                        tasks.add(new ImageTaskNameDto(reference.getDownloadUrl(), name));
                    }
                }
                emitter.onSuccess(tasks);
            });
        }

        public Completable deleteToppingImage(String restaurantId, String dishId, String name) {
            return Completable.create(emitter -> {
                service.storageReference
                        .child(RESTAURANT_MENUS_PATH)
                        .child(restaurantId + "/")
                        .child(dishId + "/")
                        .child(TOPPINGS + "/")
                        .child(name + JPG)
                        .delete().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                emitter.onComplete();
                            } else {
                                emitter.onError(new Throwable(task.getException()));
                            }
                        });
            });
        }

        public Completable deleteTopping(String restaurantId, String categoryId, String dishId, String name) {
            return Completable.create(emitter -> {
                service.fireStore
                        .collection(RESTAURANT_LIST)
                        .document(restaurantId)
                        .collection(RESTAURANT_MENU)
                        .document(categoryId)
                        .collection(DISHES)
                        .document(dishId)
                        .collection(TOPPINGS)
                        .document(name)
                        .delete().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                emitter.onComplete();
                            } else {
                                emitter.onError(new Throwable(task.getException()));
                            }
                        });
            });
        }

        public Completable uploadToppingImage(String name, Uri uri, String restaurantId, String dishId) {
            StorageReference basic = service.storageReference
                    .child(RESTAURANT_MENUS_PATH)
                    .child(restaurantId + "/")
                    .child(dishId + "/")
                    .child(TOPPINGS + "/")
                    .child(name + JPG);

            return Completable.create(emitter -> {
                basic.putFile(uri).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        emitter.onComplete();
                    } else {
                        emitter.onError(new Throwable(task.getException()));
                    }
                });
            });
        }
    }

    public static class RequiredChoice {

        public Single<List<RequiredChoiceDto>> getRequiredChoices(String restaurantId, String categoryId, String dishId) {
            List<RequiredChoiceDto> choices = new ArrayList<>();
            return Single.create(emitter -> {
                service.fireStore
                        .collection(RESTAURANT_LIST)
                        .document(restaurantId)
                        .collection(RESTAURANT_MENU)
                        .document(categoryId)
                        .collection(DISHES)
                        .document(dishId)
                        .collection(REQUIRED_CHOICES)
                        .get().addOnCompleteListener(taskChoices -> {
                            if (taskChoices.isSuccessful()) {
                                List<DocumentSnapshot> snapshotChoices = taskChoices.getResult().getDocuments();
                                for (int i = 0; i < snapshotChoices.size(); i++) {
                                    DocumentSnapshot currentChoice = snapshotChoices.get(i);
                                    List<String> variants = (List<String>) currentChoice.get(CHOICE_VARIANT);
                                    choices.add(new RequiredChoiceDto(
                                            currentChoice.getId(),
                                            currentChoice.getString(CHOICE_NAME),
                                            variants
                                    ));
                                }
                                emitter.onSuccess(choices);
                            } else {
                                emitter.onSuccess(Collections.emptyList());
                            }
                        });
            });
        }

        public Completable addRequiredChoice(String restaurantId, String categoryId, String dishId, String choiceId, String name, List<String> variantsNames) {

            return Completable.create(emitter -> {
                DocumentReference docRef = service.fireStore
                        .collection(RESTAURANT_LIST)
                        .document(restaurantId)
                        .collection(RESTAURANT_MENU)
                        .document(categoryId)
                        .collection(DISHES)
                        .document(dishId)
                        .collection(REQUIRED_CHOICES)
                        .document(choiceId);

                HashMap<String, Object> variants = new HashMap<>();
                variants.put(CHOICE_VARIANT, "empty");
                variants.put(CHOICE_NAME, name);

                docRef.set(variants).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (int j = 0; j < variantsNames.size(); j++) {
                            docRef.update(CHOICE_VARIANT, FieldValue.arrayUnion(variantsNames.get(j)));
                        }
                        emitter.onComplete();
                    } else {
                        emitter.onError(new Throwable(task.getException()));
                    }
                });
            });
        }

        public Completable updateRequiredChoiceName(String restaurantId, String categoryId, String dishId, String choiceId, String name) {
            return Completable.create(emitter -> {
                service.fireStore
                        .collection(RESTAURANT_LIST)
                        .document(restaurantId)
                        .collection(RESTAURANT_MENU)
                        .document(categoryId)
                        .collection(DISHES)
                        .document(dishId)
                        .collection(REQUIRED_CHOICES)
                        .document(choiceId)
                        .update(CHOICE_NAME, name)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                emitter.onComplete();
                            } else {
                                emitter.onError(new Throwable(task.getException()));
                            }
                        });
            });
        }

        public Completable addNewRequiredChoiceVariant(String restaurantId, String categoryId, String dishId, String choiceId, String newVariant) {
            return Completable.create(emitter -> {
                service.fireStore
                        .collection(RESTAURANT_LIST)
                        .document(restaurantId)
                        .collection(RESTAURANT_MENU)
                        .document(categoryId)
                        .collection(DISHES)
                        .document(dishId)
                        .collection(REQUIRED_CHOICES)
                        .document(choiceId)
                        .update(CHOICE_VARIANT, FieldValue.arrayUnion(newVariant))
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                emitter.onComplete();
                            } else {
                                emitter.onError(new Throwable(task.getException()));
                            }
                        });
            });
        }

        public Completable updateVariant(String restaurantId, String categoryId, String dishId, String choiceId, String previousVariant, String newVariant) {
            return Completable.create(emitter -> {
                DocumentReference docRef = service.fireStore
                        .collection(RESTAURANT_LIST)
                        .document(restaurantId)
                        .collection(RESTAURANT_MENU)
                        .document(categoryId)
                        .collection(DISHES)
                        .document(dishId)
                        .collection(REQUIRED_CHOICES)
                        .document(choiceId);

                docRef.update(CHOICE_VARIANT, FieldValue.arrayRemove(previousVariant), CHOICE_VARIANT, FieldValue.arrayUnion(newVariant))
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                emitter.onComplete();
                            } else {
                                emitter.onError(new Throwable(task.getException()));
                            }
                        });
            });
        }

        public Completable deleteRequiredChoiceVariant(String restaurantId, String categoryId, String dishId, String choiceId, String variant) {
            return Completable.create(emitter -> {
                service.fireStore
                        .collection(RESTAURANT_LIST)
                        .document(restaurantId)
                        .collection(RESTAURANT_MENU)
                        .document(categoryId)
                        .collection(DISHES)
                        .document(dishId)
                        .collection(REQUIRED_CHOICES)
                        .document(choiceId)
                        .update(CHOICE_VARIANT, FieldValue.arrayRemove(variant))
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                emitter.onComplete();
                            } else {
                                emitter.onError(new Throwable(task.getException()));
                            }
                        });
            });
        }

        public Completable deleteRequiredChoiceById(String restaurantId, String categoryId, String dishId, String choiceId) {
            return Completable.create(emitter -> {
                service.fireStore
                        .collection(RESTAURANT_LIST)
                        .document(restaurantId)
                        .collection(RESTAURANT_MENU)
                        .document(categoryId)
                        .collection(DISHES)
                        .document(dishId)
                        .collection(REQUIRED_CHOICES)
                        .document(choiceId)
                        .delete().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                emitter.onComplete();
                            } else {
                                emitter.onError(new Throwable(task.getException()));
                            }
                        });
            });
        }

        public Single<RequiredChoiceDto> getSingleRequireChoiceById(String restaurantId, String categoryId, String dishId, String choiceId) {
            return Single.create(emitter -> {
                service.fireStore
                        .collection(RESTAURANT_LIST)
                        .document(restaurantId)
                        .collection(RESTAURANT_MENU)
                        .document(categoryId)
                        .collection(DISHES)
                        .document(dishId)
                        .collection(REQUIRED_CHOICES)
                        .document(choiceId)
                        .get().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                DocumentSnapshot snapshot = task.getResult();
                                emitter.onSuccess(new RequiredChoiceDto(
                                        snapshot.getId(),
                                        snapshot.getString(CHOICE_NAME),
                                        (List<String>) snapshot.get(CHOICE_VARIANT)
                                ));
                            } else {
                                emitter.onError(new Throwable(task.getException()));
                            }
                        });
            });
        }
    }

    public static class IngredientsToRemove {

        public Completable addIngredientToRemove(String restaurantId, String categoryId, String dishId, String variant) {
            return Completable.create(emitter -> {
                DocumentReference docRef = service.fireStore
                        .collection(RESTAURANT_LIST)
                        .document(restaurantId)
                        .collection(RESTAURANT_MENU)
                        .document(categoryId)
                        .collection(DISHES)
                        .document(dishId);

                docRef.update(INGREDIENT_TO_REMOVE, FieldValue.arrayUnion(variant))
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                emitter.onComplete();
                            } else {
                                emitter.onError(new Throwable(task.getException()));
                            }
                        });
            });
        }

        public Single<List<String>> getToRemove(String restaurantId, String categoryId, String dishId) {
            return Single.create(emitter -> {
                service.fireStore
                        .collection(RESTAURANT_LIST)
                        .document(restaurantId)
                        .collection(RESTAURANT_MENU)
                        .document(categoryId)
                        .collection(DISHES)
                        .document(dishId)
                        .get().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                List<String> toRemove = (List<String>) task.getResult().get(INGREDIENT_TO_REMOVE);
                                if (toRemove != null) {
                                    emitter.onSuccess(toRemove);
                                } else {
                                    emitter.onSuccess(Collections.emptyList());
                                }
                            }
                        });

            });
        }

        public Completable deleteIngredientToRemove(String restaurantId, String categoryId, String dishId, String name) {
            return Completable.create(emitter -> {
                service.fireStore
                        .collection(RESTAURANT_LIST)
                        .document(restaurantId)
                        .collection(RESTAURANT_MENU)
                        .document(categoryId)
                        .collection(DISHES)
                        .document(dishId)
                        .update(INGREDIENT_TO_REMOVE, FieldValue.arrayRemove(name))
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                emitter.onComplete();
                            } else {
                                emitter.onError(new Throwable(task.getException()));
                            }
                        });
            });
        }

        public Completable updateIngredientsToRemove(String restaurantId, String categoryId, String dishId, String previousName, String newName) {
            return Completable.create(emitter -> {
                service.fireStore
                        .collection(RESTAURANT_LIST)
                        .document(restaurantId)
                        .collection(RESTAURANT_MENU)
                        .document(categoryId)
                        .collection(DISHES)
                        .document(dishId)
                        .update(INGREDIENT_TO_REMOVE, FieldValue.arrayRemove(previousName),
                                INGREDIENT_TO_REMOVE, FieldValue.arrayUnion(newName))
                        .addOnCompleteListener(task -> {
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
