package com.example.myapplication.presentation.home.stories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

public class StoriesViewModel extends ViewModel {

    private final MutableLiveData<Integer> _page = new MutableLiveData<>(0);
    LiveData<Integer> page = _page;

    public void onNext(int stories) {
        Integer current = _page.getValue();
        if (current != null) {
            if (current != stories) {
                _page.postValue(current + 1);
            }
        }
    }

    public int getPage(){
        return Objects.requireNonNull(_page.getValue());
    }

    public void onPrev() {
        Integer current = _page.getValue();
        if (current != null) {
            if (current != 0) {
                _page.postValue(current - 1);
            }
        }
    }

}