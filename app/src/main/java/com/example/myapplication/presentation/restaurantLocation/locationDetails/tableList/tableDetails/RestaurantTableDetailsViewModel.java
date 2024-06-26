package com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.tableDetails;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.restaurant.RestaurantTableDI;
import com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.model.TableModel;
import com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.tableDetails.model.TableDetailModel;
import com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.tableDetails.state.TableDetailsState;
import com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.tableDetails.state.TableDetailsStateModel;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RestaurantTableDetailsViewModel extends ViewModel {

    private final MutableLiveData<Uri> _pdfUri = new MutableLiveData<>(Uri.EMPTY);
    LiveData<Uri> pdfUri = _pdfUri;

    private final MutableLiveData<TableDetailsState> _state = new MutableLiveData<>(new TableDetailsState.Loading());
    LiveData<TableDetailsState> state = _state;

    public void getTableData(String restaurantId, String locationId, String tableId) {
        RestaurantTableDI.getSingleTableByIdUseCase.invoke(restaurantId, locationId, tableId)
                .zipWith(RestaurantTableDI.getTableQrCodeJpgUseCase.invoke(tableId), (tableModel, uri) ->
                        new TableDetailsStateModel(
                        tableModel.getNumber(),
                        tableModel.getOrderId(),
                        uri
                ))
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<TableDetailsStateModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull TableDetailsStateModel tableDetailsStateModel) {
                        _state.postValue(new TableDetailsState.Success(tableDetailsStateModel));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _state.postValue(new TableDetailsState.Error());
                    }
                });
    }

    public void getQrCodePdf(String tableId) {
        RestaurantTableDI.getTableQrCodePdfUseCase.invoke(tableId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Uri>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Uri uri) {
                        _pdfUri.postValue(uri);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}