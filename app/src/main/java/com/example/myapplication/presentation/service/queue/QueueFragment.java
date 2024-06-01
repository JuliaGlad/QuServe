package com.example.myapplication.presentation.service.queue;

import static com.example.myapplication.presentation.utils.constants.Utils.NOT_PARTICIPATE_IN_QUEUE;
import static com.example.myapplication.presentation.utils.constants.Utils.NOT_QUEUE_OWNER;
import static com.example.myapplication.presentation.utils.constants.Utils.QUEUE_DATA;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentQueueBinding;
import com.example.myapplication.presentation.common.JoinQueueFragment.JoinQueueActivity;
import com.example.myapplication.presentation.dialogFragments.alreadyOwnQueue.AlreadyOwnQueueDialogFragment;
import com.example.myapplication.presentation.dialogFragments.alreadyParticipateInQueue.AlreadyParticipateInQueueDialogFragment;
import com.example.myapplication.presentation.dialogFragments.needAccountDialog.NeedAccountDialogFragment;
import com.example.myapplication.presentation.service.ScanCode;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class QueueFragment extends Fragment {

    private String isParticipateInQueue = NOT_PARTICIPATE_IN_QUEUE;
    private String isOwnQueue = NOT_QUEUE_OWNER;
    private QueueViewModel viewModel;
    private FragmentQueueBinding binding;
    private ActivityResultLauncher<ScanOptions> joinQueueLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(QueueViewModel.class);
        initJoinQueueLauncher();
        viewModel.signInAnonymously();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentQueueBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
        initCreateQueueButton();
        initJoinQueueButton();
        initBackButton();
    }

    private void initBackButton() {
        binding.buttonBack.setOnClickListener(v -> {
            requireActivity().finish();
        });
    }

    private void setupObserves() {

        viewModel.isSignIn.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){
                viewModel.getUserData();
            }
        });

        viewModel.isParticipateInQueue.observe(getViewLifecycleOwner(), isParticipant -> {
            if (isParticipant != null){
            isParticipateInQueue = isParticipant;
            }
        });

        viewModel.isOwnQueue.observe(getViewLifecycleOwner(), aBoolean -> isOwnQueue = aBoolean);

        viewModel.queueIdOwner.observe(getViewLifecycleOwner(), queueId -> {
            if (queueId != null){
                showAlreadyOwnDialog(queueId);
            }
        });
    }

    private void initJoinQueueButton() {
        binding.buttonJoinQueue.headLine.setText(getResources().getString(R.string.join_queue));
        binding.buttonJoinQueue.description.setText(getResources().getString(R.string.scan_queue_s_qr_code_and_join_it));
        binding.buttonJoinQueue.icon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.qr_code, requireContext().getTheme()));

        binding.buttonJoinQueue.item.setOnClickListener(v -> {
            if (isParticipateInQueue.equals(NOT_PARTICIPATE_IN_QUEUE)) {
                setJoinQueueScanOptions();
            } else {
                showAlreadyParticipateDialog();
            }
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

    private void setJoinQueueScanOptions() {
        ScanOptions scanOptions = new ScanOptions();
        scanOptions.setPrompt("Scan Qr-Code");
        scanOptions.setBeepEnabled(true);
        scanOptions.setCaptureActivity(ScanCode.class);
        scanOptions.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
        joinQueueLauncher.launch(scanOptions);
    }

    private void showAlreadyOwnDialog(String queueId) {
        AlreadyOwnQueueDialogFragment dialogFragment = new AlreadyOwnQueueDialogFragment(queueId);
        dialogFragment.show(requireActivity().getSupportFragmentManager(), "ALREADY_OWN_QUEUE_DIALOG");
        dialogFragment.onDismissListener(bundle -> ((QueueActivity) requireActivity()).openCreateQueueActivity());
    }

    private void initCreateQueueButton() {
        binding.buttonCreateQueue.icon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_add_queue, requireContext().getTheme()));
        binding.buttonCreateQueue.headLine.setText(getResources().getString(R.string.create_queue));
        binding.buttonCreateQueue.description.setText(getResources().getString(R.string.create_your_own_queue_so_people_can_join_it));

        binding.buttonCreateQueue.item.setOnClickListener(v -> {
            if (viewModel.checkAuthentication()) {
                if (isOwnQueue.equals(NOT_QUEUE_OWNER)) {
                    ((QueueActivity) requireActivity()).openCreateQueueActivity();
                } else {
                    viewModel.getQueueData();
                }
            } else {
                showYouNeedAccountDialog();
            }
        });
    }

    private void showYouNeedAccountDialog() {
        NeedAccountDialogFragment dialogFragment = new NeedAccountDialogFragment();
        dialogFragment.show(requireActivity().getSupportFragmentManager(), "YOU_NEED_ACCOUNT_DIALOG");
    }

    private void showAlreadyParticipateDialog() {
        AlreadyParticipateInQueueDialogFragment dialogFragment = new AlreadyParticipateInQueueDialogFragment();
        dialogFragment.show(requireActivity().getSupportFragmentManager(), "ALREADY_PARTICIPATE_IN_QUEUE_DIALOG");
        dialogFragment.onDismissListener(bundle -> {

        });
    }
}