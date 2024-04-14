package com.example.myapplication.presentation.profile.createAccount.createCompanyAccountFragment;

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

    FragmentApprovalBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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
        getActivity().setResult(Activity.RESULT_OK, intent);
        requireActivity().finish();
    }
}