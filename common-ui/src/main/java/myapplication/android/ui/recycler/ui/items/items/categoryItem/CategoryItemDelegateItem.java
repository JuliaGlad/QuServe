package myapplication.android.ui.recycler.ui.items.items.categoryItem;

import androidx.annotation.NonNull;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class CategoryItemDelegateItem implements DelegateItem<CategoryItemModel> {

    CategoryItemModel value;

    public CategoryItemDelegateItem(CategoryItemModel value) {
        this.value = value;
    }

    @Override
    public CategoryItemModel content() {
        return value;
    }

    public void setValue(CategoryItemModel value) {
        this.value = value;
    }

    @Override
    public int id() {
        return value.hashCode();
    }

    @Override
    public boolean compareToOther(DelegateItem other) {
        return ((CategoryItemModel)other.content()).equals(value);
    }

}
