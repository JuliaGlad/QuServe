package myapplication.android.ui.recycler.ui.items.items.imageDrawable;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class ImageViewDrawableDelegateItem implements DelegateItem<ImageViewDrawableModel> {

    ImageViewDrawableModel value;

    public ImageViewDrawableDelegateItem(ImageViewDrawableModel value) {
        this.value = value;
    }

    @Override
    public ImageViewDrawableModel content() {
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
