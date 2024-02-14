package com.example.myapplication.presentation.queue.waitingFragment.fragment;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentWaitingBinding;
import com.example.myapplication.presentation.MainActivity;
import com.example.myapplication.presentation.queue.waitingFragment.fragment.recycler.items.leaveQueueItem.LeaveQueueDelegate;
import com.example.myapplication.presentation.queue.waitingFragment.fragment.recycler.items.waitingDelegateItem.WaitingItemDelegate;
import com.example.myapplication.presentation.utils.NotificationForegroundService;

import java.io.IOException;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.stringTextView.StringTextViewDelegate;

public class WaitingFragment extends Fragment {

    private WaitingViewModel viewModel;
    private NotificationForegroundService service;
    private FragmentWaitingBinding binding;
    private MainAdapter mainAdapter = new MainAdapter();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(WaitingViewModel.class);
        binding = FragmentWaitingBinding.inflate(inflater, container, false);
        viewModel.getQueue(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sendNotification();
        setupObserves();
        setMainAdapter();
        initCloseButton();
    }

    private void sendNotification() {
        if (!checkForegroundServiceRunning()) {
            ((WaitingActivity) requireActivity()).startNotificationForegroundService();
        }
    }

    private void initCloseButton() {
        binding.imageButtonClose.setOnClickListener(v -> requireActivity().finish());
    }

    private void setMainAdapter() {
        mainAdapter.addDelegate(new StringTextViewDelegate());
        mainAdapter.addDelegate(new WaitingItemDelegate());
        mainAdapter.addDelegate(new LeaveQueueDelegate());
        binding.recycler.setAdapter(mainAdapter);
    }

    private void showLeaveQueueDialog() {
        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_leave_queue, null);
        AlertDialog leaveQueueDialog = new AlertDialog.Builder(getContext())
                .setView(dialogView).create();

        leaveQueueDialog.show();

        Button leaveQueue = dialogView.findViewById(R.id.leave_button);
        Button cancel = dialogView.findViewById(R.id.cancel_button);
        leaveQueue.setOnClickListener(viewLeave -> {
            viewModel.leaveQueue()
                    .subscribeOn(Schedulers.io())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                        }

                        @Override
                        public void onComplete() {
                            leaveQueueDialog.dismiss();
                            requireActivity().finish();
                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                        }
                    });
        });

        cancel.setOnClickListener(viewCancel -> {
            leaveQueueDialog.dismiss();
        });

    }


    private void setupObserves() {
        viewModel.items.observe(getViewLifecycleOwner(), mainAdapter::submitList);

        viewModel.showLeaveDialog.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                showLeaveQueueDialog();
            }
        });
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


}