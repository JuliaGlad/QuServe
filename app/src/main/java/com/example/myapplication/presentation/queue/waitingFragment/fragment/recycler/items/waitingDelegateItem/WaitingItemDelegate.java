package com.example.myapplication.presentation.queue.waitingFragment.fragment.recycler.items.waitingDelegateItem;

import static com.example.myapplication.presentation.utils.Utils.EDIT_PEOPLE_BEFORE_YOU;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DI;
import com.example.myapplication.databinding.RecyclerViewWaitingItemBinding;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class WaitingItemDelegate implements AdapterDelegate {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewWaitingItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((WaitingItemDelegate.ViewHolder) holder).bind((WaitingItemModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof WaitingItemDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerViewWaitingItemBinding binding;

        public ViewHolder(RecyclerViewWaitingItemBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(WaitingItemModel model) {
            binding.icon.setImageResource(model.drawable);
            binding.headLine.setText(model.headerText);
            binding.description.setText(model.descriptionText);

            if (model.editable && model.flag.equals(EDIT_PEOPLE_BEFORE_YOU)) {
                DI.addPeopleBeforeYouSnapshot.invoke(model.queueID, model.size)
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Observer<Integer>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@NonNull Integer integer) {
                                binding.description.setText(String.valueOf(integer));
                                model.size -= 1;
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

}
