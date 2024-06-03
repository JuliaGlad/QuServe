package com.example.myapplication.presentation.profile.loggedProfile.basicUser.historyProfile.delegates.history;

import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT;
import static com.example.myapplication.presentation.utils.constants.Utils.QUEUE;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.RecyclerViewHistoryItemBinding;

import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class HistoryDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewHistoryItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder)holder).bind((HistoryDelegateModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof HistoryDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewHistoryItemBinding binding;

        public ViewHolder(RecyclerViewHistoryItemBinding _binding) {
            super(_binding.getRoot());

            binding = _binding;
        }

        void bind(HistoryDelegateModel model){
            binding.queueName.setText(model.name);
            binding.time.setText(model.time);

            if (model.service.equals(QUEUE)){
                binding.icon.setImageDrawable(ResourcesCompat.getDrawable(itemView.getResources(), R.drawable.queue_round, itemView.getContext().getTheme()));
            } else if (model.service.equals(RESTAURANT)){
                binding.icon.setImageDrawable(ResourcesCompat.getDrawable(itemView.getResources(), R.drawable.restaurant_round_icon, itemView.getContext().getTheme()));
            }
        }
    }
}
