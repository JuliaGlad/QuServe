package com.example.myapplication.presentation.queue.main;

import static com.example.myapplication.presentation.utils.Utils.COMPANY;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_DETAILS;
import static com.example.myapplication.presentation.utils.Utils.CREATE_QUEUE;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_DATA;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_MANAGER;
import static com.example.myapplication.presentation.utils.Utils.STATE;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.DI;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentQueueBinding;
import com.example.myapplication.presentation.MainActivity;
import com.example.myapplication.presentation.profile.becomeEmployee.BecomeEmployeeActivity;
import com.example.myapplication.presentation.profile.becomeEmployee.BecomeEmployeeViewModel;
import com.example.myapplication.presentation.queue.JoinQueueFragment.JoinQueueActivity;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class QueueFragment extends Fragment {
    private int peoplePassed = 0;
    private FragmentQueueBinding binding;
    private boolean isOwnQueue, isParticipateInQueue;
    private QueueViewModel viewModel;
    private ActivityResultLauncher<ScanOptions> joinQueueLauncher;
    private ActivityResultLauncher<ScanOptions> becomeEmployeeLauncher;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(QueueViewModel.class);

        viewModel.getQueueData();
        viewModel.getUserData();

        binding = FragmentQueueBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupObserves();
        initCreateButton();
        initEnterButton();
        initEmployeeButton();
        initJoinQueueLauncher();
        initBecomeEmployeeLauncher();

        binding.manager.setOnClickListener(v -> {
           ((MainActivity)requireActivity()).openChooseCompanyActivity(QUEUE_MANAGER);
        });

        binding.button4.setOnClickListener(v -> {
            ((MainActivity)requireActivity()).openChooseCompanyActivity(CREATE_QUEUE);
        });

        initQueueOwnerDetailsButton();
        initQueueParticipantDetailsButton();
    }

    private void initBecomeEmployeeLauncher() {
        becomeEmployeeLauncher = registerForActivityResult(new ScanContract(), result -> {
            if (result.getContents() != null) {
                Intent intent = new Intent(requireContext(), BecomeEmployeeActivity.class);
                intent.putExtra(COMPANY, result.getContents());
                requireActivity().startActivity(intent);
            }
        });
    }

    private void initEmployeeButton() {
        binding.button3.setOnClickListener(v -> {
            setBecomeEmployeeScanOptions();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initQueueParticipantDetailsButton() {
        binding.testQueueParticipant.setOnClickListener(v ->
                ((MainActivity) requireActivity()).openQueueWaitingActivity());
    }

    private void initQueueOwnerDetailsButton() {
        binding.testQueueOwner.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).openQueueDetailsActivity();
        });
    }

    private void showYouNeedToLoginDialog() {
        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_you_need_to_register, null);
        AlertDialog needToLoginQueueDialog = new AlertDialog.Builder(getContext())
                .setView(dialogView).create();

        needToLoginQueueDialog.show();

        Button ok = dialogView.findViewById(R.id.ok_button);

        ok.setOnClickListener(view -> {
            needToLoginQueueDialog.dismiss();
        });
    }

    private void initJoinQueueLauncher() {
        joinQueueLauncher = registerForActivityResult(new ScanContract(), result -> {
            if (result.getContents() != null) {
                Intent intent = new Intent(requireContext(), JoinQueueActivity.class);
                intent.putExtra(QUEUE_DATA, result.getContents());
                requireActivity().startActivity(intent);
            }
        });
    }

    private void setBecomeEmployeeScanOptions() {
        ScanOptions scanOptions = new ScanOptions();
        scanOptions.setPrompt("Scan Qr-Code");
        scanOptions.setBeepEnabled(true);
        scanOptions.setCaptureActivity(ScanCode.class);
        scanOptions.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
        becomeEmployeeLauncher.launch(scanOptions);
    }


    private void setJoinQueueScanOptions() {
        ScanOptions scanOptions = new ScanOptions();
        scanOptions.setPrompt("Scan Qr-Code");
        scanOptions.setBeepEnabled(true);
        scanOptions.setCaptureActivity(ScanCode.class);
        scanOptions.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
        joinQueueLauncher.launch(scanOptions);
    }

    private void initEnterButton() {
        binding.buttonEnter.setOnClickListener(v -> {
            if (!isParticipateInQueue) {
                setJoinQueueScanOptions();
            } else {
                showAlreadyParticipateDialog();
            }
        });
    }

    private void showAlreadyParticipateDialog() {
        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_already_participate, null);
        AlertDialog participateInQueueDialog = new AlertDialog.Builder(getContext())
                .setView(dialogView).create();

        participateInQueueDialog.show();

        Button cancel = dialogView.findViewById(R.id.cancel_button);
        Button leave = dialogView.findViewById(R.id.leave_button);

        leave.setOnClickListener(view -> {
            viewModel.leaveQueue();
            participateInQueueDialog.dismiss();
        });

        cancel.setOnClickListener(view -> {
            participateInQueueDialog.dismiss();
        });
    }

    private void showAlreadyOwnDialog() {
        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_already_own_queue, null);
        AlertDialog ownQueueDialog = new AlertDialog.Builder(getContext())
                .setView(dialogView).create();

        ownQueueDialog.show();

        Button cancel = dialogView.findViewById(R.id.cancel_button);
        Button finish = dialogView.findViewById(R.id.finish_button);

        finish.setOnClickListener(view -> {
            viewModel.finishQueue();
            ownQueueDialog.dismiss();
        });

        cancel.setOnClickListener(view -> {
            ownQueueDialog.dismiss();
        });
    }

    private void initCreateButton() {
        binding.button.setOnClickListener(v -> {
            if (DI.checkAuthentificationUseCase.invoke()) {
                if (!isOwnQueue) {
                    ((MainActivity) requireActivity()).openCreateQueueActivity();
                } else {
                    showAlreadyOwnDialog();
                }
            } else {
                showYouNeedToLoginDialog();
            }
        });
    }

    private void setupObserves() {
        viewModel.isParticipateInQueue.observe(getViewLifecycleOwner(), aBoolean -> {
            isParticipateInQueue = aBoolean;
        });

        viewModel.isOwnQueue.observe(getViewLifecycleOwner(), aBoolean -> {
            isOwnQueue = aBoolean;
        });
    }

}