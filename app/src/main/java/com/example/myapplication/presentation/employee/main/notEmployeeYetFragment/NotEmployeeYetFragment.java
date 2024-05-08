package com.example.myapplication.presentation.employee.main.notEmployeeYetFragment;

import static android.app.Activity.RESULT_OK;
import static com.example.myapplication.presentation.utils.Utils.ADMIN;
import static com.example.myapplication.presentation.utils.Utils.ANONYMOUS;
import static com.example.myapplication.presentation.utils.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.Utils.APP_STATE;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.Utils.EMPLOYEE_ROLE;
import static com.example.myapplication.presentation.utils.Utils.WORKER;
import static com.example.myapplication.presentation.utils.constants.Restaurant.COOK;

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
import androidx.fragment.app.FragmentManager;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentNotEmployeeYetBinding;
import com.example.myapplication.presentation.MainActivity;
import com.example.myapplication.presentation.dialogFragments.needAccountDialog.NeedAccountDialogFragment;
import com.example.myapplication.presentation.employee.main.queueAdminFragment.QueueAdminFragment;
import com.example.myapplication.presentation.employee.main.queueWorkerFragment.QueueWorkerFragment;
import com.example.myapplication.presentation.employee.main.restaurantCook.CookEmployeeFragment;
import com.example.myapplication.presentation.service.main.basicUser.becomeEmployeeOptions.BecomeEmployeeOptionsActivity;

public class NotEmployeeYetFragment extends Fragment {

    private FragmentNotEmployeeYetBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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
            if (getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE).getString(APP_STATE, null).equals(ANONYMOUS)) {
                NeedAccountDialogFragment needAccountDialogFragment = new NeedAccountDialogFragment();
                needAccountDialogFragment.show(getActivity().getSupportFragmentManager(), "NEED_ACCOUNT_DIALOG");
            } else {
                ((MainActivity)requireActivity()).openBecomeEmployeeOptionsActivity();
            }
        });
    }
}