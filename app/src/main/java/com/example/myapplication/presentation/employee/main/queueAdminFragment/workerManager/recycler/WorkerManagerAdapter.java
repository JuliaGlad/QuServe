package com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.recycler;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.di.CompanyQueueUserDI;
import com.example.myapplication.di.DI;
import com.example.myapplication.R;
import com.example.myapplication.databinding.RecyclerViewWorkerManagerItemBinding;
import com.example.myapplication.domain.model.common.ImageModel;

import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class WorkerManagerAdapter extends ListAdapter<WorkerManagerModel, RecyclerView.ViewHolder>{

    public WorkerManagerAdapter() {
        super(new WorkerManagerItemCallBack());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RecyclerViewWorkerManagerItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).bind(getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewWorkerManagerItemBinding binding;

        public ViewHolder( RecyclerViewWorkerManagerItemBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(WorkerManagerModel model){

            binding.employeeName.setText(model.name);
            binding.queuesCount.setText(model.count);

            CompanyQueueUserDI.getEmployeePhotoUseCase.invoke(model.getUserId())
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
                                        .load(ResourcesCompat.getDrawable(itemView.getResources(),R.drawable.avatar, itemView.getContext().getTheme()))
                                        .apply(RequestOptions.circleCropTransform())
                                        .into(binding.employeePhoto);
                            }
                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                        }
                    });

            binding.item.setOnClickListener(v -> {
                model.getListener().onClick();
            });
        }
    }
}
