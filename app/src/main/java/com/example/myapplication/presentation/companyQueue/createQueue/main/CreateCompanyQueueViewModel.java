package com.example.myapplication.presentation.companyQueue.createQueue.main;

import static com.example.myapplication.presentation.companyQueue.createQueue.arguments.Arguments.city;
import static com.example.myapplication.presentation.companyQueue.createQueue.arguments.Arguments.queueLocation;
import static com.example.myapplication.presentation.companyQueue.createQueue.arguments.Arguments.queueName;
import static com.example.myapplication.presentation.companyQueue.createQueue.arguments.Arguments.queueTime;
import static com.example.myapplication.presentation.utils.Utils.CITY_KEY;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.Utils.FINE_PERMISSION_CODE;
import static com.example.myapplication.presentation.utils.Utils.PAGE_1;
import static com.example.myapplication.presentation.utils.Utils.PAGE_2;
import static com.example.myapplication.presentation.utils.Utils.PAGE_3;
import static com.example.myapplication.presentation.utils.Utils.PAGE_4;
import static com.example.myapplication.presentation.utils.Utils.PAGE_KEY;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_LOCATION_KEY;
import static com.example.myapplication.presentation.utils.Utils.WORKERS_LIST;
import static com.example.myapplication.presentation.utils.Utils.stringsTimeArray;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.DI;
import com.example.myapplication.R;
import com.example.myapplication.presentation.basicQueue.createQueue.arguments.Arguments;
import com.example.myapplication.presentation.companyQueue.createQueue.CreateCompanyQueueActivity;
import com.example.myapplication.presentation.companyQueue.createQueue.delegates.chooseLocation.LocationDelegateItem;
import com.example.myapplication.presentation.companyQueue.createQueue.delegates.chooseLocation.LocationModel;
import com.example.myapplication.presentation.companyQueue.createQueue.delegates.workers.WorkerDelegateItem;
import com.example.myapplication.presentation.companyQueue.createQueue.delegates.workers.WorkerModel;
import com.example.myapplication.presentation.companyQueue.models.EmployeeModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
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

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.ui.items.items.autoCompleteText.AutoCompleteTextDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.autoCompleteText.AutoCompleteTextModel;
import myapplication.android.ui.recycler.ui.items.items.editText.EditTextDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.editText.EditTextModel;
import myapplication.android.ui.recycler.ui.items.items.floatingActionButton.FloatingActionButtonDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.floatingActionButton.FloatingActionButtonModel;
import myapplication.android.ui.recycler.ui.items.items.textView.TextViewHeaderDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.textView.TextViewHeaderModel;

public class CreateCompanyQueueViewModel extends ViewModel {

    private List<EmployeeModel> employeeModels = new ArrayList<>();
    private final List<DelegateItem> itemList = new ArrayList<>();

    private final MutableLiveData<Boolean> _openMap = new MutableLiveData<>(false);
    public LiveData<Boolean> openMap = _openMap;

    private final MutableLiveData<List<DelegateItem>> _items = new MutableLiveData<>();
    public LiveData<List<DelegateItem>> items = _items;

    public void onPageInit(String page, String companyId, String[] array, Fragment fragment) {
        switch (page) {
            case PAGE_1:
                itemList.add(new TextViewHeaderDelegateItem(new TextViewHeaderModel(1, R.string.enter_name, 24)));
                itemList.add(new EditTextDelegateItem(new EditTextModel(3, R.string.name, queueName, InputType.TYPE_CLASS_TEXT, true, stringName -> {
                    queueName = stringName;
                })));
                break;

            case PAGE_2:
                itemList.add(new TextViewHeaderDelegateItem(new TextViewHeaderModel(2, R.string.set_queue_life_time, 24)));
                itemList.add(new AutoCompleteTextDelegateItem(new AutoCompleteTextModel(3, R.array.lifetime, R.string.no_set_lifetime, stringTime -> {
                    for (int i = 0; i < array.length; i++) {
                        if (array[i].equals(stringTime)) {
                            queueTime = stringsTimeArray[i];
                        }
                    }
                })));
                break;

            case PAGE_3:
                if (fragment.getArguments().getString(QUEUE_LOCATION_KEY) != null) {
                    queueLocation = fragment.getArguments().getString(QUEUE_LOCATION_KEY);
                    city = fragment.getArguments().getString(CITY_KEY);
                }
                itemList.add(new TextViewHeaderDelegateItem(new TextViewHeaderModel(1, R.string.choose_queue_location, 24)));
                itemList.add(new LocationDelegateItem(new LocationModel(2, queueLocation, () -> {
                    if (checkSelfPermission(fragment)) {
                        NavHostFragment.findNavController(fragment)
                                .navigate(R.id.action_createCompanyQueueFragment_to_mapFragment);
                    }
                })));
                break;
            case PAGE_4:

                if (itemList.size() == 0) {

                    if (fragment.getArguments().getString(WORKERS_LIST) != null) {
                        String workers = fragment.getArguments().getString(WORKERS_LIST);
                        employeeModels = new Gson().fromJson(workers, new TypeToken<List<EmployeeModel>>() {
                        }.getType());
                    }

                    itemList.add(new TextViewHeaderDelegateItem(new TextViewHeaderModel(1, R.string.add_workers, 24)));
                    addWorkers();
                    itemList.add(new FloatingActionButtonDelegateItem(new FloatingActionButtonModel(3, () -> {
                        Bundle bundle = new Bundle();
                        bundle.putString(COMPANY_ID, companyId);
                        bundle.putString(WORKERS_LIST, new Gson().toJson(employeeModels));
                        NavHostFragment.findNavController(fragment)
                                .navigate(R.id.action_createCompanyQueueFragment_to_chooseWorkersFragment, bundle);
                    })));
                }
                break;
        }
        _items.postValue(itemList);
    }

