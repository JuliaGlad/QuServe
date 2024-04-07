package com.example.myapplication.presentation.employee.main.notEmployeeYetFragment;

import static com.example.myapplication.presentation.utils.Utils.ANONYMOUS;
import static com.example.myapplication.presentation.utils.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.Utils.APP_STATE;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.Utils.STATE;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.DI;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentNotEmployeeYetBinding;
import com.example.myapplication.presentation.dialogFragments.needAccountDialog.NeedAccountDialogFragment;
import com.example.myapplication.presentation.employee.becomeEmployee.BecomeEmployeeActivity;
import com.example.myapplication.presentation.service.main.ScanCode;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class NotEmployeeYetFragment extends Fragment {

    FragmentNotEmployeeYetBinding binding;
    private ActivityResultLauncher<ScanOptions> becomeEmployeeLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBecomeEmployeeLauncher();
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
                setBecomeEmployeeScanOptions();
            }
        });
    }

    private void initBecomeEmployeeLauncher() {
        becomeEmployeeLauncher = registerForActivityResult(new ScanContract(), result -> {
            if (result.getContents() != null) {
                Intent intent = new Intent(requireContext(), BecomeEmployeeActivity.class);
                intent.putExtra(COMPANY_ID, result.getContents());
                requireActivity().startActivity(intent);
            }
        });
    }

    private void setBecomeEmployeeScanOptions() {
        ScanOptions scanOptions = new ScanOptions();
        scanOptions.setPrompt("Scan Company Qr-Code");
        scanOptions.setBeepEnabled(true);
        scanOptions.setCaptureActivity(ScanCode.class);
        scanOptions.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
        becomeEmployeeLauncher.launch(scanOptions);
    }
}