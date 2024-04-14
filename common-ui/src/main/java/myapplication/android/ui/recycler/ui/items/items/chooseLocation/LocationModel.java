package myapplication.android.ui.recycler.ui.items.items.chooseLocation;

import myapplication.android.ui.listeners.ButtonItemListener;

public class LocationModel {
    int id;
    String location;
    ButtonItemListener listener;

    public LocationModel(int id, String location, ButtonItemListener listener) {
        this.id = id;
        this.location = location;
        this.listener = listener;
    }
}
