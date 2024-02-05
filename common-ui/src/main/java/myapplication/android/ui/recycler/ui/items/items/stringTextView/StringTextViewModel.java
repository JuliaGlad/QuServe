package myapplication.android.ui.recycler.ui.items.items.stringTextView;

public class StringTextViewModel {
    int id;
    int textSize;
    String text;
    int alignment;

    public StringTextViewModel(int id, String text, int textSize, int alignment){
        this.id = id;
        this.text = text;
        this.alignment = alignment;
        this.textSize = textSize;
    }
}
