package com.example.myapplication.presentation.profile.loggedProfile.delegates.companyListItem;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.databinding.RecyclerViewCompanyListItemBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageException;

import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class CompanyListItemDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewCompanyListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder) holder).bind((CompanyListItemDelegateModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof CompanyListItemDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerViewCompanyListItemBinding binding;

        public ViewHolder(RecyclerViewCompanyListItemBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(CompanyListItemDelegateModel model) {

            binding.companyName.setText(model.name);

            binding.item.setOnClickListener(v -> {
                model.listener.onClick();
            });

            if (model.uriTask != null) {
                model.uriTask.addOnCompleteListener(new OnCompleteListener<Uri>() {
                    Uri uri = Uri.EMPTY;

                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        try {
                            if (task.isSuccessful()) {
                                uri = task.getResult();
                                Glide.with(itemView.getContext())
                                        .load(uri)
                                        .apply(RequestOptions.circleCropTransform())
                                        .into(binding.companyLogo);
                            }
                        } catch (RuntimeExecutionException e) {
                            Log.d("RuntimeExecutionException", e.getMessage());
                        }
                    }
                });
            }
        }
    }
}
