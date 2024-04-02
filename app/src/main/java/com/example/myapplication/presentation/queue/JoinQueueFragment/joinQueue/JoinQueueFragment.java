package com.example.myapplication.presentation.queue.JoinQueueFragment.joinQueue;

import static com.example.myapplication.presentation.utils.Utils.QUEUE_DATA;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentJoinQueueBinding;
import com.example.myapplication.presentation.queue.JoinQueueFragment.JoinQueueActivity;
import com.example.myapplication.presentation.queue.JoinQueueFragment.joinQueue.model.JoinQueueModel;
import com.example.myapplication.presentation.queue.JoinQueueFragment.joinQueue.state.JoinQueueState;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class JoinQueueFragment extends Fragment {

    private JoinQueueViewModel viewModel;
    private FragmentJoinQueueBinding binding;
    private String queueData;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(JoinQueueViewModel.class);

        binding = FragmentJoinQueueBinding.inflate(inflater, container, false);
        queueData = requireActivity().getIntent().getStringExtra(QUEUE_DATA);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (viewModel.checkUserID()) {
            viewModel.signInAnonymously();
        } else {
            initUi();
        }
    }

    private void initOkButton() {
        binding.buttonYes.setOnClickListener(view -> {
            viewModel.addToParticipantsList(queueData);
        });
    }

    private void initNoButton() {
        binding.buttonNo.setOnClickListener(v -> requireActivity().finish());
    }

    private void initUi(){
        viewModel.getQueueData(queueData);
        setupObserves();
        initOkButton();
        initNoButton();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void setupObserves() {

        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof JoinQueueState.Success){
                JoinQueueModel model = ((JoinQueueState.Success)state).data;
                Glide.with(JoinQueueFragment.this).load(model.getUri()).into(binding.qrCodeImage);
                binding.queueName.setText(model.getName());
            } else if (state instanceof JoinQueueState.Loading){

            } else if (state instanceof JoinQueueState.Error){

            }
        });

        viewModel.isUpdated.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){
                requireActivity().finish();
                ((JoinQueueActivity)requireActivity()).openWaitingActivity();
            }
        });

        viewModel.isSignIn.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){
                initUi();
            }
        });

    }
}