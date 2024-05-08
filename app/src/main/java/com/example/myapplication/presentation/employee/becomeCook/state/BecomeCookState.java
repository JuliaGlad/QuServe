package com.example.myapplication.presentation.employee.becomeCook.state;

public interface BecomeCookState {
    class Success implements BecomeCookState{

        public BecomeCookStateModel data;

        public Success(BecomeCookStateModel data) {
            this.data = data;
        }
    }
    class Loading implements BecomeCookState{}

    class Error implements BecomeCookState{}
}
