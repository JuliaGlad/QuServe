package com.example.myapplication.presentation.common.finishedQueueCreation;

import static android.app.Activity.RESULT_OK;
import static com.example.myapplication.presentation.utils.constants.Utils.BASIC;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Utils.QUEUE_ID;
import static com.example.myapplication.presentation.utils.constants.Utils.QUEUE_NAME_KEY;
import static com.example.myapplication.presentation.utils.constants.Utils.STATE;
import static com.example.myapplication.presentation.utils.constants.Utils.stringsTimeArray;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentFinishedQueueCreationBinding;
import com.example.myapplication.presentation.MainActivity;
import com.example.myapplication.presentation.basicQueue.createQueue.CreateQueueActivity;
import com.example.myapplication.presentation.common.finishedQueueCreation.state.FinishedQueueState;
import com.example.myapplication.presentation.companyQueue.createQueue.CreateCompanyQueueActivity;
import com.example.myapplication.presentation.home.companyUser.models.QueueCompanyHomeModel;
import com.example.myapplication.presentation.home.recycler.homeDelegates.homeQueueCompanyDelegate.HomeCompanyQueueDelegateItem;
import com.example.myapplication.presentation.home.recycler.homeDelegates.homeQueueCompanyDelegate.HomeCompanyQueueModel;
import com.example.myapplication.presentation.utils.workers.BasicQueueFinishWorker;
import com.example.myapplication.presentation.utils.workers.BasicQueueMidTimeWorker;
import com.example.myapplication.presentation.utils.workers.CompanyQueueFinishWorker;
import com.example.myapplication.presentation.utils.workers.CompanyQueueMidTimeWorker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import myapplication.android.ui.recycler.ui.items.items.adviseBox.AdviseBoxDelegateItem;

public class FinishedQueueCreationFragment extends Fragment {

    private FinishedQueueCreationViewModel viewModel;
    private FragmentFinishedQueueCreationBinding binding;
    private String type, companyId, queueId;
    private ActivityResultLauncher<Intent> queueDetailsLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = requireArguments().getString(STATE);
        queueId = requireArguments().getString(QUEUE_ID);
        if (type.equals(COMPANY)) {
            companyId = requireActivity().getIntent().getStringExtra(COMPANY_ID);
        }
        setLauncher();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        initBackButtonPressed();
        viewModel = new ViewModelProvider(requireActivity()).get(FinishedQueueCreationViewModel.class);
        binding = FragmentFinishedQueueCreationBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setMainObserves();
        initInfoBox();
        initSeeDetails();
        viewModel.getQrCode(queueId);

