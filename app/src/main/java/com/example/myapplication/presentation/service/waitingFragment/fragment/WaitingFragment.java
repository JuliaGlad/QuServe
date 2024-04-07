package com.example.myapplication.presentation.service.waitingFragment.fragment;

import static com.example.myapplication.presentation.utils.Utils.EDIT_ESTIMATED_TIME;
import static com.example.myapplication.presentation.utils.Utils.EDIT_PEOPLE_BEFORE_YOU;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentWaitingBinding;
import com.example.myapplication.presentation.dialogFragments.leaveQueue.LeaveQueueDialogFragment;
import com.example.myapplication.presentation.service.waitingFragment.fragment.model.WaitingModel;
import com.example.myapplication.presentation.service.waitingFragment.fragment.recycler.items.leaveQueueItem.LeaveQueueDelegate;
import com.example.myapplication.presentation.service.waitingFragment.fragment.recycler.items.leaveQueueItem.LeaveQueueDelegateItem;
import com.example.myapplication.presentation.service.waitingFragment.fragment.recycler.items.leaveQueueItem.LeaveQueueModel;
import com.example.myapplication.presentation.service.waitingFragment.fragment.recycler.items.waitingDelegateItem.WaitingItemDelegate;
import com.example.myapplication.presentation.service.waitingFragment.fragment.recycler.items.waitingDelegateItem.WaitingItemDelegateItem;
import com.example.myapplication.presentation.service.waitingFragment.fragment.recycler.items.waitingDelegateItem.WaitingItemModel;
import com.example.myapplication.presentation.service.waitingFragment.fragment.state.WaitingState;
import com.example.myapplication.presentation.utils.waitingNotification.NotificationForegroundService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import myapplication.android.ui.recycler.delegate.DelegateItem;
import myapplication.android.ui.recycler.delegate.MainAdapter;
import myapplication.android.ui.recycler.ui.items.items.stringTextView.StringTextViewDelegate;

public class WaitingFragment extends Fragment {

    private WaitingViewModel viewModel;
    private FragmentWaitingBinding binding;
    private List<DelegateItem> list = new ArrayList<>();;
    private final MainAdapter mainAdapter = new MainAdapter();

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
        binding.buttonBack.setOnClickListener(v -> requireActivity().finish());
    }

    private void setMainAdapter() {
        mainAdapter.addDelegate(new StringTextViewDelegate());
        mainAdapter.addDelegate(new WaitingItemDelegate());
        mainAdapter.addDelegate(new LeaveQueueDelegate());
        binding.recycler.setAdapter(mainAdapter);
    }

    private void showLeaveQueueDialog(String queueId) {
        LeaveQueueDialogFragment dialogFragment = new LeaveQueueDialogFragment(queueId);
        dialogFragment.show(requireActivity().getSupportFragmentManager(), "LEAVE_QUEUE_DIALOG");
        dialogFragment.onDismissListener(bundle -> {
            requireActivity().finish();
        });
    }

    private void setupObserves() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof WaitingState.Success){
                WaitingModel model = ((WaitingState.Success)state).data;
                List<String> participantsList = model.getParticipants();
                int peopleBeforeSize, midTime = 0;
                for (int i = 0; i < participantsList.size(); i++) {
                    if (viewModel.checkParticipantsIndex(participantsList, i)) {
                        peopleBeforeSize = i + 1;
                        midTime = Integer.parseInt(model.getMidTime()) * peopleBeforeSize;
                        break;
                    }
                }
                initRecycler(model.getId(), model.getParticipants().size(), midTime);
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

    private void initRecycler(String queueId, int peopleBeforeSize, int midTime) {
        buildList(new DelegateItem[]{
                new WaitingItemDelegateItem(new WaitingItemModel(2, queueId, peopleBeforeSize, requireContext().getString(R.string.estimated_waiting_time), String.valueOf(midTime), R.drawable.ic_time, true, EDIT_ESTIMATED_TIME)),
                new WaitingItemDelegateItem(new WaitingItemModel(3, queueId, peopleBeforeSize, requireContext().getString(R.string.people_before_you), String.valueOf(peopleBeforeSize), R.drawable.ic_queue_filled_24, true, EDIT_PEOPLE_BEFORE_YOU)),
                new WaitingItemDelegateItem(new WaitingItemModel(4, queueId, peopleBeforeSize, requireContext().getString(R.string.useful_tips), requireContext().getString(R.string.tips_description), R.drawable.ic_sparkles_primary, false, null)),
                new LeaveQueueDelegateItem(new LeaveQueueModel(4, () -> showLeaveQueueDialog(queueId)))
        });
    }

    private void buildList(DelegateItem[] items) {
        list = Arrays.asList(items);
        mainAdapter.submitList(list);
    }
}