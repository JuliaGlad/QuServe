package com.example.myapplication.presentation.restaurantLocation.addLocation;

import static com.example.myapplication.presentation.utils.constants.Restaurant.COOKS;
import static com.example.myapplication.presentation.utils.constants.Restaurant.WAITERS;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.restaurant.RestaurantEmployeeDI;
import com.example.myapplication.di.restaurant.RestaurantLocationDI;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;
import java.util.Random;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddLocationViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _isAdded = new MutableLiveData<>();
    LiveData<Boolean> isAdded = _isAdded;

    public void addLocation(String restaurantId, String location, String city) {

       String locationId = generateLocationId();

        RestaurantLocationDI.createRestaurantLocationDocumentUseCase.invoke(restaurantId, locationId, location, city)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull String path) {
                        generateCookQrCode(path, locationId);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    private String generateLocationId() {
        Random rand = new Random();
        int id = rand.nextInt(90000000) + 10000000;
        return "@Location_" + id;
    }

    private void generateCookQrCode(String path, String locationId) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        try {

            BitMatrix bitMatrix = multiFormatWriter.encode(path + "/" + COOKS, BarcodeFormat.QR_CODE, 300, 300);
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap qrCode = encoder.createBitmap(bitMatrix);

            uploadCookQrCode(qrCode, locationId, path);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void generateWaiterQrCode(String path, String locationId) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        try {

            BitMatrix bitMatrix = multiFormatWriter.encode(path, BarcodeFormat.QR_CODE, 300, 300);
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap qrCode = encoder.createBitmap(bitMatrix);

            uploadWaitersQrCode(qrCode, locationId);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void uploadCookQrCode(Bitmap qrCode, String locationId, String path) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        qrCode.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] data = baos.toByteArray();

        RestaurantEmployeeDI.uploadCookQrCodeUseCase.invoke(locationId, data)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        generateWaiterQrCode(path + "/" + WAITERS, locationId);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    private void uploadWaitersQrCode(Bitmap qrCode, String locationId) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        qrCode.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] data = baos.toByteArray();

        RestaurantEmployeeDI.uploadWaiterQrCodeUseCase.invoke(locationId, data)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _isAdded.postValue(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}