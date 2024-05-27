package com.example.myapplication.presentation.restaurantMenu.AddCategory.recycler_view.chooseCategoryImage;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.databinding.RecyclerViewCategoryImageBinding;
import com.example.myapplication.presentation.restaurantMenu.AddCategory.main.ArgumentsCategory;
import com.example.myapplication.presentation.restaurantMenu.AddCategory.recycler_view.categoryImageAddOwnPhoto.CategoryOwnImageDelegate;
import com.example.myapplication.presentation.restaurantMenu.AddCategory.recycler_view.categoryImageAddOwnPhoto.CategoryOwnImageDelegateItem;
import com.example.myapplication.presentation.restaurantMenu.AddCategory.recycler_view.categoryImageAddOwnPhoto.CategoryOwnImageModel;
import com.example.myapplication.presentation.restaurantMenu.AddCategory.recycler_view.categoryImageExample.CategoryImageExampleDelegateItem;
import com.example.myapplication.presentation.restaurantMenu.AddCategory.recycler_view.categoryImageExample.CategoryImageExampleDelegate;
import com.example.myapplication.presentation.restaurantMenu.AddCategory.recycler_view.categoryImageExample.CategoryImageExampleModel;

import java.util.ArrayList;
import java.util.List;

import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.delegate.MainAdapter;

public class ChooseCategoryImageDelegate implements AdapterDelegate {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewCategoryImageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder) holder).bind((ChooseCategoryImageModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof ChooseCategoryImageDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerViewCategoryImageBinding binding;

        public ViewHolder(RecyclerViewCategoryImageBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(ChooseCategoryImageModel model) {
            MainAdapter adapter = new MainAdapter();
            adapter.addDelegate(new CategoryImageExampleDelegate());
            adapter.addDelegate(new CategoryOwnImageDelegate());

            binding.recyclerView.setAdapter(adapter);

            binding.chosenImage.foodTitle.setText(model.name);

            if (!model.uri.equals(Uri.EMPTY)){
                Glide.with(itemView.getContext())
                        .load(model.uri)
                        .into(binding.chosenImage.foodImage);
            }

            List<DelegateItem> items = new ArrayList<>();

            items.add(new CategoryOwnImageDelegateItem(new CategoryOwnImageModel(items.size() + 1, R.drawable.add_a_photo_primary, model.listener)));

            for (int i = 0; i < model.drawables.size(); i++) {
                int current = model.drawables.get(i);
                int index = i;
                items.add(new CategoryImageExampleDelegateItem(new CategoryImageExampleModel(index, current, drawable -> {
                    ArgumentsCategory.chosenImage = model.drawablesIds.get(index);
                    binding.chosenImage.foodImage.setImageDrawable(ResourcesCompat.getDrawable(itemView.getResources(), drawable, itemView.getContext().getTheme()));
                })));
            }

            adapter.submitList(items);
        }
    }
}
