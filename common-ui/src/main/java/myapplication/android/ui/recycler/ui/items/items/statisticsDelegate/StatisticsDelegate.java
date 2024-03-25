package myapplication.android.ui.recycler.ui.items.items.statisticsDelegate;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import myapplication.android.common_ui.databinding.RecyclerViewStatisticItemBinding;
import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class StatisticsDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewStatisticItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder)holder).bind((StatisticsModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof StatisticsDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewStatisticItemBinding binding;

        public ViewHolder(RecyclerViewStatisticItemBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(StatisticsModel model){
             binding.staticLayout.setOnClickListener(v -> {

             });
        }
    }
}
