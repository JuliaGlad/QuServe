package com.example.myapplication.presentation.restaurantMenu.dishDetails.recyclers.requiredChoice;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.RecyclerViewRequiredChoiceItemBinding;

public class RequiredChoiceAdapter extends ListAdapter<RequiredChoiceItemModel, RecyclerView.ViewHolder> {

    public RequiredChoiceAdapter() {
        super(new RequiredChoiceItemCallBack());
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

        public ViewHolder(RecyclerViewRequiredChoiceItemBinding _binding){
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(RequiredChoiceItemModel model){
            binding.variantName.setText(model.name);

            binding.item.setOnClickListener(v -> model.listener.onClick());

            if (model.isChosen){
                binding.layout.setBackgroundColor(ResourcesCompat.getColor(itemView.getResources(), R.color.colorPrimaryContainer, itemView.getContext().getTheme()));
                binding.variantName.setTextColor(ResourcesCompat.getColor(itemView.getResources(), R.color.colorPrimary, itemView.getContext().getTheme()));
            } else {
                binding.layout.setBackgroundColor(ResourcesCompat.getColor(itemView.getResources(), R.color.colorDisabled, itemView.getContext().getTheme()));
                binding.variantName.setTextColor(ResourcesCompat.getColor(itemView.getResources(), R.color.colorTextDisabled, itemView.getContext().getTheme()));
            }
        }
    }
}
