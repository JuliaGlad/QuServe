package myapplication.android.ui.recycler.ui.items.items.floatingActionButton;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class FloatingActionButtonDelegateItem implements DelegateItem {

    private FloatingActionButtonModel value;

    public FloatingActionButtonDelegateItem(FloatingActionButtonModel value){
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
    public boolean equals(Object obj) {
        return this.value == ((FloatingActionButtonDelegateItem) obj).value;
    }

    @Override
    public boolean compareToOther(DelegateItem other) {
        return other.content() == content();
    }
}
