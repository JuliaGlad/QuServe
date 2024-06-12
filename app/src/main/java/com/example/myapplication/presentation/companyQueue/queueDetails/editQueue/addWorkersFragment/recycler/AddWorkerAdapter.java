package com.example.myapplication.presentation.companyQueue.queueDetails.editQueue.addWorkersFragment.recycler;

import static com.example.myapplication.presentation.utils.constants.Utils.CHOSEN;
import static com.example.myapplication.presentation.utils.constants.Utils.NOT_CHOSEN;
import static com.example.myapplication.presentation.utils.constants.Utils.WORKER;

import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.di.company.CompanyQueueUserDI;
import com.example.myapplication.R;
import com.example.myapplication.databinding.RecyclerViewAddWorkerItemBinding;
import com.example.myapplication.domain.model.common.ImageModel;
import com.example.myapplication.presentation.companyQueue.queueDetails.editQueue.addWorkersFragment.model.AddWorkerModel;

import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddWorkerAdapter extends ListAdapter<AddWorkerRecyclerModel, RecyclerView.ViewHolder> {

    public AddWorkerAdapter() {
        super(new AddWorkerItemCallBack());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RecyclerViewAddWorkerItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bind(getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerViewAddWorkerItemBinding binding;

        public ViewHolder(RecyclerViewAddWorkerItemBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(AddWorkerRecyclerModel model) {

            binding.employeeName.setText(model.getName());

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
                                        .load(itemView.getContext().getDrawable(R.drawable.avatar))
                                        .apply(RequestOptions.circleCropTransform())
                                        .into(binding.employeePhoto);
                            }
                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                        }
                    });

            binding.item.setOnClickListener(v -> {
                if (model.state.equals(NOT_CHOSEN)) {

                    model.state = CHOSEN;
                    binding.item.setStrokeColor(itemView.getResources().getColor(R.color.colorPrimary, itemView.getContext().getTheme()));
                    model.chosen.add(new AddWorkerModel(model.getName(), model.getUserId(), WORKER, model.getQueueCount()));

                } else {
                    model.state = NOT_CHOSEN;
                    binding.item.setStrokeColor(Color.TRANSPARENT);
                    for (int i = 0; i < model.chosen.size(); i++) {
                        if (model.chosen.get(i).getId().equals(model.getUserId())){
                            model.chosen.remove(i);
                            break;
                        }
                    }
                }
            });
        }
    }
}
