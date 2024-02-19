package com.example.myapplication.presentation.queue.main;

import static com.example.myapplication.DI.service;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_DATA;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_IN_PROGRESS;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_LIST;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentQueueBinding;
import com.example.myapplication.presentation.queue.JoinQueueFragment.JoinQueueActivity;
import com.example.myapplication.presentation.MainActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class QueueFragment extends Fragment {

    private FragmentQueueBinding binding;
    private QueueViewModel viewModel;
    private ActivityResultLauncher<ScanOptions> launcher;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(QueueViewModel.class);

        binding = FragmentQueueBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initCreateButton();
        initEnterButton();
        initLauncher();

        binding.testQueueOwner.setOnClickListener(v -> {
            ((MainActivity)requireActivity()).openQueueDetailsActivity();
        });

        binding.testQueueParticipant.setOnClickListener(v ->
                ((MainActivity)requireActivity()).openQueueWaitingActivity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initLauncher(){
        launcher = registerForActivityResult(new ScanContract(), result -> {
            if (result.getContents() != null) {
                Intent intent = new Intent(requireContext(), JoinQueueActivity.class);
                intent.putExtra(QUEUE_DATA, result.getContents());
                requireActivity().startActivity(intent);
            }
        });
    }

    private void setScanOptions() {
        ScanOptions scanOptions = new ScanOptions();
        scanOptions.setPrompt("Scan Qr-Code For Your Queue");
        scanOptions.setBeepEnabled(true);
        scanOptions.setCaptureActivity(ScanCode.class);
        scanOptions.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
        launcher.launch(scanOptions);
    }

    private void initEnterButton() {
        binding.buttonEnter.setOnClickListener(v -> {
            setScanOptions();
        });
    }

    private void initCreateButton() {
        binding.button.setOnClickListener(v -> {
           ((MainActivity)requireActivity()).openCreateQueueActivity();
        });
    }
}