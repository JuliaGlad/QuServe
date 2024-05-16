package com.example.myapplication.presentation.companyQueue.queueDetails.editQueue;

import static com.example.myapplication.presentation.utils.Utils.ADMIN;
import static com.example.myapplication.presentation.utils.Utils.WORKER;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.company.CompanyQueueDI;
import com.example.myapplication.domain.model.company.EmployeeMainModel;
import com.example.myapplication.domain.model.company.WorkerModel;
import com.example.myapplication.presentation.companyQueue.queueDetails.editQueue.models.EditQueueModel;
import com.example.myapplication.presentation.companyQueue.queueDetails.editQueue.models.EmployeeModel;
import com.example.myapplication.presentation.companyQueue.queueDetails.editQueue.state.EditQueueState;
import com.example.myapplication.presentation.companyQueue.queueDetails.recycler.QueueEmployeeModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class EditQueueViewModel extends ViewModel {

    private List<QueueEmployeeModel> models = new ArrayList<>();

    private final MutableLiveData<EditQueueState> _state = new MutableLiveData<>(new EditQueueState.Loading());
    LiveData<EditQueueState> state = _state;

    private final MutableLiveData<Boolean> _saved = new MutableLiveData<>(false);
    LiveData<Boolean> saved = _saved;

    private final MutableLiveData<List<QueueEmployeeModel>> _items = new MutableLiveData<>();
    LiveData<List<QueueEmployeeModel>> items = _items;

    public void getQueueData(String companyId, String queueId) {
        Single.zip(CompanyQueueDI.getQueueNameAndLocationById.invoke(companyId, queueId),
                        CompanyQueueDI.getQueueWorkersListUseCase.invoke(companyId, queueId), CompanyQueueDI.getAdminsUseCase.invoke(companyId),
                        (companyQueueNameAndLocationModel, workerModels, adminModels) -> {
                            List<EmployeeModel> list = new ArrayList<>();
                            for (int i = 0; i < workerModels.size(); i++) {
                                WorkerModel current = workerModels.get(i);
                                list.add(new EmployeeModel(current.getId(), current.getName(), WORKER));
                            }

                            for (int i = 0; i < adminModels.size(); i++) {
                                EmployeeMainModel current = adminModels.get(i);
                                list.add(new EmployeeModel(current.getId(), current.getName(), ADMIN));
                            }
                            return new EditQueueModel(
                                    companyQueueNameAndLocationModel.getName(),
                                    companyQueueNameAndLocationModel.getLocation(),
                                    list
                            );
                        })
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<EditQueueModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull EditQueueModel editQueueModel) {
                        _state.postValue(new EditQueueState.Success(editQueueModel));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _state.postValue(new EditQueueState.Error());
                    }
                });
    }

    public void saveData(String companyId, String queueId, String name, String location) {
        CompanyQueueDI.updateQueueDataUseCase.invoke(companyId, queueId, name, location)
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