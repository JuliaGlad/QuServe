package myapplication.android.ui.recycler.delegate;

import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

public interface AdapterDelegate{
    RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent);

    void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position);

    boolean isOfViewType(DelegateItem item);
}
