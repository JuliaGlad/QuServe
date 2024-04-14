package myapplication.android.ui.recycler.ui.items.items.chooseLocation;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class LocationDelegateItem implements DelegateItem {

    LocationModel value;

    public LocationDelegateItem(LocationModel value) {
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
        return other.content() == content();
    }
}
