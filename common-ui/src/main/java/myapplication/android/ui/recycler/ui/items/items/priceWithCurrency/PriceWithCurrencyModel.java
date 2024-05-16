package myapplication.android.ui.recycler.ui.items.items.priceWithCurrency;

import myapplication.android.ui.listeners.ResultListener;

public class PriceWithCurrencyModel {
    int id;
    String price;
    ResultListener<String> resultListener;

    public PriceWithCurrencyModel(int id, String price, ResultListener<String> resultListener) {
        this.id = id;
        this.price = price;
        this.resultListener = resultListener;
    }
}
