package myapplication.android.ui.recycler.ui.items.items.queueDetailsButton;

import myapplication.android.ui.listeners.QueueDetailButtonItemListener;

public class QueueDetailButtonModel {
    int id;
    int title;
    int description;
    int drawable;
     QueueDetailButtonItemListener listener;

    public QueueDetailButtonModel(int id, int title, int description, int drawable, QueueDetailButtonItemListener listener){
        this.id = id;
        this.title = title;
        this.drawable = drawable;
        this.description = description;
        this.listener = listener;
    }
}
