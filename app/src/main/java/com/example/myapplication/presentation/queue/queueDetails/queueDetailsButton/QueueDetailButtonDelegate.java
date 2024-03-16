package com.example.myapplication.presentation.queue.queueDetails.queueDetailsButton;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.RecyclerViewQueueDetailsItemBinding;

import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class QueueDetailButtonDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewQueueDetailsItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((QueueDetailButtonDelegate.ViewHolder)holder).bind((QueueDetailButtonModel) item.content(), ((QueueDetailButtonModel) item.content()).listener);
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof QueueDetailsButtonDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

            RecyclerViewQueueDetailsItemBinding binding;

        public ViewHolder(RecyclerViewQueueDetailsItemBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        public void bind(QueueDetailButtonModel model, QueueDetailButtonItemListener listener){
            binding.title.setText(model.title);
            binding.textBody.setText(model.description);
            binding.icon.setImageDrawable(ResourcesCompat.getDrawable(itemView.getResources(), model.drawable, itemView.getContext().getTheme()));
            binding.queueDetailsButtonLayout.setOnClickListener(v -> listener.onClick());
        }
    }
}
