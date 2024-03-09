package com.example.myapplication.presentation.profile.employees.fragment;

import static com.example.myapplication.presentation.utils.Utils.WORKER;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.DI;
import com.example.myapplication.R;
import com.example.myapplication.domain.model.common.ImageModel;
import com.example.myapplication.domain.model.company.EmployeeMainModel;
import com.example.myapplication.presentation.profile.employees.delegateRecycler.RecyclerDelegateItem;
import com.example.myapplication.presentation.profile.employees.delegateRecycler.RecyclerModel;
import com.example.myapplication.presentation.profile.employees.recyclerViewItem.EmployeeItemAdapter;
import com.example.myapplication.presentation.profile.employees.recyclerViewItem.EmployeeItemModel;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import myapplication.android.ui.listeners.TabSelectedListener;
import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.ui.items.items.floatingActionButton.FloatingActionButtonDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.floatingActionButton.FloatingActionButtonModel;
import myapplication.android.ui.recycler.ui.items.items.searchView.SearchViewDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.searchView.SearchViewModel;
import myapplication.android.ui.recycler.ui.items.items.tabLayout.TabLayoutDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.tabLayout.TabLayoutModel;

public class EmployeesViewModel extends ViewModel {

    private final EmployeeItemAdapter adapter = new EmployeeItemAdapter();

    private final MutableLiveData<List<DelegateItem>> _items = new MutableLiveData<>();
    LiveData<List<DelegateItem>> items = _items;

    private final MutableLiveData<Task<Uri>> _image = new MutableLiveData<>();
    LiveData<Task<Uri>> image = _image;

    private final List<DelegateItem> itemsList = new ArrayList<>();

    private final List<EmployeeItemModel> basicList = new ArrayList<>();
    private final List<EmployeeItemModel> workersList = new ArrayList<>();
    private final List<EmployeeItemModel> adminsList = new ArrayList<>();

    private void initRecycler(String companyId) {
        setItems(companyId);
        _items.postValue(itemsList);

    }

    private void setItems(String companyId) {

        itemsList.add(new SearchViewDelegateItem(new SearchViewModel(1)));
        itemsList.add(new TabLayoutDelegateItem(new TabLayoutModel(2, R.string.all, R.string.admins, R.string.workers, new TabSelectedListener() {
            @Override
            public void onFirstTabSelected() {
                setList(basicList);
            }

            @Override
            public void onSecondTabSelected() {
                setList(adminsList);
            }

            @Override
            public void onThirdTabSelected() {
                setList(workersList);
            }
        })));

        itemsList.add(new FloatingActionButtonDelegateItem(new FloatingActionButtonModel(3, () -> {
            getQrCode(companyId);
        })));

        itemsList.add(new RecyclerDelegateItem(new RecyclerModel(4, basicList, adapter)));

    }

    public void getEmployees(String companyId) {
        DI.getEmployeesUseCase.invoke(companyId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<EmployeeMainModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<EmployeeMainModel> employeeMainModels) {

                        for (int i = 0; i < employeeMainModels.size(); i++) {
                            basicList.add(new EmployeeItemModel(
                                    i,
                                    employeeMainModels.get(i).getName(),
                                    employeeMainModels.get(i).getId(),
                                    employeeMainModels.get(i).getRole(),
                                    () -> {

                                    })
                            );
                        }

                        initSubLists();
                        initRecycler(companyId);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    private void initSubLists(){
        for (int i = 0; i < basicList.size(); i++) {
            if (basicList.get(i).getRole().equals(WORKER)){
                workersList.add(basicList.get(i));
            } else {
                adminsList.add(basicList.get(i));
            }
        }
    }

    private void getQrCode(String companyId) {
        DI.getEmployeeQrCodeUseCase.invoke(companyId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<ImageModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull ImageModel imageModel) {
//                        _image.postValue(imageModel.getImageUri());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

    }

    private void setList(List<EmployeeItemModel> list) {
        List<DelegateItem> newItems = new ArrayList<>(_items.getValue());
        newItems.remove(newItems.size() - 1);
        newItems.add(new RecyclerDelegateItem(new RecyclerModel(5, list, adapter)));
        _items.postValue(newItems);
    }
}