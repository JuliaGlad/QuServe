package myapplication.android.ui.recycler.ui.items.items.editText;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import myapplication.android.common_ui.databinding.RecyclerViewEditTextBinding;
import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class EditTextDelegate implements AdapterDelegate {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewEditTextBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder) holder).bind((EditTextModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof EditTextDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final RecyclerViewEditTextBinding binding;

        public ViewHolder(RecyclerViewEditTextBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(EditTextModel model) {
            binding.editLayout.setInputType(model.inputType);

            binding.editLayout.setHint(model.hint);
            binding.editLayout.setText(model.text);

            if (!model.editable) {
                binding.editLayout.setFocusable(false);
                binding.editLayout.setClickable(false);
                binding.editLayout.setCursorVisible(false);
            }

            binding.editLayout.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence text, int start, int before, int count) {
                    model.resultListener.getResult(text.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }
}
