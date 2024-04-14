package com.example.myapplication.presentation.employee.main.navFragment;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.DI;
import com.example.myapplication.di.ProfileDI;
import com.example.myapplication.domain.model.profile.UserEmployeeModel;
import com.example.myapplication.presentation.employee.employeeUserModel.EmployeeRoleModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class EmployeeNavigationViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _isEmployee = new MutableLiveData<>(null);
    LiveData<Boolean> isEmployee = _isEmployee;

    private final MutableLiveData<List<EmployeeRoleModel>> _roles = new MutableLiveData<>();
    LiveData<List<EmployeeRoleModel>> roles = _roles;

    public void isEmployee(){
        ProfileDI.isEmployeeUseCase.isEmployee()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Boolean aBoolean) {
                        _isEmployee.postValue(aBoolean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void getRoles(){
        ProfileDI.getEmployeeRolesUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<UserEmployeeModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<UserEmployeeModel> userEmployeeModels) {
                        List<EmployeeRoleModel> models = new ArrayList<>();
                        for (int i = 0; i < userEmployeeModels.size(); i++) {
                            UserEmployeeModel current = userEmployeeModels.get(i);
                            models.add(new EmployeeRoleModel(
                                    current.getRole(),
                                    current.getCompanyId()
                            ));
                        }
                        Log.d("Roles size", String.valueOf(models.size()));
                        _roles.postValue(models);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

}