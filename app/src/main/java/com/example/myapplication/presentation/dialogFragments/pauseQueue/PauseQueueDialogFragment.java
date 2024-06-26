package com.example.myapplication.presentation.dialogFragments.pauseQueue;

import static com.example.myapplication.presentation.utils.constants.Utils.PAUSED_HOURS;
import static com.example.myapplication.presentation.utils.constants.Utils.PAUSED_MINUTES;
import static com.example.myapplication.presentation.utils.constants.Utils.PAUSED_SECONDS;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.DialogNumberPickerBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import myapplication.android.ui.listeners.DialogDismissedListener;

public class PauseQueueDialogFragment extends DialogFragment {

    private DialogDismissedListener listener;
    private final String queueId;
    private final String companyId;
    private final String type;
    private int hours = 0;
    private int minutes = 0;
    private int seconds = 0;
    boolean isPaused = false;

    public PauseQueueDialogFragment(String queueId, String companyId, String type){
        this.type = type;
        this.companyId = companyId;
        this.queueId = queueId;
    }

    DialogNumberPickerBinding binding;
    PauseQueueViewModel viewModel;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());

        binding = DialogNumberPickerBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(PauseQueueViewModel.class);

        setupObserves();
        initHoursPicker();
        initMinutesPicker();
        initSecondsPicker();

        binding.buttonPause.setOnClickListener(v -> {

            binding.loader.setVisibility(View.VISIBLE);
            binding.buttonPause.setEnabled(false);
            binding.buttonPause.setText("");

            hours = binding.hoursPicker.getValue();
            minutes = binding.minutesPicker.getValue();
            seconds = binding.secondPicker.getValue();

            viewModel.pauseQueue(hours, minutes, seconds, queueId, companyId, type);
        });

        binding.buttonCancel.setOnClickListener(v -> {
            dismiss();
        });

        return builder.setView(binding.getRoot()).create();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);

        if (listener != null && isPaused) {

            Bundle bundle = new Bundle();
            bundle.putInt(PAUSED_HOURS, hours);
            bundle.putInt(PAUSED_MINUTES,minutes);
            bundle.putInt(PAUSED_SECONDS, seconds);

            listener.handleDialogClose(bundle);
        }
    }

    public void onDismissListener(DialogDismissedListener listener) {
        this.listener = listener;
    }

    private void setupObserves(){
        viewModel.isPaused.observe(this, aBoolean -> {
            isPaused = aBoolean;
            dismiss();
        });
    }

    private void initHoursPicker(){
        binding.hoursPicker.setMinValue(0);
        binding.hoursPicker.setMaxValue(99);
        binding.hoursPicker.setValue(0);
        binding.hoursPicker.setWrapSelectorWheel(false);
    }

    private void initMinutesPicker(){
        binding.minutesPicker.setMinValue(0);
        binding.minutesPicker.setMaxValue(59);
        binding.minutesPicker.setValue(10);
        binding.minutesPicker.setWrapSelectorWheel(false);
    }

    private void initSecondsPicker(){
        binding.secondPicker.setMinValue(0);
        binding.secondPicker.setMaxValue(59);
        binding.secondPicker.setValue(0);
        binding.secondPicker.setWrapSelectorWheel(false);
    }
}
