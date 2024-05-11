package com.example.myapplication.presentation.employee.main.companiesEmployees.employeesRestaurant.recycler;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;
import com.example.myapplication.databinding.RecyclerViewEmployeeItemBinding;
import com.example.myapplication.di.company.CompanyQueueUserDI;
import com.example.myapplication.domain.model.common.ImageModel;

import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RestaurantEmployeeItemAdapter extends ListAdapter<RestaurantEmployeeItemModel, RecyclerView.ViewHolder> {

    public RestaurantEmployeeItemAdapter() {
        super(new RestaurantEmployeeItemCallBack());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewEmployeeItemBinding binding;

        public ViewHolder(RecyclerViewEmployeeItemBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(RestaurantEmployeeItemModel model){
            binding.employeeName.setText(model.name);
            binding.role.setText(model.role);


            CompanyQueueUserDI.getEmployeePhotoUseCase.invoke(model.employeeId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new SingleObserver<ImageModel>() {
                        @Override
                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                        }

                        @Override
                        public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull ImageModel imageModel) {
                            if (imageModel.getImageUri() != Uri.EMPTY) {
                                Glide.with(itemView.getContext())
                                        .load(imageModel.getImageUri())
                                        .apply(RequestOptions.circleCropTransform())
                                        .into(binding.employeePhoto);
                            } else {
                                Glide.with(itemView.getContext())
                                        .load(itemView.getContext().getDrawable(R.drawable.avatar))
                                        .apply(RequestOptions.circleCropTransform())
                                        .into(binding.employeePhoto);
                            }
                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                        }
                    });

            binding.buttonDelete.setOnClickListener(v -> {

            });

        }
    }
}
