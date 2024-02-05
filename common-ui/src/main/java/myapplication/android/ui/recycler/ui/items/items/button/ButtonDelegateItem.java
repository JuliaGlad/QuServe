package myapplication.android.ui.recycler.ui.items.items.button;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class ButtonDelegateItem implements DelegateItem {
    private ButtonModel value;

    public ButtonDelegateItem( ButtonModel value) {
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
