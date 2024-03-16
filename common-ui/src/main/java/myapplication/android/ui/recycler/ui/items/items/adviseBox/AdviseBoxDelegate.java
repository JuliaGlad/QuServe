package myapplication.android.ui.recycler.ui.items.items.adviseBox;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import myapplication.android.common_ui.databinding.RecyclerViewAdviseDialogBinding;
import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class AdviseBoxDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewAdviseDialogBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder)holder).bind((AdviseBoxModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof AdviseBoxDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewAdviseDialogBinding binding;

        public ViewHolder( RecyclerViewAdviseDialogBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(AdviseBoxModel model){
            binding.body.setText(model.title);
        }
    }
}
