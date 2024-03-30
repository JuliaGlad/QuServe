package com.example.myapplication.presentation.queue.main;

import static com.example.myapplication.presentation.utils.Utils.COMPANY;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.app.AppState;
import com.example.myapplication.databinding.FragmentServiceBinding;
import com.example.myapplication.presentation.MainActivity;
import com.example.myapplication.presentation.dialogFragments.employeeQrCode.EmployeeQrCodeDialogFragment;
import com.example.myapplication.presentation.profile.becomeEmployee.BecomeEmployeeActivity;
import com.example.myapplication.presentation.queue.main.recyclerView.QueueButtonAdapter;
import com.example.myapplication.presentation.queue.main.recyclerView.QueueButtonModel;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServiceFragment extends Fragment {
    //    private int peoplePassed = 0;
    private FragmentServiceBinding binding;
    private ServiceModel viewModel;
    private AppState appState;
    private final QueueButtonAdapter adapter = new QueueButtonAdapter();
    private ActivityResultLauncher<ScanOptions> becomeEmployeeLauncher;

    List<QueueButtonModel> list = new ArrayList<>();
    String companyId = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ServiceModel.class);
        viewModel.getState();
        initBecomeEmployeeLauncher();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentServiceBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserves();
//        binding.manager.setOnClickListener(v -> {
//           ((MainActivity)requireActivity()).openChooseCompanyActivity(QUEUE_MANAGER);
//        });
//
//        binding.button4.setOnClickListener(v -> {
//            ((MainActivity)requireActivity()).openChooseCompanyActivity(CREATE_QUEUE);
//        });
//
//        initQueueOwnerDetailsButton();
//        initQueueParticipantDetailsButton();
    }

    private void setupObserves() {
        viewModel.appState.observe(getViewLifecycleOwner(), appState -> {
            this.appState = appState;
            if (appState instanceof AppState.CompanyState){
                companyId = ((AppState.CompanyState) appState).companyId;
            }
            initRecycler();
        });
    }


    private void initRecycler() {
        if (appState instanceof AppState.BasicState) {
            buildList(new QueueButtonModel[]{
                    new QueueButtonModel(1, R.drawable.queue_action_background, getResources().getString(R.string.queue), () -> {
                        ((MainActivity) requireActivity()).openQueueActivity();
                    }),
                    new QueueButtonModel(2, R.drawable.restaurant_action_background, getResources().getString(R.string.restaurant), () -> {

                    }),
                    new QueueButtonModel(3, R.drawable.on_board_catering_background, getResources().getString(R.string.on_board_catering), () -> {

                    }),
                    new QueueButtonModel(4, R.drawable.become_employee_background, getResources().getString(R.string.become_employee), this::setBecomeEmployeeScanOptions)
            });

        } else if (appState instanceof AppState.CompanyState) {
            buildList(new QueueButtonModel[]{
                    new QueueButtonModel(1, R.drawable.queue_action_background, getResources().getString(R.string.create_queue), () -> {
                        ((MainActivity) requireActivity()).openCreateCompanyQueueActivity(companyId);
                    }),
                    new QueueButtonModel(2, R.drawable.become_employee_background, getResources().getString(R.string.add_employees), () -> {
                        EmployeeQrCodeDialogFragment dialogFragment = new EmployeeQrCodeDialogFragment(companyId);
                        dialogFragment.show(getActivity().getSupportFragmentManager(), "EMPLOYEE_QE_CODE_DIALOG");
                    }),

                    new QueueButtonModel(3, R.drawable.queue_manager_background, getResources().getString(R.string.queue_manager), () -> {
                        ((MainActivity)requireActivity()).openQueueManagerActivity(companyId);
                    })

            });

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initBecomeEmployeeLauncher() {
        becomeEmployeeLauncher = registerForActivityResult(new ScanContract(), result -> {
            if (result.getContents() != null) {
                Intent intent = new Intent(requireContext(), BecomeEmployeeActivity.class);
                intent.putExtra(COMPANY, result.getContents());
                requireActivity().startActivity(intent);
            }
        });
    }

    private void setBecomeEmployeeScanOptions() {
        ScanOptions scanOptions = new ScanOptions();
        scanOptions.setPrompt("Scan Qr-Code");
        scanOptions.setBeepEnabled(true);
        scanOptions.setCaptureActivity(ScanCode.class);
        scanOptions.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
        becomeEmployeeLauncher.launch(scanOptions);
    }

    private void buildList(QueueButtonModel[] models) {
        list = Arrays.asList(models);
        binding.recyclerView.setAdapter(adapter);
        adapter.submitList(list);
    }

//    private void initQueueParticipantDetailsButton() {
//        binding.testQueueParticipant.setOnClickListener(v ->
//                ((MainActivity) requireActivity()).openQueueWaitingActivity());
//    }
//
//    private void initQueueOwnerDetailsButton() {
//        binding.testQueueOwner.setOnClickListener(v -> {
//            ((MainActivity) requireActivity()).openQueueDetailsActivity();
//        });
//    }


}