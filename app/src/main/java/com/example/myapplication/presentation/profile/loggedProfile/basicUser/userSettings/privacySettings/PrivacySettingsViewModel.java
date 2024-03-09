package com.example.myapplication.presentation.profile.loggedProfile.basicUser.userSettings.privacySettings;

import static com.example.myapplication.presentation.utils.Utils.EMAIL;
import static com.example.myapplication.presentation.utils.Utils.PASSWORD;

import android.content.DialogInterface;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;
import com.example.myapplication.R;
import com.example.myapplication.presentation.dialogFragments.changeEmail.ChangeEmailDialogFragment;
import com.example.myapplication.presentation.dialogFragments.emailUpdateSuccessful.EmailUpdateSuccessfulDialogFragment;
import com.example.myapplication.presentation.dialogFragments.updatePasswordDialog.UpdatePasswordDialogFragment;
import com.example.myapplication.presentation.dialogFragments.verification.VerificationDialogFragment;
import com.example.myapplication.presentation.dialogFragments.verifyBeforeUpdateDialogFragment.VerifyBeforeUpdateDialogFragment;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.delegates.serviceItem.ServiceItemDelegateItem;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.delegates.serviceItem.ServiceItemModel;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.delegates.serviceRedItem.ServiceRedItemDelegateItem;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.delegates.serviceRedItem.ServiceRedItemModel;

import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import myapplication.android.ui.listeners.DialogDismissedListener;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class PrivacySettingsViewModel extends ViewModel {

    private final MutableLiveData<List<DelegateItem>> _items = new MutableLiveData<>();
    LiveData<List<DelegateItem>> item = _items;

    private final MutableLiveData<Boolean> _openSuccessDialog = new MutableLiveData<>();
    LiveData<Boolean> openSuccessDialog = _openSuccessDialog;

    private final MutableLiveData<Boolean> _openVerifyDialog = new MutableLiveData<>();
    LiveData<Boolean> openVerifyDialog = _openVerifyDialog;

    private final MutableLiveData<Boolean> _openChangeEmailDialog = new MutableLiveData<>();
    LiveData<Boolean> openChangeEmailDialog = _openChangeEmailDialog;

    private final MutableLiveData<Boolean> _openUpdatePasswordDialog = new MutableLiveData<>();
    LiveData<Boolean> openUpdatePasswordDialog = _openUpdatePasswordDialog;

    private final MutableLiveData<Boolean> _openDeleteDialog = new MutableLiveData<>();
    LiveData<Boolean> openDeleteDialog = _openDeleteDialog;

    public void initRecycler(){
        buildList(new DelegateItem[]{
                new ServiceItemDelegateItem(new ServiceItemModel(1, R.drawable.ic_mail, R.string.change_email, () -> {
                    _openChangeEmailDialog.postValue(true);
                })),
                new ServiceItemDelegateItem(new ServiceItemModel(2, R.drawable.ic_lock_reset, R.string.update_password, () -> {
                    _openUpdatePasswordDialog.postValue(true);

                })),
                new ServiceRedItemDelegateItem(new ServiceRedItemModel(3, R.drawable.ic_delete, R.string.delete_account, () -> {
                    _openDeleteDialog.postValue(true);
                }))
        });
    }

    public void verifyBeforeUpdate(String email){
        DI.verifyBeforeUpdateEmailUseCase.invoke(email)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                     _openVerifyDialog.postValue(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void updateEmailField(String email){
        DI.updateEmailFieldUseCase.invoke(email)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _openSuccessDialog.postValue(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

    }

    private void buildList(DelegateItem[] items){
        List<DelegateItem> list = Arrays.asList(items);
        _items.postValue(list);
    }

}