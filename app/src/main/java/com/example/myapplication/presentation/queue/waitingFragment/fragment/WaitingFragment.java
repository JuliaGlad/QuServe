package com.example.myapplication.presentation.queue.waitingFragment.fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.databinding.FragmentWaitingBinding;
import com.example.myapplication.presentation.queue.waitingFragment.fragment.recycler.items.leaveQueueItem.LeaveQueueDelegate;
import com.example.myapplication.presentation.queue.waitingFragment.fragment.recycler.items.waitingDelegateItem.WaitingItemDelegate;

import java.util.List;

import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.stringTextView.StringTextViewDelegate;

public class WaitingFragment extends Fragment {

    private WaitingViewModel viewModel;
    private FragmentWaitingBinding binding;
    private MainAdapter mainAdapter = new MainAdapter();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(WaitingViewModel.class);
        binding = FragmentWaitingBinding.inflate(inflater, container, false);
        viewModel.getQueue(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupObserves();
        setMainAdapter();
        initCloseButton();
    }

    private void initCloseButton() {
        binding.imageButtonClose.setOnClickListener(v -> requireActivity().finish());
    }

    private void setMainAdapter(){
        mainAdapter.addDelegate(new StringTextViewDelegate());
        mainAdapter.addDelegate(new WaitingItemDelegate());
        mainAdapter.addDelegate(new LeaveQueueDelegate());
        binding.recycler.setAdapter(mainAdapter);
    }

    private void setupObserves(){
        viewModel.items.observe(getViewLifecycleOwner(), mainAdapter::submitList);
    }

}