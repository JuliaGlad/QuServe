package myapplication.android.ui.recycler.ui.items.items.searchView;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class SearchViewDelegateItem implements DelegateItem {

    SearchViewModel value;

    public SearchViewDelegateItem(SearchViewModel value) {
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
