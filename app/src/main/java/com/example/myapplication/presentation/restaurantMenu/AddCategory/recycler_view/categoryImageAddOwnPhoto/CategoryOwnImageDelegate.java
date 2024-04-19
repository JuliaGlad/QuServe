package com.example.myapplication.presentation.restaurantMenu.AddCategory.recycler_view.categoryImageAddOwnPhoto;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.RecyclerViewAddCategoryImageExamplesBinding;

import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class CategoryOwnImageDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewAddCategoryImageExamplesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder)holder).bind((CategoryOwnImageModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof CategoryOwnImageDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewAddCategoryImageExamplesBinding binding;

        public ViewHolder(RecyclerViewAddCategoryImageExamplesBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(CategoryOwnImageModel model) {
            binding.foodImage.setImageDrawable(ResourcesCompat.getDrawable(itemView.getResources(), model.drawable, itemView.getContext().getTheme()));
            binding.item.setOnClickListener(v -> {
                model.listener.onClick();
            });
        }
    }
}
