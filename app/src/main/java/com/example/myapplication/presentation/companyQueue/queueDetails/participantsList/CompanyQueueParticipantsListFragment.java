package com.example.myapplication.presentation.companyQueue.queueDetails.participantsList;

import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_ID;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentParticipantsListBinding;

import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.participantListItem.ParticipantListDelegate;
import myapplication.android.ui.recycler.ui.items.items.statisticsDelegate.StatisticsDelegate;
import myapplication.android.ui.recycler.ui.items.items.stringTextView.StringTextViewDelegate;

public class CompanyQueueParticipantsListFragment extends Fragment {

    private CompanyQueueParticipantsListViewModel viewModel;
    private FragmentParticipantsListBinding binding;
    private final MainAdapter mainAdapter = new MainAdapter();
    private String queueId, companyId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        queueId = getArguments().getString(QUEUE_ID);
        companyId = getArguments().getString(COMPANY_ID);

        viewModel = new ViewModelProvider(this).get(CompanyQueueParticipantsListViewModel.class);
        binding = FragmentParticipantsListBinding.inflate(inflater, container, false);
        viewModel.getParticipantsList(this, queueId, companyId);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setMainAdapter();
        setupObserves();
        initBackButton();
        initNextParticipantButton();
    }

    private void initNextParticipantButton() {
        binding.nextButton.setOnClickListener(v -> {
            viewModel.nextParticipant(queueId, companyId);
        });
    }

    private void initBackButton() {
        binding.imageButtonBack.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).popBackStack();
        });
    }

    private void setMainAdapter(){
        mainAdapter.addDelegate(new ParticipantListDelegate());
        mainAdapter.addDelegate(new StatisticsDelegate());
        mainAdapter.addDelegate(new StringTextViewDelegate());
        binding.recyclerView.setAdapter(mainAdapter);
    }

    private void setupObserves(){
        viewModel.item.observe(getViewLifecycleOwner(), mainAdapter::submitList);
    }

}