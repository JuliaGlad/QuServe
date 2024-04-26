package com.example.myapplication.presentation.restaurantOrder.dishDetails.recycler.requireChoice;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.RecyclerViewRequiredChoiceItemBinding;

public class RequiredChoiceOrderAdapter extends ListAdapter<RequireChoiceOrderModel, RecyclerView.ViewHolder> {

    public RequiredChoiceOrderAdapter() {
        super(new RequiredChoiceOrderCallBack());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RecyclerViewRequiredChoiceItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).bind(getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewRequiredChoiceItemBinding binding;

        public ViewHolder( RecyclerViewRequiredChoiceItemBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }
        void bind(RequireChoiceOrderModel model){
            binding.variantName.setText(model.name);
            binding.item.setOnClickListener(v -> {
                model.listener.onClick();
            });
        }
    }
}
