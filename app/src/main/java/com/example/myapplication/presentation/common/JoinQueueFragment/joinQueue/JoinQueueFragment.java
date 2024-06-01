package com.example.myapplication.presentation.common.JoinQueueFragment.joinQueue;

import static com.example.myapplication.presentation.utils.constants.Utils.QUEUE_DATA;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.myapplication.databinding.FragmentJoinQueueBinding;
import com.example.myapplication.presentation.common.JoinQueueFragment.JoinQueueActivity;
import com.example.myapplication.presentation.common.JoinQueueFragment.joinQueue.model.JoinQueueModel;
import com.example.myapplication.presentation.common.JoinQueueFragment.joinQueue.state.JoinQueueState;
import com.example.myapplication.presentation.dialogFragments.wronQrCode.WrongQrCodeDialogFragment;

public class JoinQueueFragment extends Fragment {

    private JoinQueueViewModel viewModel;
    private FragmentJoinQueueBinding binding;
    private String queueData;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(JoinQueueViewModel.class);

        binding = FragmentJoinQueueBinding.inflate(inflater, container, false);
        queueData = requireActivity().getIntent().getStringExtra(QUEUE_DATA);
        viewModel.getQueueData(queueData);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUi();
    }

    private void initOkButton() {
        binding.buttonYes.setOnClickListener(view -> {
            viewModel.addToParticipantsList(queueData);
        });
    }

    private void initNoButton() {
        binding.buttonNo.setOnClickListener(v -> requireActivity().finish());
    }

    private void initUi(){
        setupObserves();
        initOkButton();
        initNoButton();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        viewModel = null;
    }

    private void setupObserves() {

        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof JoinQueueState.Success){
                JoinQueueModel model = ((JoinQueueState.Success)state).data;
                Glide.with(JoinQueueFragment.this)
                        .load(model.getUri())
                        .into(binding.qrCodeImage);

                binding.progressBar.getRoot().setVisibility(View.GONE);
                binding.errorLayout.getRoot().setVisibility(View.GONE);

                binding.queueName.setText(model.getName());
                binding.errorLayout.errorLayout.setVisibility(View.GONE);

            } else if (state instanceof JoinQueueState.Loading){
                binding.progressBar.getRoot().setVisibility(View.VISIBLE);
            } else if (state instanceof JoinQueueState.Error){
                WrongQrCodeDialogFragment dialogFragment = new WrongQrCodeDialogFragment();
                dialogFragment.show(requireActivity().getSupportFragmentManager(), "WRONG_QR_CODE");
                dialogFragment.onDialogDismissedListener(bundle -> {
                    requireActivity().finish();
                });
            }
        });

        viewModel.isUpdated.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){
                requireActivity().finish();
                ((JoinQueueActivity)requireActivity()).openWaitingActivity();
            }
        });

        viewModel.isSignIn.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){
                initUi();
            }
        });

    }
}