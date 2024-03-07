package com.example.myapplication.presentation.profile.loggedProfile.basicUser;

import static com.example.myapplication.presentation.utils.Utils.PAGE_1;

import android.net.Uri;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.DI;
import com.example.myapplication.R;
import com.example.myapplication.domain.model.common.ImageModel;
import com.example.myapplication.domain.model.profile.UserEmailAndNameModel;
import com.example.myapplication.presentation.MainActivity;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.delegates.mainItem.MainItemDelegateItem;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.delegates.mainItem.MainItemModel;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.delegates.serviceItem.ServiceItemDelegateItem;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.delegates.serviceItem.ServiceItemModel;
import com.example.myapplication.presentation.profile.loggedProfile.main.ProfileLoggedFragmentDirections;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import myapplication.android.ui.listeners.ButtonItemListener;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class BasicUserViewModel extends ViewModel {

    private boolean companyExist = false;

    private final MutableLiveData<List<DelegateItem>> _items = new MutableLiveData<>();
    LiveData<List<DelegateItem>> item = _items;

    public void initRecycler(Uri uri, String name, String email, Fragment fragment) {
        buildList(new DelegateItem[]{
                new MainItemDelegateItem(new MainItemModel(1, uri, name, email)),
                new ServiceItemDelegateItem(new ServiceItemModel(2, R.drawable.ic_edit, R.string.edit_profile, () -> {
                    ((MainActivity)fragment.requireActivity()).openEditActivity();
                })),
                new ServiceItemDelegateItem(new ServiceItemModel(3, R.drawable.ic_history, R.string.history, () -> {
                    ((MainActivity)fragment.requireActivity()).openHistoryActivity();
                })),
                addCompanyService(fragment)
        });
    }

    public void checkCompanyExist() {
        DI.checkCompanyExistUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Boolean aBoolean) {
                        companyExist = aBoolean;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    private ServiceItemDelegateItem addCompanyService(Fragment fragment) {
        if (companyExist) {
            return new ServiceItemDelegateItem(new ServiceItemModel(4, R.drawable.ic_buisness_center_24, R.string.go_to_company, new ButtonItemListener() {
                @Override
                public void onClick() {
                    NavHostFragment.findNavController(fragment)
                            .navigate(R.id.action_profileLoggedFragment_to_chooseCompanyFragment);
                }
            }));
        } else {
            return new ServiceItemDelegateItem(new ServiceItemModel(4, R.drawable.ic_add_business_24, R.string.add_company, new ButtonItemListener() {
                @Override
                public void onClick() {
                    NavHostFragment.findNavController(fragment)
                            .navigate(ProfileLoggedFragmentDirections.actionProfileLoggedFragmentToCreateCompanyAccountFragment(PAGE_1));
                }
            }));
        }
    }

    public void retrieveUserNameData(Fragment fragment) {
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
                                        imageModel.getImageUri()
                                                .addOnCompleteListener(task -> {
                                                    initRecycler(task.getResult(), userEmailAndNameModel.getName(), userEmailAndNameModel.getEmail(), fragment);
                                                });

                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {
                                    }
                                });
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    private void buildList(DelegateItem[] item) {
        List<DelegateItem> list = Arrays.asList(item);
        _items.postValue(list);
    }
}