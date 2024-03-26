package com.example.myapplication.presentation.profile.loggedProfile.basicUser.historyProfile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentHistoryBinding;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.historyProfile.delegates.date.DateDelegate;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.historyProfile.delegates.date.DateDelegateItem;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.historyProfile.delegates.date.DateModel;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.historyProfile.delegates.history.HistoryDelegate;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.historyProfile.delegates.history.HistoryDelegateItem;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.historyProfile.delegates.history.HistoryDelegateModel;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.historyProfile.models.HistoryItemModel;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.historyProfile.models.HistoryState;

import java.util.ArrayList;
import java.util.List;

import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.delegate.MainAdapter;

public class HistoryFragment extends Fragment {

    private HistoryFragmentViewModel viewModel;
    private FragmentHistoryBinding binding;
    private final MainAdapter adapter = new MainAdapter();
    private final List<String> dates = new ArrayList<>();
    private final List<DelegateItem> delegates = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HistoryFragmentViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel.getHistoryData();
        binding = FragmentHistoryBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAdapter();
        setupObserves();
    }

    private void setupObserves() {

        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof HistoryState.Success){
               List<HistoryItemModel> model = ((HistoryState.Success) state).data;

                for (int i = 0; i < model.size(); i++) {
                    String date = model.get(i).getDate();
                    if (!dates.contains(date))
                        dates.add(date);
                }

                for (int i = 0; i < dates.size(); i++) {
                    delegates.add(new DateDelegateItem(new DateModel(i, dates.get(i))));
                    for (int j = 0; j < model.size(); j++) {
                        if (dates.get(i).equals(model.get(j).getDate())){
                            delegates.add(new HistoryDelegateItem(new HistoryDelegateModel(
                                    j,
                                    model.get(j).getName(),
                                    model.get(j).getTime()
                            )));
                        }
                    }
                }
                adapter.submitList(delegates);
                binding.progressBar.setVisibility(View.GONE);

            } else if (state instanceof HistoryState.Loading){
                binding.progressBar.setVisibility(View.VISIBLE);

            } else if (state instanceof HistoryState.Error){

            }
        });
    }

    private void setAdapter() {
        adapter.addDelegate(new HistoryDelegate());
        adapter.addDelegate(new DateDelegate());
        binding.recyclerView.setAdapter(adapter);
    }
}