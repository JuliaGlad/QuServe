package myapplication.android.ui.recycler.ui.items.items.tabLayout;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class TabLayoutDelegateItem implements DelegateItem {

    TabLayoutModel value;

    public TabLayoutDelegateItem(TabLayoutModel value) {
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
