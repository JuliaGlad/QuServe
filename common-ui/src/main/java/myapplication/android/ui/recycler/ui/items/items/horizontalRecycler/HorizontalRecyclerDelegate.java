package myapplication.android.ui.recycler.ui.items.items.horizontalRecycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;

import myapplication.android.common_ui.databinding.RecyclerViewHorizontalRecyclerBinding;
import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.floatingActionButton.FloatingActionButtonDelegate;

public class HorizontalRecyclerDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewHorizontalRecyclerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder)holder).bind((HorizontalRecyclerModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof HorizontalRecyclerDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewHorizontalRecyclerBinding binding;

        public ViewHolder( RecyclerViewHorizontalRecyclerBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(HorizontalRecyclerModel model){
            ListAdapter adapter = model.adapter;
            binding.recyclerView.setAdapter(adapter);
            adapter.submitList(model.items);
        }
    }
}
