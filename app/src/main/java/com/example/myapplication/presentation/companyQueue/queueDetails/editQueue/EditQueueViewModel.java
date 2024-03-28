package com.example.myapplication.presentation.companyQueue.queueDetails.editQueue;

import static com.example.myapplication.presentation.utils.Utils.ADMIN;
import static com.example.myapplication.presentation.utils.Utils.WORKER;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.company.EmployeeMainModel;
import com.example.myapplication.domain.model.company.WorkerModel;
import com.example.myapplication.presentation.companyQueue.queueDetails.editQueue.models.EditQueueModel;
import com.example.myapplication.presentation.companyQueue.queueDetails.editQueue.models.EmployeeModel;
import com.example.myapplication.presentation.companyQueue.queueDetails.editQueue.state.EditQueueState;
import com.example.myapplication.presentation.companyQueue.queueDetails.recycler.RecyclerEmployeeModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class EditQueueViewModel extends ViewModel {

    private List<RecyclerEmployeeModel> models = new ArrayList<>();

    private final MutableLiveData<EditQueueState> _state = new MutableLiveData<>(new EditQueueState.Loading());
    LiveData<EditQueueState> state = _state;

    private final MutableLiveData<Boolean> _saved = new MutableLiveData<>(false);
    LiveData<Boolean> saved = _saved;

    private final MutableLiveData<String> _name = new MutableLiveData<>();
    LiveData<String> name = _name;

    private final MutableLiveData<String> _location = new MutableLiveData<>();
    LiveData<String> location = _location;

    private final MutableLiveData<List<RecyclerEmployeeModel>> _items = new MutableLiveData<>();
    LiveData<List<RecyclerEmployeeModel>> items = _items;

    public void getQueueData(String companyId, String queueId) {
        Single.zip(DI.getQueueNameAndLocationById.invoke(companyId, queueId), DI.getQueueWorkersListUseCase.invoke(companyId, queueId), DI.getAdminsUseCase.invoke(companyId),
                        (companyQueueNameAndLocationModel, workerModels, adminModels) -> {
                            List<EmployeeModel> list = new ArrayList<>();
                                    for (int i = 0; i < workerModels.size(); i++) {
                                        WorkerModel current = workerModels.get(i);
                                        list.add(new EmployeeModel(current.getId(),current.getName(), WORKER));
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