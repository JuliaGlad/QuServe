package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.di.profile.ProfileDI;
import com.example.myapplication.domain.model.profile.HistoryModel;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public class GetHistoryUseCase {

    public Single<List<HistoryModel>> invoke() {
        return ProfileDI.profileRepository.getHistoryList().map(historyQueueDtos ->
                historyQueueDtos.stream()
                        .map(historyQueueDto -> new HistoryModel(
                                historyQueueDto.getDate(),
                                historyQueueDto.getService(),
                                historyQueueDto.getTime(),
                                historyQueueDto.getName()
                        )).collect(Collectors.toList()));

    }
}
