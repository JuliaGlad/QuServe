package myapplication.android.ui.recycler.ui.items.items.autoCompleteText;

import myapplication.android.ui.listeners.ResultListener;

public class AutoCompleteTextModel {
    int id;
    int stringArray;
    int hint;
    ResultListener<String> listener;

    public AutoCompleteTextModel( int id, int stringArray, int hint, ResultListener<String> listener) {
        this.id = id;
        this.stringArray = stringArray;
        this.hint = hint;
        this.listener = listener;
    }
}
