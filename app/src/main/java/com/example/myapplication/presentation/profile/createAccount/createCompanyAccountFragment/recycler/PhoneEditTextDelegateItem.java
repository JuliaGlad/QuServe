package com.example.myapplication.presentation.profile.createAccount.createCompanyAccountFragment.recycler;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class PhoneEditTextDelegateItem implements DelegateItem<PhoneEditTextModel> {

    PhoneEditTextModel value;

    public PhoneEditTextDelegateItem(PhoneEditTextModel value) {
        this.value = value;
    }

    @Override
    public PhoneEditTextModel content() {
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
