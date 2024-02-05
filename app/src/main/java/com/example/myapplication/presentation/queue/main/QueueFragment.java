package com.example.myapplication.presentation.queue.main;

import static com.example.myapplication.presentation.utils.Utils.QUEUE_DATA;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.FragmentQueueBinding;
import com.example.myapplication.presentation.queue.ParticipateInQueueFragment.ParticipatingInQueueActivity;
import com.example.myapplication.presentation.MainActivity;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

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

        binding.button3.setOnClickListener(v -> {
            ((MainActivity)requireActivity()).openQueueDetailsActivity();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initLauncher(){
        launcher = registerForActivityResult(new ScanContract(), result -> {
            if (result.getContents() != null) {
                Intent intent = new Intent(requireContext(), ParticipatingInQueueActivity.class);
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