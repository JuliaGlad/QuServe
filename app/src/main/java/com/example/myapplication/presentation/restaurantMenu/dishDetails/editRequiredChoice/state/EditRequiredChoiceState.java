package com.example.myapplication.presentation.restaurantMenu.dishDetails.editRequiredChoice.state;

import com.example.myapplication.presentation.restaurantMenu.dishDetails.editRequiredChoice.model.EditRequireChoiceModel;

import java.util.List;

public interface EditRequiredChoiceState {
    class Success implements EditRequiredChoiceState{
        public EditRequireChoiceModel data;

        public Success(EditRequireChoiceModel data) {
            this.data = data;
        }
    }
    class Loading implements EditRequiredChoiceState{}

    class Error implements EditRequiredChoiceState{}
}
