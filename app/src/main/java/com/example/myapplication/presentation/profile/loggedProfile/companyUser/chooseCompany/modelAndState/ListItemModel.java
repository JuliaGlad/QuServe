package com.example.myapplication.presentation.profile.loggedProfile.companyUser.chooseCompany.modelAndState;

import com.example.myapplication.domain.model.common.ImageTaskModel;
import com.example.myapplication.domain.model.company.CompanyNameIdModel;

import java.util.List;

public class ListItemModel {
    private final List<CompanyListModel> companyModels;
    private final List<ImageTaskModel> imageTaskModels;

    public ListItemModel(List<CompanyListModel> companyModels, List<ImageTaskModel> imageTaskModels) {
        this.companyModels = companyModels;
        this.imageTaskModels = imageTaskModels;
    }

    public List<CompanyListModel> getCompanyModels() {
        return companyModels;
    }

    public List<ImageTaskModel> getImageTaskModels() {
        return imageTaskModels;
    }

}
