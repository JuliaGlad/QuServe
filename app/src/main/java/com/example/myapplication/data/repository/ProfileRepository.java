package com.example.myapplication.data.repository;

import static com.example.myapplication.DI.service;
import static com.example.myapplication.presentation.utils.Utils.BACKGROUND_IMAGE;
import static com.example.myapplication.presentation.utils.Utils.BACKGROUND_IMAGES;
import static com.example.myapplication.presentation.utils.Utils.BIRTHDAY_KEY;
import static com.example.myapplication.presentation.utils.Utils.EMAIL_KEY;
import static com.example.myapplication.presentation.utils.Utils.GENDER_KEY;
import static com.example.myapplication.presentation.utils.Utils.OWN_QUEUE;
import static com.example.myapplication.presentation.utils.Utils.PARTICIPATE_IN_QUEUE;
import static com.example.myapplication.presentation.utils.Utils.PHONE_NUMBER_KEY;
import static com.example.myapplication.presentation.utils.Utils.PROFILE_IMAGES;
import static com.example.myapplication.presentation.utils.Utils.PROFILE_PHOTO;
import static com.example.myapplication.presentation.utils.Utils.PROFILE_UPDATED_AT;
import static com.example.myapplication.presentation.utils.Utils.URI;
import static com.example.myapplication.presentation.utils.Utils.USER_LIST;
import static com.example.myapplication.presentation.utils.Utils.USER_NAME_KEY;


import android.net.Uri;
import android.util.Log;

