package myapplication.android.ui.recycler.ui.items.items.categoryItem;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import myapplication.android.common_ui.databinding.RecyclerViewMenuCategoryBinding;
import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class CategoryItemDelegate implements AdapterDelegate {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewMenuCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder) holder).bind((CategoryItemModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof CategoryItemDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerViewMenuCategoryBinding binding;

        public ViewHolder(RecyclerViewMenuCategoryBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(CategoryItemModel model) {

            if (model.getDrawable() != 0) {
                binding.foodImage.setImageDrawable(ResourcesCompat.getDrawable(itemView.getResources(), model.getDrawable(), itemView.getContext().getTheme()));
            }

            binding.item.setOnClickListener(v -> {
                model.getListener().onClick();
            });

            binding.foodTitle.setText(model.getName());

            if (model.getTask() != null) {
                model.getTask().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Glide.with(itemView.getContext())
                                .load(task.getResult())
                                .into(binding.foodImage);
                    }
                });
            }
        }
    }
}
