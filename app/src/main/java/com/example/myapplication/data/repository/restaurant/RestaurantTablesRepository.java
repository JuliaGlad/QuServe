package com.example.myapplication.data.repository.restaurant;

import static com.example.myapplication.di.DI.service;
import static com.example.myapplication.presentation.utils.Utils.JPG;
import static com.example.myapplication.presentation.utils.Utils.PDF;
import static com.example.myapplication.presentation.utils.constants.Restaurant.ORDER_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_LIST;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT_LOCATION;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TABLE_LIST;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TABLE_NUMBER;
import static com.example.myapplication.presentation.utils.constants.Restaurant.TABLE_QR_CODES;

import android.net.Uri;

import com.example.myapplication.data.dto.restaurant.TableDto;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class RestaurantTablesRepository {

    public Single<TableDto> getSingleTableById(String restaurantId, String locationId, String tableId) {
        return Single.create(emitter -> {
            service.fireStore
                    .collection(RESTAURANT_LIST)
                    .document(restaurantId)
                    .collection(RESTAURANT_LOCATION)
                    .document(locationId)
                    .collection(TABLE_LIST)
                    .document(tableId).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            emitter.onSuccess(new TableDto(
                                    document.getId(),
                                    document.getString(TABLE_NUMBER),
                                    document.getString(ORDER_ID)
                            ));
                        }
                    });
        });
    }

    public Single<String> addTable(String locationId, String restaurantId, String tableId, String tableNumber) {
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
                                        current.getString(TABLE_NUMBER),
                                        current.getString(ORDER_ID)
                                ));
                            }
                            emitter.onSuccess(tables);
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

    public static class TableImages{
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
    }

}
