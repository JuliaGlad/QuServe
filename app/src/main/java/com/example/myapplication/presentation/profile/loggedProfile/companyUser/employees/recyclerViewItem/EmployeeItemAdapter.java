package com.example.myapplication.presentation.profile.loggedProfile.companyUser.employees.recyclerViewItem;

import static com.example.myapplication.presentation.utils.Utils.ADMIN;
import static com.example.myapplication.presentation.utils.Utils.EMPLOYEE_ROLE;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.DI;
import com.example.myapplication.R;
import com.example.myapplication.databinding.RecyclerViewEmployeeItemBinding;
import com.example.myapplication.domain.model.common.ImageModel;
import com.google.firebase.firestore.DocumentSnapshot;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import myapplication.android.ui.listeners.DialogDismissedListener;

public class EmployeeItemAdapter extends ListAdapter<EmployeeItemModel, RecyclerView.ViewHolder> {

    public EmployeeItemAdapter() {
        super(new EmployeeItemCallBack());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RecyclerViewEmployeeItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bind(getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerViewEmployeeItemBinding binding;

        public ViewHolder(RecyclerViewEmployeeItemBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(EmployeeItemModel model) {

            binding.employeeName.setText(model.getName());
            binding.role.setText(model.role);

            DI.getEmployeePhotoUseCase.invoke(model.employeeId)
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
                model.listener.onClick();
            });
            addSnapshot(model);
        }

        private void addSnapshot(EmployeeItemModel model) {
            DI.addEmployeeSnapshotUseCase.invoke(model.companyId, model.employeeId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<DocumentSnapshot>() {
                        @Override
                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@io.reactivex.rxjava3.annotations.NonNull DocumentSnapshot documentSnapshot) {
                            binding.role.setText(documentSnapshot.getString(EMPLOYEE_ROLE));
                            model.role = documentSnapshot.getString(EMPLOYEE_ROLE);
                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }
}
