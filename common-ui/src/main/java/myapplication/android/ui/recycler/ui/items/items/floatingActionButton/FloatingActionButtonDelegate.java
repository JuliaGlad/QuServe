package myapplication.android.ui.recycler.ui.items.items.floatingActionButton;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import myapplication.android.common_ui.databinding.RecyclerViewFloatingActionButtonBinding;
import myapplication.android.ui.listeners.FloatingActionButtonItemListener;
import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;


public class FloatingActionButtonDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewFloatingActionButtonBinding.inflate
                (LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((FloatingActionButtonDelegate.ViewHolder) holder).bind((FloatingActionButtonModel) item.content(), ((FloatingActionButtonModel) item.content()).listener);
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof FloatingActionButtonDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private RecyclerViewFloatingActionButtonBinding binding;

        public ViewHolder(RecyclerViewFloatingActionButtonBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(FloatingActionButtonModel model, FloatingActionButtonItemListener listener){
            binding.floatingActionButton.setOnClickListener(v -> {
                listener.onClick();
            });
        }
    }
}
