package com.example.myapplication.presentation.profile.loggedProfile.companyUser.employees.fragment;

import static com.example.myapplication.presentation.utils.Utils.ADMIN;
import static com.example.myapplication.presentation.utils.Utils.EMPLOYEE_ROLE;
import static com.example.myapplication.presentation.utils.Utils.WORKER;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.common.ImageModel;
import com.example.myapplication.domain.model.common.ImageTaskModel;
import com.example.myapplication.domain.model.company.EmployeeMainModel;
import com.example.myapplication.presentation.dialogFragments.changeRole.ChangeRoleDialogFragment;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.employees.recyclerViewItem.EmployeeItemAdapter;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.employees.recyclerViewItem.EmployeeItemModel;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import myapplication.android.ui.listeners.ButtonItemListener;
import myapplication.android.ui.listeners.DialogDismissedListener;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class EmployeesViewModel extends ViewModel {

    private final MutableLiveData<Task<Uri>> _image = new MutableLiveData<>();
    LiveData<Task<Uri>> image = _image;

    private final MutableLiveData<List<EmployeeItemModel>> _basicList = new MutableLiveData<>();
    LiveData<List<EmployeeItemModel>> basicList = _basicList;

    private final MutableLiveData<List<EmployeeItemModel>> _workerList = new MutableLiveData<>();
    LiveData<List<EmployeeItemModel>> workerList = _workerList;

    private final MutableLiveData<List<EmployeeItemModel>> _adminList = new MutableLiveData<>();
    LiveData<List<EmployeeItemModel>> adminList = _adminList;

    private final MutableLiveData<Boolean> _onComplete = new MutableLiveData<>(false);
    LiveData<Boolean> onComplete = _onComplete;

    private final MutableLiveData<Bundle> _showDialog = new MutableLiveData<>(null);
    LiveData<Bundle> showDialog = _showDialog;

    private final MutableLiveData<String> _addToAdmins = new MutableLiveData<>(null);
    LiveData<String> addToAdmins = _addToAdmins;

    private final MutableLiveData<String> _addToWorkers = new MutableLiveData<>(null);
    LiveData<String> addToWorkers = _addToWorkers;

    private final List<EmployeeItemModel> basic = new ArrayList<>();
    private final List<EmployeeItemModel> workers = new ArrayList<>();
    private final List<EmployeeItemModel> admins = new ArrayList<>();

    public void getEmployees(String companyId, Fragment fragment) {
        DI.getEmployeesUseCase.invoke(companyId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<EmployeeMainModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<EmployeeMainModel> employeeMainModels) {
                        final String[] role = new String[1];
                        for (int i = 0; i < employeeMainModels.size(); i++) {
                            int index = i;
                            String id = employeeMainModels.get(index).getId();
                            String name = employeeMainModels.get(index).getName();
                            role[0] = employeeMainModels.get(index).getRole();
                            basic.add(new EmployeeItemModel(i, fragment, name, id, role[0], companyId, () -> {
                                    ChangeRoleDialogFragment dialogFragment = new ChangeRoleDialogFragment(
                                            companyId,
                                            id,
                                            role[0]
                                    );

                                    dialogFragment.show(fragment.getActivity().getSupportFragmentManager(), "CHANGE_ROLE_DIALOG");
                                    DialogDismissedListener listener = bundleDialog -> {
                                        if (bundleDialog.getString(EMPLOYEE_ROLE).equals(ADMIN)) {
                                            _addToAdmins.postValue(id);
                                            role[0] = ADMIN;
                                        } else {
                                            _addToWorkers.postValue(id);
                                            role[0] = WORKER;
                                        }
                                    };
                                    dialogFragment.onDismissListener(listener);


                            }));
                            Log.d("Item" + i, id + " " + name + " " + role[0] + " ");
                        }
                        _basicList.postValue(basic);
                        initSubLists();

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    private void initSubLists() {
        for (int i = 0; i < basic.size(); i++) {
            if (basic.get(i).getRole().equals(WORKER)) {
                workers.add(basic.get(i));
            } else {
                admins.add(basic.get(i));
            }
        }


        _workerList.postValue(workers);
        _adminList.postValue(admins);
        _onComplete.postValue(true);
    }

}