package com.example.myapplication.presentation.basicQueue.createQueue.mainFragment;

import static com.example.myapplication.presentation.basicQueue.createQueue.arguments.Arguments.queueName;
import static com.example.myapplication.presentation.basicQueue.createQueue.arguments.Arguments.queueTime;

import android.graphics.Bitmap;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.profile.ProfileDI;
import com.example.myapplication.di.QueueDI;
import com.example.myapplication.presentation.basicQueue.createQueue.arguments.Arguments;
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
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class CreateQueueViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _onComplete = new MutableLiveData<>(false);
    LiveData<Boolean> onComplete = _onComplete;

    public void setArgumentsNull() {
        queueName = null;
        queueTime = null;
    }

    public void initQueueData() {
        Arguments.queueID = generateID();
        createQueueDocument(Arguments.queueID);
    }

    private void createQueueDocument(String queueID) {
        QueueDI.createQueueDocumentUseCase.invoke(queueID, queueName, queueTime)
                .flatMapCompletable(path -> {
                    generateQrCode(path, queueID);
                    return ProfileDI.updateOwnQueueUseCase.invoke(queueID);
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d("Successfully created", "created");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("Error creating document", e.getMessage());
                    }
                });
    }

    private String generateID() {
        Random rand = new Random();
        int id = rand.nextInt(90000000) + 10000000;
        return "@" + id;
    }

    private Completable uploadImageToFireStorage(Bitmap qrCode, String queueID) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        qrCode.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] data = baos.toByteArray();

        return QueueDI.uploadBytesToFireStorageUseCase.invoke(queueID, data);
    }

    private void uploadPdfToFireStorage(File file, String queueID) {
        QueueDI.uploadFileToFireStorageUseCase.invoke(file, queueID).subscribeOn(Schedulers.io())
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

    private void generateQrCode(String path, String queueId) {

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        try {

            BitMatrix bitMatrix = multiFormatWriter.encode(path, BarcodeFormat.QR_CODE, 300, 300);
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap qrCode = encoder.createBitmap(bitMatrix);

            createPdfDocument(qrCode, queueId);

            uploadImageToFireStorage(qrCode, queueId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onComplete() {
                            _onComplete.postValue(true);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Log.e("Exception", e.getMessage());
                        }
                    });

        } catch (WriterException e) {
            Log.e("Exception", e.getMessage());
        }
    }

    private void createPdfDocument(Bitmap qrCode, String queueID) {

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

                uploadPdfToFireStorage(file, queueID);

                Log.e("File", file.getAbsolutePath() + queueID);

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