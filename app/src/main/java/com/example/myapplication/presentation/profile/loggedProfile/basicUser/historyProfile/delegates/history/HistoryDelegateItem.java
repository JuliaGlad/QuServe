package com.example.myapplication.presentation.profile.loggedProfile.basicUser.historyProfile.delegates.history;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class HistoryDelegateItem implements DelegateItem<HistoryDelegateModel> {

    HistoryDelegateModel value;

    public HistoryDelegateItem(HistoryDelegateModel value) {
        this.value = value;
    }

    @Override
    public HistoryDelegateModel content() {
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
