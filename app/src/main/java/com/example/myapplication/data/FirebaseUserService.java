package com.example.myapplication.data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseUserService {

    private static FirebaseUserService INSTANCE;


    public FirebaseAuth auth = FirebaseAuth.getInstance();
    public FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
    public StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    public static FirebaseUserService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FirebaseUserService();
        }
        return INSTANCE;
    }

    private FirebaseUserService() {}
}
