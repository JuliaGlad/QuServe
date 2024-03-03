package myapplication.android.ui.recycler.ui.items.items.horizontalRecycler;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class HorizontalRecyclerModel {
    int id;
    DelegateItem[] items;

    public HorizontalRecyclerModel(int id, DelegateItem[] items) {
        this.id = id;
        this.items = items;
    }

    public DelegateItem[] getItems() {
        return items;
    }

    public int getId() {
        return id;
    }
}
