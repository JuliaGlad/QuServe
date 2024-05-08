package myapplication.android.ui.recycler.ui.items.items.buttonWithDescription;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;
import myapplication.android.common_ui.databinding.RecyclerViewButtonWithDescriptionLayoutBinding;
import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class ButtonWithDescriptionDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewButtonWithDescriptionLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder)holder).bind((ButtonWithDescriptionModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof ButtonWithDescriptionDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewButtonWithDescriptionLayoutBinding binding;

        public ViewHolder(RecyclerViewButtonWithDescriptionLayoutBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(ButtonWithDescriptionModel model){
            binding.headLine.setText(model.title);
            binding.description.setText(model.description);
            binding.icon.setImageDrawable(ResourcesCompat.getDrawable(itemView.getResources(), model.drawable, itemView.getContext().getTheme()));
            binding.item.setOnClickListener(v -> {
                model.listener.onClick();
            });
        }

    }
}
