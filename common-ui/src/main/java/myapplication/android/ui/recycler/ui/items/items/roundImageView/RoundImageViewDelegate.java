package myapplication.android.ui.recycler.ui.items.items.roundImageView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import myapplication.android.common_ui.databinding.RecyclerViewRoundImageViewBinding;
import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class RoundImageViewDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewRoundImageViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder) holder).bind((RoundImageViewModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof RoundImageDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerViewRoundImageViewBinding binding;

        public ViewHolder(RecyclerViewRoundImageViewBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(RoundImageViewModel model) {

            String uri = String.valueOf(model.uri);

            if (model.uri != null) {
                Glide.with(itemView.getContext())
                        .load(model.uri)
                        .apply(RequestOptions.circleCropTransform())
                        .into(binding.addCompanyLogo);
            }

            binding.addCompanyLogo.setOnClickListener(v -> {
                model.listener.onClick()
                        .subscribeOn(Schedulers.newThread())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onComplete() {
                                Log.d("URI", String.valueOf(String.valueOf(model.uri).equals(uri)));
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }
                        });
            });
        }
    }
}
