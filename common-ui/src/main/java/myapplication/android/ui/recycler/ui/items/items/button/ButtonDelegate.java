package myapplication.android.ui.recycler.ui.items.items.button;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;


import myapplication.android.common_ui.databinding.RecyclerViewButtonBinding;
import myapplication.android.ui.listeners.ButtonItemListener;
import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class ButtonDelegate implements AdapterDelegate {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewButtonBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder) holder).bind((ButtonModel) item.content(), ((ButtonModel) item.content()).clickListener);
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof ButtonDelegateItem;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final RecyclerViewButtonBinding binding;

        public ViewHolder(RecyclerViewButtonBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(ButtonModel model, ButtonItemListener listener) {
            binding.button.setText(model.text);
            binding.button.setOnClickListener(v -> listener.onClick());
        }
    }
}
