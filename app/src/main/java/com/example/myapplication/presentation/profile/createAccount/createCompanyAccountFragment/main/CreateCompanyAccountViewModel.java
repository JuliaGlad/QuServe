package com.example.myapplication.presentation.profile.createAccount.createCompanyAccountFragment.main;

import static com.example.myapplication.presentation.profile.createAccount.createCompanyAccountFragment.arguments.email;
import static com.example.myapplication.presentation.profile.createAccount.createCompanyAccountFragment.arguments.name;
import static com.example.myapplication.presentation.profile.createAccount.createCompanyAccountFragment.arguments.phoneNumber;
import static com.example.myapplication.presentation.profile.createAccount.createCompanyAccountFragment.arguments.service;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.Utils.PAGE_1;
import static com.example.myapplication.presentation.utils.Utils.PAGE_2;
import static com.example.myapplication.presentation.utils.Utils.PAGE_3;
import static com.example.myapplication.presentation.utils.Utils.PAGE_4;
import static com.example.myapplication.presentation.utils.Utils.PAGE_5;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_ID;
import static com.example.myapplication.presentation.utils.Utils.stringsServicesArray;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.InputType;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.fragment.NavHostFragment;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.myapplication.DI;
import com.example.myapplication.R;
import com.example.myapplication.presentation.utils.workers.ApprovedWorker;
import com.example.myapplication.presentation.utils.workers.QueueTimeWorker;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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

public class CreateCompanyAccountViewModel extends ViewModel {

    String companyID = null;

    private final MutableLiveData<List<DelegateItem>> _items = new MutableLiveData<>();
    LiveData<List<DelegateItem>> items = _items;

    private final MutableLiveData<String> _finished = new MutableLiveData<>(null);
    LiveData<String> finished = _finished;

    public void onPageInit(String page) {
        switch (page) {
            case PAGE_1:

                buildList(new DelegateItem[]{
                        new TextViewHeaderDelegateItem(new TextViewHeaderModel(0, R.string.enter_company_name, 24)),
                        new EditTextDelegateItem(new EditTextModel(1, R.string.company_name, name, InputType.TYPE_CLASS_TEXT, true, stringName -> {
                            name = stringName;
                        }))
                });
                break;
            case PAGE_2:
                buildList(new DelegateItem[]{
                        new TextViewHeaderDelegateItem(new TextViewHeaderModel(0, R.string.add_work_email, 24)),
                        new EditTextDelegateItem(new EditTextModel(1, R.string.work_email, email, InputType.TYPE_CLASS_TEXT, true, stringEmail -> {
                            email = stringEmail;
                        }))
                });
                break;
            case PAGE_3:
                buildList(new DelegateItem[]{
                        new TextViewHeaderDelegateItem(new TextViewHeaderModel(0, R.string.add_work_phone, 24)),
                        new EditTextDelegateItem(new EditTextModel(1, R.string.work_phone_number, phoneNumber, InputType.TYPE_CLASS_PHONE, true, stringPhone -> {
                            phoneNumber = stringPhone;
                        }))
                });
                break;

            case PAGE_4:
                buildList(new DelegateItem[]{
                        new TextViewHeaderDelegateItem(new TextViewHeaderModel(0, R.string.choose_service, 24)),
                        new AutoCompleteTextDelegateItem(new AutoCompleteTextModel(1, R.array.services, R.string.select_item, serviceString -> {
                            for (String item : stringsServicesArray) {
                                if (serviceString.equals(item)) {
                                    service = item;
                                    break;
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
                if (name == null) {
                    Snackbar.make(fragment.getView(), "You field is required!", Snackbar.LENGTH_LONG).show();
                } else {
                    NavHostFragment.findNavController(fragment)
                            .navigate(CreateCompanyAccountFragmentDirections.actionCreateCompanyAccountFragmentSelf(PAGE_2));
                }
                break;
            case PAGE_2:
                if (email == null) {
                    Snackbar.make(fragment.getView(), "You field is required!", Snackbar.LENGTH_LONG).show();
                }
                NavHostFragment.findNavController(fragment)
                        .navigate(CreateCompanyAccountFragmentDirections.actionCreateCompanyAccountFragmentSelf(PAGE_3));
                break;
            case PAGE_3:
                NavHostFragment.findNavController(fragment)
                        .navigate(CreateCompanyAccountFragmentDirections.actionCreateCompanyAccountFragmentSelf(PAGE_4));
                break;

            case PAGE_4:
                if (service == null) {
                    Snackbar.make(fragment.getView(), "You field is required!", Snackbar.LENGTH_LONG).show();
                } else {
                    initQueueData(fragment);
                }
                break;
        }
    }

    public void navigateBack(String page, Fragment fragment) {
        switch (page) {
            case PAGE_1:
                setArgumentsNull();
                fragment.requireActivity().finish();
                break;
            case PAGE_2:
                NavHostFragment.findNavController(fragment)
                        .navigate(CreateCompanyAccountFragmentDirections.actionCreateCompanyAccountFragmentSelf(PAGE_1));
                break;
            case PAGE_3:
                NavHostFragment.findNavController(fragment)
                        .navigate(CreateCompanyAccountFragmentDirections.actionCreateCompanyAccountFragmentSelf(PAGE_2));
                break;
            case PAGE_4:
                NavHostFragment.findNavController(fragment)
                        .navigate(CreateCompanyAccountFragmentDirections.actionCreateCompanyAccountFragmentSelf(PAGE_3));
                break;
            case PAGE_5:
                NavHostFragment.findNavController(fragment)
                        .navigate(CreateCompanyAccountFragmentDirections.actionCreateCompanyAccountFragmentSelf(PAGE_4));
        }
    }

    private void initQueueData(Fragment fragment) {
        companyID = generateID();
        String path = DI.getCompanyPathUseCase.invoke(companyID);
        createQueueDocument(companyID, name, email, phoneNumber, service);
        generateQrCode(path, fragment);
    }

    private void createQueueDocument(String companyID, String name, String email, String phone, String companyService) {
        DI.createCompanyDocumentUseCase.invoke(companyID, name, email, phone, companyService);
    }

    private String generateID() {
        Random rand = new Random();
        int id = rand.nextInt(90000000) + 10000000;
        return "@COMPANY_" + id;
    }

    private Completable uploadImageToFireStorage(Bitmap qrCode, String companyId) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        qrCode.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] data = baos.toByteArray();

        return DI.uploadCompanyBytesUseCase.invoke(companyId, data);
    }

    public void setArgumentsNull(){
        name = null;
        service = null;
        email = null;
        phoneNumber = null;
    }

    private void generateQrCode(String path, Fragment fragment) {

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        try {

            BitMatrix bitMatrix = multiFormatWriter.encode(path, BarcodeFormat.QR_CODE, 300, 300);
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap qrCode = encoder.createBitmap(bitMatrix);

            uploadImageToFireStorage(qrCode,companyID)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onComplete() {

                            Data data = new Data.Builder()
                                    .putString(COMPANY_ID, companyID)
                                    .build();

                            Constraints constraints = new Constraints.Builder()
                                    .setRequiresDeviceIdle(false)
                                    .build();

                            OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(QueueTimeWorker.class)
                                    .setInputData(data)
                                    .setConstraints(constraints)
                                    .setInitialDelay(10, TimeUnit.MINUTES)
                                    .build();

                            WorkManager.getInstance(fragment.requireContext()).enqueue(request);

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

    private void buildList(DelegateItem[] items) {
        List<DelegateItem> list = Arrays.asList(items);
        _items.postValue(list);
    }

}