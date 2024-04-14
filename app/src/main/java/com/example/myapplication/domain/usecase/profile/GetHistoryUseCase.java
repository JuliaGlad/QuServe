package com.example.myapplication.domain.usecase.profile;

import com.example.myapplication.di.DI;
import com.example.myapplication.domain.model.profile.HistoryModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class GetHistoryUseCase {

    public Single<List<HistoryModel>> invoke() {
        List<HistoryModel> list = new ArrayList<>();
        return DI.profileRepository.getHistoryList().map(historyQueueDtos -> {
            for (int i = 0; i < historyQueueDtos.size(); i++) {
                list.add(new HistoryModel(
                        historyQueueDtos.get(i).getDate(),
                        historyQueueDtos.get(i).getTime(),
                        historyQueueDtos.get(i).getName()
                ));
            }
            return list;
        });

    }
}
