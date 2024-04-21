package com.example.myapplication.presentation.dialogFragments.ingredientsToRemoveOwner.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.RecyclerViewIngredientToRemoveEditBinding;

import myapplication.android.ui.listeners.ButtonStringListener;

public class IngredientToRemoveAdapter extends ListAdapter<IngredientToRemoveItemModel, RecyclerView.ViewHolder> {

    public IngredientToRemoveAdapter() {
        super(new IngredientToRemoveItemCallBack());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RecyclerViewIngredientToRemoveEditBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bind((IngredientToRemoveItemModel) getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerViewIngredientToRemoveEditBinding binding;

        public ViewHolder(RecyclerViewIngredientToRemoveEditBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(IngredientToRemoveItemModel model) {
            if (model.listenerAdded != null) {
                setEditTextVisible();
                binding.button.setImageDrawable(ResourcesCompat.getDrawable(itemView.getResources(), R.drawable.ic_round_done, itemView.getContext().getTheme()));
                binding.button.setOnClickListener(v -> {
                    String name = binding.editTextIngredientName.getText().toString();
                    model.listenerAdded.onClick(name);
                    binding.button.setImageDrawable(ResourcesCompat.getDrawable(itemView.getResources(), R.drawable.ic_edit, itemView.getContext().getTheme()));
                });
            } else {
                binding.textIngredientName.setText(model.name);
                initButtonEdit(model.name, model.listenerUpdated);

                binding.buttonDelete.setOnClickListener(v -> {
                    model.listenerDelete.onClick();
                });

            }
        }

        private void setEditTextVisible() {
            binding.textIngredientName.setVisibility(View.GONE);
            binding.editTextIngredientName.setVisibility(View.VISIBLE);
            binding.buttonDelete.setVisibility(View.GONE);
        }

        void initButtonEdit(String namePrevious, ButtonStringListener listener) {
            binding.button.setOnClickListener(v -> {

                setEditTextVisible();
                binding.editTextIngredientName.setText(namePrevious);
                binding.button.setImageDrawable(ResourcesCompat.getDrawable(itemView.getResources(), R.drawable.ic_round_done, itemView.getContext().getTheme()));

                binding.button.setOnClickListener(view -> {
                    String name = binding.editTextIngredientName.getText().toString();
                    listener.onClick(name);

                    binding.button.setImageDrawable(ResourcesCompat.getDrawable(itemView.getResources(), R.drawable.ic_edit, itemView.getContext().getTheme()));
                    binding.editTextIngredientName.setVisibility(View.GONE);
                    String newName = binding.editTextIngredientName.getText().toString();

                    binding.textIngredientName.setVisibility(View.VISIBLE);
                    binding.textIngredientName.setText(newName);

                    binding.buttonDelete.setVisibility(View.VISIBLE);

                    binding.button.setOnClickListener(viewNew -> {
                        initButtonEdit(newName, listener);
                    });
                });
            });
        }
    }

}
