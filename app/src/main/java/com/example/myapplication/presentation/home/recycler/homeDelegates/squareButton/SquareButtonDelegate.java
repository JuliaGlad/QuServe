package com.example.myapplication.presentation.home.recycler.homeDelegates.squareButton;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.RecyclerViewHomeButtonsLayoutBinding;

import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class SquareButtonDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewHomeButtonsLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder)holder).bind((SquareButtonModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof SquareButtonDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewHomeButtonsLayoutBinding binding;

        public ViewHolder(RecyclerViewHomeButtonsLayoutBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(SquareButtonModel model){
           initFirstButton(model);
           initSecondButton(model);
        }

        private void initFirstButton(SquareButtonModel model){
            binding.firstButtonTitle.setText(model.firstTitle);

            binding.firstButton.setOnClickListener(v -> {
                model.firstListener.onClick();
            });

            binding.firstItemIcon.setImageDrawable(ResourcesCompat.getDrawable(itemView.getResources(), model.firstDrawable, itemView.getContext().getTheme()));
        }

        private void initSecondButton(SquareButtonModel model){
            binding.secondButtonTitle.setText(model.secondTitle);
            binding.secondItemIcon.setImageDrawable(ResourcesCompat.getDrawable(itemView.getResources(), model.secondDrawable, itemView.getContext().getTheme()));
            binding.secondButton.setOnClickListener(v -> {
                model.secondListener.onClick();
            });
        }
    }
}
