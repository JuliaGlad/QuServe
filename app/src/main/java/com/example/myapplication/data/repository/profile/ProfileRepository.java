package com.example.myapplication.data.repository.profile;

import static com.example.myapplication.di.DI.service;
import static com.example.myapplication.presentation.utils.Utils.ACTIVE_QUEUES_LIST;
import static com.example.myapplication.presentation.utils.Utils.BACKGROUND_IMAGE;
import static com.example.myapplication.presentation.utils.Utils.BIRTHDAY_KEY;
import static com.example.myapplication.presentation.utils.Utils.COMPANIES_QUEUES;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_EMPLOYEE;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_LIST;
import static com.example.myapplication.presentation.utils.Utils.DATE_LEFT;
import static com.example.myapplication.presentation.utils.Utils.EMAIL_KEY;
import static com.example.myapplication.presentation.utils.Utils.EMPLOYEES;
import static com.example.myapplication.presentation.utils.Utils.GENDER_KEY;
import static com.example.myapplication.presentation.utils.Utils.HISTORY_KEY;
import static com.example.myapplication.presentation.utils.Utils.NOT_PARTICIPATE_IN_QUEUE;
import static com.example.myapplication.presentation.utils.Utils.NOT_QUEUE_OWNER;
import static com.example.myapplication.presentation.utils.Utils.NOT_RESTAURANT_VISITOR;
import static com.example.myapplication.presentation.utils.Utils.OWN_QUEUE;
import static com.example.myapplication.presentation.utils.Utils.PARTICIPATE_IN_QUEUE;
import static com.example.myapplication.presentation.utils.Utils.PHONE_NUMBER_KEY;
import static com.example.myapplication.presentation.utils.Utils.PROFILE_IMAGES;
import static com.example.myapplication.presentation.utils.Utils.PROFILE_PHOTO;
import static com.example.myapplication.presentation.utils.Utils.PROFILE_UPDATED_AT;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_LIST;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_NAME_KEY;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_PARTICIPANTS_LIST;
import static com.example.myapplication.presentation.utils.Utils.TIME;
import static com.example.myapplication.presentation.utils.Utils.URI;
import static com.example.myapplication.presentation.utils.Utils.USER_LIST;
import static com.example.myapplication.presentation.utils.Utils.USER_NAME_KEY;
import static com.example.myapplication.presentation.utils.Utils.WORKERS_LIST;
import static com.example.myapplication.presentation.utils.constants.Restaurant.IS_RESTAURANT_VISITOR;

import android.net.Uri;
import android.util.Log;

