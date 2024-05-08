package com.example.myapplication.presentation.employee.main.differentRolesFragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.company.CompanyQueueUserDI;
import com.example.myapplication.domain.model.company.EmployeeRoleCompanyModel;
import com.example.myapplication.presentation.employee.employeeUserModel.EmployeeRoleModel;
import com.example.myapplication.presentation.employee.main.differentRolesFragment.model.DifferentRoleModel;
import com.example.myapplication.presentation.employee.main.differentRolesFragment.state.DifferentRoleState;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DifferentRolesEmployeeViewModel extends ViewModel {

    private final MutableLiveData<DifferentRoleState> _state = new MutableLiveData<>();
    LiveData<DifferentRoleState> state = _state;

    public void getEmployeeCompanyRoles(List<EmployeeRoleModel> models){
        List<String> ids = new ArrayList<>();
        for (int i = 0; i < models.size(); i++) {
            ids.add(models.get(i).getCompanyId());
        }
        CompanyQueueUserDI.getCompanyNameUseCaseByEmployee.invoke(ids)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<EmployeeRoleCompanyModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<EmployeeRoleCompanyModel> employeeRoleCompanyModels) {
                        List<DifferentRoleModel> differentRoleModels = new ArrayList<>();

                        for (int i = 0; i < employeeRoleCompanyModels.size(); i++) {
                            for (int j = 0; j < models.size(); j++) {
                                if (models.get(j).getCompanyId().equals(employeeRoleCompanyModels.get(i).getCompanyId())){
                                    differentRoleModels.add(new DifferentRoleModel(
                                            models.get(j).getRole(),
                                            employeeRoleCompanyModels.get(i).getCompanyId(),
                                            employeeRoleCompanyModels.get(i).getCompanyName()
                                    ));
                                }
                            }
                        }
                        _state.postValue(new DifferentRoleState.Success(differentRoleModels));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}