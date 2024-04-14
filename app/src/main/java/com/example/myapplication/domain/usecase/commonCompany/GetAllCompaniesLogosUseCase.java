package com.example.myapplication.domain.usecase.commonCompany;

import com.example.myapplication.di.DI;
import com.example.myapplication.domain.model.common.ImageTaskModel;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public class GetAllCompaniesLogosUseCase {
    public Single<List<ImageTaskModel>> invoke() {
        return DI.commonCompanyRepository.getAllCompaniesLogos().map(tasks ->
                tasks.stream()
                        .map(ImageTaskModel::new)
                        .collect(Collectors.toList()));
    }
}
