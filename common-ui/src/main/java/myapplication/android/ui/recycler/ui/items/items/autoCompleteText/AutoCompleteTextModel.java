package myapplication.android.ui.recycler.ui.items.items.autoCompleteText;

import myapplication.android.ui.listeners.ResultListener;

public class AutoCompleteTextModel {
    int id;
    int stringArray;
    int defaultValue;
    ResultListener<String> listener;

    public AutoCompleteTextModel( int id, int stringArray, int defaultValue, ResultListener<String> listener) {
        this.id = id;
        this.stringArray = stringArray;
        this.defaultValue = defaultValue;
        this.listener = listener;
    }
}
