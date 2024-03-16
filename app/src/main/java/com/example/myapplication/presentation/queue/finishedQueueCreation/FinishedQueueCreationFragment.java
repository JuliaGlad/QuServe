package com.example.myapplication.presentation.queue.finishedQueueCreation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentFinishedQueueCreationBinding;
import com.example.myapplication.presentation.queue.basic.createQueue.arguments.Arguments;
import com.example.myapplication.presentation.queue.basic.createQueue.CreateQueueActivity;

public class FinishedQueueCreationFragment extends Fragment {

    private FinishedQueueCreationViewModel viewModel;
    private FragmentFinishedQueueCreationBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(requireActivity()).get(FinishedQueueCreationViewModel.class);

        binding = FragmentFinishedQueueCreationBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupObserves();
        initInfoBox();
        viewModel.addTimeCounterWorker(view);
        viewModel.delayQueueFinish(view);
        viewModel.delayPauseAvailable(view);
        viewModel.getQrCode(Arguments.queueID);
        initSeeDetails();

    }

    private void initInfoBox() {
        binding.infoLayout.body.setText(R.string.show_this_qr_code_to_people_who_are_looking_forward_to_join_your_queue);
    }

    private void initSeeDetails(){
        binding.buttonSeeDetails.setOnClickListener(view -> {
            ((CreateQueueActivity)requireActivity()).openQueueDetailsActivity();
        });
    }

    private void setupObserves(){
        viewModel.image.observe(getViewLifecycleOwner(), uri-> {
                Glide.with(this).load(uri).into(binding.qrCodeImage);
            });
    }
}