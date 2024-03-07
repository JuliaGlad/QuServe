package com.example.myapplication.presentation.profile.loggedProfile.basicUser.userSettings.privacySettings;

import static com.example.myapplication.presentation.utils.Utils.EMAIL;
import static com.example.myapplication.presentation.utils.Utils.PASSWORD;

import android.content.DialogInterface;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.DI;
import com.example.myapplication.R;
import com.example.myapplication.presentation.dialogFragments.changeEmail.ChangeEmailDialogFragment;
import com.example.myapplication.presentation.dialogFragments.deleteAccount.DeleteAccountDialogFragment;
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

    public void initRecycler(Fragment fragment){
        buildList(new DelegateItem[]{
                new ServiceItemDelegateItem(new ServiceItemModel(1, R.drawable.ic_mail, R.string.change_email, () -> {

                    ChangeEmailDialogFragment dialogFragment = new ChangeEmailDialogFragment();
                    dialogFragment.show(fragment.getActivity().getSupportFragmentManager(), "CHANGE_EMAIL_DIALOG");
                    DialogDismissedListener listener = bundle -> {

                        verifyBeforeUpdate(bundle.getString(EMAIL), bundle.getString(PASSWORD), fragment);

                    };
                    dialogFragment.onDismissListener(listener);

                })),
                new ServiceItemDelegateItem(new ServiceItemModel(2, R.drawable.ic_lock_reset, R.string.update_password, () -> {

                    UpdatePasswordDialogFragment dialogFragment = new UpdatePasswordDialogFragment();
                    dialogFragment.show(fragment.getActivity().getSupportFragmentManager(), "UPDATE_PASSWORD_DIALOG");
                    DialogDismissedListener listener = bundle -> {
                        EmailUpdateSuccessfulDialogFragment successDialog = new EmailUpdateSuccessfulDialogFragment();
                        successDialog.show(fragment.getActivity().getSupportFragmentManager(), "SUCCESS_DIALOG");
                    };
                    dialogFragment.onDismissListener(listener);

                })),
                new ServiceRedItemDelegateItem(new ServiceRedItemModel(3, R.drawable.ic_delete, R.string.delete_account, () -> {

                    DeleteAccountDialogFragment dialogFragment = new DeleteAccountDialogFragment();
                    dialogFragment.show(fragment.getActivity().getSupportFragmentManager(), "DELETE_ACCOUNT_DIALOG");
                    DialogDismissedListener listener = bundle -> {
                        fragment.requireActivity().finish();
                    };
                    dialogFragment.onDismissListener(listener);
                }))
        });
    }


    public void verifyBeforeUpdate(String email, String password, Fragment fragment){
        DI.verifyBeforeUpdateEmailUseCase.invoke(email)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        VerifyBeforeUpdateDialogFragment dialogFragment = new VerifyBeforeUpdateDialogFragment(email, password);
                        dialogFragment.show(fragment.getActivity().getSupportFragmentManager(), "VERIFY_BEFORE_UPDATE_DIALOG");
                        DialogDismissedListener dismissedListener = object -> {
                            DI.updateEmailFieldUseCase.invoke(email)
                                    .subscribeOn(Schedulers.io())
                                    .subscribe(new CompletableObserver() {
                                        @Override
                                        public void onSubscribe(@NonNull Disposable d) {

                                        }

                                        @Override
                                        public void onComplete() {
                                            EmailUpdateSuccessfulDialogFragment successfulDialogFragment = new EmailUpdateSuccessfulDialogFragment();
                                            successfulDialogFragment.show(fragment.getActivity().getSupportFragmentManager(), "SUCCESS_DIALOG");
                                        }

                                        @Override
                                        public void onError(@NonNull Throwable e) {

                                        }
                                    });

                        };
                        dialogFragment.onDismissListener(dismissedListener);
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