package myapplication.android.ui.recycler.ui.items.items.textView;


import myapplication.android.ui.recycler.delegate.DelegateItem;

public class TextViewHeaderDelegateItem implements DelegateItem {

    private TextViewHeaderModel value;

    public TextViewHeaderDelegateItem(TextViewHeaderModel value){
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
        return this.value == ((TextViewHeaderDelegateItem) obj).value;
    }

    @Override
    public boolean compareToOther(DelegateItem other) {
        return other.content() == content();
    }
}
