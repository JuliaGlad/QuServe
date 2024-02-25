package com.example.myapplication.data.repository;

import static com.example.myapplication.DI.service;
import static com.example.myapplication.presentation.utils.Utils.EMAIL_KEY;
import static com.example.myapplication.presentation.utils.Utils.GENDER_KEY;
import static com.example.myapplication.presentation.utils.Utils.OWN_QUEUE;
import static com.example.myapplication.presentation.utils.Utils.PARTICIPATE_IN_QUEUE;
import static com.example.myapplication.presentation.utils.Utils.PHONE_NUMBER_KEY;
import static com.example.myapplication.presentation.utils.Utils.PROFILE_IMAGES;
import static com.example.myapplication.presentation.utils.Utils.PROFILE_PHOTO;
import static com.example.myapplication.presentation.utils.Utils.PROFILE_UPDATED_AT;
import static com.example.myapplication.presentation.utils.Utils.USER_LIST;
import static com.example.myapplication.presentation.utils.Utils.USER_NAME_KEY;


import android.net.Uri;
import android.util.Log;

import com.example.myapplication.data.dto.ImageDto;
import com.example.myapplication.data.dto.UserDto;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class ProfileRepository {

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
                    .addOnCompleteListener(task -> emitter.onComplete());
        });

    }

    public Single<Boolean> checkVerification() {
        return Single.create(emitter -> {
            service.auth.getCurrentUser().reload().addOnCompleteListener(task -> {
                emitter.onSuccess(service.auth.getCurrentUser().isEmailVerified());
            });
        });

    }

    public boolean checkAuthentification() {
        return service.auth.getCurrentUser() != null && !service.auth.getCurrentUser().isAnonymous();
    }

    public void deleteAccount() {
        service.auth.getCurrentUser().delete();
        service.fireStore.collection(USER_LIST).document(service.auth.getCurrentUser().getUid()).delete();
    }

    public void logout() {
        service.auth.signOut();
    }

    public void sendResetPasswordEmail(String email) {
        service.auth.sendPasswordResetEmail(email);
    }

    public Completable sendEmailVerification() {
        return Completable.create(emitter -> {
            emitter.onComplete();
            service.auth.getCurrentUser().sendEmailVerification();
        });
    }

    public void updateOwnQueue(boolean value){
        DocumentReference docRef = service.fireStore.collection(USER_LIST).document(service.auth.getCurrentUser().getUid());
        docRef.update(OWN_QUEUE, value);
    }

    public void updateParticipateInQueue(boolean value){
        DocumentReference docRef = service.fireStore.collection(USER_LIST).document(service.auth.getCurrentUser().getUid());
        docRef.update(PARTICIPATE_IN_QUEUE, value);
    }

    public Completable createAccount(String email, String password, String userName, String phoneNumber) {
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
                    user.put(PHONE_NUMBER_KEY, phoneNumber);
                    user.put(EMAIL_KEY, email);
                    user.put(GENDER_KEY, null);

                    docRef.set(user);

                    emitter.onComplete();
                }
            });
        });
    }

    public Completable signInWithEmailAndPassword(String email, String password) {
        return Completable.create(emitter -> {
            service.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                emitter.onComplete();
            });
        });
    }

    public Completable uploadToFireStorage(Uri imageUri) {
        return Completable.create(emitter -> {
            StorageReference reference = service.storageReference.child(PROFILE_IMAGES + PROFILE_PHOTO + service.auth.getCurrentUser().getUid());
            reference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
                emitter.onComplete();
            });
        });
    }

    public Completable updateUserData(String newUserName, String newUserPhone, String newUserGender) {
        return Completable.create(emitter -> {
            DocumentReference docRef = service.fireStore.collection(USER_LIST).document(service.auth.getCurrentUser().getUid());
            FieldValue timestamp = FieldValue.serverTimestamp();
            docRef.update(
                    PROFILE_UPDATED_AT, timestamp,
                    USER_NAME_KEY, newUserName,
                    GENDER_KEY, newUserGender,
                    PHONE_NUMBER_KEY, newUserPhone).addOnCompleteListener(task -> emitter.onComplete());
        });
    }

    public Completable updateEmailField(String newEmail) {
        return Completable.create(emitter -> {
            service.fireStore.collection(USER_LIST).document(service.auth.getCurrentUser().getUid())
                    .update(EMAIL_KEY, newEmail);
        });
    }

    public Single<UserDto> getUserData() {
        return Single.create(emitter -> {
            DocumentReference docRef = service.fireStore.collection(USER_LIST).document(service.auth.getCurrentUser().getUid());
            docRef.addSnapshotListener((value, error) -> {
                if (value != null) {
                    emitter.onSuccess(new UserDto(value.getString(USER_NAME_KEY), value.getString(GENDER_KEY),
                            value.getString(PHONE_NUMBER_KEY), value.getString(EMAIL_KEY), Boolean.TRUE.equals(value.getBoolean(OWN_QUEUE)), Boolean.TRUE.equals(value.getBoolean(PARTICIPATE_IN_QUEUE))));
                }
            });
        });
    }

    public Single<ImageDto> getProfileImage() {
        return Single.create(emitter -> {
            StorageReference local = service.storageReference.child(PROFILE_IMAGES).child(PROFILE_PHOTO + service.auth.getCurrentUser().getUid());
            emitter.onSuccess(new ImageDto(local.getDownloadUrl()));
        });
    }
}

