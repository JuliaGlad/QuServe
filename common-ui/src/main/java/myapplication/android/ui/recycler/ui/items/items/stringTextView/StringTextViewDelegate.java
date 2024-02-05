package myapplication.android.ui.recycler.ui.items.items.stringTextView;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import myapplication.android.common_ui.databinding.RecyclerViewStringTextViewItemBinding;
import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;


public class StringTextViewDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewStringTextViewItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder) holder).bind((StringTextViewModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof StringTextViewDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private RecyclerViewStringTextViewItemBinding binding;

        public ViewHolder(RecyclerViewStringTextViewItemBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(StringTextViewModel model) {
            binding.setText.setText(model.text);
            binding.setText.setTextSize(model.textSize);
            binding.setText.setTextAlignment(model.alignment);
        }
    }
}
