package com.example.myapplication.presentation.restaurantMenu.AddCategory.recycler_view.categoryImageExample;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.RecyclerViewAddCategoryImageExamplesBinding;

import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class CategoryImageExampleDelegate implements AdapterDelegate {


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewAddCategoryImageExamplesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder)holder).bind((CategoryImageExampleModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof CategoryImageExampleDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewAddCategoryImageExamplesBinding binding;

        public ViewHolder(RecyclerViewAddCategoryImageExamplesBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(CategoryImageExampleModel model){
            Drawable drawable = ResourcesCompat.getDrawable(itemView.getResources(), model.drawable, itemView.getContext().getTheme());
            binding.foodImage.setImageDrawable(drawable);
            binding.item.setOnClickListener(v -> {
                model.listener.onClick(model.drawable);
            });
        }
    }
}
