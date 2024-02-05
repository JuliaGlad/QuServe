package myapplication.android.ui.recycler.delegate;

public interface DelegateItem<T> {
    T content();

    int id();

    boolean compareToOther(DelegateItem other);
}
