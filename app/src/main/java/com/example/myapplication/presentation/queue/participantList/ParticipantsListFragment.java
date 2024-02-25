package com.example.myapplication.presentation.queue.participantList;

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
import com.example.myapplication.presentation.queue.participantList.participantListItem.ParticipantListDelegate;

import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.stringTextView.StringTextViewDelegate;

public class ParticipantsListFragment extends Fragment {

    private ParticipantsListViewModel viewModel;
    private FragmentParticipantsListBinding binding;
    private final MainAdapter mainAdapter = new MainAdapter();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentParticipantsListBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(ParticipantsListViewModel.class);

        viewModel.getParticipantsList(this);

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
            viewModel.nextParticipant(getView());
        });
    }

    private void initBackButton() {
        binding.imageButtonBack.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_participantsListFragment_to_detailsQueueFragment);
        });
    }

    private void setMainAdapter(){
        mainAdapter.addDelegate(new ParticipantListDelegate());
        mainAdapter.addDelegate(new StringTextViewDelegate());
        binding.recyclerView.setAdapter(mainAdapter);
    }

    private void setupObserves(){
        viewModel.item.observe(getViewLifecycleOwner(), mainAdapter::submitList);
    }
}