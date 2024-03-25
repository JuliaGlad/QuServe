package com.example.myapplication.presentation.companyQueue.queueDetails.editQueue;

import static com.example.myapplication.presentation.utils.Utils.ADMIN;
import static com.example.myapplication.presentation.utils.Utils.WORKER;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.company.CompanyQueueNameAndLocationModel;
import com.example.myapplication.domain.model.company.CompanyQueueNameModel;
import com.example.myapplication.domain.model.company.EmployeeMainModel;
import com.example.myapplication.domain.model.company.WorkerModel;
import com.example.myapplication.presentation.companyQueue.queueDetails.editQueue.models.EmployeeModel;
import com.example.myapplication.presentation.companyQueue.queueDetails.recycler.QueueEmployeeModel;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.employees.recyclerViewItem.EmployeeItemModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class EditQueueViewModel extends ViewModel {

    private List<QueueEmployeeModel> models = new ArrayList<>();

    private final MutableLiveData<Boolean> _saved = new MutableLiveData<>(false);
    LiveData<Boolean> saved = _saved;

    private final MutableLiveData<String> _name = new MutableLiveData<>();
    LiveData<String> name = _name;

    private final MutableLiveData<String> _location = new MutableLiveData<>();
    LiveData<String> location = _location;

    private final MutableLiveData<List<QueueEmployeeModel>> _items = new MutableLiveData<>();
    LiveData<List<QueueEmployeeModel>> items = _items;

    public void getQueueData(String companyId, String queueId) {
        DI.getQueueNameAndLocationById.invoke(companyId, queueId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<CompanyQueueNameAndLocationModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull CompanyQueueNameAndLocationModel companyQueueNameAndLocationModel) {
                        _name.postValue(companyQueueNameAndLocationModel.getName());
                        _location.postValue(companyQueueNameAndLocationModel.getLocation());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void getWorkers(String companyId, String queueId) {
        DI.getQueueWorkersListUseCase.invoke(companyId, queueId)
                .zipWith(DI.getAdminsUseCase.invoke(companyId), new BiFunction<List<WorkerModel>, List<EmployeeMainModel>, List<EmployeeModel>>() {
                    @Override
                    public List<EmployeeModel> apply(List<WorkerModel> workerModels, List<EmployeeMainModel> adminModels) throws Throwable {
                        List<EmployeeModel> list = new ArrayList<>();
                        for (int i = 0; i < workerModels.size(); i++) {
                            list.add(new EmployeeModel(workerModels.get(i).getId(), workerModels.get(i).getName(), WORKER));
                        }

                        for (int i = 0; i < adminModels.size(); i++) {
                            list.add(new EmployeeModel(adminModels.get(i).getId(), adminModels.get(i).getName(), ADMIN));
                        }
                        return list;
                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<EmployeeModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<EmployeeModel> employeeModels) {
                        for (int i = 0; i < employeeModels.size(); i++) {
                            models.add(new QueueEmployeeModel(
                                    i,
                                    employeeModels.get(i).getId(),
                                    employeeModels.get(i).getName(),
                                    employeeModels.get(i).getRole(),
                                    Uri.EMPTY)
                            );
                        }
                        _items.postValue(models);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }


    public void saveData(String companyId, String queueId, String name, String location) {
        DI.updateQueueDataUseCase.invoke(companyId, queueId, name, location)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        _saved.postValue(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}