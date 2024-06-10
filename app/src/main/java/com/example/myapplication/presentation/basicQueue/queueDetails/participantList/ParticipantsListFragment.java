package com.example.myapplication.presentation.basicQueue.queueDetails.participantList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentParticipantsListBinding;
import com.example.myapplication.presentation.basicQueue.queueDetails.participantList.state.ParticipantsListState;

import java.util.ArrayList;
import java.util.List;

import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.categoryItem.CategoryItemDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.categoryItem.CategoryItemModel;
import myapplication.android.ui.recycler.ui.items.items.participantListItem.ParticipantListDelegate;
import myapplication.android.ui.recycler.ui.items.items.participantListItem.ParticipantListDelegateItem;
import myapplication.android.ui.recycler.ui.items.items.participantListItem.ParticipantListModel;
import myapplication.android.ui.recycler.ui.items.items.statisticsDelegate.StatisticsDelegate;
import myapplication.android.ui.recycler.ui.items.items.stringTextView.StringTextViewDelegate;

public class ParticipantsListFragment extends Fragment {

    private ParticipantsListViewModel viewModel;
    private FragmentParticipantsListBinding binding;
    private final List<DelegateItem> itemsList = new ArrayList<>();
    private final MainAdapter mainAdapter = new MainAdapter();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentParticipantsListBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(ParticipantsListViewModel.class);
        viewModel.getParticipantsList();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
        setMainAdapter();
        initBackButton();
        initNextParticipantButton();
    }

    private void initNextParticipantButton() {
        binding.nextButton.setOnClickListener(v -> {
            viewModel.nextParticipant();
        });
    }

    private void initBackButton() {
        binding.imageButtonBack.setOnClickListener(v -> {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_participantsListFragment_to_detailsQueueFragment);
        });
    }

    private void setMainAdapter(){
        mainAdapter.addDelegate(new ParticipantListDelegate());
        mainAdapter.addDelegate(new StatisticsDelegate());
        mainAdapter.addDelegate(new StringTextViewDelegate());
        binding.recyclerView.setAdapter(mainAdapter);
    }

    private void setupObserves(){

        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof ParticipantsListState.Success){
                List<String> participants = ((ParticipantsListState.Success)state).data;
                initRecyclerView(participants.size());

            } else if (state instanceof  ParticipantsListState.Loading){
                binding.progressBar.getRoot().setVisibility(View.VISIBLE);

            } else if (state instanceof ParticipantsListState.Error){
                binding.progressBar.getRoot().setVisibility(View.GONE);
                binding.errorLayout.getRoot().setVisibility(View.VISIBLE);
                binding.errorLayout.buttonTryAgain.setOnClickListener(v -> {
                    viewModel.getParticipantsList();
                });
            }
        });

        viewModel.addParticipant.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){
                int number = itemsList.size() + 1;
                itemsList.add(new ParticipantListDelegateItem(new ParticipantListModel(itemsList.size() + 1, requireContext().getString(R.string.participant), number)));
                mainAdapter.notifyItemInserted(itemsList.size() - 1);
            }
        });

        viewModel.removeParticipant.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){
                itemsList.remove(0);
                mainAdapter.notifyItemRemoved(0);
                for (int i = 0; i < itemsList.size(); i++) {
                    if (itemsList.get(i) instanceof ParticipantListDelegateItem){
                        ParticipantListModel currentModel = (ParticipantListModel) itemsList.get(i).content();
                        currentModel.setNumber(i + 1);
                        mainAdapter.notifyItemChanged(i);
                    }
                }
            }
        });

        viewModel.item.observe(getViewLifecycleOwner(), mainAdapter::submitList);
    }

    private void initRecyclerView(int participantsSize) {
        addParticipantListDelegateItems(participantsSize);
        mainAdapter.submitList(itemsList);
        binding.progressBar.getRoot().setVisibility(View.GONE);
        binding.errorLayout.getRoot().setVisibility(View.GONE);
    }

    private void addParticipantListDelegateItems(int queueLength) {
        if (queueLength != 0) {
            for (int i = 0; i < queueLength; i++) {
                itemsList.add(new ParticipantListDelegateItem(new ParticipantListModel(i, requireContext().getString(R.string.participant), i + 1)));
            }
        } else {
            binding.noParticipants.setVisibility(View.VISIBLE);
        }
    }
}