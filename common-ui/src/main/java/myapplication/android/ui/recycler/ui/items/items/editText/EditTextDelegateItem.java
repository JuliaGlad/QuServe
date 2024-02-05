package myapplication.android.ui.recycler.ui.items.items.editText;


import myapplication.android.ui.recycler.delegate.DelegateItem;

public class EditTextDelegateItem implements DelegateItem {

    private EditTextModel value;

    public EditTextDelegateItem(EditTextModel value) {
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
        return this.value == ((EditTextDelegateItem) obj).value;
    }

    @Override
    public boolean compareToOther(DelegateItem other) {
        return other.content() == content();
    }
}
