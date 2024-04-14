package com.example.myapplication.presentation.service.finishedQueueCreation;

import static com.example.myapplication.presentation.utils.Utils.BASIC;
import static com.example.myapplication.presentation.utils.Utils.COMPANY;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_NAME;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_ID;
import static com.example.myapplication.presentation.utils.Utils.STATE;
import static com.example.myapplication.presentation.utils.Utils.TIME;
import static com.example.myapplication.presentation.utils.Utils.stringsTimeArray;

import android.net.Uri;
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
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentFinishedQueueCreationBinding;
import com.example.myapplication.presentation.basicQueue.createQueue.CreateQueueActivity;
import com.example.myapplication.presentation.companyQueue.createQueue.CreateCompanyQueueActivity;
import com.example.myapplication.presentation.service.finishedQueueCreation.state.FinishedQueueState;
import com.example.myapplication.presentation.utils.workers.BasicQueueMidTimeWorker;
import com.example.myapplication.presentation.utils.workers.BasicQueueFinishWorker;
import com.example.myapplication.presentation.utils.workers.CompanyQueueFinishWorker;
import com.example.myapplication.presentation.utils.workers.CompanyQueueMidTimeWorker;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FinishedQueueCreationFragment extends Fragment {

    private FinishedQueueCreationViewModel viewModel;
    private FragmentFinishedQueueCreationBinding binding;
    private String type, name, companyId, queueId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getString(STATE);
        queueId = getArguments().getString(QUEUE_ID);
        if (type.equals(COMPANY)) {
            name = getActivity().getIntent().getStringExtra(COMPANY_NAME);
            companyId = getActivity().getIntent().getStringExtra(COMPANY_ID);
        }
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

        switch (type){
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


    private void setMainObserves(){
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof FinishedQueueState.Success) {
                Uri uri = ((FinishedQueueState.Success) state).data;
                Glide.with(this)
                        .load(uri)
                        .into(binding.qrCodeImage);
                binding.progressBar.setVisibility(View.GONE);

            } else if (state instanceof FinishedQueueState.Loading) {
                binding.progressBar.setVisibility(View.VISIBLE);

            } else if (state instanceof FinishedQueueState.Error) {

            }
        });
    }

    private void setupBasicObserves() {
        viewModel.queue.observe(getViewLifecycleOwner(), this::addFinishBasicQueueDelayWorker);
    }

    private void setupCompanyObserves() {
        viewModel.queue.observe(getViewLifecycleOwner(), time -> {
            if (time != null){
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
                    requireActivity().finish();
                    ((CreateQueueActivity) requireActivity()).openQueueDetailsActivity();
                    break;
                case COMPANY:
                    requireActivity().finish();
                    ((CreateCompanyQueueActivity) requireActivity()).openCompanyQueueDetailsActivity(companyId, queueId);
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

    private void addFinishBasicQueueDelayWorker(String time){
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