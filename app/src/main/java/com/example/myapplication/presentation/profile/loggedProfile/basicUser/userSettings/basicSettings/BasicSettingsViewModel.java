package com.example.myapplication.presentation.profile.loggedProfile.basicUser.userSettings.basicSettings;

import static com.example.myapplication.presentation.utils.constants.Utils.NOT_PARTICIPATE_IN_QUEUE;
import static com.example.myapplication.presentation.utils.constants.Utils.NOT_QUEUE_OWNER;
import static com.example.myapplication.presentation.utils.constants.Utils.NOT_RESTAURANT_VISITOR;
import static com.example.myapplication.presentation.utils.constants.Utils.NO_ORDER;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.profile.ProfileDI;
import com.example.myapplication.domain.model.profile.UserActionsDataModel;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.userSettings.basicSettings.model.SettingsUserModel;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.userSettings.basicSettings.state.BasicSettingsState;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BasicSettingsViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _haveActions = new MutableLiveData<>(null);
    LiveData<Boolean> haveActions = _haveActions;

    private final MutableLiveData<BasicSettingsState> _state = new MutableLiveData<>(new BasicSettingsState.Loading());
    LiveData<BasicSettingsState> state = _state;

    public void retrieveUserData() {
        ProfileDI.getProfileImageUseCase.invoke()
                .zipWith(ProfileDI.getUserEmailAndNameDataUseCase.invoke(), (imageModel, userEmailAndNameModel) ->
                        new SettingsUserModel(userEmailAndNameModel.getName(), userEmailAndNameModel.getEmail(), imageModel.getImageUri()))
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<SettingsUserModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull SettingsUserModel userModel) {
                        _state.postValue(new BasicSettingsState.Success(userModel));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _state.postValue(new BasicSettingsState.Error());
                    }
                });
    }

    public void getUserActions(){
        ProfileDI.getUserActionsDataUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<UserActionsDataModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull UserActionsDataModel userActionsDataModel) {
                        if (!userActionsDataModel.getRestaurantVisitor().equals(NOT_RESTAURANT_VISITOR) || !userActionsDataModel.isOwnQueue().equals(NOT_QUEUE_OWNER) || !userActionsDataModel.isParticipateInQueue().equals(NOT_PARTICIPATE_IN_QUEUE)){
                            _haveActions.postValue(true);
                        } else {
                            _haveActions.postValue(false);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void setArgumentsNull() {
        _haveActions.postValue(null);
    }
}
