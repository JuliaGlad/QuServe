package com.example.myapplication.presentation.profile.createAccount.chooseFragment;

import static android.app.Activity.RESULT_OK;
import static com.example.myapplication.presentation.utils.constants.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.constants.Utils.APP_STATE;
import static com.example.myapplication.presentation.utils.constants.Utils.BASIC;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_SERVICE;
import static com.example.myapplication.presentation.utils.constants.Utils.PAGE_1;
import static com.example.myapplication.presentation.utils.constants.Utils.PAGE_KEY;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentChooseBinding;
import com.example.myapplication.presentation.profile.createAccount.createCompanyAccountFragment.CreateCompanyActivity;

public class ChooseFragment extends Fragment {

    private FragmentChooseBinding binding;
    private ActivityResultLauncher<Intent> createCompanyLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLauncher();
    }

    private void initLauncher() {
        createCompanyLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {

                        String state = result.getData().getStringExtra(COMPANY_SERVICE);
                        String companyId = result.getData().getStringExtra(COMPANY_ID);

                        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
                        sharedPreferences.edit().putString(APP_STATE, state).apply();
                        sharedPreferences.edit().putString(COMPANY_ID, companyId).apply();

                        requireActivity().setResult(RESULT_OK);
                        requireActivity().finish();
                    }
                });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initBackButtonPressed();
        binding = FragmentChooseBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initCompanyButton();
        initYourselfButton();
    }

    private void initYourselfButton() {
        binding.forYourselfLayout.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
            sharedPreferences.edit().putString(APP_STATE, BASIC).apply();
            requireActivity().setResult(RESULT_OK);
            requireActivity().finish();
        });
    }

    private void initCompanyButton(){
        binding.forCompanyLayout.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), CreateCompanyActivity.class);
            intent.putExtra(PAGE_KEY, PAGE_1);
            createCompanyLauncher.launch(intent);
        });
    }

    private void initBackButtonPressed(){
        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Log.d("On buttonWithDescription back pressed", "pressed");
            }
        });
    }

}