package com.example.myapplication.presentation.restaurantMenu.dishDetails.editRequiredChoice.recyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.RecyclerViewEditRequiredChoiceItemBinding;

public class EditRequiredChoiceItemAdapter extends ListAdapter<EditRequiredChoiceItemModel, RecyclerView.ViewHolder> {

    public EditRequiredChoiceItemAdapter() {
        super(new EditRequiredChoiceItemCallBack());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RecyclerViewEditRequiredChoiceItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).bind(getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewEditRequiredChoiceItemBinding binding;

        public ViewHolder(RecyclerViewEditRequiredChoiceItemBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(EditRequiredChoiceItemModel model){
            binding.ingredientName.setText(model.ingredientName);
            binding.buttonDelete.setOnClickListener(v -> {
                model.listener.onClick();
            });

            binding.buttonEdit.setOnClickListener(v -> {
                binding.editLayout.ingredientName.setText(model.ingredientName);
                binding.editLayout.getRoot().setVisibility(View.VISIBLE);
                binding.editLayout.buttonSave.setOnClickListener(view -> {
                    String newVariant = binding.editLayout.ingredientName.getText().toString();

                    model.editListener.onClick(newVariant);
                    binding.editLayout.getRoot().setVisibility(View.GONE);
                    binding.ingredientName.setText(newVariant);
                });
            });
        }
    }
}
