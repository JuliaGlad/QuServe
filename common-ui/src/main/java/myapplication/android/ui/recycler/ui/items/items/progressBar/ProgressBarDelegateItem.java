package myapplication.android.ui.recycler.ui.items.items.progressBar;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class ProgressBarDelegateItem implements DelegateItem {

    private ProgressBarModel value;

    public ProgressBarDelegateItem(ProgressBarModel value) {
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
        return this.value == ((ProgressBarDelegateItem) obj).value;
    }

    @Override
    public boolean compareToOther(DelegateItem other) {
        return other.content() == content();
    }
}