package com.example.myapplication.presentation.queue.queueDetails.finishQueueButton;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.databinding.RecyclerViewButtonFinishQueueBinding;
import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class FinishQueueButtonDelegate implements AdapterDelegate {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewButtonFinishQueueBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder) holder).bind((FinishQueueButtonModel) item.content(), ((FinishQueueButtonModel) item.content()).clickListener);
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof FinishQueueButtonDelegateItem;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private RecyclerViewButtonFinishQueueBinding binding;

        public ViewHolder(RecyclerViewButtonFinishQueueBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(FinishQueueButtonModel model, FinishQueueButtonItemListener listener) {
            binding.finishQueue.setText(model.text);
            binding.finishQueue.setOnClickListener(v -> listener.onClick());
        }
    }
}
