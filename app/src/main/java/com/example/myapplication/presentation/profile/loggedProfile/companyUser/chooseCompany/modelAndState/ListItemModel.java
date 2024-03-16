package com.example.myapplication.presentation.profile.loggedProfile.companyUser.chooseCompany.modelAndState;

import com.example.myapplication.domain.model.common.ImageTaskModel;
import com.example.myapplication.domain.model.company.CompanyNameIdModel;

import java.util.List;

public class ListItemModel {
    private List<CompanyNameIdModel> companyNameIdModels;
    private List<ImageTaskModel> imageTaskModels;

    public ListItemModel(List<CompanyNameIdModel> companyNameIdModels, List<ImageTaskModel> imageTaskModels) {
        this.companyNameIdModels = companyNameIdModels;
        this.imageTaskModels = imageTaskModels;
    }

    public List<CompanyNameIdModel> getCompanyNameIdModels() {
        return companyNameIdModels;
    }

    public List<ImageTaskModel> getImageTaskModels() {
        return imageTaskModels;
    }

}
