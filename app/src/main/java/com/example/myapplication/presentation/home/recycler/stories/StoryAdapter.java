package com.example.myapplication.presentation.home.recycler.stories;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.RecyclerViewStoryBinding;

public class StoryAdapter extends ListAdapter<StoryModel, RecyclerView.ViewHolder> {

    public StoryAdapter() {
        super(new StoryItemCallBack());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RecyclerViewStoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).bind(getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewStoryBinding binding;

        public ViewHolder(RecyclerViewStoryBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(StoryModel model){
            binding.title.setText(model.title);
            binding.constraint.setBackground(ResourcesCompat.getDrawable(itemView.getResources(), model.background, itemView.getContext().getTheme()));
            binding.storyImage.setBackground(ResourcesCompat.getDrawable(itemView.getResources(), model.image, itemView.getContext().getTheme()));
            binding.cardItem.setOnClickListener(v -> {
                model.listener.onClick();
            });
        }
    }
}
