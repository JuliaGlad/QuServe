package myapplication.android.ui.recycler.ui.items.items.button;

import myapplication.android.ui.listeners.ButtonItemListener;

public class ButtonModel {
    int id;
    int text;
    ButtonItemListener clickListener;

    public ButtonModel(int id, int text, ButtonItemListener clickListener) {
        this.id = id;
        this.text = text;
        this.clickListener = clickListener;
    }
}
