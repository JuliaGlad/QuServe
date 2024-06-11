package com.example.myapplication.presentation.common.JoinQueueFragment.joinQueue;

import static android.app.Activity.RESULT_OK;
import static com.example.myapplication.presentation.utils.constants.Utils.QUEUE_DATA;
import static com.example.myapplication.presentation.utils.constants.Utils.QUEUE_NAME_KEY;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import com.journeyapps.barcodescanner.ScanContract;

public class JoinQueueFragment extends Fragment {

    private JoinQueueViewModel viewModel;
    private FragmentJoinQueueBinding binding;
    private String queueData;
    private ActivityResultLauncher<Intent> launcher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(JoinQueueViewModel.class);
        queueData = requireActivity().getIntent().getStringExtra(QUEUE_DATA);
        viewModel.getQueueData(queueData);
        initLauncher();
    }

    private void initLauncher() {
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Intent intent = new Intent();
                            String name = data.getStringExtra(QUEUE_NAME_KEY);
                            intent.putExtra(QUEUE_NAME_KEY, name);
                            requireActivity().setResult(RESULT_OK, intent);
                            requireActivity().finish();
                        } else {
                            requireActivity().setResult(RESULT_OK);
                            requireActivity().finish();
                        }
                    }
                });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentJoinQueueBinding.inflate(inflater, container, false);
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
                ((JoinQueueActivity)requireActivity()).openWaitingActivity(launcher);
            }
        });

        viewModel.isSignIn.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){
                initUi();
            }
        });

    }
}