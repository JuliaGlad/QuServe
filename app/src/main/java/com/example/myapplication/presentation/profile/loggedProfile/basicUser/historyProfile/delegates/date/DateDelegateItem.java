package com.example.myapplication.presentation.profile.loggedProfile.basicUser.historyProfile.delegates.date;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class DateDelegateItem implements DelegateItem<DateModel> {

    DateModel value;

    public DateDelegateItem(DateModel value) {
        this.value = value;
    }

    @Override
    public DateModel content() {
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
