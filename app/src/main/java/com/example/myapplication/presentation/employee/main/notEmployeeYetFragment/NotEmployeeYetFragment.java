package com.example.myapplication.presentation.employee.main.notEmployeeYetFragment;

import static android.app.Activity.RESULT_OK;
import static com.example.myapplication.presentation.utils.constants.Utils.ANONYMOUS;
import static com.example.myapplication.presentation.utils.constants.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.constants.Utils.APP_STATE;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Utils.EMPLOYEE_ROLE;
import static com.example.myapplication.presentation.utils.constants.Utils.WORKER;
import static com.example.myapplication.presentation.utils.constants.Restaurant.COOK;
import static com.example.myapplication.presentation.utils.constants.Restaurant.WAITER;

import android.content.Context;
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
import com.example.myapplication.databinding.FragmentNotEmployeeYetBinding;
import com.example.myapplication.presentation.dialogFragments.needAccountDialog.NeedAccountDialogFragment;
import com.example.myapplication.presentation.employee.main.queueWorkerFragment.QueueWorkerFragment;
import com.example.myapplication.presentation.employee.main.restaurantCook.CookEmployeeFragment;
import com.example.myapplication.presentation.employee.main.restaurantWaiter.startWork.StartWorkFragment;
import com.example.myapplication.presentation.service.basicUser.becomeEmployeeOptions.BecomeEmployeeOptionsActivity;

public class NotEmployeeYetFragment extends Fragment {

    private FragmentNotEmployeeYetBinding binding;
    private ActivityResultLauncher<Intent> launcher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLauncher();
    }

    private void initLauncher() {
        launcher =  registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        String companyId = result.getData().getStringExtra(COMPANY_ID);
                        String role = result.getData().getStringExtra(EMPLOYEE_ROLE);

                        Bundle bundle = new Bundle();
                        bundle.putString(COMPANY_ID, companyId);

                        switch (role){
                            case WORKER:
                                requireActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.employee_nav_container, QueueWorkerFragment.class, bundle)
                                    .commit();
                                break;
                            case WAITER:
                                requireActivity().getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.employee_nav_container, StartWorkFragment.class, bundle)
                                        .commit();
                                break;
                            case COOK:
                                requireActivity().getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.employee_nav_container, CookEmployeeFragment.class, bundle)
                                        .commit();
                                break;
                        }


                    }
                });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNotEmployeeYetBinding.inflate(inflater, container, false);
        binding.infoBox.body.setText(getResources().getString(R.string.join_the_company_and_then_come_back));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initBecomeEmployee();
    }

    private void initBecomeEmployee() {
        binding.buttonBecomeEmployee.setOnClickListener(v -> {
            if (requireActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE).getString(APP_STATE, ANONYMOUS).equals(ANONYMOUS)) {
                NeedAccountDialogFragment needAccountDialogFragment = new NeedAccountDialogFragment();
                needAccountDialogFragment.show(requireActivity().getSupportFragmentManager(), "NEED_ACCOUNT_DIALOG");
            } else {
                Intent intent = new Intent(requireActivity(), BecomeEmployeeOptionsActivity.class);
                launcher.launch(intent);
            }
        });
    }
}