import com.example.myapplication.app.App;
import com.example.myapplication.data.db.entity.AnonymousUserEntity;
import com.example.myapplication.data.dto.user.AnonymousUserDto;
import com.example.myapplication.data.dto.user.HistoryQueueDto;
import com.example.myapplication.data.dto.common.ImageDto;
import com.example.myapplication.data.dto.user.UserDto;
import com.example.myapplication.data.providers.AnonymousUserProvider;
import com.example.myapplication.data.providers.CompanyUserProvider;
import com.example.myapplication.data.providers.UserDatabaseProvider;
import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableEmitter;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class ProfileRepository {

    public Completable deleteRestaurantVisitor() {
        return Completable.create(emitter -> {
            service.fireStore.collection(USER_LIST).document(service.auth.getCurrentUser().getUid())
                    .update(IS_RESTAURANT_VISITOR, NOT_RESTAURANT_VISITOR).addOnCompleteListener(taskUpdate -> {
                        if (taskUpdate.isSuccessful()){
                            emitter.onComplete();
                        }
                    });
        });
    }

    public Single<List<HistoryQueueDto>> getHistoryList() {
        List<HistoryQueueDto> list = new ArrayList<>();
        return Single.create(emitter -> {
            CollectionReference collectionRef = service.fireStore
                    .collection(USER_LIST)
                    .document(service.auth.getCurrentUser().getUid())
                    .collection(HISTORY_KEY);

            collectionRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    List<DocumentSnapshot> documents = task.getResult().getDocuments();
                    for (int i = 0; i < documents.size(); i++) {
                        list.add(new HistoryQueueDto(
                                documents.get(i).getString(DATE_LEFT),
                                documents.get(i).getString(QUEUE_NAME_KEY),
                                documents.get(i).getString(TIME)
                        ));
                    }
                    emitter.onSuccess(list);
                } else {
                    emitter.onSuccess(Collections.emptyList());
                }
            });
        });
    }

    public Completable onPasswordCheck(String password) {
        return Completable.create(emitter -> {
            AuthCredential authCredential = EmailAuthProvider.getCredential(service.auth.getCurrentUser().getEmail(), password);
            service.auth.getCurrentUser().reauthenticate(authCredential).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onComplete();
                }
            });
        });
    }

    public Completable signInAnonymously() {
        return Completable.create(emitter -> {
            service.auth.signInAnonymously().addOnCompleteListener(task -> {
                String userId = task.getResult().getUser().getUid();
                AnonymousUserProvider.insertUser(new AnonymousUserDto(
                        userId,
                        NOT_PARTICIPATE_IN_QUEUE,
                        NOT_RESTAURANT_VISITOR
                ));
                emitter.onComplete();
            });
        });
    }

    public Observable<DocumentSnapshot> addSnapshot() {
        return Observable.create(emitter -> {
            service.fireStore.collection(USER_LIST).document(service.auth.getCurrentUser().getUid())
                    .addSnapshotListener((value, error) -> {
                        if (value != null) {
                            emitter.onNext(value);
                        }
                    });
        });
    }

    public Completable changePassword(String oldPassword, String newPassword) {
        AuthCredential authCredential = EmailAuthProvider.getCredential(service.auth.getCurrentUser().getEmail(), oldPassword);
        return Completable.create(emitter -> {
            service.auth.getCurrentUser().reauthenticate(authCredential).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    service.auth.getCurrentUser().updatePassword(newPassword);
                    emitter.onComplete();
                } else {
                    emitter.onError(new Throwable("EXCEPTION"));
                }
            });
        });
    }

    public Completable verifyBeforeUpdateEmail(String email) {
        return Completable.create(emitter -> {
            service.auth.getCurrentUser().verifyBeforeUpdateEmail(email)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onComplete();
                        }
                    });
        });

    }

    public Single<Boolean> checkVerification() {
        return Single.create(emitter -> {
            service.auth.getCurrentUser().reload().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onSuccess(service.auth.getCurrentUser().isEmailVerified());
                }
            });
        });

    }

    public Completable deleteAccount(String password) {
        return Completable.create(emitter -> {
            AuthCredential authCredential = EmailAuthProvider.getCredential(Objects.requireNonNull(service.auth.getCurrentUser().getEmail()), password);
            service.auth.getCurrentUser().reauthenticate(authCredential)
                    .addOnCompleteListener(auth -> {
                        if (auth.isSuccessful()) {
                            String userId = service.auth.getCurrentUser().getUid();
                            DocumentReference docRef = service.fireStore
                                    .collection(USER_LIST)
                                    .document(userId);
                            getUserDocumentForDelete(docRef, userId, emitter);
                        }
                    });
        });
    }

    public void logout() {
        service.auth.signOut();
        CompanyUserProvider.deleteAll();
        UserDatabaseProvider.deleteUser();
    }

    public Single<Boolean> sendResetPasswordEmail(String email) {
        return Single.create(emitter -> {
            service.auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onSuccess(true);
                        } else {
                            emitter.onSuccess(false);
                        }
                    });
        });
    }

    public Completable sendEmailVerification() {
        return Completable.create(emitter -> {
            emitter.onComplete();
            service.auth.getCurrentUser().sendEmailVerification();
        });
    }

    public Completable createAccount(String email, String password, String userName) {
        return Completable.create(emitter -> {
            service.auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentReference docRef = service.fireStore
                            .collection(USER_LIST)
                            .document(service.auth.getCurrentUser().getUid());

                    Map<String, Object> user = new HashMap<>();

                    user.put(OWN_QUEUE, NOT_QUEUE_OWNER);
                    user.put(IS_RESTAURANT_VISITOR, NOT_RESTAURANT_VISITOR);
                    user.put(PARTICIPATE_IN_QUEUE, NOT_PARTICIPATE_IN_QUEUE);
                    user.put(USER_NAME_KEY, userName);
                    user.put(EMAIL_KEY, email);
                    user.put(PHONE_NUMBER_KEY, "");
                    user.put(GENDER_KEY, "");
                    user.put(BIRTHDAY_KEY, "");

                    docRef.set(user).addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            UserDto userDto = new UserDto(
                                    userName, "", "",
                                    email, "",
                                    NOT_QUEUE_OWNER, NOT_PARTICIPATE_IN_QUEUE, IS_RESTAURANT_VISITOR);

                            UserDatabaseProvider.insertUser(userDto);

                            emitter.onComplete();
                        }
                    });
                }
            });
        });
    }

    public Completable signInWithEmailAndPassword(String email, String password) {
        App.getInstance().getDatabase().userDao().deleteALl();
        return Completable.create(emitter -> {
            service.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    AnonymousUserEntity entity = AnonymousUserProvider.getUser();
                    if (entity != null){
                        AnonymousUserProvider.deleteUser(entity);
                    }
                    emitter.onComplete();
                } else
                    emitter.onError(new Throwable("Wrong data"));
            });
        });
    }

    public Completable updateUserData(String newUserName, String newUserPhone, String
            newUserGender, String newBirthday) {
        return Completable.create(emitter -> {
            DocumentReference docRef = service.fireStore.collection(USER_LIST).document(service.auth.getCurrentUser().getUid());
            FieldValue timestamp = FieldValue.serverTimestamp();
            docRef.update(
                    PROFILE_UPDATED_AT, timestamp,
                    USER_NAME_KEY, newUserName,
                    GENDER_KEY, newUserGender,
                    PHONE_NUMBER_KEY, newUserPhone,
                    BIRTHDAY_KEY, newBirthday
            ).addOnCompleteListener(task -> {
                UserDatabaseProvider.updateUser(newUserName, newUserGender, newUserPhone, newBirthday);
                emitter.onComplete();
            });
        });
    }

    public Completable updateEmailField(String newEmail) {
        return Completable.create(emitter -> {
            service.fireStore.collection(USER_LIST).document(service.auth.getCurrentUser().getUid())
                    .update(EMAIL_KEY, newEmail).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onComplete();
                        }
                    });
        });
    }

    public Single<UserDto> getUserData() {
        UserDto localUser = UserDatabaseProvider.getUser();
        if (localUser != null) {
            return Single.create(emitter -> {
                emitter.onSuccess(localUser);
            });
        } else {
            return
                    Single.create(emitter -> {
                        DocumentReference docRef = service.fireStore.collection(USER_LIST).document(service.auth.getCurrentUser().getUid());
                        service.auth.getCurrentUser().reload().addOnSuccessListener(unused -> {
                            docRef.get().addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    UserDto userDto = new UserDto(
                                            document.getString(USER_NAME_KEY),
                                            document.getString(GENDER_KEY),
                                            document.getString(PHONE_NUMBER_KEY),
                                            document.getString(EMAIL_KEY),
                                            document.getString(BIRTHDAY_KEY),
                                            document.getString(OWN_QUEUE),
                                            document.getString(PARTICIPATE_IN_QUEUE),
                                            document.getString(IS_RESTAURANT_VISITOR)
                                    );

                                    UserDatabaseProvider.insertUser(userDto);
                                    emitter.onSuccess(userDto);
                                }
                            });
                        }).addOnFailureListener(e -> {
                            emitter.onError(new Throwable(e.getMessage()));
                        });

                    });
        }
    }

    public Single<String> getActiveOrder() {
        return Single.create(emitter -> {
            service.fireStore.collection(USER_LIST)
                    .document(service.auth.getCurrentUser().getUid())
                    .get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onSuccess(task.getResult().getString(IS_RESTAURANT_VISITOR));
                        }
                    });
        });
    }

    public Completable addActiveOrder(String path) {
        return Completable.create(emitter -> {
            if (service.auth.getCurrentUser().isAnonymous()) {
                service.fireStore
                        .collection(USER_LIST)
                        .document(service.auth.getCurrentUser().getUid())
                        .update(IS_RESTAURANT_VISITOR, path).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                emitter.onComplete();
                            }
                        });
            } else {
                AnonymousUserProvider.updateRestaurantVisitor(path);
                emitter.onComplete();
            }
        });
    }

    public Completable updateOwnQueue(String value) {
        DocumentReference docRef = service.fireStore.collection(USER_LIST).document(service.auth.getCurrentUser().getUid());
        return Completable.create(emitter -> {
            docRef.update(OWN_QUEUE, value).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    UserDatabaseProvider.updateOwnQueue(value);
                    emitter.onComplete();
                }
            });
        });
    }

    public Completable updateParticipateInQueue(String path) {
        DocumentReference docRef = service.fireStore.collection(USER_LIST).document(service.auth.getCurrentUser().getUid());
        if (!service.auth.getCurrentUser().isAnonymous()) {
            return Completable.create(emitter -> {
                docRef.update(PARTICIPATE_IN_QUEUE, path).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        UserDatabaseProvider.updateParticipateInQueue(path);
                        emitter.onComplete();
                    }
                });
            });
        } else {
            return Completable.create(emitter -> {
                AnonymousUserProvider.updateParticipateInQueue(path);
                emitter.onComplete();
            });

        }
    }

    private void getUserDocumentForDelete(DocumentReference docRef, String userId, CompletableEmitter emitter) {
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                DocumentSnapshot userDocument = task.getResult();

                String participantPath = userDocument.getString(PARTICIPATE_IN_QUEUE);
                String ownQueue = userDocument.getString(OWN_QUEUE);
                String restaurantVisitor = userDocument.getString(IS_RESTAURANT_VISITOR);

                assert participantPath != null;
                getActions(docRef, userId, emitter, participantPath, ownQueue, restaurantVisitor);
            }
        });
    }

    private void getActions(DocumentReference docRef, String userId, CompletableEmitter emitter, String participantPath, String ownQueue, String restaurantVisitor) {

        if (!participantPath.equals(NOT_PARTICIPATE_IN_QUEUE)) {
            service.fireStore.document(participantPath).update(QUEUE_PARTICIPANTS_LIST, FieldValue.arrayRemove(userId))
                    .addOnCompleteListener(taskRemove -> {
                        if (taskRemove.isSuccessful()) {
                            getQueueOwnerActions(docRef, userId, emitter, ownQueue, restaurantVisitor);
                        }
                    });
        } else {
            getQueueOwnerActions(docRef, userId, emitter, ownQueue, restaurantVisitor);
        }
    }

    private void getQueueOwnerActions(DocumentReference docRef, String userId, CompletableEmitter emitter, String ownQueue, String restaurantVisitor) {
        assert ownQueue != null;
        if (!ownQueue.equals(NOT_QUEUE_OWNER)) {
            service.fireStore.document(ownQueue).delete().addOnCompleteListener(taskDeleteQueue -> {
                if (taskDeleteQueue.isSuccessful()) {
                    getRestaurantActions(docRef, userId, emitter, restaurantVisitor);
                }
            });
        } else {
            getRestaurantActions(docRef, userId, emitter, restaurantVisitor);
        }
    }

    private void getRestaurantActions(DocumentReference docRef, String userId, CompletableEmitter emitter, String restaurantVisitor) {
        if (!restaurantVisitor.equals(NOT_RESTAURANT_VISITOR)) {
            service.fireStore.document(restaurantVisitor).delete().addOnCompleteListener(taskUserDelete -> {
                if (taskUserDelete.isSuccessful()) {
                    deleteCompanyEmployee(docRef, userId);
                    deleteUser(docRef, emitter);
                }
            });
        } else {
            deleteCompanyEmployee(docRef, userId);
            deleteUser(docRef, emitter);
        }
    }

    private void deleteUser(DocumentReference docRef, CompletableEmitter emitter) {
        docRef.delete().addOnCompleteListener(taskDelete -> {
            if (taskDelete.isSuccessful()) {
                service.auth.getCurrentUser().delete()
                        .addOnCompleteListener(taskNew -> {
                            if (taskNew.isSuccessful()) {
                                UserDatabaseProvider.deleteUser();
                                CompanyUserProvider.deleteAll();
                                emitter.onComplete();
                            }
                        });
            }
        });
    }

    private void deleteCompanyEmployee(DocumentReference docRef, String userId) {
        docRef.collection(COMPANY_EMPLOYEE).get().addOnCompleteListener(taskEmployee -> {
            if (taskEmployee.isSuccessful()) {
                List<DocumentSnapshot> snapshots = taskEmployee.getResult().getDocuments();
                for (DocumentSnapshot current : snapshots) {
                    String id = current.getId();
                    service.fireStore.collection(COMPANY_LIST).document(id).collection(EMPLOYEES).document(userId)
                            .delete();

                    docRef.collection(COMPANY_EMPLOYEE).document(id).collection(ACTIVE_QUEUES_LIST)
                            .get().addOnCompleteListener(taskQueues -> {

                                List<DocumentSnapshot> documents = taskQueues.getResult().getDocuments();
                                for (DocumentSnapshot currentSnapshot : documents) {
                                    service.fireStore
                                            .collection(QUEUE_LIST)
                                            .document(COMPANIES_QUEUES)
                                            .collection(id)
                                            .document(currentSnapshot.getId())
                                            .collection(WORKERS_LIST)
                                            .document(userId)
                                            .delete();
                                }

                            });
                }
            }
        });
    }

    public static class ProfileImages {

        public Single<ImageDto> getBackgroundImage() {
            return Single.create(emitter -> {
                StorageReference local = service.storageReference.child("BACKGROUND_IMAGES/").child(BACKGROUND_IMAGE + service.auth.getCurrentUser().getUid());
                try {
                    local.getDownloadUrl().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onSuccess(new ImageDto(task.getResult()));
                        } else {
                            emitter.onSuccess(new ImageDto(Uri.EMPTY));
                        }
                    });
                } catch (RuntimeExecutionException e) {
                    emitter.onSuccess(new ImageDto(Uri.EMPTY));
                }

            });

        }

        public Single<ImageDto> getProfileImage() {
            return
                    Single.create(emitter -> {
                        StorageReference local = service.storageReference.child(PROFILE_IMAGES).child(PROFILE_PHOTO + service.auth.getCurrentUser().getUid());
                        try {
                            local.getDownloadUrl().addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Uri uri = task.getResult();
                                    emitter.onSuccess(new ImageDto(uri));
                                } else {
                                    emitter.onSuccess(new ImageDto(Uri.EMPTY));
                                }
                            });
                        } catch (RuntimeExecutionException e) {
                            Log.d("RuntimeExecutionException", e.getMessage());
                            emitter.onSuccess(new ImageDto(Uri.EMPTY));
                        }

                    });
        }

        public Completable uploadProfileImageToFireStorage(Uri imageUri) {
            return Completable.create(emitter -> {
                if (imageUri != null) {
                    StorageReference reference = service.storageReference.child(PROFILE_IMAGES + PROFILE_PHOTO + service.auth.getCurrentUser().getUid());
                    reference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
                        DocumentReference docRef = service.fireStore.collection(USER_LIST).document(service.auth.getCurrentUser().getUid());
                        docRef.update(URI, imageUri).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                emitter.onComplete();
                            }
                        });

                    });
                } else {
                    emitter.onComplete();
                }
            });
        }

        public Completable uploadBackgroundToFireStorage(Uri imageUri) {
            return Completable.create(emitter -> {
                StorageReference reference = service.storageReference.child("BACKGROUND_IMAGES/").child(BACKGROUND_IMAGE + service.auth.getCurrentUser().getUid());

                reference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
                    DocumentReference docRef = service.fireStore.collection(USER_LIST).document(service.auth.getCurrentUser().getUid());
                    docRef.update(BACKGROUND_IMAGE, imageUri).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onComplete();
                        }
                    });

                });

            });
        }

    }
}