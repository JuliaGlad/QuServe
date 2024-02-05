package myapplication.android.ui.recycler.ui.items.items.floatingActionButton;

import myapplication.android.ui.listeners.FloatingActionButtonItemListener;

public class FloatingActionButtonModel {
    int id;
    FloatingActionButtonItemListener listener;

    public FloatingActionButtonModel(int id, FloatingActionButtonItemListener listener){
        this.id = id;
        this.listener = listener;
    }
}
