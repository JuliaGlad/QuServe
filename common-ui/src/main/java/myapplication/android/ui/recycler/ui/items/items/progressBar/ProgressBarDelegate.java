package myapplication.android.ui.recycler.ui.items.items.progressBar;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import myapplication.android.common_ui.databinding.RecyclerViewProgressBarBinding;
import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class ProgressBarDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewProgressBarBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ProgressBarDelegate.ViewHolder) holder).bind((ProgressBarModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof ProgressBarDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private final RecyclerViewProgressBarBinding binding;

        public ViewHolder(RecyclerViewProgressBarBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(ProgressBarModel model){
            binding.queueProgressBar.incrementProgressBy(model.diff);
        }
    }
}
