package com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.addTable;

import android.graphics.Bitmap;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.QueueDI;
import com.example.myapplication.di.RestaurantDI;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddTableViewModel extends ViewModel {

    private final MutableLiveData<String> _isAdded = new MutableLiveData<>(null);
    LiveData<String> isAdded = _isAdded;

    public void addTable(String restaurantId, String locationId, String number) {
        String tableId = generateId();
        RestaurantDI.addRestaurantTablesUseCase.invoke(restaurantId, locationId, tableId, number)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull String path) {
                        generateQrCode(path, tableId);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    private String generateId() {
        Random rand = new Random();
        int id = rand.nextInt(90000000) + 10000000;
        return "@" + id;
    }

    private Completable uploadImageToFireStorage(Bitmap qrCode, String tableId) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        qrCode.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] data = baos.toByteArray();

        return RestaurantDI.uploadTableQrCodeJpgUseCase.invoke(tableId, data);
    }

    private void generateQrCode(String path, String tableId) {

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(path, BarcodeFormat.QR_CODE, 300, 300);
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap qrCode = encoder.createBitmap(bitMatrix);

            createPdfDocument(qrCode, path);

            uploadImageToFireStorage(qrCode, tableId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onComplete() {
                            _isAdded.postValue(tableId);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }
                    });

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void uploadPdfToFireStorage(File file, String tableId) {
        RestaurantDI.uploadTableQrCodePdfUseCase.invoke(file, tableId).subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d("Upload to FireStorage", "uploaded");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("Upload to fireStorage", e.getMessage());
                    }
                });
    }

    private void createPdfDocument(Bitmap qrCode, String tableId) {

        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(qrCode.getWidth(), qrCode.getHeight(), 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        page.getCanvas().drawBitmap(qrCode, 0, 0, null);

        pdfDocument.finishPage(page);

        File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File folder = new File(root.getAbsolutePath());

        if (!folder.exists()) {
            folder.mkdirs();
        }

        File file = new File(folder.getAbsolutePath() + "/" + "QR-CODE.pdf");

        try {
            if (file.exists()) {
                FileOutputStream fos = new FileOutputStream(file);
                pdfDocument.writeTo(fos);
                pdfDocument.close();
                fos.close();

                uploadPdfToFireStorage(file, tableId);

                Log.e("File", file.getAbsolutePath() + tableId);

            } else {

                Log.e("NOT EXIST!", "File does not exists");
            }
        } catch (FileNotFoundException e) {
            Log.e("FileNotFoundException", e.getMessage());
        } catch (IOException e) {
            Log.e("IOEXCEPTION", e.getMessage());
        }
    }

}