    public void navigateNext(String page, Fragment fragment, String companyId) {
        switch (page) {
            case PAGE_1:
                if (queueName == null) {
                    Snackbar.make(fragment.getView(), "You need to write name", Snackbar.LENGTH_LONG).show();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString(PAGE_KEY, PAGE_2);
                    NavHostFragment.findNavController(fragment)
                            .navigate(R.id.action_createCompanyQueueFragment_self, bundle);
                }
                break;
            case PAGE_2:
                if (queueTime != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString(PAGE_KEY, PAGE_3);
                    NavHostFragment.findNavController(fragment)
                            .navigate(R.id.action_createCompanyQueueFragment_self, bundle);
                }
                break;

            case PAGE_3:
                if (queueLocation != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString(PAGE_KEY, PAGE_4);
                    NavHostFragment.findNavController(fragment)
                            .navigate(R.id.action_createCompanyQueueFragment_self, bundle);
                }
                break;

            case PAGE_4:
                initQueueData(fragment, companyId);
                break;
        }
    }

    public void navigateBack(String page, Fragment fragment) {
        switch (page) {

            case PAGE_1:
                fragment.requireActivity().finish();
                break;

            case PAGE_2:
                Bundle bundleSecond = new Bundle();
                bundleSecond.putString(PAGE_KEY, PAGE_1);
                NavHostFragment.findNavController(fragment)
                        .navigate(R.id.action_createCompanyQueueFragment_self, bundleSecond);
                break;

            case PAGE_3:
                Bundle bundleThird = new Bundle();
                bundleThird.putString(PAGE_KEY, PAGE_2);
                NavHostFragment.findNavController(fragment)
                        .navigate(R.id.action_createCompanyQueueFragment_self, bundleThird);
                break;

            case PAGE_4:
                Bundle bundleFourth = new Bundle();
                bundleFourth.putString(PAGE_KEY, PAGE_3);
                NavHostFragment.findNavController(fragment)
                        .navigate(R.id.action_createCompanyQueueFragment_self, bundleFourth);
                break;
        }
    }

    public void setArgumentsNull() {
        queueName = null;
        queueTime = null;
        queueLocation = null;
        employeeModels.clear();
    }

    private void addWorkers() {
        if (employeeModels.size() != 0) {
            for (int i = 0; i < employeeModels.size(); i++) {
                itemList.add(new WorkerDelegateItem(new WorkerModel(i + 1, employeeModels.get(i).getName())));
            }
        }
    }

    private void initQueueData(Fragment fragment, String companyId) {
        Arguments.queueID = generateID();
        createQueueDocument(Arguments.queueID, companyId);
        generateQrCode(Arguments.queueID, fragment);
    }

    private void createQueueDocument(String queueID, String companyId) {
        DI.createCompanyQueueDocumentUseCase.invoke(queueID, city, queueTime, queueName, queueLocation, companyId, employeeModels);
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
        DI.uploadFileToFireStorageUseCase.invoke(file, queueID)
                .subscribeOn(Schedulers.io())
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

            createPdfDocument(qrCode, queueID);

            uploadImageToFireStorage(qrCode, queueID)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onComplete() {
                            NavHostFragment.findNavController(fragment)
                                    .navigate(R.id.action_createCompanyQueueFragment_to_finishedQueueCreationFragment2);
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

            } else {
                Log.e("NOT EXIST!", "File does not exists");
            }

        } catch (FileNotFoundException e) {
            Log.e("FileNotFoundException", e.getMessage());
        } catch (IOException e) {
            Log.e("IOEXCEPTION", e.getMessage());
        }
    }

    private boolean checkSelfPermission(Fragment fragment) {
        boolean permission = false;
        if (ActivityCompat.checkSelfPermission(fragment.requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(fragment.requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(fragment.requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
        } else {
            permission = true;
        }
        return permission;
    }

}