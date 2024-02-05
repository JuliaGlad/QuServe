package myapplication.android.ui.recycler.ui.items.items.autoCompleteText;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class AutoCompleteTextDelegateItem implements DelegateItem {
    private AutoCompleteTextModel value;

    public AutoCompleteTextDelegateItem(AutoCompleteTextModel value) {
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
