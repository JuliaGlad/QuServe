package com.example.myapplication.presentation.home.recycler.homeDelegates.actionButton;

import static com.example.myapplication.presentation.utils.constants.Restaurant.VISITOR;
import static com.example.myapplication.presentation.utils.constants.Utils.PARTICIPANT;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.RecyclerViewActionButtonHomeLayoutBinding;

import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class HomeActionButtonDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewActionButtonHomeLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder)holder).bind((HomeActionButtonModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof HomeActionButtonDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewActionButtonHomeLayoutBinding binding;

        public ViewHolder(RecyclerViewActionButtonHomeLayoutBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(HomeActionButtonModel model){
            binding.title.setText(model.title);
            binding.roleSubtext.setText(model.role);

            if (model.type.equals(PARTICIPANT) || model.type.equals(VISITOR)){
                binding.icon.setVisibility(View.GONE);
            }

            binding.item.setOnClickListener(v -> {
                model.listener.onClick();
            });

        }
    }
}
