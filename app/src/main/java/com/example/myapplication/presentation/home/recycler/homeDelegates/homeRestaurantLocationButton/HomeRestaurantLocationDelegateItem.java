package com.example.myapplication.presentation.home.recycler.homeDelegates.homeRestaurantLocationButton;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class HomeRestaurantLocationDelegateItem implements DelegateItem<HomeRestaurantLocationModel> {

    HomeRestaurantLocationModel value;

    public HomeRestaurantLocationDelegateItem(HomeRestaurantLocationModel value) {
        this.value = value;
    }

    @Override
    public HomeRestaurantLocationModel content() {
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
