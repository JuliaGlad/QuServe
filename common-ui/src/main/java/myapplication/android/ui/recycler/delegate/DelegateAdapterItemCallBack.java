package myapplication.android.ui.recycler.delegate;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class DelegateAdapterItemCallBack extends DiffUtil.ItemCallback<DelegateItem> {
    @Override
    public boolean areItemsTheSame(@NonNull DelegateItem oldItem, @NonNull DelegateItem newItem) {
        return oldItem == newItem && oldItem.id() == newItem.id();
    }

    @Override
    public boolean areContentsTheSame(@NonNull DelegateItem oldItem, @NonNull DelegateItem newItem) {
        return oldItem.compareToOther(newItem);
    }
}
