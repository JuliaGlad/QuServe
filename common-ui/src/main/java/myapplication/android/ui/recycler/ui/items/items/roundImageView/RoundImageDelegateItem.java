package myapplication.android.ui.recycler.ui.items.items.roundImageView;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class RoundImageDelegateItem implements DelegateItem {

    RoundImageViewModel value;

    public RoundImageDelegateItem(RoundImageViewModel value) {
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
