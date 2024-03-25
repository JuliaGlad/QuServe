package com.example.myapplication.domain.usecase.companyQueue.company;

import android.util.Log;

import com.example.myapplication.DI;
import com.example.myapplication.domain.model.company.CompanyNameAndEmailModel;

import java.util.Objects;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GetSingleCompanyUseCase {
    public Single<CompanyNameAndEmailModel> invoke(String companyId){

        return DI.companyUserRepository.getCompany().flatMap(companyDtos ->
                Single.just(Objects.requireNonNull(companyDtos
                        .stream()
                        .filter(companyDto -> companyDto.getId().equals(companyId))
                        .map(companyDto -> new CompanyNameAndEmailModel(companyDto.getCompanyName(), companyDto.getCompanyEmail()))
                        .findFirst()
                        .orElse(null))));
    }
}
