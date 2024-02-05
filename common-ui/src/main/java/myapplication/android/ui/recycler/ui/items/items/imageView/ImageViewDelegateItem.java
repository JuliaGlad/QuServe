package myapplication.android.ui.recycler.ui.items.items.imageView;


import myapplication.android.ui.recycler.delegate.DelegateItem;

public class ImageViewDelegateItem implements DelegateItem {

    private ImageViewModel value;

    public ImageViewDelegateItem(ImageViewModel value){
        this.value = value;
    }

    @Override
    public Object content() {
        return value;
    }

    @Override
    public int id() {
        return value.hashCode();
    }


    @Override
    public boolean equals(Object obj) {
        return this.value == ((ImageViewDelegateItem) obj).value;
    }

    @Override
    public boolean compareToOther(DelegateItem other) {
        return other.content() == content();
    }
}
