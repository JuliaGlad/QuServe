package myapplication.android.ui.recycler.ui.items.items.buttonWithDescription;

import myapplication.android.ui.listeners.ButtonItemListener;

public class ButtonWithDescriptionModel {
    int id;
    String title;
    String description;
    int drawable;
    ButtonItemListener listener;

    public ButtonWithDescriptionModel(int id, String title, String description, int drawable, ButtonItemListener listener) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.drawable = drawable;
        this.listener = listener;
    }
}
