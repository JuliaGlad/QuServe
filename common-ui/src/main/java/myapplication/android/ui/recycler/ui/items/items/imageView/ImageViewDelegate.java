package myapplication.android.ui.recycler.ui.items.items.imageView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import myapplication.android.common_ui.databinding.RecyclerViewImageViewBinding;
import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class ImageViewDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ImageViewDelegate.ViewHolder(RecyclerViewImageViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ImageViewDelegate.ViewHolder) holder).bind((ImageViewModel) item.content(), holder.itemView);
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof ImageViewDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerViewImageViewBinding binding;

        public ViewHolder(RecyclerViewImageViewBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        public void bind(ImageViewModel model, View itemView) {
            if (model.uri != null) {
                Glide.with(itemView.getContext()).load(model.uri).into(binding.imageView);
            }
        }
    }
}
