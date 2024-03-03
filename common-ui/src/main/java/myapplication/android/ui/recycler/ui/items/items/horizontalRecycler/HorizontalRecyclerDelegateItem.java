package myapplication.android.ui.recycler.ui.items.items.horizontalRecycler;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class HorizontalRecyclerDelegateItem implements DelegateItem {

    HorizontalRecyclerModel value;

    public HorizontalRecyclerDelegateItem(HorizontalRecyclerModel value) {
        this.value = value;
    }

    @Override
    public Object content() {
        return value;
    }

    @Override
    public int id() {
        return value.hashCode();
    }

    @Override
    public boolean compareToOther(DelegateItem other) {
        return other.content() == value;
    }
}
