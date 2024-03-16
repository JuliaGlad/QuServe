package myapplication.android.ui.recycler.ui.items.items.textView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

import myapplication.android.common_ui.databinding.RecyclerViewTextviewBinding;
import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class TextViewHeaderDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewTextviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((TextViewHeaderDelegate.ViewHolder) holder).bind((TextViewHeaderModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof TextViewHeaderDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private RecyclerViewTextviewBinding binding;

        public ViewHolder(RecyclerViewTextviewBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(TextViewHeaderModel model) {
            binding.setText.setText(model.text);
        }
    }
}
