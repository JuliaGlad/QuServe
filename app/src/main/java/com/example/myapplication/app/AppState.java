package com.example.myapplication.app;

public interface AppState {
    class BasicState implements AppState{}

    class CompanyState implements AppState{
        public String companyId;

        CompanyState(String companyId){
            this.companyId = companyId;
        }
    }
}
