package com.example.myapplication.presentation.queue.ParticipateInQueueFragment;

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
            viewModel.signInAnonymously()
                    .subscribeOn(Schedulers.io())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                        }

                        @Override
                        public void onComplete() {
                            viewModel.getQrCode(queueData);
                            viewModel.getQueueData(queueData);
                            setupObserves();
                            initOkButton();
                            initNoButton();
                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                        }
                    });
        } else {
            viewModel.getQrCode(queueData);
            viewModel.getQueueData(queueData);
            setupObserves();
            initOkButton();
            initNoButton();
        }
    }

    private void initOkButton() {
        binding.buttonYes.setOnClickListener(view -> {
            viewModel.addToParticipantsList(queueData)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                        }

                        @Override
                        public void onComplete() {
                            NavHostFragment.findNavController(JoinQueueFragment.this)
                                    .navigate(R.id.action_joinQueueFragment2_to_waitingFragment);
                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                            Log.e("Adding", "failed");
                        }
                    });
        });
    }

    private void initNoButton() {
        binding.buttonNo.setOnClickListener(v -> requireActivity().finish());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void setupObserves() {
        viewModel.image.observe(getViewLifecycleOwner(), uriTask -> {
            uriTask.addOnSuccessListener(uri -> Glide.with(JoinQueueFragment.this).load(uri).into(binding.qrCodeImage));
        });

        viewModel.name.observe(getViewLifecycleOwner(), queueName -> {
            binding.queueName.setText(queueName);
        });
    }
}