import com.example.myapplication.App;
import com.example.myapplication.data.dto.ImageDto;
import com.example.myapplication.data.dto.UserDto;
import com.example.myapplication.data.providers.CompanyUserProvider;
import com.example.myapplication.data.providers.UserDatabaseProvider;
import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class ProfileRepository {

    public Completable onPasswordCheck(String password) {
        return Completable.create(emitter -> {
            AuthCredential authCredential = EmailAuthProvider.getCredential(service.auth.getCurrentUser().getEmail(), password);
            Log.d("Check", password + " " + service.auth.getCurrentUser().getUid() + " " + service.auth.getCurrentUser().getEmail() + " " + authCredential);
            service.auth.getCurrentUser().reauthenticate(authCredential).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onComplete();
                }
            });
        });
    }

    public boolean checkUserId() {
        return service.auth.getCurrentUser() == null;
    }

    public Completable signInAnonymously() {
        return Completable.create(emitter -> {
            service.auth.signInAnonymously().addOnCompleteListener(task -> {
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
                            Log.d("Current user", service.auth.getCurrentUser().getUid());
                            emitter.onComplete();
                        }
                    });
        });

    }

    public Single<Boolean> checkVerification() {
        return Single.create(emitter -> {
            service.auth.getCurrentUser().reload().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d("Current user", service.auth.getCurrentUser().getUid());
                    emitter.onSuccess(service.auth.getCurrentUser().isEmailVerified());
                }
            });
        });

    }

    public boolean checkAuth() {
        return service.auth.getCurrentUser() != null && !service.auth.getCurrentUser().isAnonymous();
    }

    public Completable deleteAccount(String password) {
        return Completable.create(emitter -> {
            AuthCredential authCredential = EmailAuthProvider.getCredential(service.auth.getCurrentUser().getEmail(), password);
            service.auth.getCurrentUser().reauthenticate(authCredential)
                    .addOnCompleteListener(auth -> {
                        if (auth.isSuccessful()) {
                            service.fireStore
                                    .collection(USER_LIST)
                                    .document(service.auth.getCurrentUser().getUid())
                                    .delete().addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            service.auth.getCurrentUser().delete()
                                                    .addOnCompleteListener(taskNew -> {
                                                        if (taskNew.isSuccessful()) {
                                                            UserDatabaseProvider.deleteUser();
                                                            CompanyUserProvider.deleteAll();
                                                            emitter.onComplete();
                                                        } else {
                                                            Log.d("Error", taskNew.getException().getMessage());
                                                        }
                                                    });
                                        } else {
                                            Log.d("Error", task.getException().getMessage());
                                        }
                                    });


                        } else {
                            Log.d("Error", auth.getException().getMessage());
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

    public void updateOwnQueue(boolean value) {
        DocumentReference docRef = service.fireStore.collection(USER_LIST).document(service.auth.getCurrentUser().getUid());
        docRef.update(OWN_QUEUE, value);
    }

    public void updateParticipateInQueue(boolean value) {
        DocumentReference docRef = service.fireStore.collection(USER_LIST).document(service.auth.getCurrentUser().getUid());
        docRef.update(PARTICIPATE_IN_QUEUE, value);
    }

    public Completable createAccount(String email, String password, String userName, String uri) {
        return Completable.create(emitter -> {
            service.auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentReference docRef = service.fireStore
                            .collection(USER_LIST)
                            .document(service.auth.getCurrentUser().getUid());

                    Map<String, Object> user = new HashMap<>();
                    user.put(OWN_QUEUE, false);
                    user.put(PARTICIPATE_IN_QUEUE, false);
                    user.put(USER_NAME_KEY, userName);
                    user.put(PHONE_NUMBER_KEY, "");
                    user.put(EMAIL_KEY, email);
                    user.put(GENDER_KEY, "");
                    user.put(BIRTHDAY_KEY, "");
                    user.put(URI, uri);
                    user.put(BACKGROUND_IMAGE, String.valueOf(Uri.EMPTY));

                    docRef.set(user).addOnCompleteListener(task1 -> {
                        if (task.isSuccessful()) {
                            UserDto userDto = new UserDto(userName, "", "", email, "", String.valueOf(Uri.EMPTY), String.valueOf(Uri.EMPTY), false, false);
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
                emitter.onComplete();
            });
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
                            UserDatabaseProvider.updateUri(String.valueOf(imageUri));
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
                        UserDatabaseProvider.updateBackground(String.valueOf(imageUri));
                        emitter.onComplete();
                    }
                });

            });

        });
    }

    public Completable updateUserData(String newUserName, String newUserPhone, String newUserGender, String newBirthday) {
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

    public Completable updateUri(String uri) {
        return Completable.create(emitter -> {
            DocumentReference docRef = service.fireStore.collection(USER_LIST).document(service.auth.getCurrentUser().getUid());
            docRef.update(URI, uri).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onComplete();
                }
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
                        docRef.addSnapshotListener((value, error) -> {
                            if (value != null) {
                                UserDto userDto = new UserDto(
                                        value.getString(USER_NAME_KEY),
                                        value.getString(GENDER_KEY),
                                        value.getString(PHONE_NUMBER_KEY),
                                        value.getString(EMAIL_KEY),
                                        value.getString(BIRTHDAY_KEY),
                                        value.getString(URI),
                                        value.getString(BACKGROUND_IMAGE),
                                        Boolean.TRUE.equals(value.getBoolean(OWN_QUEUE)),
                                        Boolean.TRUE.equals(value.getBoolean(PARTICIPATE_IN_QUEUE))
                                );
                                UserDatabaseProvider.insertUser(userDto);
                                emitter.onSuccess(userDto);
                            }
                        });
                    });
        }
    }

    public Single<ImageDto> getBackgroundImage() {
        return Single.create(emitter -> {
            StorageReference local = service.storageReference.child("BACKGROUND_IMAGES/").child(BACKGROUND_IMAGE + service.auth.getCurrentUser().getUid());
            try {
                local.getDownloadUrl().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Uri uri = task.getResult();
                        emitter.onSuccess(new ImageDto(uri));
                    } else {
                        Log.d("Error", task.getException().getMessage());
                        emitter.onSuccess(new ImageDto(Uri.EMPTY));
                    }
                });
            } catch (RuntimeExecutionException e) {
                Log.d("RuntimeExecutionException", e.getMessage());
                emitter.onSuccess(new ImageDto(Uri.EMPTY));
            }

        });
//        }
    }

    public Single<ImageDto> getProfileImage() {
        return
                Single.create(emitter -> {
                    StorageReference local = service.storageReference.child(PROFILE_IMAGES).child(PROFILE_PHOTO + service.auth.getCurrentUser().getUid());
                    try {
                        local.getDownloadUrl().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Uri uri = task.getResult();
                                Log.d("Uri test", String.valueOf(uri));
                                emitter.onSuccess(new ImageDto(uri));
                            } else {
                                Log.d("Error", task.getException().getMessage());
                                emitter.onSuccess(new ImageDto(Uri.EMPTY));
                            }
                        });
                    } catch (RuntimeExecutionException e) {
                        Log.d("RuntimeExecutionException", e.getMessage());
                        emitter.onSuccess(new ImageDto(Uri.EMPTY));
                    }

                });
        //}
    }
}

