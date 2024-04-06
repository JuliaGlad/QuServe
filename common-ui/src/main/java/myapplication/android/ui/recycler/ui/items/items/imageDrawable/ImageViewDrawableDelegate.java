package myapplication.android.ui.recycler.ui.items.items.imageDrawable;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import myapplication.android.common_ui.databinding.RecyclerViewDrawableImageBinding;
import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class ImageViewDrawableDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewDrawableImageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder)holder).bind((ImageViewDrawableModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof ImageViewDrawableDelegateItem;
    }
    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewDrawableImageBinding binding;

        public ViewHolder(RecyclerViewDrawableImageBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(ImageViewDrawableModel model){
            binding.imageView.setImageDrawable(
                    ResourcesCompat.getDrawable(itemView.getResources(), model.drawable, itemView.getContext().getTheme())
            );
        }
    }
}
