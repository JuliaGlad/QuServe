package com.example.myapplication.presentation.home.basicUser;

import static com.example.myapplication.presentation.utils.Utils.NOT_PARTICIPATE_IN_QUEUE;
import static com.example.myapplication.presentation.utils.Utils.NOT_QUEUE_OWNER;
import static com.example.myapplication.presentation.utils.Utils.NOT_RESTAURANT_VISITOR;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.company.CommonCompanyDI;
import com.example.myapplication.di.profile.ProfileDI;
import com.example.myapplication.di.QueueDI;
import com.example.myapplication.di.restaurant.RestaurantOrderDI;
import com.example.myapplication.domain.model.common.CommonCompanyModel;
import com.example.myapplication.domain.model.queue.QueueIdAndNameModel;
import com.example.myapplication.domain.model.queue.QueueModel;
import com.example.myapplication.domain.model.restaurant.order.OrderModel;
import com.example.myapplication.presentation.home.basicUser.model.CompanyBasicUserModel;
import com.example.myapplication.presentation.home.basicUser.model.HomeBasicUserActionModel;
import com.example.myapplication.presentation.home.basicUser.model.HomeBasicUserModel;
import com.example.myapplication.presentation.home.basicUser.model.QueueBasicUserHomeModel;
import com.example.myapplication.presentation.home.basicUser.state.HomeBasicUserState;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeBasisUserViewModel extends ViewModel {

    private boolean isCompanyOwner = false;
    private String isQueueParticipant = NOT_PARTICIPATE_IN_QUEUE;
    private String isQueueOwner = NOT_QUEUE_OWNER;
    private String isRestaurantVisitor = NOT_RESTAURANT_VISITOR;

    QueueBasicUserHomeModel ownerQueue = null;
    QueueBasicUserHomeModel participantQueue = null;
    QueueBasicUserHomeModel restaurantVisitor = null;
    List<CompanyBasicUserModel> companies = new ArrayList<>();

    private final MutableLiveData<Boolean> _getRestaurant = new MutableLiveData<>(false);
    LiveData<Boolean> getRestaurant = _getRestaurant;

    private final MutableLiveData<Boolean> _getCompanies = new MutableLiveData<>(false);
    LiveData<Boolean> getCompanies = _getCompanies;

    private final MutableLiveData<Boolean> _getQueueByAuthorId = new MutableLiveData<>(false);
    LiveData<Boolean> getQueueById = _getQueueByAuthorId;

    private final MutableLiveData<HomeBasicUserState> _state = new MutableLiveData<>(new HomeBasicUserState.Loading());
    LiveData<HomeBasicUserState> state = _state;

    public void getUserBooleanData() {
        CommonCompanyDI.checkAnyCompanyExistUseCase.invoke()
                .zipWith(ProfileDI.getUserActionsDataUseCase.invoke(), (companyBoolean, userBooleanDataModel) ->
                        new HomeBasicUserActionModel(
                                userBooleanDataModel.isOwnQueue(),
                                userBooleanDataModel.isParticipateInQueue(),
                                userBooleanDataModel.getRestaurantVisitor(),
                                companyBoolean)
                )
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<HomeBasicUserActionModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull HomeBasicUserActionModel homeBasicUserActionModel) {
                        if (!homeBasicUserActionModel.isCompanyOwner() && homeBasicUserActionModel.isParticipateInQueue().equals(NOT_PARTICIPATE_IN_QUEUE) && homeBasicUserActionModel.isOwnQueue().equals(NOT_QUEUE_OWNER)) {
                            _state.postValue(new HomeBasicUserState.Success(null));
                        } else {
                            isCompanyOwner = homeBasicUserActionModel.isCompanyOwner();
                            isQueueParticipant = homeBasicUserActionModel.isParticipateInQueue();
                            isRestaurantVisitor = homeBasicUserActionModel.isRestaurantVisitor();
                            isQueueOwner = homeBasicUserActionModel.isOwnQueue();
                            getCompanies();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void getCompanies() {
        if (isCompanyOwner) {
            CommonCompanyDI.getAllServiceCompaniesUseCase.invoke()
                    .subscribeOn(Schedulers.io())
                    .subscribe(new SingleObserver<List<CommonCompanyModel>>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onSuccess(@NonNull List<CommonCompanyModel> commonCompanyModels) {
                            for (CommonCompanyModel model : commonCompanyModels) {
                                companies.add(new CompanyBasicUserModel(
                                        model.getCompanyId(),
                                        model.getCompanyName(),
                                        model.getService()
                                ));
                            }
                            _getCompanies.postValue(true);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }
                    });
        } else {
            _getCompanies.postValue(true);
        }
    }

    public void getRestaurantByVisitorPath() {
        if (!isRestaurantVisitor.equals(NOT_RESTAURANT_VISITOR)) {
            ProfileDI.getActiveRestaurantOrderUseCase.invoke()
                    .flatMap(path -> RestaurantOrderDI.getOrderModelByPathUseCase.invoke(path))
                    .subscribeOn(Schedulers.io())
                    .subscribe(new SingleObserver<OrderModel>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onSuccess(@NonNull OrderModel orderModel) {
                            restaurantVisitor = new QueueBasicUserHomeModel(
                                    isRestaurantVisitor,
                                    orderModel.getRestaurantName()
                            );
                            _getRestaurant.postValue(true);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }
                    });
        } else {
            _getRestaurant.postValue(true);
        }
    }

    public void getQueueByParticipantPath() {
        if (!isQueueParticipant.equals(NOT_PARTICIPATE_IN_QUEUE)) {
            ProfileDI.getParticipantQueuePathUseCase.invoke()
                    .flatMap(path -> QueueDI.getQueueByParticipantPathUseCase.invoke(path))
                    .subscribeOn(Schedulers.io())
                    .subscribe(new SingleObserver<QueueModel>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onSuccess(@NonNull QueueModel queueModel) {
                            participantQueue = new QueueBasicUserHomeModel(
                                    isQueueParticipant,
                                    queueModel.getName()
                            );
                            _state.postValue(new HomeBasicUserState.Success(new HomeBasicUserModel(companies, participantQueue, ownerQueue)));
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }
                    });
        } else {
            _state.postValue(new HomeBasicUserState.Success(new HomeBasicUserModel(companies, participantQueue, ownerQueue)));
        }
    }

    public void getQueueByAuthorId() {
        if (!isQueueOwner.equals(NOT_QUEUE_OWNER)) {
            QueueDI.getQueueByAuthorUseCase.invoke()
                    .subscribeOn(Schedulers.io())
                    .subscribe(new SingleObserver<QueueIdAndNameModel>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onSuccess(@NonNull QueueIdAndNameModel queueIdAndNameModel) {
                            ownerQueue = new QueueBasicUserHomeModel(
                                    queueIdAndNameModel.getId(),
                                    queueIdAndNameModel.getName()
                            );
                            _getQueueByAuthorId.postValue(true);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }
                    });
        } else {
            _getQueueByAuthorId.postValue(true);
        }
    }
}