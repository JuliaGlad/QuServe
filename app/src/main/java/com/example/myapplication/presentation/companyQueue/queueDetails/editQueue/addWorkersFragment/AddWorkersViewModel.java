package com.example.myapplication.presentation.companyQueue.queueDetails.editQueue.addWorkersFragment;

import static com.example.myapplication.presentation.utils.Utils.ADMIN;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.company.CompanyQueueDI;
import com.example.myapplication.di.company.CompanyQueueUserDI;
import com.example.myapplication.domain.model.company.EmployeeMainModel;
import com.example.myapplication.presentation.companyQueue.queueDetails.editQueue.addWorkersFragment.model.AddWorkerModel;
import com.example.myapplication.presentation.companyQueue.queueDetails.editQueue.addWorkersFragment.state.AddWorkersState;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddWorkersViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _isAdded = new MutableLiveData<>(false);
    LiveData<Boolean> isAdded = _isAdded;

    private final MutableLiveData<AddWorkersState> _state = new MutableLiveData<>(new AddWorkersState.Loading());
    LiveData<AddWorkersState> state = _state;

    public void getEmployees(String companyId) {
        CompanyQueueUserDI.getEmployeesUseCase.invoke(companyId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<EmployeeMainModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<EmployeeMainModel> employeeMainModels) {

                        List<AddWorkerModel> models = new ArrayList<>();

                        for (int i = 0; i < employeeMainModels.size(); i++) {
                            EmployeeMainModel current = employeeMainModels.get(i);
                            if (!current.getRole().equals(ADMIN)) {
                                models.add(new AddWorkerModel(
                                        current.getName(),
                                        current.getId(),
                                        current.getRole()
                                ));
                            }
                        }

                        _state.postValue(new AddWorkersState.Success(models));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _state.postValue(new AddWorkersState.Error());
                    }
                });
    }

    public void addEmployees(List<AddWorkerModel> chosen, String companyId, String queueId) {
        CompanyQueueDI.getQueueNameAndLocationById.invoke(companyId, queueId)
                .flatMapCompletable(model ->  CompanyQueueDI.addListEmployeesToQueue.invoke(chosen, companyId, queueId, model.getName(), model.getLocation()))
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _isAdded.postValue(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}