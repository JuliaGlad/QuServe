package com.example.myapplication.presentation.basicQueue.queueDetails.pausedQueueFragment;

import static com.example.myapplication.presentation.utils.constants.Utils.BASIC;
import static com.example.myapplication.presentation.utils.constants.Utils.CURRENT_TIMER_TIME;
import static com.example.myapplication.presentation.utils.constants.Utils.IS_DEFAULT;
import static com.example.myapplication.presentation.utils.constants.Utils.PAUSED_HOURS;
import static com.example.myapplication.presentation.utils.constants.Utils.PAUSED_MINUTES;
import static com.example.myapplication.presentation.utils.constants.Utils.PAUSED_SECONDS;
import static com.example.myapplication.presentation.utils.constants.Utils.PROGRESS;
import static com.example.myapplication.presentation.utils.constants.Utils.QUEUE_ID;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.example.myapplication.presentation.utils.backToWorkNotification.HideNotificationWorker;
import com.example.myapplication.presentation.utils.waitingNotification.NotificationForegroundService;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class PausedQueueFragment extends Fragment {

    private PausedQueueFragmentViewModel viewModel;
    private FragmentPausedQueueBinding binding;
    private long timeMillis;
    private String timeLeft, queueId;
    private boolean isStopped = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(PausedQueueFragmentViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentPausedQueueBinding.inflate(inflater, container, false);


        if (getArguments() != null) {
            queueId = getArguments().getString(QUEUE_ID);
            if (getArguments().getBoolean(IS_DEFAULT)) {
                long hours = getArguments().getInt(PAUSED_HOURS) * 3600000L;
                long minutes = getArguments().getInt(PAUSED_MINUTES) * 60000L;
                long seconds = getArguments().getInt(PAUSED_SECONDS) * 1000L;

                timeMillis = hours + minutes + seconds;
                PROGRESS = (int) timeMillis;
            } else {
                timeMillis = CURRENT_TIMER_TIME;
            }
        } else {
            timeMillis = CURRENT_TIMER_TIME;
        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupObserves();
        initBox();
        binding.indicator.setMax(PROGRESS);
        binding.indicator.setProgress(PROGRESS, true);
        initBackButton();
        //initStopButton();
        startCountDown();
    }

    private void initBox() {
        binding.layoutBox.icon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_sparkles, getActivity().getTheme()));
        binding.layoutBox.body.setText(R.string.have_a_little_rest_and_then_come_back);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isStopped) {
            ((QueueDetailsActivity) requireActivity()).stopServiceForeground();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!checkForegroundServiceRunning() && timeMillis != 0) {
           ((QueueDetailsActivity) requireActivity()).startTimerForegroundService(timeMillis, timeLeft, queueId, BASIC);
        }
    }

    private void initBackButton() {
        binding.buttonBack.setOnClickListener(v -> {
            requireActivity().finish();
        });
    }

//    private void initStopButton() {
//        binding.buttonStopPause.setOnClickListener(v -> {
//            StopPauseDialogFragment dialogFragment = new StopPauseDialogFragment(queueId, null, BASIC);
//            dialogFragment.show(requireActivity().getSupportFragmentManager(), "STOP_PAUSE_DIALOG");
//            dialogFragment.onDismissListener(bundle -> {
//                isStopped = true;
//                NavHostFragment.findNavController(PausedQueueFragment.this)
//                        .navigate(R.id.action_pausedQueueFragment_to_detailsQueueFragment);
//            });
//        });
//    }

    private void startCountDown() {
        new CountDownTimer(timeMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeMillis = millisUntilFinished;
                binding.indicator.setProgress((int) (timeMillis - 1000), true);
                updateTimer();
            }

            @Override
            public void onFinish() {
                viewModel.continueQueue(queueId);
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

    private void updateTimer() {
        long hours = (timeMillis / 1000) / 3600;
        long minutes = ((timeMillis / 1000) % 3600) / 60;
        long seconds = (timeMillis / 1000) % 60;

        timeLeft = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
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

        viewModel.isContinued.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                ((QueueDetailsActivity) requireActivity()).startNotificationForegroundService(BASIC);
                initNotificationWorker();
                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_pausedQueueFragment_to_detailsQueueFragment);
            }
        });
    }
}