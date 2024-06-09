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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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
        initCloseButton();
    }

    private void initCloseButton() {
        binding.buttonBack.setOnClickListener(v -> {
            requireActivity().finish();
        });
    }

    private void setupObserves() {

        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof HistoryState.Success) {
                List<HistoryItemModel> models = ((HistoryState.Success) state).data;

                if (!models.isEmpty()) {

                    for (int i = 0; i < models.size(); i++) {
                        String date = models.get(i).getDate();
                        if (!dates.contains(date))
                            dates.add(date);
                    }

                    for (int i = 0; i < dates.size(); i++) {
                        String currentDate = dates.get(i);
                        delegates.add(new DateDelegateItem(new DateModel(i, currentDate)));
                        for (int j = 0; j < models.size(); j++) {
                            if (currentDate.equals(models.get(j).getDate())) {
                                delegates.add(new HistoryDelegateItem(new HistoryDelegateModel(
                                        j,
                                        models.get(j).getName(),
                                        models.get(j).getTime(),
                                        models.get(j).getService()
                                )));
                            }
                        }
                    }
                    adapter.submitList(delegates);
                } else {
                   initEmptyLayout();
                }
                binding.errorLayout.getRoot().setVisibility(View.GONE);
                binding.progressBar.getRoot().setVisibility(View.GONE);

            } else if (state instanceof HistoryState.Loading) {
                binding.progressBar.getRoot().setVisibility(View.VISIBLE);

            } else if (state instanceof HistoryState.Error) {
                binding.errorLayout.getRoot().setVisibility(View.VISIBLE);
            }
        });
    }

    private void initEmptyLayout() {
        binding.emptyLayout.getRoot().setVisibility(View.VISIBLE);
        binding.emptyLayout.title.setText(getString(R.string.you_don_t_have_a_history_of_your_previous_action_yet));
        binding.emptyLayout.infoBox.getRoot().setVisibility(View.GONE);
        binding.emptyLayout.buttonAdd.setVisibility(View.GONE);
    }

    private void setAdapter() {
        adapter.addDelegate(new HistoryDelegate());
        adapter.addDelegate(new DateDelegate());
        binding.recyclerView.setAdapter(adapter);
    }
}