package myapplication.android.ui.recycler.ui.items.items.priceWithCurrency;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import myapplication.android.common_ui.R;
import myapplication.android.common_ui.databinding.RecyclerViewChoosePriceBinding;
import myapplication.android.ui.recycler.delegate.AdapterDelegate;
import myapplication.android.ui.recycler.delegate.DelegateItem;

public class PriceWithCurrencyDelegate implements AdapterDelegate {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(RecyclerViewChoosePriceBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, DelegateItem item, int position) {
        ((ViewHolder)holder).bind((PriceWithCurrencyModel) item.content());
    }

    @Override
    public boolean isOfViewType(DelegateItem item) {
        return item instanceof PriceWithCurrencyDelegateItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewChoosePriceBinding binding;
        public ViewHolder( RecyclerViewChoosePriceBinding  _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(PriceWithCurrencyModel model){
            if (model.price != null){
                binding.editLayoutEmail.setText(model.price);
            }

            binding.buttonChooseCurrency.setImageDrawable(ResourcesCompat.getDrawable(itemView.getResources(), R.drawable.ic_currency_ruble, itemView.getContext().getTheme()));
            binding.editLayoutEmail.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                   model.resultListener.getResult(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }
}
