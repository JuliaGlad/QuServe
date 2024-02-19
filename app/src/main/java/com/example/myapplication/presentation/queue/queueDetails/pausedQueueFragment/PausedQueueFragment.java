package com.example.myapplication.presentation.queue.queueDetails.pausedQueueFragment;

import static com.example.myapplication.presentation.utils.Utils.PAUSED_TIME;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.work.Constraints;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentPausedQueueBinding;
import com.example.myapplication.presentation.queue.waitingFragment.fragment.WaitingActivity;
import com.example.myapplication.presentation.utils.backToWorkNotification.HideNotificationWorker;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PausedQueueFragment extends Fragment {

    private PausedQueueFragmentViewModel viewModel;
    private FragmentPausedQueueBinding binding;
    private long timeMillis;
    private int data;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        data = getArguments().getInt(PAUSED_TIME);
        timeMillis = data * 60000L;
        viewModel = new ViewModelProvider(this).get(PausedQueueFragmentViewModel.class);
        binding = FragmentPausedQueueBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getQueue();
        startCountDown();
        initStopButton();
    }

    private void initStopButton() {
        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_stop_pause, null);
        AlertDialog stopPauseQueueDialog = new AlertDialog.Builder(getContext())
                .setView(dialogView).create();

        stopPauseQueueDialog.show();

        Button stop = dialogView.findViewById(R.id.stop_pause_button);
        Button cancel = dialogView.findViewById(R.id.cancel_button);

        stop.setOnClickListener(view -> {
            stopPauseQueueDialog.dismiss();
            NavHostFragment.findNavController(PausedQueueFragment.this)
                    .navigate(R.id.action_pausedQueueFragment_to_queueDetailsFragment);
        });

        cancel.setOnClickListener(view -> {
            stopPauseQueueDialog.dismiss();
        });

    }

    private void startCountDown() {
        new CountDownTimer(timeMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeMillis = millisUntilFinished;
                binding.countdownTimer.setMax(data * 60000);
                binding.countdownTimer.incrementProgressBy(1000);
                updateTimer();
            }

            @Override
            public void onFinish() {
                viewModel.continueQueue()
                        .subscribeOn(Schedulers.io())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                            }

                            @Override
                            public void onComplete() {
                                ((WaitingActivity) requireActivity()).startNotificationForegroundService();

                                Constraints constraints = new Constraints.Builder()
                                        .setRequiresDeviceIdle(false)
                                        .build();

                                OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(HideNotificationWorker.class)
                                        .setConstraints(constraints)
                                        .setInitialDelay(5, TimeUnit.MINUTES)
                                        .addTag("StopPause")
                                        .build();

                                WorkManager.getInstance(requireContext()).enqueue(request);

                                NavHostFragment.findNavController(PausedQueueFragment.this)
                                        .navigate(R.id.action_pausedQueueFragment_to_queueDetailsFragment);
                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                            }
                        });
            }
        }.start();
    }

    private void updateTimer() {
        int minutes = (int) (timeMillis / 1000) / 60;
        int seconds = (int) (timeMillis / 1000) % 60;

        String timeLeft = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        binding.timer.setText(timeLeft);
    }
}