package myapplication.android.ui.recycler.ui.items.items.optionImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class OptionImageButtonCallBack extends DiffUtil.ItemCallback<OptionImageButtonModel> {
    @Override
    public boolean areItemsTheSame(@NonNull OptionImageButtonModel oldItem, @NonNull OptionImageButtonModel newItem) {
        return oldItem.id == newItem.id;
    }

    @Override
    public boolean areContentsTheSame(@NonNull OptionImageButtonModel oldItem, @NonNull OptionImageButtonModel newItem) {
        return oldItem.compareTo(newItem);
    }
}
