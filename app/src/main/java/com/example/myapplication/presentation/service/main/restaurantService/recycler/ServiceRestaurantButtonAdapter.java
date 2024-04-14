package com.example.myapplication.presentation.service.main.restaurantService.recycler;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.RecyclerViewServiceRestaurantButtonBinding;

public class ServiceRestaurantButtonAdapter extends ListAdapter<ServiceRestaurantButtonModel, RecyclerView.ViewHolder> {

    public ServiceRestaurantButtonAdapter() {
        super(new ServiceRestaurantButtonCallBack());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RecyclerViewServiceRestaurantButtonBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).bind((ServiceRestaurantButtonModel) getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewServiceRestaurantButtonBinding binding;

        public ViewHolder(RecyclerViewServiceRestaurantButtonBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(ServiceRestaurantButtonModel model){
            binding.title.setText(model.title);
            binding.buttonBackground.setImageDrawable(ResourcesCompat.getDrawable(itemView.getResources(), model.drawable, itemView.getContext().getTheme()));
            binding.item.setOnClickListener(v -> {
                model.listener.onClick();
            });
        }
    }
}
