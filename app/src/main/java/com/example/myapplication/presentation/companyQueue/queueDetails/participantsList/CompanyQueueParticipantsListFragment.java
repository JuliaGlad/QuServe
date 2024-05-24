package com.example.myapplication.presentation.companyQueue.queueDetails.participantsList;

import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_ID;

import androidx.activity.OnBackPressedCallback;
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
import com.example.myapplication.presentation.companyQueue.queueDetails.participantsList.state.CompanyParticipantsState;

import java.util.ArrayList;
import java.util.List;

import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.participantListItem.ParticipantListDelegate;
import myapplication.android.ui.recycler.ui.items.items.participantListItem.ParticipantListDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.participantListItem.ParticipantListModel;
import myapplication.android.ui.recycler.ui.items.items.statisticsDelegate.StatisticsDelegate;
import myapplication.android.ui.recycler.ui.items.items.statisticsDelegate.StatisticsDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.statisticsDelegate.StatisticsModel;
import myapplication.android.ui.recycler.ui.items.items.stringTextView.StringTextViewDelegate;
import myapplication.android.ui.recycler.ui.items.items.stringTextView.StringTextViewDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.stringTextView.StringTextViewModel;

public class CompanyQueueParticipantsListFragment extends Fragment {

    private CompanyQueueParticipantsListViewModel viewModel;
    private FragmentParticipantsListBinding binding;
    private final MainAdapter mainAdapter = new MainAdapter();
    private List<DelegateItem> itemsList = new ArrayList<>();
    private String queueId, companyId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        queueId = getArguments().getString(QUEUE_ID);
        companyId = getArguments().getString(COMPANY_ID);

        viewModel = new ViewModelProvider(this).get(CompanyQueueParticipantsListViewModel.class);
        binding = FragmentParticipantsListBinding.inflate(inflater, container, false);
        viewModel.getParticipantsList(queueId, companyId);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setMainAdapter();
        setupObserves();
        handleButtonBackPressed();
        initBackButton();
        initNextParticipantButton();
    }

    private void handleButtonBackPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                NavHostFragment.findNavController(CompanyQueueParticipantsListFragment.this).popBackStack();
            }
        });
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

    private void setMainAdapter() {
        mainAdapter.addDelegate(new ParticipantListDelegate());
        mainAdapter.addDelegate(new StringTextViewDelegate());
        binding.recyclerView.setAdapter(mainAdapter);
    }

    private void addParticipantListDelegateItems(int queueLength) {
        if (queueLength != 0) {
            for (int i = 0; i < queueLength; i++) {
                itemsList.add(new ParticipantListDelegateItem(new ParticipantListModel(i, requireContext().getString(R.string.participant))));
            }
        } else {
            binding.noParticipants.setVisibility(View.VISIBLE);
        }
    }

    private void initRecycler(int participantsSize) {
        itemsList.add(new StatisticsDelegateItem(new StatisticsModel(1)));
        if (participantsSize != 0) {
            addParticipantListDelegateItems(participantsSize);
        } else {
            binding.noParticipants.setVisibility(View.GONE);
        }
        mainAdapter.submitList(itemsList);
        binding.progressBar.getRoot().setVisibility(View.GONE);
        binding.errorLayout.getRoot().setVisibility(View.GONE);
    }

    private void setupObserves() {

        viewModel.addParticipant.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                List<DelegateItem> newItems = new ArrayList<>(itemsList);
                newItems.add(new ParticipantListDelegateItem(new ParticipantListModel(itemsList.size() + 1, requireContext().getString(R.string.participant))));
                mainAdapter.submitList(newItems);
                itemsList = newItems;
            }
        });

        viewModel.removeParticipant.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                List<DelegateItem> newItems = new ArrayList<>(itemsList);
                newItems.remove(1);
                mainAdapter.submitList(newItems);
                itemsList = newItems;
            }
        });

        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof CompanyParticipantsState.Success) {
                List<String> participants = ((CompanyParticipantsState.Success) state).data;
                initRecycler(participants.size());

            } else if (state instanceof CompanyParticipantsState.Loading) {
                binding.progressBar.getRoot().setVisibility(View.VISIBLE);

            } else if (state instanceof CompanyParticipantsState.Error) {
                setErrorLayout();
            }
        });

    }

    private void setErrorLayout() {
        binding.progressBar.getRoot().setVisibility(View.GONE);
        binding.errorLayout.getRoot().setVisibility(View.VISIBLE);
        binding.errorLayout.buttonTryAgain.setOnClickListener(v -> {
            viewModel.getParticipantsList(queueId, companyId);
        });
    }

}