package com.example.myapplication.presentation.profile.createAccount.createCompanyAccountFragment.main;

import static com.example.myapplication.presentation.profile.createAccount.createCompanyAccountFragment.main.arguments.email;
import static com.example.myapplication.presentation.profile.createAccount.createCompanyAccountFragment.main.arguments.name;
import static com.example.myapplication.presentation.profile.createAccount.createCompanyAccountFragment.main.arguments.phoneNumber;
import static com.example.myapplication.presentation.profile.createAccount.createCompanyAccountFragment.main.arguments.service;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY;
import static com.example.myapplication.presentation.utils.constants.Utils.stringsServicesArray;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.company.CommonCompanyDI;
import com.example.myapplication.di.company.CompanyQueueUserDI;
import com.example.myapplication.di.restaurant.RestaurantUserDI;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Random;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class CreateCompanyAccountViewModel extends ViewModel {

    String companyID = null;

    private final MutableLiveData<List<DelegateItem>> _items = new MutableLiveData<>();
    LiveData<List<DelegateItem>> items = _items;

    private final MutableLiveData<String> _finished = new MutableLiveData<>(null);
    LiveData<String> finished = _finished;

    void initData() {
        createDocument(name, email, phoneNumber, service);
    }

    private void createDocument(String name, String email, String phone, String companyService) {
        if (companyService.equals(stringsServicesArray[0])) {
            companyID = generateID(COMPANY);
            CompanyQueueUserDI.createCompanyDocumentUseCase.invoke(companyID, name, email, phone, companyService)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onComplete() {
                            generateQrCode();
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }
                    });


        } else if (companyService.equals(stringsServicesArray[1])) {
            companyID = generateID(RESTAURANT);
            RestaurantUserDI.createRestaurantDocumentUseCase.invoke(companyID, name, email, phone)
                    .concatWith(CommonCompanyDI.setCompanyUserUseCase.invoke(companyID, name, RESTAURANT))
                    .subscribeOn(Schedulers.io())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onComplete() {
                            generateQrCode();
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }
                    });
        }


    }

    private String generateID(String title) {
        Random rand = new Random();
        int id = rand.nextInt(90000000) + 10000000;
        return "@" + title + "_" + id;
    }

    private Completable uploadImageToFireStorage(Bitmap qrCode, String companyId) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        qrCode.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] data = baos.toByteArray();

        return CompanyQueueUserDI.uploadCompanyBytesUseCase.invoke(companyId, data);
    }

    public void setArgumentsNull() {
        name = null;
        service = null;
        email = null;
        phoneNumber = null;
    }

    private void generateQrCode() {

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        try {

            BitMatrix bitMatrix = multiFormatWriter.encode(companyID, BarcodeFormat.QR_CODE, 300, 300);
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap qrCode = encoder.createBitmap(bitMatrix);

            uploadImageToFireStorage(qrCode, companyID)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onComplete() {
                            _finished.postValue(companyID);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Log.e("Exception", e.getMessage());
                        }
                    });

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

}