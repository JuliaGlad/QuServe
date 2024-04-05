package com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.workerDetails.delegates.header;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.DI;
import com.example.myapplication.R;
import com.example.myapplication.databinding.RecyclerViewWorkerDetailsHeaderBinding;
import com.example.myapplication.domain.model.common.ImageModel;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class WorkerDetailsHeaderDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewWorkerDetailsHeaderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder) holder).bind((WorkerDetailsHeaderModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof WorkerDetailsHeaderDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerViewWorkerDetailsHeaderBinding binding;

        public ViewHolder(RecyclerViewWorkerDetailsHeaderBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(WorkerDetailsHeaderModel model) {
            binding.employeeName.setText(model.getName());
            binding.employeeRole.setText(model.getRole());

            DI.getEmployeePhotoUseCase.invoke(model.getEmployeeId())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new SingleObserver<ImageModel>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onSuccess(@NonNull ImageModel imageModel) {
                            if (imageModel.getImageUri() != Uri.EMPTY) {
                                Glide.with(itemView.getContext())
                                        .load(imageModel.getImageUri())
                                        .apply(RequestOptions.circleCropTransform())
                                        .into(binding.employeePhoto);
                            } else {
                                Glide.with(itemView.getContext())
                                        .load(ResourcesCompat.getDrawable(itemView.getResources(),R.drawable.avatar, itemView.getContext().getTheme()))
                                        .apply(RequestOptions.circleCropTransform())
                                        .into(binding.employeePhoto);
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }
                    });
        }
    }
}
