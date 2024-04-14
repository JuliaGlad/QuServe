package myapplication.android.ui.recycler.ui.items.items.optionImageButton;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import myapplication.android.common_ui.databinding.RecyclerViewOptionImageButtonLayoutBinding;

public class OptionImageButtonAdapter extends ListAdapter<OptionImageButtonModel, RecyclerView.ViewHolder> {

    public OptionImageButtonAdapter() {
        super(new OptionImageButtonCallBack());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RecyclerViewOptionImageButtonLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).bind(getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewOptionImageButtonLayoutBinding binding;

        public ViewHolder(RecyclerViewOptionImageButtonLayoutBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(OptionImageButtonModel model){
            binding.title.setText(model.title);
            binding.buttonBackground.setImageDrawable(ResourcesCompat.getDrawable(itemView.getResources(), model.drawable, itemView.getContext().getTheme()));
            binding.item.setOnClickListener(v -> {
                model.listener.onClick();
            });
        }
    }
}
