package com.example.myapplication.presentation.restaurantMenu.dishDetails.addRequiredChoice.recycler;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.RecyclerViewRequiredChoiceEditTextBinding;

import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class RequiredChoiceEditDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewRequiredChoiceEditTextBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder)holder).bind((RequiredChoiceEditItemModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof RequiredChoiceEditDelegateItem;
    }
    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewRequiredChoiceEditTextBinding binding;

        public ViewHolder(RecyclerViewRequiredChoiceEditTextBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(RequiredChoiceEditItemModel model){
            if (model.id > 2){
                binding.buttonRemove.setVisibility(View.VISIBLE);
                binding.buttonRemove.setOnClickListener(v -> {
                    model.listener.onClick();
                });
            }
            binding.ingredientName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    model.name.getResult(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }
    }
}
