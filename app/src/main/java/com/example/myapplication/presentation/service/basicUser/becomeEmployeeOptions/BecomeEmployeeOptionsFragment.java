package com.example.myapplication.presentation.service.basicUser.becomeEmployeeOptions;

import static android.app.Activity.RESULT_OK;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Utils.COOK_DATA;
import static com.example.myapplication.presentation.utils.constants.Utils.EMPLOYEE_DATA;
import static com.example.myapplication.presentation.utils.constants.Utils.EMPLOYEE_ROLE;
import static com.example.myapplication.presentation.utils.constants.Utils.STATE;
import static com.example.myapplication.presentation.utils.constants.Utils.WAITER_DATA;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentBecomeEmployeeOptionsBinding;
import com.example.myapplication.presentation.employee.becomeCook.BecomeCookActivity;
import com.example.myapplication.presentation.employee.becomeEmployee.BecomeEmployeeActivity;
import com.example.myapplication.presentation.employee.becomeWaiter.BecomeWaiterActivity;
import com.example.myapplication.presentation.service.basicUser.recyclerView.ButtonWithDescriptionAdapter;
import com.example.myapplication.presentation.service.basicUser.recyclerView.ButtonWithDescriptionModel;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.Arrays;
import java.util.List;

public class BecomeEmployeeOptionsFragment extends Fragment {

    private FragmentBecomeEmployeeOptionsBinding binding;
    private ActivityResultLauncher<ScanOptions> launcherWorker, launcherWaiter, launcherCook;
    private ActivityResultLauncher<Intent> launcherEmployee;
    private final ButtonWithDescriptionAdapter adapter = new ButtonWithDescriptionAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWorkerLauncher();
        initEmployeeLauncher();
        initCookLauncher();
        initWaiterLauncher();
    }

    private void initWaiterLauncher() {
        launcherWaiter = registerForActivityResult(new ScanContract(), result -> {
            if (result.getContents() != null) {
                Intent intent = new Intent(requireContext(), BecomeWaiterActivity.class);
                intent.putExtra(WAITER_DATA, result.getContents());
                launcherEmployee.launch(intent);
            }
        });
    }

    private void initEmployeeLauncher() {
        launcherEmployee = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Intent intent = new Intent();
                            intent.putExtra(COMPANY_ID, data.getStringExtra(COMPANY_ID));
                            intent.putExtra(EMPLOYEE_ROLE, data.getStringExtra(EMPLOYEE_ROLE));
                            requireActivity().setResult(RESULT_OK, intent);
                            requireActivity().finish();
                        }
                    }
                });
    }

    private void initWorkerLauncher() {
        launcherWorker = registerForActivityResult(new ScanContract(), result -> {
            if (result.getContents() != null) {
                Intent intent = new Intent(requireContext(), BecomeEmployeeActivity.class);
                intent.putExtra(EMPLOYEE_DATA, result.getContents());
                launcherEmployee.launch(intent);
            }
        });
    }

    private void initCookLauncher() {
        launcherCook = registerForActivityResult(new ScanContract(), result -> {
            if (result.getContents() != null) {
                Intent intent = new Intent(requireContext(), BecomeCookActivity.class);
                intent.putExtra(COOK_DATA, result.getContents());
                launcherEmployee.launch(intent);
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentBecomeEmployeeOptionsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecycler();
    }

    private void initRecycler() {
        buildList(new ButtonWithDescriptionModel[]{
                new ButtonWithDescriptionModel(1, R.drawable.ic_worker, R.string.worker, R.string.become_worker_description, launcherWorker),
                new ButtonWithDescriptionModel(2, R.drawable.ic_waiter, R.string.waiter, R.string.become_waiter_description, launcherWaiter),
                new ButtonWithDescriptionModel(3, R.drawable.ic_chef_hat, R.string.cooker, R.string.become_cooker_description, launcherCook)
        });
    }

    private void buildList(ButtonWithDescriptionModel[] models){
        List<ButtonWithDescriptionModel> recyclerList = Arrays.asList(models);
        binding.recyclerView.setAdapter(adapter);
        adapter.submitList(recyclerList);
    }
}