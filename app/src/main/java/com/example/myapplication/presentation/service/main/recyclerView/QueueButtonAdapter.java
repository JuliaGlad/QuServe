package com.example.myapplication.presentation.service.main.recyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.RecyclerViewQueueFragmentButtonLayoutBinding;

public class QueueButtonAdapter extends ListAdapter<QueueButtonModel, RecyclerView.ViewHolder> {

    public QueueButtonAdapter() {
        super(new QueueButtonCallBack());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RecyclerViewQueueFragmentButtonLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).bind(getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewQueueFragmentButtonLayoutBinding binding;

        public ViewHolder(RecyclerViewQueueFragmentButtonLayoutBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(QueueButtonModel model){
            binding.title.setText(model.title);
            binding.buttonBackground.setImageDrawable(ResourcesCompat.getDrawable(itemView.getResources(), model.drawable, itemView.getContext().getTheme()));
            binding.item.setOnClickListener(v -> {
                model.listener.onClick();
            });
        }
    }
}
