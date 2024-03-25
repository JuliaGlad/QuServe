package com.example.myapplication.presentation.basicQueue.queueDetails.pausedQueueFragment;

import static com.example.myapplication.presentation.utils.Utils.CURRENT_TIMER_TIME;
import static com.example.myapplication.presentation.utils.Utils.PAUSED_TIME;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.work.Constraints;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentPausedQueueBinding;
import com.example.myapplication.presentation.basicQueue.queueDetails.QueueDetailsActivity;
import com.example.myapplication.presentation.utils.waitingNotification.NotificationForegroundService;
import com.example.myapplication.presentation.utils.workers.PauseAvailableWorker;
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
    private String timeLeft, queueId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            data = getArguments().getInt(PAUSED_TIME);
            timeMillis = data * 60000L;
        } else {
            timeMillis = CURRENT_TIMER_TIME;
        }


        viewModel = new ViewModelProvider(this).get(PausedQueueFragmentViewModel.class);
        binding = FragmentPausedQueueBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupObserves();

        viewModel.getQueue();
        initBox();
        initBackButton();
        startCountDown();
        initStopButton();
    }

    private void initBox() {
        binding.layoutBox.icon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_stars, getActivity().getTheme()));
        binding.layoutBox.body.setText(R.string.have_a_little_rest_and_then_come_back);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((QueueDetailsActivity) requireActivity()).stopServiceForeground();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!checkForegroundServiceRunning()) {
            ((QueueDetailsActivity) requireActivity()).startTimerForegroundService(timeMillis, timeLeft, queueId);
        }
    }

    private void initBackButton() {
        binding.buttonBack.setOnClickListener(v -> {
            requireActivity().finish();
        });
    }

    private void initStopButton() {
        binding.buttonStopPause.setOnClickListener(v -> {
            final View dialogView = getLayoutInflater().inflate(R.layout.dialog_stop_pause, null);
            AlertDialog stopPauseQueueDialog = new AlertDialog.Builder(getContext())
                    .setView(dialogView).create();

            stopPauseQueueDialog.show();

            Button stop = dialogView.findViewById(R.id.stop_pause_button);
            Button cancel = dialogView.findViewById(R.id.cancel_button);

            stop.setOnClickListener(view -> {
                stopPauseQueueDialog.dismiss();
                viewModel.continueQueue()
                        .subscribeOn(Schedulers.io())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                            }

                            @Override
                            public void onComplete() {
                                NavHostFragment.findNavController(PausedQueueFragment.this)
                                        .navigate(R.id.action_pausedQueueFragment_to_detailsQueueFragment);
                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                            }
                        });
            });

            cancel.setOnClickListener(view -> {
                stopPauseQueueDialog.dismiss();
            });
        });
    }

    private void startCountDown() {
        new CountDownTimer(timeMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeMillis = millisUntilFinished;

                binding.indicator.setMax(data * 60000);
                binding.indicator.incrementProgressBy(1000);
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
                                ((QueueDetailsActivity) requireActivity()).startNotificationForegroundService();
                                initNotificationWorker();
                                initPauseAvailableWorker();
                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                            }
                        });
            }
        }.start();
    }

    private void initNotificationWorker() {
        Constraints constraints = new Constraints.Builder()
                .setRequiresDeviceIdle(false)
                .build();

        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(HideNotificationWorker.class)
                .setConstraints(constraints)
                .setInitialDelay(5, TimeUnit.MINUTES)
                .addTag("StopPause")
                .build();

        WorkManager.getInstance(requireContext()).enqueue(request);
    }

    private void initPauseAvailableWorker() {

        Constraints constraints = new Constraints.Builder()
                .setRequiresDeviceIdle(false)
                .build();

        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(PauseAvailableWorker.class)
                .setConstraints(constraints)
                .setInitialDelay(2, TimeUnit.HOURS)
                .addTag("PauseAvailable")
                .build();

        WorkManager.getInstance(requireContext()).enqueue(request);
    }

    private void updateTimer() {
        int minutes = (int) (timeMillis / 1000) / 60;
        int seconds = (int) (timeMillis / 1000) % 60;

        timeLeft = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        binding.timer.setText(timeLeft);
    }

    private boolean checkForegroundServiceRunning() {
        ActivityManager manager = (ActivityManager) requireContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (NotificationForegroundService.class.getName().equals(service.service.getClassName())) {
                if (service.foreground) {
                    return true;
                }
            }
        }
        return false;
    }

    private void setupObserves() {
        viewModel.queueId.observe(getViewLifecycleOwner(), queueId -> {
            this.queueId = queueId;
        });
    }
}