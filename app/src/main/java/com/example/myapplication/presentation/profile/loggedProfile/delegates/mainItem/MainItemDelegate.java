package com.example.myapplication.presentation.profile.loggedProfile.delegates.mainItem;

import static com.example.myapplication.presentation.utils.Utils.BASIC;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_EMAIL;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_NAME;
import static com.example.myapplication.presentation.utils.Utils.URI;
import static com.example.myapplication.presentation.utils.Utils.USER_NAME_KEY;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.DI;
import com.example.myapplication.databinding.RecyclerViewUserProfileMainItemBinding;
import com.example.myapplication.domain.model.common.ImageModel;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Objects;

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


            binding.name.setText(model.name);
            binding.yourEmail.setText(model.email);

            if (model.uri != Uri.EMPTY) {
                Glide.with(itemView.getContext())
                        .load(model.uri)
                        .apply(RequestOptions.circleCropTransform())
                        .into(binding.profilePhoto);
            }

            if (model.type.equals(BASIC)) {
                addProfileSnapshot(model);
            } else {
                addCompanySnapshot(model);
            }

        }

        private void addCompanySnapshot(MainItemModel model) {
            DI.addCompanySnapshotUseCase.invoke(model.companyId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<DocumentSnapshot>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull DocumentSnapshot documentSnapshot) {
                            if (!Objects.equals(documentSnapshot.getString(COMPANY_NAME), model.name)) {
                                binding.name.setText(documentSnapshot.getString(COMPANY_NAME));
                            }
                            if (!Objects.equals(documentSnapshot.getString(COMPANY_EMAIL), model.email)) {
                                binding.yourEmail.setText(documentSnapshot.getString(COMPANY_EMAIL));
                            }

                            DI.getCompanyLogoUseCase.invoke(model.companyId)
                                    .subscribeOn(Schedulers.io())
                                    .subscribe(new SingleObserver<ImageModel>() {
                                        @Override
                                        public void onSubscribe(@NonNull Disposable d) {

                                        }

                                        @Override
                                        public void onSuccess(@NonNull ImageModel imageModel) {
                                            Uri uri = imageModel.getImageUri();
                                            if (!uri.equals(model.uri)) {
                                                Glide.with(itemView.getContext())
                                                        .load(uri)
                                                        .apply(RequestOptions.circleCropTransform())
                                                        .into(binding.profilePhoto);
                                            }
                                        }

                                        @Override
                                        public void onError(@NonNull Throwable e) {

                                        }
                                    });

//                            if (!String.valueOf(model.uri).equals(documentSnapshot.getString(URI))) {
//                                Glide.with(itemView.getContext())
//                                        .load(documentSnapshot.getString(URI))
//                                        .apply(RequestOptions.circleCropTransform())
//                                        .into(binding.profilePhoto);
//                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }

        private void addProfileSnapshot(MainItemModel model) {
            DI.addSnapshotProfileUseCase.invoke()
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<DocumentSnapshot>() {
                        @Override
                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@io.reactivex.rxjava3.annotations.NonNull DocumentSnapshot snapshot) {
                            String name = snapshot.getString(USER_NAME_KEY);

                            if (!name.equals(model.name)) {
                                binding.name.setText(snapshot.getString(USER_NAME_KEY));
                            }

                            DI.getProfileImageUseCase.invoke()
                                    .subscribeOn(Schedulers.io())
                                    .subscribe(new SingleObserver<ImageModel>() {
                                        @Override
                                        public void onSubscribe(@NonNull Disposable d) {

                                        }

                                        @Override
                                        public void onSuccess(@NonNull ImageModel imageModel) {
                                            Uri uri = imageModel.getImageUri();
                                            if (!uri.equals(model.uri)){
                                                Glide.with(itemView.getContext())
                                                        .load(uri)
                                                        .apply(RequestOptions.circleCropTransform())
                                                        .into(binding.profilePhoto);
                                            }
                                        }

                                        @Override
                                        public void onError(@NonNull Throwable e) {

                                        }
                                    });

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
