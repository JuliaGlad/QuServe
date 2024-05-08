package com.example.myapplication.presentation.home.recycler.homeDelegates.squareButton;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class SquareButtonDelegateItem implements DelegateItem<SquareButtonModel> {

    SquareButtonModel value;

    public SquareButtonDelegateItem(SquareButtonModel value) {
        this.value = value;
    }

    @Override
    public SquareButtonModel content() {
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
