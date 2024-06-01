package com.example.myapplication.presentation.profile.createAccount.createCompanyAccountFragment;

import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_NAME;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_SERVICE;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentApprovalBinding;

public class ApprovalFragment extends Fragment {

    private FragmentApprovalBinding binding;
    private String companyId, companyName, companyService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        companyId = getArguments().getString(COMPANY_ID);
        companyName = getArguments().getString(COMPANY_NAME);
        companyService = getArguments().getString(COMPANY_SERVICE);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentApprovalBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.infoBoxLayout.body.setText(
                getResources().getString(R.string.your_will_receive_notification_when_your_company_is_approved)
        );

        binding.buttonOk.setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });
    }

    private void finish() {
        Intent intent = new Intent();
        intent.putExtra(COMPANY_NAME, companyName);
        intent.putExtra(COMPANY_ID, companyId);
        intent.putExtra(COMPANY_SERVICE, companyService);
        requireActivity().setResult(Activity.RESULT_OK, intent);
        requireActivity().finish();
    }
}