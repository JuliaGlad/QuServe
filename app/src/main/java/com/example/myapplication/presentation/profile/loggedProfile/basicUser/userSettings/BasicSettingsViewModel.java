package com.example.myapplication.presentation.profile.loggedProfile.basicUser.userSettings;

import android.net.Uri;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.DI;
import com.example.myapplication.R;
import com.example.myapplication.domain.model.common.ImageModel;
import com.example.myapplication.domain.model.profile.UserEmailAndNameModel;
import com.example.myapplication.presentation.dialogFragments.aboutUs.AboutUsDialogFragment;
import com.example.myapplication.presentation.dialogFragments.help.HelpDialogFragment;
import com.example.myapplication.presentation.dialogFragments.logout.LogoutDialogFragment;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.delegates.serviceItem.ServiceItemDelegateItem;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.delegates.serviceItem.ServiceItemModel;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.delegates.serviceRedItem.ServiceRedItemDelegateItem;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.delegates.serviceRedItem.ServiceRedItemModel;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.delegates.userItemDelegate.SettingsUserItemDelegateItem;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.delegates.userItemDelegate.SettingsUserItemModel;

import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import myapplication.android.ui.listeners.DialogDismissedListener;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class BasicSettingsViewModel extends ViewModel {

    private MutableLiveData<List<DelegateItem>> _items = new MutableLiveData<>();
    LiveData<List<DelegateItem>> items = _items;

    private void initRecycler(String name, String email, Uri uri, Fragment fragment){
        buildList(new DelegateItem[]{
                new SettingsUserItemDelegateItem(new SettingsUserItemModel(1, name, email, uri)),
                new ServiceItemDelegateItem(new ServiceItemModel(2, R.drawable.ic_shield_person, R.string.privacy_and_security, () -> {
                    NavHostFragment.findNavController(fragment)
                            .navigate(R.id.action_basicSettingsFragment_to_privacySettingsFragment);
                })),
                new ServiceItemDelegateItem(new ServiceItemModel(3, R.drawable.ic_help, R.string.help, () -> {
                    HelpDialogFragment dialogFragment = new HelpDialogFragment();
                    dialogFragment.show(fragment.getActivity().getSupportFragmentManager(), "HELP_DIALOG");
                })),
                new ServiceItemDelegateItem(new ServiceItemModel(4, R.drawable.ic_group, R.string.about_us, () -> {
                    AboutUsDialogFragment dialogFragment = new AboutUsDialogFragment();
                    dialogFragment.show(fragment.getActivity().getSupportFragmentManager(), "ABOUT_US_DIALOG");
                })),
                new ServiceRedItemDelegateItem(new ServiceRedItemModel(5, R.drawable.ic_logout, R.string.logout, () -> {

                    LogoutDialogFragment dialogFragment = new LogoutDialogFragment();
                    dialogFragment.show(fragment.getActivity().getSupportFragmentManager(), "LOGOUT_DIALOG");
                    DialogDismissedListener listener = bundle -> {
                        fragment.getActivity().finish();
                    };
                    dialogFragment.onDismissListener(listener);

                }))

        });
    }

    public void retrieveUserData(Fragment fragment) {
        DI.getUserEmailAndNameDataUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<UserEmailAndNameModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull UserEmailAndNameModel userEmailAndNameModel) {
                        DI.getProfileImageUseCase.invoke()
                                .subscribeOn(Schedulers.io())
                                .subscribe(new SingleObserver<ImageModel>() {
                                    @Override
                                    public void onSubscribe(@NonNull Disposable d) {

                                    }

                                    @Override
                                    public void onSuccess(@NonNull ImageModel imageModel) {
                                        imageModel.getImageUri().addOnCompleteListener(task -> {
                                            initRecycler(userEmailAndNameModel.getName(), userEmailAndNameModel.getEmail(), task.getResult(), fragment );
                                        });
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {
                                        Log.e("Exception", e.getMessage());
                                    }
                                });
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("Exception", e.getMessage());
                    }
                });
    }

    private void buildList(DelegateItem[] items){
        List<DelegateItem> list = Arrays.asList(items);
        _items.postValue(list);
    }
}