        switch (type) {
            case BASIC:
                setupBasicObserves();
                viewModel.delayBasicQueueFinish();
                addBasicQueueTimeCounterWorker();
                break;
            case COMPANY:
                setupCompanyObserves();
                viewModel.delayCompanyQueueFinish(queueId, companyId);
                addCompanyQueueTimeCounterWorker();
                break;
        }
    }

    private void setLauncher() {
        queueDetailsLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Intent intent = new Intent();
                            intent.putExtra(QUEUE_NAME_KEY, data.getStringExtra(QUEUE_NAME_KEY));
                            intent.putExtra(QUEUE_ID, queueId);
                            requireActivity().setResult(RESULT_OK, intent);
                            requireActivity().finish();
                        } else {
                            requireActivity().setResult(RESULT_OK);
                            requireActivity().finish();
                        }
                    }
                });
    }

    private void setMainObserves() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof FinishedQueueState.Success) {
                Uri uri = ((FinishedQueueState.Success) state).data;
                Glide.with(this)
                        .load(uri)
                        .into(binding.qrCodeImage);

                binding.progressBar.getRoot().setVisibility(View.GONE);
                binding.errorLayout.getRoot().setVisibility(View.GONE);

            } else if (state instanceof FinishedQueueState.Loading) {
                binding.progressBar.getRoot().setVisibility(View.VISIBLE);

            } else if (state instanceof FinishedQueueState.Error) {
                setErrorLayout();
            }
        });
    }

    private void setErrorLayout() {
        binding.progressBar.getRoot().setVisibility(View.GONE);
        binding.errorLayout.getRoot().setVisibility(View.VISIBLE);
        binding.errorLayout.buttonTryAgain.setOnClickListener(v -> {
            viewModel.getQrCode(queueId);
        });
    }

    private void setupBasicObserves() {
        viewModel.queue.observe(getViewLifecycleOwner(), time -> {
            if (time != null) {
                addFinishBasicQueueDelayWorker(time);
            }
        });
    }

    private void setupCompanyObserves() {
        viewModel.queue.observe(getViewLifecycleOwner(), time -> {
            if (time != null) {
                addFinishCompanyQueueDelayWorker(time);
            }
        });
    }

    private void initInfoBox() {
        binding.infoLayout.body.setText(R.string.show_this_qr_code_to_people_who_are_looking_forward_to_join_your_queue);
    }

    private void initSeeDetails() {
        binding.buttonSeeDetails.setOnClickListener(view -> {

            switch (type) {
                case BASIC:
                    ((CreateQueueActivity) requireActivity()).openQueueDetailsActivity(queueDetailsLauncher);
                    break;
                case COMPANY:
                    ((CreateCompanyQueueActivity) requireActivity()).openCompanyQueueDetailsActivity(companyId, queueId, queueDetailsLauncher);
                    break;
            }
        });
    }

    private void addCompanyQueueTimeCounterWorker() {
        Data.Builder builder = new Data.Builder();

        builder.putString(QUEUE_ID, queueId);
        builder.putString(COMPANY_ID, companyId);

        Constraints constraints = new Constraints.Builder()
                .setRequiresDeviceIdle(false)
                .build();

        PeriodicWorkRequest request = new PeriodicWorkRequest.Builder(CompanyQueueMidTimeWorker.class, 15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance(requireContext()).enqueue(request);
    }

    private void addBasicQueueTimeCounterWorker() {
        Constraints constraints = new Constraints.Builder()
                .setRequiresDeviceIdle(false)
                .build();

        PeriodicWorkRequest request = new PeriodicWorkRequest.Builder(BasicQueueMidTimeWorker.class, 15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance(requireContext()).enqueue(request);
    }

    private void addFinishBasicQueueDelayWorker(String time) {
        if (!time.equals(stringsTimeArray[0])) {
            List<String> list = Arrays.asList(time.split(" "));
            int number = Integer.parseInt(list.get(0));
            TimeUnit timeUnit = TimeUnit.valueOf(list.get(1));

            Data data = new Data.Builder()
                    .putString(QUEUE_ID, queueId)
                    .build();

            Constraints constraints = new Constraints.Builder()
                    .setRequiresDeviceIdle(false)
                    .build();

            OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(BasicQueueFinishWorker.class)
                    .setInputData(data)
                    .setConstraints(constraints)
                    .setInitialDelay(number, timeUnit)
                    .addTag("FinishQueueScheduler")
                    .build();

            WorkManager.getInstance(requireContext()).enqueue(request);
        }
    }

    private void addFinishCompanyQueueDelayWorker(String time) {
        if (!time.equals(stringsTimeArray[0])) {
            List<String> list = Arrays.asList(time.split(" "));
            int number = Integer.parseInt(list.get(0));
            TimeUnit timeUnit = TimeUnit.valueOf(list.get(1));

            Data data = new Data.Builder()
                    .putString(QUEUE_ID, queueId)
                    .putString(COMPANY_ID, companyId)
                    .build();

            Constraints constraints = new Constraints.Builder()
                    .setRequiresDeviceIdle(false)
                    .build();

            OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(CompanyQueueFinishWorker.class)
                    .setInputData(data)
                    .setConstraints(constraints)
                    .setInitialDelay(number, timeUnit)
                    .addTag("FinishQueueScheduler")
                    .build();

            WorkManager.getInstance(requireContext()).enqueue(request);
        }
    }

    private void initBackButtonPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Log.d("Back buttonWithDescription pressed", "pressed");
            }
        });
    }
}