package com.example.myapplication.presentation.profile.loggedProfile.basicUser.userSettings.basicSettings;

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
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.userSettings.model.SettingsUserModel;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.serviceItem.ServiceItemDelegateItem;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.serviceItem.ServiceItemModel;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.serviceRedItem.ServiceRedItemDelegateItem;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.serviceRedItem.ServiceRedItemModel;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.userItemDelegate.SettingsUserItemDelegateItem;
import com.example.myapplication.presentation.profile.loggedProfile.delegates.userItemDelegate.SettingsUserItemModel;

import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class BasicSettingsViewModel extends ViewModel {

    private Uri uri;

    private final MutableLiveData<List<DelegateItem>> _items = new MutableLiveData<>();
    LiveData<List<DelegateItem>> items = _items;

    private final MutableLiveData<Boolean> _openHelpDialog = new MutableLiveData<>();
    LiveData<Boolean> openHelpDialog = _openHelpDialog;

    private final MutableLiveData<Boolean> _openAboutUsDialog = new MutableLiveData<>(false);
    LiveData<Boolean> openAboutUsDialog = _openAboutUsDialog;

    private final MutableLiveData<Boolean> _logoutDialog = new MutableLiveData<>(false);
    LiveData<Boolean> logoutDialog = _logoutDialog;

    private final MutableLiveData<Boolean> _navigateToPrivacy = new MutableLiveData<>(false);
    LiveData<Boolean> navigateToPrivacy = _navigateToPrivacy;

    private void initRecycler(String name, String email, Uri uri, Fragment fragment){
        buildList(new DelegateItem[]{
                new SettingsUserItemDelegateItem(new SettingsUserItemModel(1, name, email, uri)),
                new ServiceItemDelegateItem(new ServiceItemModel(2, R.drawable.ic_shield_person, R.string.privacy_and_security, () -> {
                    NavHostFragment.findNavController(fragment)
                            .navigate(R.id.action_basicSettingsFragment_to_privacySettingsFragment);
                })),
                new ServiceItemDelegateItem(new ServiceItemModel(3, R.drawable.ic_help, R.string.help, () -> {
                    _openHelpDialog.postValue(true);
                })),
                new ServiceItemDelegateItem(new ServiceItemModel(4, R.drawable.ic_group, R.string.about_us, () -> {
                    _openAboutUsDialog.postValue(true);
                })),
                new ServiceRedItemDelegateItem(new ServiceRedItemModel(5, R.drawable.ic_logout, R.string.logout, () -> {
                    _logoutDialog.postValue(true);
                }))

        });
    }

    public void retrieveUserData(Fragment fragment) {
        DI.getProfileImageUseCase.invoke()
                .zipWith(DI.getUserEmailAndNameDataUseCase.invoke(), (imageModel, userEmailAndNameModel) ->
                        new SettingsUserModel(userEmailAndNameModel.getName(), userEmailAndNameModel.getEmail(), imageModel.getImageUri()))
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<SettingsUserModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull SettingsUserModel userModel) {
                        initRecycler(userModel.getName(), userModel.getEmail(), userModel.getUri(), fragment );
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
