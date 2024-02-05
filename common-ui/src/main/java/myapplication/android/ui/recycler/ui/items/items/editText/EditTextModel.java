package myapplication.android.ui.recycler.ui.items.items.editText;


import myapplication.android.ui.listeners.ResultListener;

public class EditTextModel{
    public int inputType;
    int id;
    int hint;
    String text;
    boolean editable;
    ResultListener<String> resultListener;

    public EditTextModel(int id, int hint, String text, int inputType, boolean editable, ResultListener<String> resultListener) {
        this.id = id;
        this.hint = hint;
        this.inputType = inputType;
        this.editable = editable;
        this.text = text;
        this.resultListener = resultListener;
    }

}