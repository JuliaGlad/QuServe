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

import java.util.ArrayList;
import java.util.List;

import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.delegate.MainAdapter;

public class HistoryFragment extends Fragment {

    private HistoryFragmentViewModel viewModel;
    private FragmentHistoryBinding binding;
    private final MainAdapter adapter = new MainAdapter();

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
        viewModel.items.observe(getViewLifecycleOwner(), adapter::submitList);
    }

    private void setAdapter() {
        adapter.addDelegate(new HistoryDelegate());
        adapter.addDelegate(new DateDelegate());
        binding.recyclerView.setAdapter(adapter);
    }
}