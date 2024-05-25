package com.example.myapplication.presentation.employee.main.navFragment;

import static com.example.myapplication.presentation.utils.constants.Restaurant.IS_WORKING;

import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.profile.ProfileDI;
import com.example.myapplication.di.profile.ProfileEmployeeDI;
import com.example.myapplication.di.restaurant.RestaurantEmployeeDI;
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

    private final MutableLiveData<Boolean> _getCompanyEmployeeRoles = new MutableLiveData<>(false);
    LiveData<Boolean> getCompanyEmployeeRoles = _getCompanyEmployeeRoles;

    private final MutableLiveData<List<EmployeeRoleModel>> _roles = new MutableLiveData<>(null);
    LiveData<List<EmployeeRoleModel>> roles = _roles;

        List<EmployeeRoleModel> models = new ArrayList<>();

    private final MutableLiveData<Bundle> _isWorking = new MutableLiveData<>(null);
    LiveData<Bundle> isWorking = _isWorking;

    private final MutableLiveData<Boolean> _noWork = new MutableLiveData<>(false);
    LiveData<Boolean> noWork = _noWork;

    public void checkIsWorking(String restaurantId, String locationId, Bundle bundle) {
        RestaurantEmployeeDI.checkIsWorkingUseCase.invoke(restaurantId, locationId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Boolean aBoolean) {
                        bundle.putBoolean(IS_WORKING, aBoolean);
                        _isWorking.postValue(bundle);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void isEmployee() {
        ProfileEmployeeDI.isEmployeeUseCase.isEmployee()
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

    public void getRestaurantRoles() {
        ProfileEmployeeDI.getRestaurantEmployeeRolesUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<UserEmployeeModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<UserEmployeeModel> userEmployeeModels) {
                        if (!userEmployeeModels.isEmpty()) {
                            for (UserEmployeeModel user : userEmployeeModels) {
                                models.add(new EmployeeRoleModel(
                                        user.getRole(),
                                        user.getCompanyId(),
                                        user.getLocationId()
                                ));
                            }
                            Log.d("Roles restaurant if more", "got");
                            _roles.postValue(models);
                        } else {
                            Log.d("Roles restaurant if less", "got");
                            _roles.postValue(models);
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void getCompanyRoles() {
        ProfileEmployeeDI.getEmployeeRolesUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<UserEmployeeModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<UserEmployeeModel> userEmployeeModels) {
                        if (!userEmployeeModels.isEmpty()) {
                            for (int i = 0; i < userEmployeeModels.size(); i++) {
                                UserEmployeeModel current = userEmployeeModels.get(i);
                                models.add(new EmployeeRoleModel(
                                        current.getRole(),
                                        current.getCompanyId(),
                                        null
                                ));
                            }
                            Log.d("Roles company if more", "got");
                            _getCompanyEmployeeRoles.postValue(true);
                        } else {
                            Log.d("Roles company if less", "got");
                            _getCompanyEmployeeRoles.postValue(true);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void setArgumentsNull() {
        _isEmployee.postValue(null);
        _roles.postValue(null);
        _getCompanyEmployeeRoles.postValue(false);
        models.clear();
    }
}