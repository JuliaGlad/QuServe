package myapplication.android.ui.recycler.ui.items.items.roundImageView;

import android.net.Uri;
import android.view.View;

import myapplication.android.ui.listeners.ImageClickListener;

public class RoundImageViewModel {
    int id;
    Uri uri;
    ImageClickListener listener;

    public RoundImageViewModel(int id, Uri uri, ImageClickListener listener) {
        this.id = id;
        this.uri = uri;
        this.listener = listener;
    }

    public ImageClickListener getListener() {
        return listener;
    }

    public int getId() {
        return id;
    }

    public Uri getUri() {
        return uri;
    }
}
