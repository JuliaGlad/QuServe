package myapplication.android.ui.recycler.ui.items.items.participantListItem;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import myapplication.android.common_ui.databinding.RecyclerViewParticipantItemBinding;
import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class ParticipantListDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewParticipantItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ParticipantListDelegate.ViewHolder) holder).bind((ParticipantListModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof ParticipantListDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private RecyclerViewParticipantItemBinding binding;

        public ViewHolder(RecyclerViewParticipantItemBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(ParticipantListModel model) {

        }
    }
}
