package myapplication.android.ui.recycler.ui.items.items.priceWithCurrency;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class PriceWithCurrencyDelegateItem implements DelegateItem<PriceWithCurrencyModel> {

    PriceWithCurrencyModel value;

    public PriceWithCurrencyDelegateItem(PriceWithCurrencyModel value) {
        this.value = value;
    }

    @Override
    public PriceWithCurrencyModel content() {
        return value;
    }

    @Override
    public int id() {
        return value.hashCode();
    }

    @Override
    public boolean compareToOther(DelegateItem other) {
        return other.content() == content();
    }
}
