package myapplication.android.ui.recycler.ui.items.items.adviseBox;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class AdviseBoxDelegateItem implements DelegateItem {

    AdviseBoxModel value;

    public AdviseBoxDelegateItem(AdviseBoxModel value) {
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
