package myapplication.android.ui.recycler.ui.items.items.chooseLocation;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import myapplication.android.common_ui.databinding.RecyclerViewLocationItemBinding;
import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class LocationDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewLocationItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder)holder).bind((LocationModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof LocationDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewLocationItemBinding binding;

        public ViewHolder(RecyclerViewLocationItemBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(LocationModel model){
            binding.buttonOpenMap.setOnClickListener(v -> {
                model.listener.onClick();
            });
            binding.editLayoutEmail.setOnClickListener(v -> {
                model.listener.onClick();
            });
            binding.editLayoutEmail.setText(model.location);
        }
    }
}
