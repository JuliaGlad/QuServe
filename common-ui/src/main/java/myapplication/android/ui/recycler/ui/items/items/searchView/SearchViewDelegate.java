package myapplication.android.ui.recycler.ui.items.items.searchView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import myapplication.android.common_ui.databinding.RecyclerViewSearchTextBinding;
import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class SearchViewDelegate implements AdapterDelegate {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewSearchTextBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder) holder).bind((SearchViewModel)item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof SearchViewDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewSearchTextBinding binding;

        public ViewHolder(RecyclerViewSearchTextBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(SearchViewModel model){
        }

    }
}
