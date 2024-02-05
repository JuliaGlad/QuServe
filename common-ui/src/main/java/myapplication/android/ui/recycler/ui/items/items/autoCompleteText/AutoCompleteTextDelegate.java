package myapplication.android.ui.recycler.ui.items.items.autoCompleteText;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import myapplication.android.common_ui.databinding.RecyclerViewAutoCompleteTextBinding;
import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class AutoCompleteTextDelegate implements AdapterDelegate {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewAutoCompleteTextBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder) holder).bind((AutoCompleteTextModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof AutoCompleteTextDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final RecyclerViewAutoCompleteTextBinding binding;

        public ViewHolder(RecyclerViewAutoCompleteTextBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(AutoCompleteTextModel model) {
            binding.autoCompleteText.setSimpleItems(model.stringArray);

            binding.autoCompleteText.setOnItemClickListener((parent, view, position, id) -> {
                String lifetime = (parent.getItemAtPosition(position).toString());
                model.listener.getResult(lifetime);
            });
        }
    }
}
