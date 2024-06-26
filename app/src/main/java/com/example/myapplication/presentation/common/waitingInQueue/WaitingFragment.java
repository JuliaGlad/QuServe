package com.example.myapplication.presentation.common.waitingInQueue;

import static com.example.myapplication.presentation.utils.constants.Utils.EDIT_ESTIMATED_TIME;
import static com.example.myapplication.presentation.utils.constants.Utils.EDIT_PEOPLE_BEFORE_YOU;
import static com.example.myapplication.presentation.utils.constants.Utils.QUEUE_NAME_KEY;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentWaitingBinding;
import com.example.myapplication.presentation.dialogFragments.leaveQueue.LeaveQueueDialogFragment;
import com.example.myapplication.presentation.common.waitingInQueue.model.WaitingModel;
import com.example.myapplication.presentation.common.waitingInQueue.recycler.items.leaveQueueItem.LeaveQueueDelegate;
import com.example.myapplication.presentation.common.waitingInQueue.recycler.items.leaveQueueItem.LeaveQueueDelegateItem;
import com.example.myapplication.presentation.common.waitingInQueue.recycler.items.leaveQueueItem.LeaveQueueModel;
import com.example.myapplication.presentation.common.waitingInQueue.recycler.items.waitingDelegateItem.WaitingItemDelegate;
import com.example.myapplication.presentation.common.waitingInQueue.recycler.items.waitingDelegateItem.WaitingItemDelegateItem;
import com.example.myapplication.presentation.common.waitingInQueue.recycler.items.waitingDelegateItem.WaitingItemModel;
import com.example.myapplication.presentation.common.waitingInQueue.state.WaitingState;
import com.example.myapplication.presentation.utils.waitingNotification.NotificationForegroundService;

import java.util.Arrays;
import java.util.List;

import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.stringTextView.StringTextViewDelegate;

public class WaitingFragment extends Fragment {

    private WaitingViewModel viewModel;
    private FragmentWaitingBinding binding;
    private final MainAdapter mainAdapter = new MainAdapter();
    private String name;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(WaitingViewModel.class);
        binding = FragmentWaitingBinding.inflate(inflater, container, false);
        viewModel.getQueue();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sendNotification();
        setupObserves();
        setMainAdapter();
        initCloseButton();
        handleBackButtonPressed();
    }

    private void handleBackButtonPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });
    }

    private void sendNotification() {
        if (!checkForegroundServiceRunning()) {
            ((WaitingActivity) requireActivity()).startNotificationForegroundService();
        }
    }

    private void initCloseButton() {
        binding.buttonBack.setOnClickListener(v -> {
            finish();
        });
    }

    private void finish() {
        Intent intent = new Intent();
        intent.putExtra(QUEUE_NAME_KEY, name);
        requireActivity().setResult(Activity.RESULT_OK, intent);
        requireActivity().finish();
    }

    private void setMainAdapter() {
        mainAdapter.addDelegate(new StringTextViewDelegate());
        mainAdapter.addDelegate(new WaitingItemDelegate());
        mainAdapter.addDelegate(new LeaveQueueDelegate());
        binding.recycler.setAdapter(mainAdapter);
    }

    private void showLeaveQueueDialog() {
        LeaveQueueDialogFragment dialogFragment = new LeaveQueueDialogFragment();
        dialogFragment.show(requireActivity().getSupportFragmentManager(), "LEAVE_QUEUE_DIALOG");
        dialogFragment.onDismissListener(bundle -> {
            requireActivity().setResult(Activity.RESULT_OK);
            requireActivity().finish();
        });
    }

    private void setupObserves() {

        viewModel.isAdded.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_waitingFragment_to_yourTurnFragment);
            }
        });

        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof WaitingState.Success) {
                WaitingModel model = ((WaitingState.Success) state).data;
                List<String> participantsList = model.getParticipants();
                name = model.getName();
                binding.queueName.setText(name);
                int peopleBeforeSize = 0, midTime = 0, startMidTime = Integer.parseInt(model.getMidTime());
                for (int i = 0; i < participantsList.size(); i++) {
                    if (viewModel.checkParticipantsIndex(participantsList, i)) {
                        peopleBeforeSize = i + 1;
                        midTime = Integer.parseInt(model.getMidTime()) * peopleBeforeSize;
                        break;
                    }
                }
                initRecycler(model.getPath(), peopleBeforeSize, midTime, startMidTime);
            }
            else if (state instanceof WaitingState.Loading) {
                binding.progressLayout.getRoot().setVisibility(View.VISIBLE);
            } else if (state instanceof WaitingState.Error) {
                setErrorLayout();
            }
        });
    }

    private void setErrorLayout() {
        binding.progressLayout.getRoot().setVisibility(View.GONE);
        binding.errorLayout.getRoot().setVisibility(View.VISIBLE);
        binding.errorLayout.buttonTryAgain.setOnClickListener(v -> {
            viewModel.getQueue();
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

    private void initRecycler(String path, int peopleBeforeSize, int midTime, int startMidTime) {
        String estimatedTime;

        estimatedTime = String.valueOf(midTime);
        buildList(new DelegateItem[]{
                new WaitingItemDelegateItem(new WaitingItemModel(2, startMidTime, path, peopleBeforeSize, requireContext().getString(R.string.estimated_waiting_time), estimatedTime, R.drawable.ic_time, true, EDIT_ESTIMATED_TIME)),
                new WaitingItemDelegateItem(new WaitingItemModel(3, startMidTime, path, peopleBeforeSize, requireContext().getString(R.string.people_before_you), String.valueOf(peopleBeforeSize), R.drawable.ic_queue_filled_24, true, EDIT_PEOPLE_BEFORE_YOU)),
                new WaitingItemDelegateItem(new WaitingItemModel(4, startMidTime, path, peopleBeforeSize, requireContext().getString(R.string.useful_tips), requireContext().getString(R.string.tips_description), R.drawable.ic_sparkles_primary, false, null)),
                new LeaveQueueDelegateItem(new LeaveQueueModel(4, this::showLeaveQueueDialog))
        });

    }

    private void buildList(DelegateItem[] items) {
        List<DelegateItem> list = Arrays.asList(items);
        mainAdapter.submitList(list);
        binding.progressLayout.getRoot().setVisibility(View.GONE);
        binding.errorLayout.getRoot().setVisibility(View.GONE);
    }
}