package com.example.myapplication.presentation.profile.loggedProfile.basicUser.delegates.mainItem;

import static com.example.myapplication.presentation.utils.Utils.USER_NAME_KEY;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.DI;
import com.example.myapplication.databinding.RecyclerViewUserProfileMainItemBinding;
import com.example.myapplication.domain.model.common.ImageModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class MainItemDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewUserProfileMainItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder) holder).bind((MainItemModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof MainItemDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerViewUserProfileMainItemBinding binding;

        public ViewHolder(RecyclerViewUserProfileMainItemBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(MainItemModel model) {

            addSnapshot(model);

            binding.name.setText(model.name);
            binding.yourEmail.setText(model.email);

            if (model.uri != Uri.EMPTY) {
                Glide.with(itemView.getContext())
                        .load(model.uri)
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .apply(RequestOptions.circleCropTransform())
                        .into(binding.profilePhoto);
            }
        }

        private void addSnapshot(MainItemModel model) {
            DI.addSnapshotProfileUseCase.invoke()
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<DocumentSnapshot>() {
                        @Override
                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@io.reactivex.rxjava3.annotations.NonNull DocumentSnapshot snapshot) {
                            binding.name.setText(snapshot.getString(USER_NAME_KEY));
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        }
    }
}
