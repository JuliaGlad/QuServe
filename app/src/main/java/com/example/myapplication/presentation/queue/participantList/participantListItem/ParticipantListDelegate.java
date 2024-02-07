package com.example.myapplication.presentation.queue.participantList.participantListItem;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DI;
import com.example.myapplication.databinding.RecyclerViewParticipantItemBinding;
import com.example.myapplication.domain.model.QueueSizeModel;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class ParticipantListDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewParticipantItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ParticipantListDelegate.ViewHolder)holder).bind((ParticipantListModel)item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof ParticipantListDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private RecyclerViewParticipantItemBinding binding;

        public ViewHolder( RecyclerViewParticipantItemBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(ParticipantListModel model){
            binding.participantNumber.setText(model.text);
        }
    }
}
