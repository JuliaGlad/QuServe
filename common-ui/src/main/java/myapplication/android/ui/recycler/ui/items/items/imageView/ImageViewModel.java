package myapplication.android.ui.recycler.ui.items.items.imageView;

import android.net.Uri;

public class ImageViewModel {
    int id;
    Uri uri;

    public ImageViewModel(int id, Uri uri) {
        this.id = id;
        this.uri = uri;
    }
}
