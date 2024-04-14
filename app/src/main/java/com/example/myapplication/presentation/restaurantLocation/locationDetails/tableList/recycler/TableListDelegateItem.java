package com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.recycler;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class TableListDelegateItem implements DelegateItem<TableListModel> {

    TableListModel value;

    public TableListDelegateItem(TableListModel value) {
        this.value = value;
    }

    @Override
    public TableListModel content() {
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
