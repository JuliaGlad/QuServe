package com.example.myapplication.data.repository;

import static com.example.myapplication.DI.service;
import static com.example.myapplication.presentation.utils.Utils.COMPANY;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_EMAIL;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_NAME;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_PHONE;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_SERVICE;
import static com.example.myapplication.presentation.utils.Utils.EMPLOYEES;
import static com.example.myapplication.presentation.utils.Utils.EMPLOYEE_EMAIL;
import static com.example.myapplication.presentation.utils.Utils.EMPLOYEE_NAME;
import static com.example.myapplication.presentation.utils.Utils.EMPLOYEE_ROLE;
import static com.example.myapplication.presentation.utils.Utils.MID_TIME_WAITING;
import static com.example.myapplication.presentation.utils.Utils.PEOPLE_PASSED;
import static com.example.myapplication.presentation.utils.Utils.QR_CODES;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_AUTHOR_KEY;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_IN_PROGRESS;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_LIFE_TIME_KEY;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_NAME_KEY;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_PARTICIPANTS_LIST;
import static com.example.myapplication.presentation.utils.Utils.USER_LIST;
import static com.example.myapplication.presentation.utils.Utils.WORKER;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplication.DI;
import com.example.myapplication.data.dto.CompanyDto;
import com.example.myapplication.data.dto.EmployeeDto;
import com.example.myapplication.data.dto.ImageDto;
import com.example.myapplication.data.dto.QueueDto;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CompanyQueueRepository {

    public Single<CompanyDto> getCompanyByStringPath(String path){
        return Single.create(emitter -> {
            service.fireStore.document(path).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    emitter.onSuccess(new CompanyDto(
                            document.getId(),
                            document.getString(COMPANY_NAME),
                            document.getString(COMPANY_EMAIL),
                            document.getString(COMPANY_PHONE),
                            document.getString(COMPANY_SERVICE)
                    ));
                }
            });
        });
    }

    public Single<List<CompanyDto>> getCompany() {
        return Single.create(emitter -> {
            service.fireStore
                    .collection(USER_LIST)
                    .document(service.auth.getCurrentUser().getUid())
                    .collection(COMPANY)
                    .get().addOnCompleteListener(task -> {
                        List<DocumentSnapshot> documents = task.getResult().getDocuments();
                        emitter.onSuccess(documents.stream().map(
                                        document -> new CompanyDto(
                                                document.getId(),
                                                document.getString(COMPANY_NAME),
                                                document.getString(COMPANY_EMAIL),
                                                document.getString(COMPANY_PHONE),
                                                document.getString(COMPANY_SERVICE))
                                ).collect(Collectors.toList())
                        );
                    });
        });
    }

    public Completable uploadCompanyBytesToFireStorage(String companyId, byte[] data) {
        StorageReference reference = service.storageReference
                .child("COMPANIES/")
                .child(companyId + "/" + "EMPLOYEES_QR-CODE.jpg");

        return Completable.create(emitter -> {
            reference.putBytes(data).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onComplete();
                }
            });
        });
    }

    public void createCompanyDocument(String companyID, String name, String email, String phone, String companyService) {
        DocumentReference docRef = service.fireStore
                .collection(USER_LIST)
                .document(service.auth.getCurrentUser().getUid())
                .collection(COMPANY)
                .document(companyID);

        Map<String, Object> company = new HashMap<>();

        company.put(COMPANY_NAME, name);
        company.put(COMPANY_EMAIL, email);
        company.put(COMPANY_PHONE, phone);
        company.put(COMPANY_SERVICE, companyService);

        docRef.set(company);
    }

    public String getCompanyPath(String companyID) {
        return service.fireStore
                .collection(USER_LIST)
                .document(service.auth.getCurrentUser().getUid())
                .collection(COMPANY)
                .document(companyID)
                .getPath();
    }

    public Single<List<EmployeeDto>> getEmployees(String companyId) {
        return Single.create(emitter -> {
            service.fireStore
                    .collection(USER_LIST)
                    .document(service.auth.getCurrentUser().getUid())
                    .collection(COMPANY)
                    .document(companyId)
                    .collection(EMPLOYEES)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            if (task.isSuccessful()) {
                                List<DocumentSnapshot> documents = task.getResult().getDocuments();
                                emitter.onSuccess(
                                        documents.stream().map(document -> new EmployeeDto(
                                                document.getString(EMPLOYEE_NAME),
                                                document.getId(),
                                                document.getString(EMPLOYEE_ROLE))
                                        ).collect(Collectors.toList()));


                            }
                        }
                    });
        });
    }

    public Single<Boolean> checkCompany() {
        return Single.create(emitter -> {
            service.fireStore
                    .collection(USER_LIST)
                    .document(service.auth.getCurrentUser().getUid())
                    .collection(COMPANY)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onSuccess(task.getResult().size() > 0);
                        }
                    });
        });
    }

    public Single<ImageDto> getEmployeeQrCode(String companyID) {
        return Single.create(emitter -> {
            StorageReference local = service.storageReference.child("COMPANIES/").child(  companyID + "/").child("EMPLOYEES_QR-CODE.jpg");
            local.getDownloadUrl().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onSuccess(new ImageDto(local.getDownloadUrl()));
                }
            });
        });
    }

    public Completable addEmployee(String path,String name, String email){
       return Completable.create(emitter -> {
           DocumentReference docRef = service.fireStore.document(path).collection(EMPLOYEES).document(service.auth.getCurrentUser().getUid());

           Map<String, Object> employee = new HashMap<>();

           employee.put(EMPLOYEE_NAME, name);
           employee.put(EMPLOYEE_ROLE, WORKER);
           employee.put(EMPLOYEE_EMAIL, email);

           docRef.set(employee).addOnCompleteListener(task -> {
               if (task.isSuccessful()){
                    emitter.onComplete();
               }
           });
       });

    }

}
