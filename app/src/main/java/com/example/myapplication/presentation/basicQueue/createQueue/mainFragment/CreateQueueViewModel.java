package com.example.myapplication.presentation.basicQueue.createQueue.mainFragment;

import static android.os.Build.VERSION.SDK_INT;
import static com.example.myapplication.presentation.basicQueue.createQueue.arguments.Arguments.queueName;
import static com.example.myapplication.presentation.basicQueue.createQueue.arguments.Arguments.queueTime;
import static com.example.myapplication.presentation.utils.Utils.BASIC;
import static com.example.myapplication.presentation.utils.Utils.FINE_PERMISSION_CODE;
import static com.example.myapplication.presentation.utils.Utils.PAGE_1;
import static com.example.myapplication.presentation.utils.Utils.PAGE_2;
import static com.example.myapplication.presentation.utils.Utils.STATE;
import static com.example.myapplication.presentation.utils.Utils.stringsTimeArray;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.InputType;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.DI;
import com.example.myapplication.R;
import com.example.myapplication.presentation.basicQueue.createQueue.arguments.Arguments;
import com.google.android.material.snackbar.Snackbar;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.ui.items.items.autoCompleteText.AutoCompleteTextDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.autoCompleteText.AutoCompleteTextModel;
import myapplication.android.ui.recycler.ui.items.items.editText.EditTextDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.editText.EditTextModel;
import myapplication.android.ui.recycler.ui.items.items.textView.TextViewHeaderDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.textView.TextViewHeaderModel;


public class CreateQueueViewModel extends ViewModel {

    private final MutableLiveData<List<DelegateItem>> _items = new MutableLiveData<>();
    public LiveData<List<DelegateItem>> items = _items;

    public void onPageInit(String page, String[] array) {
        switch (page) {
            case PAGE_1:
                buildList(new DelegateItem[]{
                        new TextViewHeaderDelegateItem(new TextViewHeaderModel(2, R.string.enter_name, 24)),
                        new EditTextDelegateItem(new EditTextModel(3, R.string.name, queueName, InputType.TYPE_CLASS_TEXT, true, stringName -> {
                            queueName = stringName;
                        }))
                });
                break;

            case PAGE_2:
                buildList(new DelegateItem[]{
                        new TextViewHeaderDelegateItem(new TextViewHeaderModel(2, R.string.set_queue_life_time, 24)),
                        new AutoCompleteTextDelegateItem(new AutoCompleteTextModel(3, R.array.lifetime, R.string.no_set_lifetime, stringTime -> {
                            for (int i = 0; i < array.length; i++) {
                                if (array[i].equals(stringTime)) {
                                    queueTime = stringsTimeArray[i];
                                }
                            }
                        }))
                });
                break;
        }
    }

    public void navigateNext(String page, Fragment fragment) {
        switch (page) {
            case PAGE_1:
                if (queueName == null) {
                    Snackbar.make(fragment.getView(), "You need to write name", Snackbar.LENGTH_LONG).show();
                } else {
                    NavHostFragment.findNavController(fragment)
                            .navigate(CreateQueueFragmentDirections.actionCreateQueueFragmentSelf(PAGE_2));
                }
                break;
            case PAGE_2:

                if (askPermission(fragment)) {
                    initQueueData(fragment);
                    setArgumentsNull();
                }
                break;
        }
    }

    public void navigateBack(String page, Fragment fragment) {

        switch (page) {
            case PAGE_1:
                fragment.requireActivity().finish();
                break;
            case PAGE_2:
                NavHostFragment.findNavController(fragment)
                        .navigate(CreateQueueFragmentDirections.actionCreateQueueFragmentSelf(PAGE_1));
                break;

        }
    }

    public void setArgumentsNull() {
        queueName = null;
        queueTime = null;
    }

    private void initQueueData(Fragment fragment) {
        Arguments.queueID = generateID();
        createQueueDocument(Arguments.queueID);
        generateQrCode(Arguments.queueID, fragment);

    }

    private void createQueueDocument(String queueID) {
        DI.createQueueDocumentUseCase.invoke(queueID, queueName, queueTime);
        DI.updateOwnQueueUseCase.invoke(true);
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

        return DI.uploadBytesToFireStorageUseCase.invoke(queueID, data);
    }

    private void uploadPdfToFireStorage(File file, String queueID) {
        DI.uploadFileToFireStorageUseCase.invoke(file, queueID).subscribeOn(Schedulers.io())
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

    private void generateQrCode(String queueID, Fragment fragment) {

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        try {

            BitMatrix bitMatrix = multiFormatWriter.encode(queueID, BarcodeFormat.QR_CODE, 300, 300);
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap qrCode = encoder.createBitmap(bitMatrix);

            createPdfDocument(qrCode, queueID, fragment);

            uploadImageToFireStorage(qrCode, queueID)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onComplete() {

                            Bundle bundle = new Bundle();
                            bundle.putString(STATE, BASIC);

                            NavHostFragment.findNavController(fragment)
                                    .navigate(R.id.action_createQueueFragment_to_finishedQueueCreationFragment, bundle);
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

    private void createPdfDocument(Bitmap qrCode, String queueID, Fragment fragment) {

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

    private void buildList(DelegateItem[] items) {
        List<DelegateItem> list = new ArrayList<>(Arrays.asList(items));
        _items.setValue(list);
    }

    private boolean askPermission(Fragment fragment) {
        boolean permission = false;
        if (SDK_INT >= 30) {
            if (!Environment.isExternalStorageManager()) {
                try {

                    Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.setData(Uri.parse(String.format("package:%s", fragment.requireContext().getApplicationContext().getPackageName())));
                    fragment.requireActivity().startActivity(intent);

                } catch (Exception e) {

                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    fragment.requireActivity().startActivity(intent);

                }

            } else {
                permission = true;
            }
        } else {
            if (ActivityCompat.checkSelfPermission(fragment.requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(fragment.requireActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, FINE_PERMISSION_CODE);
            }
            if (ActivityCompat.checkSelfPermission(fragment.requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(fragment.requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, FINE_PERMISSION_CODE);
            }
            permission = true;
        }
        return permission;
    }

}