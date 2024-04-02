package myapplication.android.ui.recycler.ui.items.items.buttonWithDescription;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class ButtonWithDescriptionDelegateItem implements DelegateItem<ButtonWithDescriptionModel> {

    ButtonWithDescriptionModel value;

    public ButtonWithDescriptionDelegateItem(ButtonWithDescriptionModel value) {
        this.value = value;
    }

    @Override
    public ButtonWithDescriptionModel content() {
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
