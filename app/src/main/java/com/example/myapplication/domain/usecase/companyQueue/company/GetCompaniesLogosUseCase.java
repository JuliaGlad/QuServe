package com.example.myapplication.domain.usecase.companyQueue.company;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.common.ImageTaskModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class GetCompaniesLogosUseCase {
    public Single<List<ImageTaskModel>> invoke(){
        List<ImageTaskModel> list = new ArrayList<>();
        return DI.companyUserRepository.getCompaniesLogos().map(imageDtos -> {
            for (int i = 0; i < imageDtos.size(); i++) {
                list.add(new ImageTaskModel(imageDtos.get(i)));
            }
            return list;
        });

    }
}
